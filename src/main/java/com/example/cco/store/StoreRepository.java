package com.example.cco.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    @Query(nativeQuery = true, value = "select pkey, store_code as storeCode, store_name as storeName, owner, manager, active_yn as activeYn, closing_ymd as closingYmd," +
            "closing_desc closingDesc, open_ymd openYmd, phone, store_address as storeAddress, brand_cd as brandCd, prepaid_rent as prepaidRent, prepaid_month as prepaidMonth," +
            "(select MAX(contract_end_ymd) from cnco.store_contract as sc where sc.store_code=s.store_code ) as contractEndYmd " +
            "from cnco.store s where active_yn = :activeYn and brand_cd= :brandCd order by contractEndYmd ")
    List<StoreListMapping> findAllByActiveYnAndBrandCd(String activeYn, String brandCd);
    List<StoreMapping> findAllByActiveYnAndBrandCdIsNot(String activeYn, String brandCd);
    @Query(nativeQuery = true, value = "select pkey, store_code as storeCode, store_name as storeName, owner, manager, active_yn as activeYn, closing_ymd as closingYmd," +
            "closing_desc closingDesc, open_ymd openYmd, phone, store_address as storeAddress, brand_cd as brandCd, prepaid_rent as prepaidRent, prepaid_month as prepaidMonth," +
            "(select MAX(contract_end_ymd) from cnco.store_contract as sc where sc.store_code=s.store_code ) as contractEndYmd " +
            "from cnco.store s where active_yn = :activeYn order by contractEndYmd ")
    List<StoreListMapping> findAllByActiveYn(String activeYn);
    @Query(nativeQuery = true , value = "select pkey, store_code as storeCode, store_name as storeName, owner, manager, active_yn as activeYn, closing_ymd as closingYmd," +
            "closing_desc closingDesc, open_ymd openYmd, phone, store_address as storeAddress, brand_cd as brandCd, prepaid_rent as prepaidRent, prepaid_month as prepaidMonth, " +
            "(select MAX(contract_end_ymd) from cnco.store_contract as sc where sc.store_code=s.store_code ) as contractEndYmd" +
            " from cnco.store s where active_yn = :activeYn and brand_cd =:brandCd and (store_name like :storeName or owner like :owner or manager like :manager )" +
            "order by contractEndYmd ")
    List<StoreListMapping> findAllByActiveYnAndBrandCdAndStoreNameContainingOrOwnerContainingOrManagerContaining(String activeYn,String brandCd,  String storeName, String owner, String manager);
    @Query(nativeQuery = true , value = "select pkey, store_code as storeCode, store_name as storeName, owner, manager, active_yn as activeYn, closing_ymd as closingYmd," +
            "closing_desc closingDesc, open_ymd openYmd, phone, store_address as storeAddress, brand_cd as brandCd, prepaid_rent as prepaidRent, prepaid_month as prepaidMonth, " +
            "(select MAX(contract_end_ymd) from cnco.store_contract as sc where sc.store_code=s.store_code ) as contractEndYmd " +
            " from cnco.store s where active_yn = :activeYn and (store_name like :storeName or owner like :owner or manager like :manager) order by contractEndYmd ")
    List<StoreListMapping> findAllByActiveYnAndStoreNameContainingOrOwnerContainingOrManagerContaining(String activeYn, String storeName, String owner, String manager);

    @Query(nativeQuery = true , value = "select pkey, store_code as storeCode, store_name as storeName from cnco.store where active_yn ='Y' and store_code not in " +
            "(select store_code from cnco.vsettlement_total where settlement_ymd=:settlementYmd )")
    List<StoreSettlementMapping> findAllWithNoSettlements(Date settlementYmd);

    Optional<StoreEntity> findByStoreCode(String storeCode);

}
