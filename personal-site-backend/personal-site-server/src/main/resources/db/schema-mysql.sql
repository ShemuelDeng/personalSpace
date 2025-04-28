
-- 用户基本信息表
CREATE TABLE user_profile (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(100) NULL COMMENT '用户名',
  gender ENUM('male', 'female', 'other') DEFAULT NULL COMMENT '性别',
  birthdate DATETIME DEFAULT NULL COMMENT '出生日期',
  email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  location VARCHAR(255) DEFAULT NULL COMMENT '居住地',
  website VARCHAR(255) DEFAULT NULL COMMENT '个人网站',
  about_me TEXT DEFAULT NULL COMMENT '自我介绍',
  password_hash VARCHAR(100) NOT NULL  COMMENT 'BCrypt哈希结果',
  failed_attempts INT DEFAULT 0 COMMENT '失败次数',
  locked_until DATETIME COMMENT '锁定时间',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_login DATETIME COMMENT '最后登录时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  user_status TINYINT  DEFAULT 0 COMMENT ' 用户状态0: 正常, 1: 锁定, 2: 禁用',
  source_type VARCHAR(20) DEFAULT 'system' COMMENT '注册来源: system/wechat',
  avatar_url VARCHAR(511) COMMENT '微信头像',
  nickname VARCHAR(100) COMMENT '微信昵称',
  openid VARCHAR(128)  COMMENT '微信唯一标识',
  unionid VARCHAR(128) COMMENT '微信开放平台统一标识',
  UNIQUE INDEX idx_openid (openid),
  UNIQUE INDEX idx_unionid (unionid),
  UNIQUE INDEX idx_email (email),
  UNIQUE INDEX idx_name (user_name),
  UNIQUE INDEX idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '用户个人信息';

-- 教育经历表
CREATE TABLE education (
   id INT AUTO_INCREMENT PRIMARY KEY,
   user_id BIGINT NOT NULL COMMENT '外键关联user_profile表的id',
   school_name VARCHAR(255) NOT NULL COMMENT '学校名称',
   major VARCHAR(255) DEFAULT NULL COMMENT '专业',
   degree VARCHAR(50) DEFAULT NULL COMMENT '学位',
   start_date DATETIME NOT NULL COMMENT '开始日期',
   end_date DATETIME DEFAULT NULL COMMENT '结束日期',
   is_current BOOLEAN DEFAULT FALSE COMMENT '是否正在该阶段',
   description TEXT DEFAULT NULL COMMENT '描述',
   sort_order INT DEFAULT 0 COMMENT '排序顺序',
   created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
   INDEX idx_education_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '教育经历';

-- 工作经历表
CREATE TABLE work_experience (
 id INT AUTO_INCREMENT PRIMARY KEY,
 user_id BIGINT NOT NULL COMMENT '外键关联user_profile表的id',
 company_name VARCHAR(255) NOT NULL COMMENT '公司名称',
 position VARCHAR(100) NOT NULL COMMENT '职位',
 start_date DATETIME NOT NULL COMMENT '开始日期',
 end_date DATETIME DEFAULT NULL COMMENT '结束日期',
 is_current BOOLEAN DEFAULT FALSE COMMENT '是否正在该阶段',
 description TEXT DEFAULT NULL COMMENT '描述',
 sort_order INT DEFAULT 0 COMMENT '排序顺序',
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
 INDEX idx_work_experience_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '工作经历';

-- 项目经验表
CREATE TABLE project_experience (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '外键关联user_profile表的id',
    project_name VARCHAR(255) NOT NULL COMMENT '项目名称',
    role VARCHAR(100) DEFAULT NULL COMMENT '角色',
    start_date DATETIME NOT NULL COMMENT '开始日期',
    end_date DATETIME DEFAULT NULL COMMENT '结束日期',
    description TEXT DEFAULT NULL COMMENT '描述',
    project_url VARCHAR(255) DEFAULT NULL COMMENT '项目链接',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    INDEX idx_project_experience_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '项目经验';

-- 技能表
CREATE TABLE skills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '外键关联user_profile表的id',
    skill_name VARCHAR(100) NOT NULL COMMENT '技能名称',
    proficiency ENUM('beginner', 'intermediate', 'advanced', 'expert') DEFAULT 'beginner' COMMENT '技能水平',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    INDEX idx_skills_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '技能信息';

-- 证书表
CREATE TABLE certificates (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL COMMENT '外键关联user_profile表的id',
  certificate_name VARCHAR(255) NOT NULL COMMENT '证书名称',
  issuing_organization VARCHAR(255) NOT NULL COMMENT '颁发机构',
  issue_date DATETIME NOT NULL COMMENT '颁发日期',
  expiration_date DATETIME DEFAULT NULL COMMENT '过期日期',
  credential_id VARCHAR(100) DEFAULT NULL COMMENT '证书ID',
  credential_url VARCHAR(255) DEFAULT NULL COMMENT '证书链接',
  sort_order INT DEFAULT 0 COMMENT '排序顺序',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  INDEX idx_certificates_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT = '证书信息';

-- 社交链接表
CREATE TABLE social_links (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL COMMENT '外键关联user_profile表的id',
  platform VARCHAR(50) DEFAULT NULL COMMENT '平台名称',
  url VARCHAR(255) NOT NULL COMMENT '链接地址',
  sort_order INT DEFAULT 0 COMMENT '排序顺序',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  INDEX idx_social_links_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT = '社交连接信息';


DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `table_id`      bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_name`    varchar(200) NULL DEFAULT '' COMMENT '表名称',
    `table_comment` varchar(500) NULL DEFAULT '' COMMENT '表描述',
    `create_time`   datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`table_id`) USING BTREE,
    UNIQUE INDEX idx_tablename (table_name)
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4  COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`
(
    `column_id`      bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_id`       bigint NOT NULL COMMENT '归属表编号',
    `column_name`    varchar(200) NULL DEFAULT NULL COMMENT '列名称',
    `column_comment` varchar(500) NULL DEFAULT NULL COMMENT '列描述',
    `column_type`    varchar(100) NULL DEFAULT NULL COMMENT '列类型',
    `java_type`      varchar(500) NULL DEFAULT NULL COMMENT 'JAVA类型',
    `java_field`     varchar(200) NULL DEFAULT NULL COMMENT 'JAVA字段名',
    `is_pk`          char(1) NULL DEFAULT NULL COMMENT '是否主键（1是）',
    `is_required`    char(1) NULL DEFAULT NULL COMMENT '是否必填（1是）',
    `is_insert`      char(1) NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
    `is_edit`        char(1) NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
    `is_list`        char(1) NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
    `is_query`       char(1) NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
    `query_type`     varchar(200) NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    `html_type`      varchar(200) NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    `sort`           int NULL DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`column_id`) USING BTREE,
    UNIQUE INDEX idx_table_id_column (table_id, column_name)
) ENGINE = InnoDB AUTO_INCREMENT = 263 CHARACTER SET = utf8mb4  COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;


-- 用户私有标签表
CREATE TABLE user_tag (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '私有标签ID',
  user_id BIGINT  NOT NULL COMMENT '所属用户ID',
  name VARCHAR(50) NOT NULL COMMENT '标签名称',
  tag_type tinyint NOT NULL DEFAULT 0 COMMENT '标签类型， 0：系统；1：用户',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE INDEX uniq_user_tag (user_id, name), -- 用户下标签名称唯一
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户私有标签库';



-- 用户私有分类表（用户下唯一）
CREATE TABLE user_category (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL ,
    parent_id INT UNSIGNED,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uniq_user_category (user_id, name),  -- 用户下名称唯一
    FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES user_category(id) ON DELETE SET NULL
) COMMENT='用户私有分类';



-- 文章系列表（原专栏）
CREATE TABLE article_series (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '系列ID',
    user_id BIGINT  NOT NULL COMMENT '作者ID',
    name VARCHAR(100) NOT NULL COMMENT '系列名称',
    slug VARCHAR(120) NOT NULL UNIQUE COMMENT 'URL标识（全站唯一）',
    description TEXT COMMENT '系列描述',
    cover_image VARCHAR(255) COMMENT '封面图URL',
    status ENUM('draft', 'published', 'archived') NOT NULL DEFAULT 'draft' COMMENT '发布状态',
    order_num INT NOT NULL DEFAULT 0 COMMENT '排序序号',
    article_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '包含文章数',
    subscribers INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '订阅人数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    INDEX idx_series_author (user_id),
    INDEX idx_series_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章专题系列';

-- 核心文章表
CREATE TABLE article (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID',
     user_id BIGINT  NOT NULL COMMENT '作者ID',
     series_id INT UNSIGNED DEFAULT NULL COMMENT '所属系列ID',
     tag_id INT UNSIGNED NOT NULL   COMMENT '所属标签ID',
     category_id INT UNSIGNED NOT NULL   COMMENT '所属分类ID',
     title VARCHAR(255) NOT NULL COMMENT '文章标题',
     summary VARCHAR(512) COMMENT '内容摘要',
     content LONGTEXT NOT NULL COMMENT '文章正文',
     cover_image VARCHAR(255) COMMENT '封面图URL',
     status ENUM('draft', 'published', 'deleted') NOT NULL DEFAULT 'draft' COMMENT '发布状态',
     view_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览数',
     like_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
     save_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '收藏数',
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 -- 外键约束定义
    FOREIGN KEY fk_article_user (user_id)
        REFERENCES user_profile(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    FOREIGN KEY fk_article_tag (tag_id)
        REFERENCES user_tag(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    FOREIGN KEY fk_article_category (category_id)
        REFERENCES user_category(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    FOREIGN KEY fk_article_series (series_id)
        REFERENCES article_series(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '核心文章数据';


-- 文章标签关联表
CREATE TABLE article_tag_relation (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '关联ID',
  article_id INT UNSIGNED NOT NULL COMMENT '文章ID',
  tag_id INT UNSIGNED NOT NULL COMMENT '标签ID',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    -- 外键约束
  FOREIGN KEY fk_relation_article (article_id)
      REFERENCES article(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,

  FOREIGN KEY fk_relation_tag (tag_id)
      REFERENCES user_tag(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,

            -- 确保文章-标签对唯一
  UNIQUE KEY uniq_article_tag (article_id, tag_id),

                    -- 索引
  INDEX idx_tag_id (tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章标签关联表';



-- 评论表（支持多级评论）
CREATE TABLE comment (
 id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID',
 user_id BIGINT  NOT NULL COMMENT '评论者ID',
 article_id INT UNSIGNED NOT NULL COMMENT '所属文章ID',
 parent_id INT UNSIGNED COMMENT '父评论ID（实现评论回复）',
 content TEXT NOT NULL COMMENT '评论内容',
 like_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数量',
 status ENUM('normal', 'deleted') NOT NULL DEFAULT 'normal' COMMENT '评论状态',
 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
 updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
 FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE RESTRICT,
 FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
 FOREIGN KEY (parent_id) REFERENCES comment(id) ON DELETE CASCADE,
 INDEX idx_article_parent (article_id, parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章评论表';

-- 插入预制分类
INSERT INTO user_category (user_id, name, description, parent_id) VALUES
                                                                      (1, '前端开发', 'Web前端开发相关技术', NULL),
                                                                      (1, '后端开发', '服务器端开发相关技术', NULL),
                                                                      (1, '人工智能', 'AI、机器学习、深度学习相关', NULL),
                                                                      (1, '数据科学', '数据分析、大数据处理相关', NULL),
                                                                      (1, 'DevOps', '开发运维一体化相关技术', NULL);


-- 插入预制标签
INSERT INTO user_tag (user_id, name, tag_type) VALUES
                                                   (1, 'Java', 0),
                                                   (1, 'Python', 0),
                                                   (1, 'JavaScript', 0),
                                                   (1, 'Go', 0),
                                                   (1, 'C++', 0);


CREATE TABLE `third_party_platform` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `platform_type` tinyint NOT NULL COMMENT '平台：1-稀土掘金；2-今日头条；3-知乎；4-CSDN',
    `platform_name` varchar(400) NOT NULL COMMENT '平台名称：1-稀土掘金；2-今日头条；3-知乎；4-CSDN',
    `create_draft_url` varchar(4000) DEFAULT NULL COMMENT '创建草稿URL',
    `update_draft_url` varchar(4000) DEFAULT NULL COMMENT '更新草稿URL',
    `set_topic_url` varchar(4000) DEFAULT NULL COMMENT '设置主题URL',
    `publish_article_url` varchar(4000) DEFAULT NULL COMMENT '发布文章URL',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_platform` (`platform_type`) COMMENT '平台唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='第三方平台信息表';

CREATE TABLE `article_sync_record` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户id',
    `article_id` varchar(64) NOT NULL COMMENT '文章ID',
    `article_title` varchar(100) NOT NULL COMMENT '文章标题',
    `platform_id` int NOT NULL COMMENT '平台ID，关联third_party_platform.id',
    `sync_result` tinyint NOT NULL COMMENT '同步结果：0-失败；1-成功',
    `sync_fail_reason` varchar(500) DEFAULT NULL COMMENT '同步失败原因（仅当sync_result=0时有效）',
    `sync_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '同步时间',
    PRIMARY KEY (`id`),
    KEY `idx_article_id` (`article_id`) COMMENT '文章ID索引',
    KEY `idx_platform_id` (`platform_id`) COMMENT '平台ID索引',
    KEY `idx_sync_time` (`sync_time`) COMMENT '同步时间索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章同步记录表';


CREATE TABLE `third_party_platform_auth_info` (
   `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   `user_id` BIGINT NOT NULL COMMENT '用户id',
   `platform_id` int NOT NULL COMMENT '平台ID，关联third_party_platform.id',
   `header` text DEFAULT NULL COMMENT '请求头信息',
   `cookie` text DEFAULT NULL COMMENT 'cookie信息',
   PRIMARY KEY (`id`),
   KEY `idx_user_id` (`user_id`) COMMENT '用户id',
   KEY `idx_platform_id` (`platform_id`) COMMENT '平台ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='第三方平台认证信息表';


CREATE TABLE timeline_event (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    event_date DATE NOT NULL COMMENT '事件日期',
    event_year INT NOT NULL COMMENT '事件年份',
    title VARCHAR(255) NOT NULL COMMENT '事件标题',
    content TEXT COMMENT '事件内容（最长2000字）',
    location VARCHAR(255) COMMENT '发生位置',
    images TEXT COMMENT '图片链接，支持多个，用逗号或JSON存',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    KEY idx_user_id (user_id),
    KEY idx_event_year (event_year),
    KEY idx_event_date (event_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='时间轴大事件表';
