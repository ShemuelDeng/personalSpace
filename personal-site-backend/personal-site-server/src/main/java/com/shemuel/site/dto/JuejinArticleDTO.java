package com.shemuel.site.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 掘金平台文章同步DTO
 */
public class JuejinArticleDTO {

    @Data
    public static class CreateDraftRequest {
        private String category_id = "6809637769959178254";
        private List<String> tag_ids = Lists.newArrayList("6809640408797167623");
        private String link_url = "";
        private String cover_image = "";
        private String title;
        private String brief_content = "";
        private Integer edit_type = 10;
        private String html_content = "deprecated";
        private String mark_content;
        private List<String> theme_ids = new ArrayList<>();
        private List<String> pics = new ArrayList<>();
    }

    @Data
    public static class CreateDraftResponse {
        private Integer err_no;
        private String err_msg;
        private DraftData data;
    }

    @Data
    public static class DraftData {
        private String id;
        private String article_id;
        private String user_id;
        private String category_id;
        private List<String> tag_ids;
        private String link_url;
        private String cover_image;
        private Integer is_gfw;
        private String title;
        private String brief_content;
        private Integer is_english;
        private Integer is_original;
        private Integer edit_type;
        private String html_content;
        private String mark_content;
        private String ctime;
        private String mtime;
        private Integer status;
        private Integer original_type;
        private List<String> theme_ids;
    }

    @Data
    public static class UpdateDraftRequest {
        private String id;
        private String category_id = "6809637769959178254";
        private List<String> tag_ids = Lists.newArrayList("6809640408797167623");
        private String link_url = "";
        private String cover_image = "";
        private String title;
        private String brief_content;
        private Integer edit_type = 10;
        private String html_content = "deprecated";
        private String mark_content;
        private List<String> theme_ids = Lists.newArrayList();
        private List<String> pics = Lists.newArrayList();
    }

    @Data
    public static class PublishRequest {
        private String draft_id;
        private Boolean sync_to_org = false;
        private List<String> column_ids = Lists.newArrayList();
        private List<String> theme_ids = Lists.newArrayList();
        private Integer encrypted_word_count = 110;
        private Integer origin_word_count = 10;
    }
}