
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;