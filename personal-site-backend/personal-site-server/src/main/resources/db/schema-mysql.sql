
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


-- 文章分类表（支持多级分类结构）
CREATE TABLE category (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
  name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称（唯一）',
  description VARCHAR(255) COMMENT '分类描述说明',
  parent_id INT UNSIGNED COMMENT '父分类ID（建立树形结构）',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分类创建时间',
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE SET NULL,
  INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章分类表';

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
     category_id INT UNSIGNED NOT NULL COMMENT '分类ID',
     series_id INT UNSIGNED COMMENT '所属系列ID',
     title VARCHAR(255) NOT NULL COMMENT '文章标题',
     summary VARCHAR(512) COMMENT '内容摘要',
     content LONGTEXT NOT NULL COMMENT '文章正文',
     cover_image VARCHAR(255) COMMENT '封面图URL',
     status ENUM('draft', 'published', 'deleted') NOT NULL DEFAULT 'draft' COMMENT '发布状态',
     view_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览数',
     like_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE RESTRICT,
     FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT,
     FOREIGN KEY (series_id) REFERENCES article_series(id) ON DELETE SET NULL,
     INDEX idx_article_author (user_id),
     INDEX idx_article_category (category_id),
     INDEX idx_article_series (series_id),
     INDEX idx_article_status (status),
     FULLTEXT INDEX ft_article_content (title, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='核心文章数据';

-- 标签表（文章标签管理）
CREATE TABLE articleTag (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '标签ID',
     name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称（唯一）',
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '标签创建时间',
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章标签表';

-- 文章-标签关联表（多对多关系）
CREATE TABLE article_tag (
     article_id INT UNSIGNED NOT NULL COMMENT '文章ID',
     tag_id INT UNSIGNED NOT NULL COMMENT '标签ID',
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关联创建时间',
     PRIMARY KEY (article_id, tag_id),
     FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
     FOREIGN KEY (tag_id) REFERENCES articleTag(id) ON DELETE CASCADE
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


