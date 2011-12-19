
/* Drop Tables */

DROP TABLE IF EXISTS "USER";

/* Create Tables */

-- 用户表
CREATE TABLE "USER"
(
	ID SERIAL NOT NULL,
	-- 用户名
	USERNAME VARCHAR(32) NOT NULL UNIQUE,
	-- 密码
	PASSWORD VARCHAR(32) NOT NULL,
	-- Email
	EMAIL VARCHAR(256),
	PRIMARY KEY (ID)
) WITHOUT OIDS;



/* Comments */

COMMENT ON TABLE "USER" IS '用户表';
COMMENT ON COLUMN "USER".USERNAME IS '用户名';
COMMENT ON COLUMN "USER".PASSWORD IS '密码';
COMMENT ON COLUMN "USER".EMAIL IS 'Email';


