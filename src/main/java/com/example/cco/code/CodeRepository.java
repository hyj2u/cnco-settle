package com.example.cco.code;

import com.example.cco.file.FileEntity;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {

    List<CodeEntity> findAllByUseYnAndGbOrderByCode(String useYn, String gb);
/*

  List<CodeEntity> findAllByGbAndUseYnAndCodeIsLessThanOrderByOrder(String gb, String useYn, Integer code);

  List<CodeEntity> findAllByGbAndUseYnAndCodeIsGreaterThanAndCodeLessThanOrderByOrder(String gb, String useYn, Integer min, Integer max);

  List<CodeEntity> findAllByGbAndUseYnAndCodeIsGreaterThanOrderByOrder(String gb, String useYn, Integer code);
*/

}
