package com.shemuel.site.dto;

import lombok.Data;

@Data
public class ZhihuArticleDTO {

    @Data
    public static class CreateDraftRequest {
        private String content;
        private boolean table_of_contents = false;
        private int delta_time = 25;
        private boolean can_reward = false;
        private String title;
    }

    @Data
    public static class CreateDraftResponse {
        private String id;
        private String state;
        private String url;
    }

    @Data
    public static class TopicRequest {
        private String introduction = "";
        private String avatarUrl = "https://picx.zhimg.com/80/7d343fb2f_l.jpg?source=4e949a73";
        private String name = "Java";
        private String url = "https://www.zhihu.com/topic/19561132";
        private String type = "topic";
        private String excerpt = "";
        private String id = "19561132";
    }

    @Data
    public static class PublishRequest {
        private String action = "article";
        private PublishData data = new PublishData();
    }

    @Data
    public static class PublishData {
        private Publish publish = new Publish();
        private ExtraInfo extra_info = new ExtraInfo();
        private Draft draft = new Draft();
        private CommentsPermission commentsPermission = new CommentsPermission();
        private CreationStatement creationStatement = new CreationStatement();
        private ContentsTables contentsTables = new ContentsTables();
        private CommercialReportInfo commercialReportInfo = new CommercialReportInfo();
        private Appreciate appreciate = new Appreciate();
        private HybridInfo hybridInfo = new HybridInfo();
    }

    @Data
    public static class Publish {
        private String traceId = "1743331661197,6b38a3bb-7b70-4726-9ae0-c374116675f41";
    }

    @Data
    public static class ExtraInfo {
        private String publisher = "pc";
        private String pc_business_params = "{\"column\":null,\"commentPermission\":\"anyone\",\"disclaimer_type\":\"none\",\"disclaimer_status\":\"close\",\"table_of_contents_enabled\":false,\"commercial_report_info\":{\"commercial_types\":[]},\"commercial_zhitask_bind_info\":null,\"canReward\":false}";
    }

    @Data
    public static class Draft {
        private int disabled = 1;
        private String id;
        private boolean isPublished = false;
    }

    @Data
    public static class CommentsPermission {
        private String comment_permission = "anyone";
    }

    @Data
    public static class CreationStatement {
        private String disclaimer_type = "none";
        private String disclaimer_status = "close";
    }

    @Data
    public static class ContentsTables {
        private boolean table_of_contents_enabled = false;
    }

    @Data
    public static class CommercialReportInfo {
        private int isReport = 0;
    }

    @Data
    public static class Appreciate {
        private boolean can_reward = false;
        private String tagline = "";
    }

    @Data
    public static class HybridInfo {
    }

    @Data
    public static class PublishResponse {
        private int code;
        private String message;
        private String toast_message;
        private Object data;
    }
}