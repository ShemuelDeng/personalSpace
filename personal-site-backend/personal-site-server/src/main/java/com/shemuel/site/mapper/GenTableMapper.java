package com.shemuel.site.mapper;


import com.shemuel.site.entity.GenTable;
import com.shemuel.site.entity.GenTableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenTableMapper {
    
    List<GenTable> selectGenTableList(GenTable genTable);

    int deleteGenTableByIds(Long[] tableIds);
    
    List<GenTableColumn> selectDbTableColumns(String tableName);
    
    void insertGenTable(GenTable genTable);
    
    GenTable selectGenTableById(Long tableId);
    
    List<GenTableColumn> selectGenTableColumns(Long tableId);
    
    void insertGenTableColumn(GenTableColumn column);
    
    List<GenTable> selectDbTableList(GenTable genTable);
    
    int insertGenTableBatch(@Param("columns") List<GenTableColumn> columns);
    
    int selectDbTableCount(GenTable genTable);
    
    GenTable selectGenTableByName(String tableName);
    
    void deleteGenTableColumns(List<Long> columnIds);
    
    void updateGenTableColumns(List<GenTableColumn> columns);
    
    void updateGenTable(GenTable table);

    void deleteGenTableColumnsByTableIds(Long[] tableIds);
}