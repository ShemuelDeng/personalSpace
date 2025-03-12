CREATE TABLE users (
   user_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) NOT NULL UNIQUE,
   email VARCHAR(100) default null UNIQUE,
   phone VARCHAR(16) default null  UNIQUE,
   frontend_salt CHAR(32) NOT NULL,      -- 前端哈希使用的盐
   password_hash VARCHAR(100) NOT NULL,  -- BCrypt哈希结果
   created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
   last_login DATETIME,
   enabled BOOLEAN DEFAULT TRUE,
   failed_attempts INT DEFAULT 0,
   locked_until DATETIME,
   UNIQUE INDEX idx_email (email),
   UNIQUE INDEX idx_username (username),
   UNIQUE INDEX idx_phone (phone),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;