package com.shemuel.site.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "代码生成业务字段信息")
public class GenTableColumn {

    @Schema( name= "字段主键")
    private Long columnId;

    @Schema(name = "表Id")
    private Long tableId;

    @Schema(name = "字段名称")
    private String columnName;

    @Schema(name = "字段描述")
    private String columnComment;

    @Schema(name = "物理类型")
    private String columnType;

    @Schema(name = "Java属性类型")
    private String javaType;

    @Schema(name = "Java属性名")
    private String javaField;

    @Schema(name = "是否主键（1是）")
    private String isPk;

    @Schema(name = "是否必填（1是）")
    private String isRequired;

    @Schema(name = "是否为插入字段（1是）")
    private String isInsert;

    @Schema(name = "是否编辑字段（1是）")
    private String isEdit;

    @Schema(name = "是否列表字段（1是）")
    private String isList;

    @Schema(name = "是否查询字段（1是）")
    private String isQuery;

    @Schema(name = "查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）")
    private String queryType;

    @Schema(name = "显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、img图片上传控件、file文件上传控件、editor富文本控件）")
    private String htmlType;

    @Schema(name = "排序")
    private Integer sort;

    private String columnKey;
}
