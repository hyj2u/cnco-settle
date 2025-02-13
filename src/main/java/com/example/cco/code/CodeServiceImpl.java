package com.example.cco.code;

import com.example.cco.base.FileUtil;
import com.example.cco.file.FileEntity;
import com.example.cco.file.FileRepository;
import com.example.cco.store.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

   private final CodeRepository codeRepository;


    @Override
    public List<CodeEntity> getSettlementCd(Integer brandCd) throws Exception {
        List<CodeEntity> codeEntities = codeRepository.findAllByUseYnAndGbOrderByCode("Y", "settlement_cd");
        List<CodeEntity> response = new ArrayList<>();
       if(brandCd==1){
            for(CodeEntity code: codeEntities){
                if(Integer.parseInt(code.getCode())<=10){
                    response.add(code);
                }
            }
        }else if(brandCd==2){
           for(CodeEntity code: codeEntities){
               if(Integer.parseInt(code.getCode())>10 && Integer.parseInt(code.getCode())<=20) {
                   response.add(code);
               }
           }
        }else if(brandCd ==3){
           for(CodeEntity code: codeEntities){
               if(Integer.parseInt(code.getCode())>20 && Integer.parseInt(code.getCode())<=30) {
                   response.add(code);
               }
           }
        }else{
           for(CodeEntity code: codeEntities){
               if(Integer.parseInt(code.getCode())>30) {
                   response.add(code);
               }
           }
       }
        return  response;
    }
}
