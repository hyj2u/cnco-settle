package com.example.cco.file;

import com.example.cco.store.StoreEntity;
import com.example.cco.store.StoreMapping;
import com.example.cco.store.StoreSettlementMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
  Optional<FileEntity> findByStoreCodeAndFileType(String storeCode, String fileType);
  List<FileEntity> findAllByStoreCodeAndFileType(String storeCode, String fileType);


}
