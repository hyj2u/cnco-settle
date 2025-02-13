package com.example.cco.relation;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface RelationService {
    public RelationNeedDto getRelationsNeeded(int page) throws Exception;
    public void excelDownRelations(HttpServletResponse response) throws Exception;
    public List<NoConnectionEntity> getNoConnections(String gb) throws Exception;
    public void insertRelation(List<StoreRelEntity> storeRelEntity) throws Exception;
    public void insertRelationList(List<StoreRelEntity> storeRelEntity) throws Exception;
    public List<VStoreRelEntity> getStoreRelation(String storeCode) throws  Exception;
    public List<StoreRelEntity> getMoneyonTerminal(String storeCode) throws Exception;
    public void setMoneyonTerminal(StoreTerminalDto storeTerminalDto) throws Exception;

}
