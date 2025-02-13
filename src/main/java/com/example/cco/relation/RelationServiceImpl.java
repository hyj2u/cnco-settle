package com.example.cco.relation;

import com.example.cco.base.PageDto;
import com.example.cco.store.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RelationServiceImpl implements RelationService {
    private final RelationRepository relationRepository;
    private final NoConnectionRepository noConnectionRepository;
    private final StoreRelRepository storeRelRepository;
    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;
    private final VStoreRelRepository vStoreRelRepository;

    @Override
    public RelationNeedDto getRelationsNeeded(int page) throws Exception {
        RelationNeedDto relationNeedDto = new RelationNeedDto();
        PageDto pageDto = new PageDto();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<RelationEntity> relationEntities = relationRepository.
                getRelationEntitiesBySemplusCodeIsNullOrBlueorderCodeIsNullOrKepcoCodeIsNullOrGreenlogisCodeIsNullOrWaveposCodeIsNullOrMoneyonCodeIsNullOrPaycoCodeIsNullOrKakaoCodeIsNullOrderByStoreCode(
                        pageable
                );
        pageDto.setTotalPage(relationEntities.getTotalPages());
        pageDto.setNumber(page);
        pageDto.setTotalElements(relationEntities.getTotalElements());
        relationNeedDto.setPage(pageDto);
        List<RelationDto> relationDtos = new ArrayList<>();
        for (RelationEntity relation : relationEntities) {
            log.info("relation : " + objectMapper.writeValueAsString(relation));
            RelationDto relationDto = new RelationDto();
            relationDto.setPkey(storeRepository.findByStoreCode(relation.getStoreCode()).get().getPkey());
            relationDto.setStoreCode(relation.getStoreCode());
            relationDto.setStoreName(relation.getStoreName());
            if (relation.getGreenlogisCode() == null) {
                relationDto.setGreenlogis(new ArrayList<>());
            } else {
                List<GreenlogisRelationDto> greenlogisRelationDtos = new ArrayList<>();
                if (relation.getGreenlogisCode().contains("#")) {
                    for (int i = 0; i < relation.getGreenlogisCode().split("#").length; i++) {
                        GreenlogisRelationDto greenlogisRelationDto = new GreenlogisRelationDto();
                        greenlogisRelationDto.setGreenlogisCode(relation.getGreenlogisCode().split("#")[i]);
                        greenlogisRelationDto.setGreenlogisName(relation.getGreenlogisName().split("#")[i]);
                        greenlogisRelationDtos.add(greenlogisRelationDto);
                    }
                } else {
                    GreenlogisRelationDto greenlogisRelationDto = new GreenlogisRelationDto();
                    greenlogisRelationDto.setGreenlogisCode(relation.getGreenlogisCode());
                    greenlogisRelationDto.setGreenlogisName(relation.getGreenlogisName());
                    greenlogisRelationDtos.add(greenlogisRelationDto);
                }
                relationDto.setGreenlogis(greenlogisRelationDtos);
            }

            if (relation.getWaveposCode() == null) {
                relationDto.setWavepos(new ArrayList<>());
            } else {
                List<WaveposRelationDto> waveposRelationDtos = new ArrayList<>();
                if (relation.getWaveposCode().contains("#")) {
                    for (int i = 0; i < relation.getWaveposCode().split("#").length; i++) {
                        WaveposRelationDto waveposRelationDto = new WaveposRelationDto();
                        waveposRelationDto.setWaveposCode(relation.getWaveposCode().split("#")[i]);
                        waveposRelationDto.setWaveposName(relation.getWaveposName().split("#")[i]);
                        waveposRelationDtos.add(waveposRelationDto);
                    }
                } else {
                    WaveposRelationDto waveposRelationDto = new WaveposRelationDto();
                    waveposRelationDto.setWaveposCode(relation.getWaveposCode());
                    waveposRelationDto.setWaveposName(relation.getWaveposName());
                    waveposRelationDtos.add(waveposRelationDto);
                }
                relationDto.setWavepos(waveposRelationDtos);
            }
            if (relation.getMoneyonCode() == null) {
                relationDto.setMoneyon(new ArrayList<>());
            } else {
                List<MoneyonRelationDto> moneyonRelationDtos = new ArrayList<>();
                if (relation.getMoneyonCode().contains(",")) {
                    for (int i = 0; i < relation.getMoneyonCode().split("#").length; i++) {
                        MoneyonRelationDto moneyonRelationDto = new MoneyonRelationDto();
                        moneyonRelationDto.setMoneyonCode(relation.getMoneyonCode().split("#")[i]);
                        moneyonRelationDto.setMoneyonName(relation.getMoneyonCode().split("#")[i]);
                        moneyonRelationDtos.add(moneyonRelationDto);
                    }
                } else {
                    MoneyonRelationDto moneyonRelationDto = new MoneyonRelationDto();
                    moneyonRelationDto.setMoneyonCode(relation.getMoneyonCode());
                    moneyonRelationDto.setMoneyonName(relation.getMoneyonCode());
                    moneyonRelationDtos.add(moneyonRelationDto);
                }
                relationDto.setMoneyon(moneyonRelationDtos);
            }
            if (relation.getSemplusCode() == null) {
                relationDto.setSemplus(new ArrayList<>());
            } else {
                List<SemplusRelationDto> semplusRelationDtos = new ArrayList<>();
                if (relation.getSemplusCode().contains(",")) {
                    for (int i = 0; i < relation.getSemplusCode().split("#").length; i++) {
                        SemplusRelationDto semplusRelationDto = new SemplusRelationDto();
                        semplusRelationDto.setSemplusCode(relation.getSemplusCode().split("#")[i]);
                        semplusRelationDto.setSemplusName(relation.getSemplusName().split("#")[i]);
                        semplusRelationDtos.add(semplusRelationDto);
                    }
                } else {
                    SemplusRelationDto semplusRelationDto = new SemplusRelationDto();
                    semplusRelationDto.setSemplusCode(relation.getSemplusCode());
                    semplusRelationDto.setSemplusName(relation.getSemplusName());
                    semplusRelationDtos.add(semplusRelationDto);
                }
                relationDto.setSemplus(semplusRelationDtos);
            }
            if (relation.getPaycoCode() == null) {
                relationDto.setPayco(new ArrayList<>());
            } else {
                List<PaycoRelationDto> paycoRelationDtos = new ArrayList<>();
                if (relation.getPaycoCode().contains("#")) {
                    for (int i = 0; i < relation.getPaycoCode().split("#").length; i++) {
                        PaycoRelationDto paycoRelationDto = new PaycoRelationDto();
                        paycoRelationDto.setPaycoCode(relation.getPaycoCode().split("#")[i]);
                        paycoRelationDto.setPaycoName(relation.getPaycoName().split("#")[i]);
                        paycoRelationDtos.add(paycoRelationDto);
                    }
                } else {
                    PaycoRelationDto paycoRelationDto = new PaycoRelationDto();
                    paycoRelationDto.setPaycoCode(relation.getPaycoCode());
                    paycoRelationDto.setPaycoName(relation.getPaycoName());
                    paycoRelationDtos.add(paycoRelationDto);
                }
                relationDto.setPayco(paycoRelationDtos);
            }
            if (relation.getBlueorderCode() == null) {
                relationDto.setBlueorder(new ArrayList<>());
            } else {
                List<BlueorderRelationDto> blueorderRelationDtos = new ArrayList<>();
                if (relation.getBlueorderCode().contains("#")) {
                    for (int i = 0; i < relation.getBlueorderCode().split("#").length; i++) {
                        BlueorderRelationDto blueorderRelationDto = new BlueorderRelationDto();
                        blueorderRelationDto.setBlueorderCode(relation.getBlueorderCode().split("#")[i]);
                        blueorderRelationDto.setBlueorderName(relation.getBlueorderName().split("#")[i]);
                        blueorderRelationDtos.add(blueorderRelationDto);
                    }
                } else {
                    BlueorderRelationDto blueorderRelationDto = new BlueorderRelationDto();
                    blueorderRelationDto.setBlueorderCode(relation.getBlueorderCode());
                    blueorderRelationDto.setBlueorderName(relation.getBlueorderName());
                    blueorderRelationDtos.add(blueorderRelationDto);
                }
                relationDto.setBlueorder(blueorderRelationDtos);
            }

            if (relation.getKepcoCode() == null) {
                relationDto.setKepco(new ArrayList<>());
            } else {
                List<KepcoRelationDto> kepcoRelationDtos = new ArrayList<>();
                if (relation.getKepcoCode().contains("#")) {
                    for (int i = 0; i < relation.getKepcoCode().split("#").length; i++) {
                        KepcoRelationDto kepcoRelationDto = new KepcoRelationDto();
                        kepcoRelationDto.setKepcoCode(relation.getKepcoCode().split("#")[i]);
                        kepcoRelationDto.setKepcoName(relation.getKepcoCode().split("#")[i]);
                        kepcoRelationDtos.add(kepcoRelationDto);
                    }
                } else {
                    KepcoRelationDto kepcoRelationDto = new KepcoRelationDto();
                    kepcoRelationDto.setKepcoCode(relation.getKepcoCode());
                    kepcoRelationDto.setKepcoName(relation.getKepcoCode());
                    kepcoRelationDtos.add(kepcoRelationDto);
                }
                relationDto.setKepco(kepcoRelationDtos);
            }
            if (relation.getKakaoCode() == null) {
                relationDto.setKakao(new ArrayList<>());
            } else {
                List<KakaoRelationDto> kakaoRelationDtos = new ArrayList<>();
                if (relation.getKakaoCode().contains("#")) {
                    for (int i = 0; i < relation.getKakaoCode().split("#").length; i++) {
                        KakaoRelationDto kakaoRelationDto = new KakaoRelationDto();
                        kakaoRelationDto.setKakaoCode(relation.getKakaoCode().split("#")[i]);
                        kakaoRelationDto.setKakaoName(relation.getKakaoName().split("#")[i]);
                        kakaoRelationDtos.add(kakaoRelationDto);
                    }
                } else {
                    KakaoRelationDto kakaoRelationDto = new KakaoRelationDto();
                    kakaoRelationDto.setKakaoCode(relation.getKakaoCode());
                    kakaoRelationDto.setKakaoName(relation.getKakaoName());
                    kakaoRelationDtos.add(kakaoRelationDto);
                }
                relationDto.setKakao(kakaoRelationDtos);
            }


            relationDtos.add(relationDto);
        }
        relationNeedDto.setRelations(relationDtos);
        return relationNeedDto;
    }


    @Override
    public void excelDownRelations(HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for(int i=0; i<9; i++){
            sheet.setColumnWidth(i, 6000);
        }
        int rowNum = 0;
        Cell cell = null;
        Row row = null;
        List<RelationEntity> relationEntities = relationRepository.findAll();
        //Header
        int cellNum = 0;
        row = sheet.createRow(rowNum++);
        cell = row.createCell(cellNum++);
        cell.setCellValue("상호");
        cell = row.createCell(cellNum++);
        cell.setCellValue("물류");
        cell = row.createCell(cellNum++);
        cell.setCellValue("웨이브포스");
        cell = row.createCell(cellNum++);
        cell.setCellValue("머니온");
        cell = row.createCell(cellNum++);
        cell.setCellValue("샘플러스");
        cell = row.createCell(cellNum++);
        cell.setCellValue("페이코");
        cell = row.createCell(cellNum++);
        cell.setCellValue("시크릿오더");
        cell = row.createCell(cellNum++);
        cell.setCellValue("한전온");
        cell = row.createCell(cellNum++);
        cell.setCellValue("카카오");

        //Body
        for (int i = 0; i < relationEntities.size(); i++) {
            cellNum = 0;
            RelationEntity relation = relationEntities.get(i);
            row = sheet.createRow(rowNum++);
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getStoreName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getGreenlogisName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getWaveposName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getMoneyonName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getSemplusName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getPaycoName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getBlueorderName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getKepcoName());
            cell = row.createCell(cellNum++);
            cell.setCellValue(relation.getKakaoName());
        }
        //Download
        response.setContentType("/ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=relation.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @Override
    public List<NoConnectionEntity> getNoConnections(String gb) throws Exception {
        return noConnectionRepository.findAllByGbOrderByStoreName(gb);
    }

    @Override
    public void insertRelation(List<StoreRelEntity> storeRelEntity) throws Exception {
        for (int i = 0; i < storeRelEntity.size(); i++) {
            StoreRelEntity storeRel = storeRelEntity.get(i);
            if (i == 0) {
                for (StoreRelEntity rel : storeRelRepository.findAllByStoreCodeAndEndTimestampIsNull(storeRel.getStoreCode())) {
                    rel.setEndTimestamp(LocalDateTime.now());
                    storeRelRepository.save(rel);
                }
            }
            if (storeRel.getRelCode() != null) {
                StoreRelEntity newStoreRel = new StoreRelEntity();
                newStoreRel.setScrapGb(storeRel.getScrapGb());
                newStoreRel.setStoreCode(storeRel.getStoreCode());
                newStoreRel.setRelCode(storeRel.getRelCode());
                newStoreRel.setStartTimestamp(LocalDateTime.now());
                storeRelRepository.save(newStoreRel);
            }
        }

    }

    @Override
    public void insertRelationList(List<StoreRelEntity> storeRelEntity) throws Exception {
        for (int i = 0; i < storeRelEntity.size(); i++) {
            StoreRelEntity storeRel = storeRelEntity.get(i);
            if (i == 0) {
                for (StoreRelEntity rel : storeRelRepository.findByStoreCodeAndScrapGbAndEndTimestampIsNull(storeRel.getStoreCode(), storeRel.getScrapGb())) {
                    rel.setEndTimestamp(LocalDateTime.now());
                    storeRelRepository.save(rel);
                }
            }
            if (storeRel.getRelCode() != null) {
                StoreRelEntity newStoreRel = new StoreRelEntity();
                newStoreRel.setScrapGb(storeRel.getScrapGb());
                newStoreRel.setStoreCode(storeRel.getStoreCode());
                newStoreRel.setRelCode(storeRel.getRelCode());
                newStoreRel.setStartTimestamp(LocalDateTime.now());
                storeRelRepository.save(newStoreRel);
            }
        }
    }

    @Override
    public List<VStoreRelEntity> getStoreRelation(String storeCode) throws Exception {
        return vStoreRelRepository.findAllByStoreCode(storeCode);
    }

    @Override
    public List<StoreRelEntity> getMoneyonTerminal(String storeCode) throws Exception {
        return storeRelRepository.findByStoreCodeAndScrapGbAndEndTimestampIsNull(storeCode, "moneyon_t");
    }

    @Override
    public void setMoneyonTerminal(StoreTerminalDto storeTerminalDto) throws Exception {
        if (!storeRelRepository.findByStoreCodeAndScrapGbAndEndTimestampIsNull(storeTerminalDto.getStoreCode(), "moneyon_t").isEmpty()) {
            for (StoreRelEntity storeRel : storeRelRepository.findByStoreCodeAndScrapGbAndEndTimestampIsNull(storeTerminalDto.getStoreCode(), "moneyon_t")) {
                storeRel.setEndTimestamp(LocalDateTime.now());
                storeRelRepository.save(storeRel);
            }

        }
        for (String rel : storeTerminalDto.getRelCode()) {
            StoreRelEntity storeRel = new StoreRelEntity();
            storeRel.setScrapGb("moneyon_t");
            storeRel.setStoreCode(storeTerminalDto.getStoreCode());
            storeRel.setRelCode(rel);
            storeRel.setStartTimestamp(LocalDateTime.now());
            storeRelRepository.save(storeRel);
        }
    }
}

