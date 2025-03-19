
-- 用户基本信息表
CREATE TABLE user_profile (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(100) NULL,
  gender ENUM('male', 'female', 'other') DEFAULT NULL,
  birthdate DATETIME DEFAULT NULL,
  email VARCHAR(100) DEFAULT NULL,
  phone VARCHAR(20) DEFAULT NULL,
  location VARCHAR(255) DEFAULT NULL,
  avatar_url VARCHAR(255) DEFAULT NULL,
  website VARCHAR(255) DEFAULT NULL,
  about_me TEXT DEFAULT NULL,
  password_hash VARCHAR(100) NOT NULL,  -- BCrypt哈希结果
  failed_attempts INT DEFAULT 0,
  locked_until DATETIME,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  last_login DATETIME,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  user_status TINYINT  DEFAULT 0, -- 0: 正常, 1: 锁定, 2: 禁用
  UNIQUE INDEX idx_email (email),
  UNIQUE INDEX idx_name (user_name),
  UNIQUE INDEX idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 教育经历表
CREATE TABLE education (
   id INT AUTO_INCREMENT PRIMARY KEY,
   user_id BIGINT NOT NULL,
   school_name VARCHAR(255) NOT NULL,
   major VARCHAR(255) DEFAULT NULL,
   degree VARCHAR(50) DEFAULT NULL,
   start_date DATETIME NOT NULL,
   end_date DATETIME DEFAULT NULL,
   is_current BOOLEAN DEFAULT FALSE,
   description TEXT DEFAULT NULL,
   sort_order INT DEFAULT 0,
   created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
   INDEX idx_education_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 工作经历表
CREATE TABLE work_experience (
 id INT AUTO_INCREMENT PRIMARY KEY,
 user_id BIGINT NOT NULL,
 company_name VARCHAR(255) NOT NULL,
 position VARCHAR(100) NOT NULL,
 start_date DATETIME NOT NULL,
 end_date DATETIME DEFAULT NULL,
 is_current BOOLEAN DEFAULT FALSE,
 description TEXT DEFAULT NULL,
 sort_order INT DEFAULT 0,
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
 INDEX idx_work_experience_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 项目经验表
CREATE TABLE project_experience (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    project_name VARCHAR(255) NOT NULL,
    role VARCHAR(100) DEFAULT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME DEFAULT NULL,
    description TEXT DEFAULT NULL,
    project_url VARCHAR(255) DEFAULT NULL,
    sort_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    INDEX idx_project_experience_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 技能表
CREATE TABLE skills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    proficiency ENUM('beginner', 'intermediate', 'advanced', 'expert') DEFAULT 'beginner',
    sort_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    INDEX idx_skills_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 证书表
CREATE TABLE certificates (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  certificate_name VARCHAR(255) NOT NULL,
  issuing_organization VARCHAR(255) NOT NULL,
  issue_date DATETIME NOT NULL,
  expiration_date DATETIME DEFAULT NULL,
  credential_id VARCHAR(100) DEFAULT NULL,
  credential_url VARCHAR(255) DEFAULT NULL,
  sort_order INT DEFAULT 0,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  INDEX idx_certificates_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 社交链接表
CREATE TABLE social_links (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  platform VARCHAR(50) DEFAULT NULL,
  url VARCHAR(255) NOT NULL,
  sort_order INT DEFAULT 0,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  INDEX idx_social_links_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;