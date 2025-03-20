package com.shemuel.site.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shemuel.site.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Date;

@Data
@Tag(name= "代码生成业务对象 gen_table")
public class GenTable {

    @Schema(name = "表ID")
    private Long tableId;

    @Schema(name = "表名称")
    private String tableName;

    @Schema(name = "表描述")
    private String tableComment;

    @JsonFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
    private Date createTime;


    @JsonFormat(pattern = DateUtil.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
    private Date updateTime;


    // 主键列信息
    private GenTableColumn pkColumn;

    // 分页参数
    private Integer offset;
    private Integer pageSize;

    @Schema(name= "实体类名称")
    private String className;

    @Schema(name = "生成包路径")
    private String packageName;

    @Schema(name = "生成模块名")
    private String moduleName;

    @Schema(name = "生成业务名")
    private String businessName;
}
