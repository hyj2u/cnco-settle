package com.example.cco.summary;

import com.example.cco.base.DateConverter;
import com.example.cco.relation.RelationEntity;
import com.example.cco.settlement.VsettlementTotalRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
@RequiredArgsConstructor
@Service
public class SummaryServiceImpl implements  SummaryService{
    private final VsettlementTotalRepository vsettlementTotalRepository;
    private final DateConverter dateConverter;
    private final VsettlementSummaryRepository vsettlementSummaryRepository;
    private final VsettlementSummaryActiveRepository vsettlementSummaryActiveRepository;
    @Override
    public List<VsettlementsDepositMapping> getDepositList(String settlementYmd) throws Exception {
        return vsettlementTotalRepository.findAllDepositBySettlementYmdOrderByBrandCdAscSettlementCdAscStoreNameAsc(dateConverter.stringToDate(settlementYmd));
    }

    @Override
    public List<VsettlementsTaxMapping> getTaxList(String settlementYmd) throws Exception {
        return vsettlementTotalRepository.findAllTaxBySettlementYmdOrderByBrandCdAscSettlementCdAscStoreNameAsc(dateConverter.stringToDate(settlementYmd));
    }

    @Override
    public VsettlementSummaryEntity getMonSettlement(String settlementYmd) throws Exception {
        return vsettlementSummaryRepository.findBySettlementYmd(dateConverter.stringToDate(settlementYmd));
    }

    @Override
    public List<VsettlementsSumMapping> getSummaryTotal(String year, String activeYn) throws Exception {
        if(activeYn.equals("Y")){
            return vsettlementSummaryActiveRepository.findAllBySettlementYmdBetweenOrderBySettlementYmdDesc(dateConverter.stringToYearStart(year), dateConverter.stringToYearEnd(year));
        }
        return vsettlementSummaryRepository.findAllBySettlementYmdBetweenOrderBySettlementYmdDesc(dateConverter.stringToYearStart(year), dateConverter.stringToYearEnd(year));
    }

    @Override
    public void downDepositList(String settlementYmd, HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowNum = 0;
        Cell cell = null;
        Row row = null;
        List<VsettlementsDepositMapping> vsettlementsDepositMappings =
                vsettlementTotalRepository.findAllDepositBySettlementYmdOrderByBrandCdAscSettlementCdAscStoreNameAsc(dateConverter.stringToDate(settlementYmd));
        //Header
        int cellNum = 0;
        row = sheet.createRow(rowNum++);
        cell = row.createCell(cellNum++);
        cell.setCellValue("브랜드");
        cell = row.createCell(cellNum++);
        cell.setCellValue("매장명");
        cell = row.createCell(cellNum++);
        cell.setCellValue("점주");
        cell = row.createCell(cellNum++);
        cell.setCellValue("금액");
        cell = row.createCell(cellNum++);
        cell.setCellValue("은행");
        cell = row.createCell(cellNum++);
        cell.setCellValue("예금주");
        cell = row.createCell(cellNum++);
        cell.setCellValue("계좌번호");
        //Body
        for (int i = 0; i < vsettlementsDepositMappings.size(); i++) {
            cellNum = 0;
            VsettlementsDepositMapping depositMapping = vsettlementsDepositMappings.get(i);
            row = sheet.createRow(rowNum++);
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getBrandName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getStoreName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getOwner());
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getDepositAmount());
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getBank());
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getOwner());
            cell = row.createCell(cellNum++);
            cell.setCellValue(depositMapping.getBankAccountNumber());

        }
        //Download
        response.setContentType("/ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=deposit_list.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void downTaxList(String settlementYmd, HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowNum = 0;
        Cell cell = null;
        Row row = null;
        List<VsettlementsTaxMapping> vsettlementsTaxMappings =
                vsettlementTotalRepository.findAllTaxBySettlementYmdOrderByBrandCdAscSettlementCdAscStoreNameAsc(dateConverter.stringToDate(settlementYmd));
        //Header
        int cellNum = 0;
        row = sheet.createRow(rowNum++);
        cell = row.createCell(cellNum++);
        cell.setCellValue("브랜드");
        cell = row.createCell(cellNum++);
        cell.setCellValue("매장명");
        cell = row.createCell(cellNum++);
        cell.setCellValue("점주");
        cell = row.createCell(cellNum++);
        cell.setCellValue("금액");
        cell = row.createCell(cellNum++);

        //Body
        for (int i = 0; i < vsettlementsTaxMappings.size(); i++) {
            cellNum = 0;
            VsettlementsTaxMapping taxMapping = vsettlementsTaxMappings.get(i);
            row = sheet.createRow(rowNum++);
            cell = row.createCell(cellNum++);
            cell.setCellValue(taxMapping.getBrandName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(taxMapping.getStoreName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(taxMapping.getOwner());
            cell = row.createCell(cellNum++);
            cell.setCellValue(taxMapping.getSettlementAmount());


        }
        //Download
        response.setContentType("/ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=tax_list.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
