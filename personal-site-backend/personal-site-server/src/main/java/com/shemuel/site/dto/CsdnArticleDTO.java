package com.shemuel.site.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CsdnArticleDTO {
    private String id = "";
    private String title;
    private String markdowncontent;
    private String content;
    private String readType = "public";
    private String level = "0";
    private String tags = "后端";
    private Integer status = 0;
    private String categories = "后端";
    private String type = "original";
    private String original_link = "";
    private Boolean authorized_status = false;
    private String Description = "";
    private String resource_url = "";
    private String not_auto_saved = "1";
    private String source = "pc_mdeditor";
    private List<String> cover_images = new ArrayList<>();
    private Integer cover_type = 1;
    private Integer is_new = 1;
    private Integer vote_id = 0;
    private String resource_id = "";
    private String pubStatus = "publish";
    private Integer sync_git_code = 0;
}