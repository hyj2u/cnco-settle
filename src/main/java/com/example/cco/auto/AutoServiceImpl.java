package com.example.cco.auto;

import com.example.cco.base.DateConverter;
import com.example.cco.exception.ExcelFormatException;
import com.example.cco.settlement.SettlementEntity;
import com.example.cco.settlement.SettlementRepository;
import com.example.cco.settlement.VsettlementTotalEntity;
import com.example.cco.settlement.VsettlementTotalRepository;
import com.example.cco.store.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AutoServiceImpl implements AutoService {

    private final StoreRepository storeRepository;
    private final SrcKepcoRepository srcKepcoRepository;

    private final DateConverter dateConverter;

    private final SettlementRepository settlementRepository;
    private final AutoSummaryRepository autoSummaryRepository;

    private final AutoTotalRepository autoTotalRepository;
    private final StoreContactRepository storeContactRepository;
    private final VsettlementTotalRepository vsettlementTotalRepository;

    @Override
    public List<AutoTotalEntity> getAutoSettles(String settlementYmd) throws Exception {
       //List<StoreEntity> storeEntities = storeRepository.findAllByActiveYn("Y");
        List<AutoTotalEntity> autoResponse = autoTotalRepository.findAllByOrderByBrandCdAscSettlementCdAscStoreNameAsc();
        //Date date = dateConverter.stringToDate(settlementYmd);
        return autoResponse;
    }

    @Override
    public void uploadKepcoExcel(MultipartFile file) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 3; i < worksheet.getPhysicalNumberOfRows(); i++) {
            DataFormatter formatter = new DataFormatter();
            XSSFRow row = worksheet.getRow(i);
            if(i==3){
                if(!formatter.formatCellValue(row.getCell(1)).equals("고객번호")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
                if(!formatter.formatCellValue(row.getCell(4)).equals("주소")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
                if(!formatter.formatCellValue(row.getCell(7)).equals("청구년월")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
                if(!formatter.formatCellValue(row.getCell(12)).startsWith("사용량")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
                if(!formatter.formatCellValue(row.getCell(14)).equals("당월요금")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
                if(!formatter.formatCellValue(row.getCell(15)).equals("TV수신료")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
                if(!formatter.formatCellValue(row.getCell(16)).startsWith("청구요금")){
                    throw new ExcelFormatException("엑셀 업로드 양식이 다릅니다.");
                }
            }else{
                String storeCode = formatter.formatCellValue(row.getCell(1));
                String address = formatter.formatCellValue(row.getCell(4));
                String date = formatter.formatCellValue(row.getCell(7));
                Date paymentYmd = dateConverter.stringToDateKepco(date);
                Integer elecUsage = Integer.parseInt(formatter.formatCellValue(row.getCell(12)).replace(",", ""));
                Integer elecCharge = Integer.parseInt(formatter.formatCellValue(row.getCell(14)).replace(",", ""));
                Integer tvCharge = Integer.parseInt(formatter.formatCellValue(row.getCell(15)).replace(",", ""));
                Integer totalCharge = Integer.parseInt(formatter.formatCellValue(row.getCell(16)).replace(",", ""));

                if(srcKepcoRepository.findByStoreCodeAndPaymentYmdAndEndTimestampIsNull(storeCode, paymentYmd).isPresent()) {
                    SrcKepcoEntity oldKepco = srcKepcoRepository.findByStoreCodeAndPaymentYmdAndEndTimestampIsNull(storeCode, paymentYmd).get();
                    oldKepco.setEndTimestamp(LocalDateTime.now());
                    srcKepcoRepository.save(oldKepco);

                }
                SrcKepcoEntity srcKepcoEntity = new SrcKepcoEntity();
                srcKepcoEntity.setStoreCode(storeCode);
                srcKepcoEntity.setPaymentYmd(paymentYmd);
                srcKepcoEntity.setAddress(address);
                srcKepcoEntity.setElecCharge(elecCharge);
                srcKepcoEntity.setElecUsage(elecUsage);
                srcKepcoEntity.setTotalCharge(totalCharge);
                srcKepcoEntity.setTvCharge(tvCharge);
                srcKepcoEntity.setInsertTimestamp(LocalDateTime.now());
                srcKepcoRepository.save(srcKepcoEntity);
            }
        }
    }

    @Override
    @Async
    @Transactional
    public void updateSettlement(String settlementYmd) throws Exception {
        List<StoreMapping> storeEntities = storeRepository.findAllByActiveYnAndBrandCdIsNot("Y", "9");
        Date date = dateConverter.stringToDate(settlementYmd);
        for (StoreMapping store : storeEntities) {
            SettlementEntity settlement = null;
            if (settlementRepository.findByStoreCodeAndSettlementYmd(store.getStoreCode(), date).isPresent()) {
                settlement = settlementRepository.findByStoreCodeAndSettlementYmd(store.getStoreCode(), date).get();
            } else {
                settlement = new SettlementEntity();
                settlement.setStoreCode(store.getStoreCode());
                settlement.setSettlementYmd(date);
                settlement.setClosedYn("N");
            }
            if(store.getPrepaidRent()!= null && store.getPrepaidMonth()!=null){
                Long rent = store.getPrepaidRent()/store.getPrepaidMonth();
                settlement.setD3rent(rent.intValue());
            }


            Integer cardFee = 0;
            if (autoSummaryRepository.findById(store.getStoreCode()).isPresent()) {
                AutoSummaryEntity autoSummaryEntity = autoSummaryRepository.findById(store.getStoreCode()).get();
                settlement.setA1card(autoSummaryEntity.getCard());
                settlement.setSrcA1card(autoSummaryEntity.getCard());
                settlement.setA1cash(autoSummaryEntity.getCash());
                settlement.setD2cash(autoSummaryEntity.getCash());
                settlement.setSrcA1cash(autoSummaryEntity.getCash());
                //카드 수수료 반영
                cardFee = (int) Math.ceil(autoSummaryEntity.getCard() * 0.033);
                settlement.setB1electricity(autoSummaryEntity.getElecCharge());
                settlement.setSrcB1electricity(autoSummaryEntity.getElecCharge());
                if (cardFee != 0) {
                    settlement.setC1cardFee(cardFee);
                    settlement.setSrcC1cardFee(cardFee);
                }
                settlement.setB3material(autoSummaryEntity.getTaxation());
                settlement.setSrcB3material(autoSummaryEntity.getTaxation());
                settlement.setD1material(autoSummaryEntity.getTaxExemption());
                settlement.setSrcD1material(autoSummaryEntity.getTaxExemption());

            }

            //지난달 내역 불러오기
            if(settlementRepository.findByStoreCodeAndSettlementYmd(store.getStoreCode(), dateConverter.getBeforeMonth(date)).isPresent()){
                SettlementEntity beforeSettlement = settlementRepository.findByStoreCodeAndSettlementYmd(store.getStoreCode(),
                        dateConverter.getBeforeMonth(date)).get();
                //settlement.setB1rent(beforeSettlement.getB1rent());
                settlement.setB1internet(beforeSettlement.getB1internet());
                settlement.setC1insurance(beforeSettlement.getC1insurance());
                settlement.setD1storeMaint(beforeSettlement.getD1storeMaint());
                //기부금 항목 추가
                if(store.getDonationYn()!=null) {
                    if (store.getDonationYn().equals("Y")) {
                        Integer total = beforeSettlement.getA1card() + beforeSettlement.getA1cash() + beforeSettlement.getA1tax();
                        settlement.setB4donation((int) (Math.floor((total * 0.0025) * 1.1)));
                    }
                }


            }
            //계약정보를 확인 후, 임대료 부분 반영
            if(storeContactRepository.findTopByStoreCodeAndContractStartYmdLessThanEqualAndContractEndYmdGreaterThanEqualOrderByPkey(
                    store.getStoreCode(), date, date).isPresent()){
                StoreContractEntity storeContractEntity =
                        storeContactRepository.findTopByStoreCodeAndContractStartYmdLessThanEqualAndContractEndYmdGreaterThanEqualOrderByPkey(
                                store.getStoreCode(), date, date
                        ).get();
                log.info("storeCode =" + store.getStoreCode()+"/ rent="+ storeContractEntity.getRent());
                settlement.setB1rent(storeContractEntity.getRent());
            }
            //전월 마이너스 금액 -> 이월금
            if(vsettlementTotalRepository.findByStoreCodeAndSettlementYmd(store.getStoreCode(), dateConverter.getBeforeMonth(date)).isPresent()){
                VsettlementTotalEntity vsettlementTotalEntity = vsettlementTotalRepository.findByStoreCodeAndSettlementYmd(store.getStoreCode(), dateConverter
                        .getBeforeMonth(date)).get();
                if(vsettlementTotalEntity.getDepositAmount()<0){
                    settlement.setD1carryover((vsettlementTotalEntity.getDepositAmount().intValue())*-1);
                }
            }

            settlementRepository.save(settlement);
        }
        log.info("async settlement job finished");

    }
}

