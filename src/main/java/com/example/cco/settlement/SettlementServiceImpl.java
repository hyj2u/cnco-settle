package com.example.cco.settlement;

import com.example.cco.base.DateConverter;
import com.example.cco.base.PageDto;
import com.example.cco.member.MemberEntity;
import com.example.cco.relation.StoreRelRepository;
import com.example.cco.store.StoreEntity;
import com.example.cco.store.StoreRepository;
import com.example.cco.store.StoreSettlementMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SettlementServiceImpl implements SettlementService {
    private final SettlementBaseFieldRepository settlementBaseFieldRepository;
    private final VsettlementTotalRepository vsettlementTotalRepository;
    private final DateConverter dateConverter;
    //private final CustomSettlementRepository customSettlementRepository;

    private final SettlementExtraFieldRepository extraFieldRepository;
    private final SettlementRepository settlementRepository;
    private final SettlementExtraDataRepository extraDataRepository;
    private final SettlementExtraSupplyRepository extraSupplyRepository;
    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;
    private final StoreRelRepository storeRelRepository;

    @Override
    public List<SettlementBaseFieldEntity> getSettlementBaseFieldEntities(String defaultYn) throws Exception {
        if (defaultYn == null) {
            return settlementBaseFieldRepository.findAllByOrderByOrder();
        } else {
            return settlementBaseFieldRepository.findAllByDefaultYnOrderByOrder("N");
        }
    }

    @Override
    public List<VsettlementsForWriteMapping> getSettlementsForWrite(String settlementYmd, String search) throws Exception {
        if (search == null) {
            return vsettlementTotalRepository.findVesttlementsForWriteBySettlementYmd(dateConverter.stringToDate(settlementYmd));
        }
        return vsettlementTotalRepository.findVesttlementsForWriteBySettlementYmdAndStoreNameContaining(dateConverter.stringToDate(settlementYmd), search);
    }

    @Override
    public void updateSettlementsList(List<SettlementsUpdateRequestDto> settlementsUpdateRequestDtos) throws Exception {
        for (SettlementsUpdateRequestDto settlementsUpdateRequestDto : settlementsUpdateRequestDtos) {
          /*  String code="";
            for(int i=0; i<settlementsUpdateRequestDto.getCode().length(); i++){
                if(Character.isUpperCase(settlementsUpdateRequestDto.getCode().charAt(i))){
                    code +="_";
                    code += Character.toLowerCase(settlementsUpdateRequestDto.getCode().charAt(i));
                }else{
                    code += settlementsUpdateRequestDto.getCode().charAt(i);
                }
            }
            log.info(code);*/
            settlementRepository.updateSettlements(settlementsUpdateRequestDto.getCode(), settlementsUpdateRequestDto.getValue(),
                    settlementsUpdateRequestDto.getPkey());
        }

    }

    @Override
    public void downloadSettlementsList(String settlementYmd, HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Date date = dateConverter.stringToDate(settlementYmd);
        int rowNum = 0;
        Cell cell = null;
        Row row = null;
        int cellNum = 0;
        // Header
        for (int i = 0; i < 2; i++) {
            cellNum = 0;
            row = sheet.createRow(rowNum++);
            cell = row.createCell(cellNum++);
            cell.setCellValue("매장코드");
            cell = row.createCell(cellNum++);
            cell.setCellValue("매장명");
            //A0-7
            cell = row.createCell(cellNum++);
            cell.setCellValue("현금");
            cell = row.createCell(cellNum++);
            cell.setCellValue("세금계산서");
            cell = row.createCell(cellNum++);
            cell.setCellValue("신용(체크)카드");
            //a1extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "a1extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            //B7-31
            cell = row.createCell(cellNum++);
            cell.setCellValue("임대료");
            cell = row.createCell(cellNum++);
            cell.setCellValue("관리비");
            cell = row.createCell(cellNum++);
            cell.setCellValue("전기요금");
            cell = row.createCell(cellNum++);
            cell.setCellValue("도시가스");
            cell = row.createCell(cellNum++);
            cell.setCellValue("인터넷");
            cell = row.createCell(cellNum++);
            cell.setCellValue("기타경비");
            //b1extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "b1extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            // cell = row.createCell(cellNum++);
            // cell.setCellValue("수수료");
            cell = row.createCell(cellNum++);
            cell.setCellValue("원부자재(과세)");
            cell = row.createCell(cellNum++);
            cell.setCellValue("기타비품");
            //b3extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "b3extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            cell = row.createCell(cellNum++);
            cell.setCellValue("초도물품대");
            //b4extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "b4extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            //C 31-49
            cell = row.createCell(cellNum++);
            cell.setCellValue("카드수수료");
            cell = row.createCell(cellNum++);
            cell.setCellValue("관리비");
            cell = row.createCell(cellNum++);
            cell.setCellValue("전기요금");
            cell = row.createCell(cellNum++);
            cell.setCellValue("수도요금");
            cell = row.createCell(cellNum++);
            cell.setCellValue("도시가스");
            cell = row.createCell(cellNum++);
            cell.setCellValue("배상책임보험");
            cell = row.createCell(cellNum++);
            cell.setCellValue("기타경비");
            //c1extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "c1extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            cell = row.createCell(cellNum++);
            cell.setCellValue("이자비용");
            //c2extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "c2extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            //D 49-70
            cell = row.createCell(cellNum++);
            cell.setCellValue("매장관리비");
            cell = row.createCell(cellNum++);
            cell.setCellValue("원부자재(면세)");
            cell = row.createCell(cellNum++);
            cell.setCellValue("전월이월금");
            //d1extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "d1extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            cell = row.createCell(cellNum++);
            cell.setCellValue("현금매출");
            //d2extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "d2extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            cell = row.createCell(cellNum++);
            cell.setCellValue("임대료");
            cell = row.createCell(cellNum++);
            cell.setCellValue("초도물품대");
            //d3extra
            for (int j = 1; j <= 5; j++) {
                String extraGb = "d3extra" + j;
                cell = row.createCell(cellNum++);
                cell.setCellValue(makeExcelExtraHeader(extraGb, date));
            }
            if (i == 0) {
                //셀병합
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
                row = sheet.getRow(0);
                cell = row.getCell(0);
                cell.setCellValue("매장");
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 9));
                cell = row.getCell(2);
                cell.setCellValue("상품매출액");
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 33));
                cell = row.getCell(11);
                cell.setCellValue("매출원가");
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 34, 51));
                cell = row.getCell(34);
                cell.setCellValue("기타제경비");
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 52, 72));
                cell = row.getCell(52);
                cell.setCellValue("미지급,선수금 등");
            }
        }

        List<VsettlementTotalEntity> totalEntities = vsettlementTotalRepository.findAllBySettlementYmd(date);

        //Body
        for (int i = 0; i < totalEntities.size(); i++) {
            cellNum = 0;
            VsettlementTotalEntity totalEntity = totalEntities.get(i);
            row = sheet.createRow(rowNum++);
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getStoreCode());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getStoreName());
            //A1
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1cash());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1tax());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1card());

            //a1extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getA1extra5());
            //B1
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1rent());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1maint());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1electricity());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1gas());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1internet());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1etcExpense());

            //b1extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB1extra5());
            //b2
            //   cell = row.createCell(cellNum++);
            // cell.setCellValue(totalEntity.getB2charge());
            //b3
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3material());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3etc());
            //b3extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB3extra5());
            //b4
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB4initProduct());
            //b4extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB4extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB4extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB4extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB4extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getB4extra5());
            //C1
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1cardFee());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1rent());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1electricity());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1water());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1gas());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1insurance());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1etcExpense());
            //c1extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC1extra5());
            //c2
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC2interestCost());
            //c2extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC2extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC2extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC2extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC2extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getC2extra5());
            //D1
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1storeMaint());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1material());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1carryover());
            //d1extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD1extra5());
            //d2
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD2cash());
            //d2extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD2extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD2extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD2extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD2extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD2extra5());
            //d3
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3rent());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3initProduct());
            //d3extra
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3extra1());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3extra2());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3extra3());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3extra4());
            cell = row.createCell(cellNum++);
            cell.setCellValue(totalEntity.getD3extra5());
        }
        //Download
        response.setContentType("/ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=settlements_list.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void uploadSettlementsList(String settlementYmd, MultipartFile file) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Date date = dateConverter.stringToDate(settlementYmd);
        DataFormatter formatter = new DataFormatter();
        extraFieldRepository.deleteAllBySettlementYmd(dateConverter.stringToDate(settlementYmd));
        extraDataRepository.deleteAllBySettlementYmd(dateConverter.stringToDate(settlementYmd));

        //정산서 업데이트
        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) {
            int cellIdx = 0;
            XSSFRow row = worksheet.getRow(i);
            String storeCode = formatter.formatCellValue(row.getCell(cellIdx));
            SettlementEntity settlement = null;
            if (settlementRepository.findByStoreCodeAndSettlementYmd(storeCode, date).isPresent()) {
                settlement = settlementRepository.findByStoreCodeAndSettlementYmd(storeCode, date).get();
            } else {
                settlement = new SettlementEntity();
            }

            cellIdx = 2;
            // settlement.setA1cash(Integer.parseInt(formatter.formatCellValue(row.getCell(cellIdx))));
            settlement.setA1tax(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            cellIdx = cellIdx + 1;
            //settlement.setA1card(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));

            for (int j = 1; j < 6; j++) {
                String extraGb = "a1extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            cellIdx = cellIdx + 1;
            //settlement.setB1rent(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setB1maint(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setB1electricity(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setB1gas(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setB1internet(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setB1etcExpense(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "b1extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            // settlement.setB2charge(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            cellIdx = cellIdx + 1;
            //  settlement.setB3material(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setB3etc(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "b3extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            settlement.setB4initProduct(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "b4extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            //settlement.setC1cardFee(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            cellIdx = cellIdx + 1;
            settlement.setC1rent(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            // settlement.setC1electricity(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            cellIdx = cellIdx + 1;
            settlement.setC1water(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setC1gas(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setC1insurance(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setC1etcExpense(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "c1extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            settlement.setC2interestCost(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "c2extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            settlement.setD1storeMaint(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            cellIdx = cellIdx + 1;
            //settlement.setD1material(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setD1carryover(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "d1extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            cellIdx = cellIdx + 1;
            //settlement.setD2cash(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "d2extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            cellIdx = cellIdx + 1;
            //settlement.setD3rent(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            settlement.setD3initProduct(Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx))));
            for (int j = 1; j < 6; j++) {
                String extraGb = "d3extra" + j;
                cellIdx = updateExtras(worksheet, date, formatter, i, cellIdx, row, storeCode, extraGb);
            }
            settlementRepository.save(settlement);
        }

    }

    @Override
    public SettlementListDto getSettlementsList(String settlementYmd, String search, int page, String activeYn) throws Exception {
        PageDto pageDto = new PageDto();
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<VsettlementsListMapping> vsettlementsListMappings = null;

        if (search == null) {
            if (settlementYmd.length() > 4) {
                vsettlementsListMappings = vsettlementTotalRepository.findVsettlementsListBySettlementYmdAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
                        dateConverter.stringToDate(settlementYmd + "-01"), activeYn, pageable);
            } else {
                vsettlementsListMappings = vsettlementTotalRepository.findVsettlementsListBySettlementYmdBetweenAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
                        dateConverter.stringToDate(settlementYmd + "-01-01"), dateConverter.stringToDate(settlementYmd + "-12-01"), activeYn
                        , pageable
                );
            }

        } else {
            if (settlementYmd.length() > 4) {
                vsettlementsListMappings =
                        vsettlementTotalRepository.findVsettlementsListBySettlementYmdAndStoreNameContainingAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
                                dateConverter.stringToDate(settlementYmd + "-01"), search, activeYn,pageable);
            } else {
                vsettlementsListMappings = vsettlementTotalRepository.findVsettlementsListBySettlementYmdBetweenAndStoreNameContainingAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
                        dateConverter.stringToDate(settlementYmd + "-01-01"), dateConverter.stringToDate(settlementYmd + "-12-01"), search, activeYn, pageable
                );
            }

        }
        pageDto.setTotalPage(vsettlementsListMappings.getTotalPages());
        pageDto.setNumber(page);
        pageDto.setTotalElements(vsettlementsListMappings.getTotalElements());
        SettlementListDto settlementListDto = new SettlementListDto();
        settlementListDto.setSettlements(vsettlementsListMappings.getContent());
        settlementListDto.setPage(pageDto);
        return settlementListDto;
    }

    @Override
    public SettlementDetailDto getSettlementDetail(Long pkey) throws Exception {
        SettlementDetailDto settlementDetailDto = new SettlementDetailDto();
        VsettlementTotalEntity totalEntity = vsettlementTotalRepository.findById(pkey).get();

        settlementDetailDto.setSettlementTotal(totalEntity);
        settlementDetailDto.setExtraSupplies(extraSupplyRepository.findAllBySettlementYmdAndStoreCode(totalEntity.getSettlementYmd(), totalEntity.getStoreCode()));
        return settlementDetailDto;
    }

    @Override
    public SettlementDetailDto getSecretOrderSettlment(String code, String settlementYmd) throws Exception {
        SettlementDetailDto settlementDetailDto = new SettlementDetailDto();
        if (storeRelRepository.findByScrapGbAndRelCodeAndEndTimestampIsNull("blueorder", code).isPresent()) {
            String storeCode = storeRelRepository.findByScrapGbAndRelCodeAndEndTimestampIsNull("blueorder", code).get().getStoreCode();

            VsettlementTotalEntity totalEntity = vsettlementTotalRepository.findByStoreCodeAndSettlementYmd(storeCode, dateConverter.stringToDate(settlementYmd)).get();
            settlementDetailDto.setSettlementTotal(totalEntity);
            settlementDetailDto.setExtraSupplies(extraSupplyRepository.findAllBySettlementYmdAndStoreCode(totalEntity.getSettlementYmd(), totalEntity.getStoreCode()));
        }
/*
        String storeCode = settlementRepository.findById(Long.valueOf(code)).get().getStoreCode();
        if (vsettlementTotalRepository.findByStoreCodeAndSettlementYmd(storeCode, dateConverter.stringToDate(settlementYmd)).isPresent()) {
            VsettlementTotalEntity totalEntity = vsettlementTotalRepository.findByStoreCodeAndSettlementYmd(storeCode, dateConverter.stringToDate(settlementYmd)).get();
            settlementDetailDto.setSettlementTotal(totalEntity);
            settlementDetailDto.setExtraSupplies(extraSupplyRepository.findAllBySettlementYmdAndStoreCode(totalEntity.getSettlementYmd(), totalEntity.getStoreCode()));
        }
        */
        return settlementDetailDto;
    }

    @Override
    public void updateSettlementDetail(SettlementDetailDto detailDto) throws Exception {

        VsettlementTotalEntity totalEntity = detailDto.getSettlementTotal();
        SettlementEntity settlement = null;
        log.info(objectMapper.writeValueAsString(totalEntity));
        if (totalEntity.getPkey() != null) {
            settlement = settlementRepository.findById(totalEntity.getPkey()).get();
        } else {
            settlement = new SettlementEntity();
        }
        settlement.setStoreCode(totalEntity.getStoreCode());
        settlement.setSettlementYmd(totalEntity.getSettlementYmd());
        settlement.setClosedYn(totalEntity.getClosedYn());
        settlement.setA1cash(totalEntity.getA1cash());
        settlement.setA1card(totalEntity.getA1card());
        settlement.setA1tax(totalEntity.getA1tax());
        settlement.setB1rent(totalEntity.getB1rent());
        settlement.setB1maint(totalEntity.getB1maint());
        settlement.setB1electricity(totalEntity.getB1electricity());
        settlement.setB1gas(totalEntity.getB1gas());
        settlement.setB1internet(totalEntity.getB1internet());
        settlement.setB2charge(totalEntity.getB2charge());
        settlement.setB3material(totalEntity.getB3material());
        //settlement.setB3etc(totalEntity.getB3etc());
        settlement.setB4initProduct(totalEntity.getB4initProduct());
        settlement.setB4donation(totalEntity.getB4donation());
        settlement.setC1cardFee(totalEntity.getC1cardFee());
        settlement.setC1rent(totalEntity.getC1rent());
        settlement.setC1electricity(totalEntity.getC1electricity());
        settlement.setC1water(totalEntity.getC1water());
        settlement.setC1gas(totalEntity.getC1gas());
        settlement.setC1insurance(totalEntity.getC1insurance());
        settlement.setC1etcExpense(totalEntity.getC1etcExpense());
        settlement.setC2interestCost(totalEntity.getC2interestCost());
        settlement.setD1storeMaint(totalEntity.getD1storeMaint());
        settlement.setD1material(totalEntity.getD1material());
        settlement.setD1carryover(totalEntity.getD1carryover());
        settlement.setD2cash(totalEntity.getD2cash());
        settlement.setD3rent(totalEntity.getD3rent());
        settlement.setD3initProduct(totalEntity.getD3initProduct());
        settlement.setB1etcExpense(totalEntity.getB1etcExpense());
        settlement.setA1cashNote(totalEntity.getA1cashNote());
        settlement.setA1cardNote(totalEntity.getA1cardNote());
        settlement.setA1taxNote(totalEntity.getA1taxNote());
        settlement.setB1rentNote(totalEntity.getB1rentNote());
        settlement.setB1maintNote(totalEntity.getB1maintNote());
        settlement.setB1electricityNote(totalEntity.getB1electricityNote());
        settlement.setB1gasNote(totalEntity.getB1gasNote());
        settlement.setB1internetNote(totalEntity.getB1internetNote());
        settlement.setB2chargeNote(totalEntity.getB2chargeNote());
        settlement.setB3materialNote(totalEntity.getB3materialNote());
        settlement.setB3etcNote(totalEntity.getB3etcNote());
        settlement.setB1etcExpenseNote(totalEntity.getB1etcExpenseNote());
        settlement.setB4initProductNote(totalEntity.getB4initProductNote());
        settlement.setB4donationNote(totalEntity.getB4donationNote());
        settlement.setC1cardFeeNote(totalEntity.getC1cardFeeNote());
        settlement.setC1rentNote(totalEntity.getC1rentNote());
        settlement.setC1electricityNote(totalEntity.getC1electricityNote());
        settlement.setC1waterNote(totalEntity.getC1waterNote());
        settlement.setC1gasNote(totalEntity.getC1gasNote());
        settlement.setC1insuranceNote(totalEntity.getC1insuranceNote());
        settlement.setC1etcExpenseNote(totalEntity.getC1etcExpenseNote());
        settlement.setC2interestCostNote(totalEntity.getC2interestCostNote());
        settlement.setD1storeMaintNote(totalEntity.getD1storeMaintNote());
        settlement.setD1carryoverNote(totalEntity.getD1carryoverNote());
        settlement.setD2cashNote(totalEntity.getD2cashNote());
        settlement.setD3rentNote(totalEntity.getD3rentNote());
        settlement.setD3initProductNote(totalEntity.getD3initProductNote());

        extraFieldRepository.deleteAllBySettlementYmd(totalEntity.getSettlementYmd());
        extraDataRepository.deleteAllBySettlementYmdAndStoreCode(totalEntity.getSettlementYmd(), totalEntity.getStoreCode());

        if (!(totalEntity.getA1extra1name() == null || totalEntity.getA1extra1name().equals(""))) {
            String gb = "a1extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getA1extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getA1extra1note());
            dataEntity.setAmount(totalEntity.getA1extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getA1extra2name() == null || totalEntity.getA1extra2name().equals(""))) {
            String gb = "a1extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getA1extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getA1extra2note());
            dataEntity.setAmount(totalEntity.getA1extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getA1extra3name() == null || totalEntity.getA1extra3name().equals(""))) {
            String gb = "a1extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getA1extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getA1extra3note());
            dataEntity.setAmount(totalEntity.getA1extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getA1extra4name() == null || totalEntity.getA1extra4name().equals(""))) {
            String gb = "a1extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getA1extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getA1extra4note());
            dataEntity.setAmount(totalEntity.getA1extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getA1extra5name() == null || totalEntity.getA1extra5name().equals(""))) {
            String gb = "a1extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getA1extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getA1extra5note());
            dataEntity.setAmount(totalEntity.getA1extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB1extra1name() == null || totalEntity.getB1extra1name().equals(""))) {
            String gb = "b1extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB1extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB1extra1note());
            dataEntity.setAmount(totalEntity.getB1extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB1extra2name() == null || totalEntity.getB1extra2name().equals(""))) {
            String gb = "b1extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB1extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB1extra2note());
            dataEntity.setAmount(totalEntity.getB1extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB1extra3name() == null || totalEntity.getB1extra3name().equals(""))) {
            String gb = "b1extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB1extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB1extra3note());
            dataEntity.setAmount(totalEntity.getB1extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB1extra4name() == null || totalEntity.getB1extra4name().equals(""))) {
            String gb = "b1extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB1extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB1extra4note());
            dataEntity.setAmount(totalEntity.getB1extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB1extra5name() == null || totalEntity.getB1extra5name().equals(""))) {
            String gb = "b1extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB1extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB1extra5note());
            dataEntity.setAmount(totalEntity.getB1extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB3extra1name() == null || totalEntity.getB3extra1name().equals(""))) {
            String gb = "b3extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB3extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB3extra1note());
            dataEntity.setAmount(totalEntity.getB3extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB3extra2name() == null || totalEntity.getB3extra2name().equals(""))) {
            String gb = "b3extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB3extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB3extra2note());
            dataEntity.setAmount(totalEntity.getB3extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB3extra3name() == null || totalEntity.getB3extra3name().equals(""))) {
            String gb = "b3extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB3extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB3extra3note());
            dataEntity.setAmount(totalEntity.getB3extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB3extra4name() == null || totalEntity.getB3extra4name().equals(""))) {
            String gb = "b3extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB3extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB3extra4note());
            dataEntity.setAmount(totalEntity.getB3extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB3extra5name() == null || totalEntity.getB3extra5name().equals(""))) {
            String gb = "b3extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB3extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB3extra5note());
            dataEntity.setAmount(totalEntity.getB3extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB4extra1name() == null || totalEntity.getB4extra1name().equals(""))) {
            String gb = "b4extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB4extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB4extra1note());
            dataEntity.setAmount(totalEntity.getB4extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB4extra2name() == null || totalEntity.getB4extra2name().equals(""))) {
            String gb = "b4extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB4extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB4extra2note());
            dataEntity.setAmount(totalEntity.getB4extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB4extra3name() == null || totalEntity.getB4extra3name().equals(""))) {
            String gb = "b4extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB4extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB4extra3note());
            dataEntity.setAmount(totalEntity.getB4extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB4extra4name() == null || totalEntity.getB4extra4name().equals(""))) {
            String gb = "b4extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB4extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB4extra4note());
            dataEntity.setAmount(totalEntity.getB4extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getB4extra5name() == null || totalEntity.getB4extra5name().equals(""))) {
            String gb = "b4extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getB4extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getB4extra5note());
            dataEntity.setAmount(totalEntity.getB4extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC1extra1name() == null || totalEntity.getC1extra1name().equals(""))) {
            String gb = "c1extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC1extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC1extra1note());
            dataEntity.setAmount(totalEntity.getC1extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC1extra2name() == null || totalEntity.getC1extra2name().equals(""))) {
            String gb = "c1extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC1extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC1extra2note());
            dataEntity.setAmount(totalEntity.getC1extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC1extra3name() == null || totalEntity.getC1extra3name().equals(""))) {
            String gb = "c1extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC1extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC1extra3note());
            dataEntity.setAmount(totalEntity.getC1extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC1extra4name() == null || totalEntity.getC1extra4name().equals(""))) {
            String gb = "c1extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC1extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC1extra4note());
            dataEntity.setAmount(totalEntity.getC1extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC1extra5name() == null || totalEntity.getC1extra5name().equals(""))) {
            String gb = "c1extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC1extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC1extra5note());
            dataEntity.setAmount(totalEntity.getC1extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC2extra1name() == null || totalEntity.getC2extra1name().equals(""))) {
            String gb = "c2extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC2extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC2extra1note());
            dataEntity.setAmount(totalEntity.getC2extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC2extra2name() == null || totalEntity.getC2extra2name().equals(""))) {
            String gb = "c2extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC2extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC2extra2note());
            dataEntity.setAmount(totalEntity.getC2extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC2extra3name() == null || totalEntity.getC2extra3name().equals(""))) {
            String gb = "c2extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC2extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC2extra3note());
            dataEntity.setAmount(totalEntity.getC2extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC2extra4name() == null || totalEntity.getC2extra4name().equals(""))) {
            String gb = "c2extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC2extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC2extra4note());
            dataEntity.setAmount(totalEntity.getC2extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getC2extra5name() == null || totalEntity.getC2extra5name().equals(""))) {
            String gb = "c2extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getC2extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getC2extra5note());
            dataEntity.setAmount(totalEntity.getC2extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD1extra1name() == null || totalEntity.getD1extra1name().equals(""))) {
            String gb = "d1extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD1extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD1extra1note());
            dataEntity.setAmount(totalEntity.getD1extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD1extra2name() == null || totalEntity.getD1extra2name().equals(""))) {
            String gb = "d1extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD1extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD1extra2note());
            dataEntity.setAmount(totalEntity.getD1extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD1extra3name() == null || totalEntity.getD1extra3name().equals(""))) {
            String gb = "d1extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD1extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD1extra3note());
            dataEntity.setAmount(totalEntity.getD1extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD1extra4name() == null || totalEntity.getD1extra4name().equals(""))) {
            String gb = "d1extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD1extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD1extra4note());
            dataEntity.setAmount(totalEntity.getD1extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD1extra5name() == null || totalEntity.getD1extra5name().equals(""))) {
            String gb = "d1extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD1extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD1extra5note());
            dataEntity.setAmount(totalEntity.getD1extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD2extra1name() == null || totalEntity.getD2extra1name().equals(""))) {
            String gb = "d2extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD2extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD2extra1note());
            dataEntity.setAmount(totalEntity.getD2extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD2extra2name() == null || totalEntity.getD2extra2name().equals(""))) {
            String gb = "d2extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD2extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD2extra2note());
            dataEntity.setAmount(totalEntity.getD2extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD2extra3name() == null || totalEntity.getD2extra3name().equals(""))) {
            String gb = "d2extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD2extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD2extra3note());
            dataEntity.setAmount(totalEntity.getD2extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD2extra4name() == null || totalEntity.getD2extra4name().equals(""))) {
            String gb = "d2extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD2extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD2extra4note());
            dataEntity.setAmount(totalEntity.getD2extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD2extra5name() == null || totalEntity.getD2extra5name().equals(""))) {
            String gb = "d2extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD2extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD2extra5note());
            dataEntity.setAmount(totalEntity.getD2extra5());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD3extra1name() == null || totalEntity.getD3extra1name().equals(""))) {
            String gb = "d3extra1";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD3extra1name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD3extra1note());
            dataEntity.setAmount(totalEntity.getD3extra1());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD3extra2name() == null || totalEntity.getD3extra2name().equals(""))) {
            String gb = "d3extra2";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD3extra2name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD3extra2note());
            dataEntity.setAmount(totalEntity.getD3extra2());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD3extra3name() == null || totalEntity.getD3extra3name().equals(""))) {
            String gb = "d3extra3";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD3extra3name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD3extra3note());
            dataEntity.setAmount(totalEntity.getD3extra3());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD3extra4name() == null || totalEntity.getD3extra4name().equals(""))) {
            String gb = "d3extra4";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD3extra4name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD3extra4note());
            dataEntity.setAmount(totalEntity.getD3extra4());
            extraDataRepository.save(dataEntity);
        }
        if (!(totalEntity.getD3extra5name() == null || totalEntity.getD3extra5name().equals(""))) {
            String gb = "d3extra5";
            SettlementExtraFieldEntity fieldEntity = saveExtraField(totalEntity, gb);
            fieldEntity.setFieldName(totalEntity.getD3extra5name());
            extraFieldRepository.save(fieldEntity);
            SettlementExtraDataEntity dataEntity = saveExtraData(totalEntity, gb);
            dataEntity.setNote(totalEntity.getD3extra5note());
            dataEntity.setAmount(totalEntity.getD3extra5());
            extraDataRepository.save(dataEntity);
        }


        extraSupplyRepository.deleteAllBySettlementYmdAndStoreCode(settlement.getSettlementYmd(), settlement.getStoreCode());
        int extra = 0;

        for (SettlementExtraSupplyEntity supplyEntity : detailDto.getExtraSupplies()) {
            SettlementExtraSupplyEntity entity = new SettlementExtraSupplyEntity();
            entity.setSettlementYmd(settlement.getSettlementYmd());
            entity.setStoreCode(settlement.getStoreCode());
            entity.setQuantity(supplyEntity.getQuantity());
            entity.setUnitPrice(supplyEntity.getUnitPrice());
            entity.setItem(supplyEntity.getItem());
            entity.setNote(supplyEntity.getNote());

            extra = extra + (int) Math.ceil((supplyEntity.getQuantity() * supplyEntity.getUnitPrice()));
            extraSupplyRepository.save(entity);
        }
        //settlement.setB3etc(extra);
        settlementRepository.save(settlement);

    }

    @Override
    public List<StoreSettlementMapping> getSettlementStores(String settlementYmd) throws Exception {
        return storeRepository.findAllWithNoSettlements(dateConverter.stringToDate(settlementYmd));
    }

    @Override
    public void finishSettlement(String settlementYmd) throws Exception {
        for (SettlementEntity settlement : settlementRepository.findAllBySettlementYmd(dateConverter.stringToDate(settlementYmd))) {
            settlement.setClosedYn("Y");
            settlementRepository.save(settlement);
        }
    }

    @Override
    public void changeCloseStatus(Long pkey, String closedYn) throws Exception {
        SettlementEntity settlement = settlementRepository.findById(pkey).get();
        settlement.setClosedYn(closedYn);
        settlementRepository.save(settlement);
    }

    @Override
    public SettlementListDto getSettlementsListForFranchise(String settlementYmd, String search, int page, MemberEntity member) throws Exception {
        PageDto pageDto = new PageDto();
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<VsettlementsListMapping> vsettlementsListMappings = null;
        if (search == null) {
            if (settlementYmd.length() > 4) {
                vsettlementsListMappings = vsettlementTotalRepository.findVsettlementsListBySettlementYmdAndPhoneAndClosedYnOrderBySettlementYmdDesc(
                        dateConverter.stringToDate(settlementYmd + "-01"), member.getPhone(), "Y", pageable);
            } else {
                vsettlementsListMappings = vsettlementTotalRepository.findVsettlementsListBySettlementYmdBetweenAndPhoneAndClosedYnOrderBySettlementYmdDesc(
                        dateConverter.stringToDate(settlementYmd + "-01-01"), dateConverter.stringToDate(settlementYmd + "-12-01"), member.getPhone(),
                        "Y", pageable
                );
            }

        } else {
            if (settlementYmd.length() > 4) {
                vsettlementsListMappings =
                        vsettlementTotalRepository.findVsettlementsListBySettlementYmdAndPhoneAndClosedYnAndStoreNameContainingOrderBySettlementYmdDesc(
                                dateConverter.stringToDate(settlementYmd + "-01"), member.getPhone(), "Y", search, pageable);
            } else {
                vsettlementsListMappings =
                        vsettlementTotalRepository.findVsettlementsListBySettlementYmdBetweenAndPhoneAndClosedYnAndStoreNameContainingOrderBySettlementYmdDesc(
                                dateConverter.stringToDate(settlementYmd + "-01-01"), dateConverter.stringToDate(settlementYmd + "-12-01"), member.getPhone(),
                                "Y", search, pageable
                        );
            }

        }
        pageDto.setTotalPage(vsettlementsListMappings.getTotalPages());
        pageDto.setNumber(page);
        pageDto.setTotalElements(vsettlementsListMappings.getTotalElements());
        SettlementListDto settlementListDto = new SettlementListDto();
        settlementListDto.setSettlements(vsettlementsListMappings.getContent());
        settlementListDto.setPage(pageDto);
        return settlementListDto;
    }

    private SettlementExtraFieldEntity saveExtraField(VsettlementTotalEntity totalEntity, String gb) {
        SettlementExtraFieldEntity fieldEntity = new SettlementExtraFieldEntity();
      /*  if (extraFieldRepository.findByExtraGbAndSettlementYmd(gb, totalEntity.getSettlementYmd()).isPresent()) {
            fieldEntity = extraFieldRepository.findByExtraGbAndSettlementYmd(gb, totalEntity.getSettlementYmd()).get();
        }*/
        fieldEntity.setSettlementYmd(totalEntity.getSettlementYmd());
        fieldEntity.setExtraGb(gb);
        return fieldEntity;
    }

    private SettlementExtraDataEntity saveExtraData(VsettlementTotalEntity totalEntity, String gb) {
        SettlementExtraDataEntity dataEntity = new SettlementExtraDataEntity();
       /* if (extraDataRepository.findBySettlementYmdAndExtraFieldAndStoreCode(totalEntity.getSettlementYmd(), gb, totalEntity.getStoreCode()).isPresent()) {
            dataEntity = extraDataRepository.findBySettlementYmdAndExtraFieldAndStoreCode(totalEntity.getSettlementYmd(), gb,
                    totalEntity.getStoreCode()).get();
        }*/
        dataEntity.setStoreCode(totalEntity.getStoreCode());
        dataEntity.setExtraField(gb);
        dataEntity.setSettlementYmd(totalEntity.getSettlementYmd());
        return dataEntity;
    }


    private int updateExtras(XSSFSheet worksheet, Date date, DataFormatter formatter, int i, int cellIdx, XSSFRow row, String storeCode, String extraGb) {

        XSSFRow fieldRow = worksheet.getRow(1);
        String field = formatter.formatCellValue(fieldRow.getCell(cellIdx + 1));
        if (i == 2) {
            if (!field.equals("기타")) {
                SettlementExtraFieldEntity fieldEntity = new SettlementExtraFieldEntity();
                fieldEntity.setSettlementYmd(date);
                fieldEntity.setExtraGb(extraGb);

                fieldEntity.setFieldName(field);
                extraFieldRepository.save(fieldEntity);
            }
        }

        int value = Integer.parseInt(formatter.formatCellValue(row.getCell(++cellIdx)));
        if (!field.equals("기타") && value != 0) {
            log.info(field + "/" + extraGb + "/" + value);
            SettlementExtraDataEntity dataEntity = new SettlementExtraDataEntity();
            dataEntity.setStoreCode(storeCode);
            dataEntity.setExtraField(extraGb);
            dataEntity.setSettlementYmd(date);

            dataEntity.setAmount(value);
            extraDataRepository.save(dataEntity);


        }

        return cellIdx;
    }

    private String makeExcelExtraHeader(String extraGb, Date date) {

        if (extraFieldRepository.findByExtraGbAndSettlementYmd(extraGb, date).isPresent()) {
            return extraFieldRepository.findByExtraGbAndSettlementYmd(extraGb, date).get().getFieldName();
        } else {
            return "기타";
        }
    }
}

