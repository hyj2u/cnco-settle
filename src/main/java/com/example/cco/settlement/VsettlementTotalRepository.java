package com.example.cco.settlement;

import com.example.cco.summary.VsettlementsDepositMapping;
import com.example.cco.summary.VsettlementsTaxMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VsettlementTotalRepository extends JpaRepository<VsettlementTotalEntity, Long> {
    List<VsettlementsForWriteMapping> findVesttlementsForWriteBySettlementYmd(Date settlementYmd);

    List<VsettlementsForWriteMapping> findVesttlementsForWriteBySettlementYmdAndStoreNameContaining(Date settlementYmd, String storeName);

    List<VsettlementTotalEntity> findAllBySettlementYmd(Date settlementYmd);

    @Query(nativeQuery = true, value = "SELECT v.pkey, v.store_code AS storeCode, v.store_name AS storeName, v.settlement_ymd AS settlementYmd, v.insert_timestamp AS insertTimestamp, " +
            "v.owner, v.deposit_amount AS depositAmount, v.settlement_title AS settlementTitle, v.closed_yn AS closedYn, v.phone " +
            "FROM cnco.vsettlement_total v " +
            "WHERE v.settlement_ymd = :settlementYmd " +
            "AND v.store_name LIKE %:storeName% " +
            "AND ((:activeFlag = 'Y' AND v.active_yn = 'Y' " +
            "AND v.settlement_cd NOT IN ('4', '14') " +
            "AND v.brand_cd NOT IN ('9')) " +
            "OR (:activeFlag = 'N' " +
            "AND (v.active_yn = 'N' OR v.settlement_cd IN ('4', '14') OR v.brand_cd IN ('9')))) " +
            "ORDER BY v.settlement_ymd DESC")
    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdAndStoreNameContainingAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
            @Param("settlementYmd") Date settlementYmd,
            @Param("storeName") String storeName,
            @Param("activeFlag") String activeFlag,
            Pageable pageable);


    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdAndPhoneAndClosedYnAndStoreNameContainingOrderBySettlementYmdDesc(Date settlementYmd,
                                                                                                                                       String phone,
                                                                                                                                       String closedYn,
                                                                                                                                       String storeName,
                                                                                                                                       Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT v.pkey, v.store_code AS storeCode, v.store_name AS storeName, v.settlement_ymd AS settlementYmd, v.insert_timestamp AS insertTimestamp, " +
            "v.owner, v.deposit_amount AS depositAmount, v.settlement_title AS settlementTitle, v.closed_yn AS closedYn, v.phone " +
            "FROM cnco.vsettlement_total v " +
            "WHERE v.settlement_ymd BETWEEN :start AND :end " +
            "AND v.store_name LIKE %:storeName% " +
            "AND ((:activeFlag = 'Y' AND v.active_yn = 'Y' " +
            "AND v.settlement_cd NOT IN ('4', '14') " +
            "AND v.brand_cd NOT IN ('9')) " +
            "OR (:activeFlag = 'N' " +
            "AND (v.active_yn = 'N' OR v.settlement_cd IN ('4', '14') OR v.brand_cd IN ('9')))) " +
            "ORDER BY v.settlement_ymd DESC")
    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdBetweenAndStoreNameContainingAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
            @Param("start") Date start, @Param("end") Date end, @Param("storeName") String storeName, @Param("activeFlag") String activeFlag,Pageable pageable);

    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdBetweenAndPhoneAndClosedYnAndStoreNameContainingOrderBySettlementYmdDesc
            (Date start, Date end, String phone, String closedYn, String storeName, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT v.pkey, v.store_code AS storeCode, v.store_name AS storeName, v.settlement_ymd AS settlementYmd, v.insert_timestamp AS insertTimestamp, " +
            "v.owner, v.deposit_amount AS depositAmount, v.settlement_title AS settlementTitle, v.closed_yn AS closedYn, v.phone " +
            "FROM cnco.vsettlement_total v " +
            "WHERE v.settlement_ymd = :settlementYmd " +
            "AND ((:activeFlag = 'Y' AND v.active_yn = 'Y' " +
            "AND v.settlement_cd NOT IN ('4', '14') " +
            "AND v.brand_cd NOT IN ('9')) " +
            "OR (:activeFlag = 'N' " +
            "AND (v.active_yn = 'N' OR v.settlement_cd IN ('4', '14') OR v.brand_cd IN ('9')))) " +
            "ORDER BY v.settlement_ymd DESC")
    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(
            @Param("settlementYmd") Date settlementYmd,
            @Param("activeFlag") String activeFlag,
            Pageable pageable);

    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdAndPhoneAndClosedYnOrderBySettlementYmdDesc(Date settlementYmd, String phone,
                                                                                                                 String closedYn, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT v.pkey, v.store_code AS storeCode, v.store_name AS storeName, v.settlement_ymd AS settlementYmd, v.insert_timestamp AS insertTimestamp, " +
            "v.owner, v.deposit_amount AS depositAmount, v.settlement_title AS settlementTitle, v.closed_yn AS closedYn, v.phone " +
            "FROM cnco.vsettlement_total v " +
            "WHERE v.settlement_ymd BETWEEN :start AND :end " +
            "AND ((:activeFlag = 'Y' AND v.active_yn = 'Y' " +
            "AND v.settlement_cd NOT IN ('4', '14') " +
            "AND v.brand_cd NOT IN ('9')) " +
            "OR (:activeFlag = 'N' " +
            "AND (v.active_yn = 'N' OR v.settlement_cd IN ('4', '14') OR v.brand_cd IN ('9')))) " +
            "ORDER BY v.settlement_ymd DESC")
    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdBetweenAndActiveYnAndSettlementCdNotInAndBrandCdNotInOrderBySettlementYmdDesc(@Param("start") Date start,
                                                                                                                                                   @Param("end") Date end,
                                                                                                                                                   @Param("activeFlag") String activeFlag,
                                                                                                                                                   Pageable pageable);

    Page<VsettlementsListMapping> findVsettlementsListBySettlementYmdBetweenAndPhoneAndClosedYnOrderBySettlementYmdDesc(Date start, Date end, String phone,
                                                                                                                        String closedYn, Pageable pageable);

    List<VsettlementsDepositMapping> findAllDepositBySettlementYmdOrderByBrandCdAscSettlementCdAscStoreNameAsc(Date settlementYmd);

    List<VsettlementsTaxMapping> findAllTaxBySettlementYmdOrderByBrandCdAscSettlementCdAscStoreNameAsc(Date settlementYmd);

    List<VsettlementTotalStoreMapping> findAllByStoreCodeAndSettlementYmdBetweenOrderBySettlementYmd(String storeCode, Date start, Date end);

    Optional<VsettlementTotalEntity> findByStoreCodeAndSettlementYmd(String storeCode, Date settlementYmd);


}
