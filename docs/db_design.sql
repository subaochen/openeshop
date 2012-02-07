
/* Drop Tables */

DROP TABLE IF EXISTS PURCHASE_ITEM;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS GOODS;
DROP TABLE IF EXISTS PURCHASE;
DROP TABLE IF EXISTS MEMBER;




/* Create Tables */

CREATE TABLE GOODS
(
	ID BIGSERIAL NOT NULL,
	-- 名称
	NAME VARCHAR(256) NOT NULL,
	-- 库存
	STORE INT,
	-- 描述
	DESCRIPTION TEXT,
	-- 编码，比如条形码等
	CODE VARCHAR(30),
	PRIMARY KEY (ID)
) WITHOUT OIDS;


-- 用户表
CREATE TABLE MEMBER
(
	ID BIGSERIAL NOT NULL,
	-- 用户名
	USERNAME VARCHAR(32) NOT NULL UNIQUE,
	-- 密码
	PASSWORD VARCHAR(32) NOT NULL,
	-- Email
	EMAIL VARCHAR(256),
	PRIMARY KEY (ID)
) WITHOUT OIDS;


CREATE TABLE PRODUCT
(
	ID BIGSERIAL NOT NULL,
	-- 名称
	NAME VARCHAR(256) NOT NULL,
	-- 编码，比如货号
	CODE VARCHAR(30),
	-- 销售价格
	PRICE DECIMAL(20,2),
	-- 库存
	STORE INT DEFAULT 0,
	-- 描述
	DESCRIPTION TEXT,
	GOODS_ID BIGINT NOT NULL,
	PRIMARY KEY (ID)
) WITHOUT OIDS;


-- 订单表
CREATE TABLE PURCHASE
(
	ID BIGSERIAL NOT NULL,
	MEMBER_ID BIGINT NOT NULL,
	-- 收货人
	ADDR_NAME VARCHAR(100),
	-- 收货人电话
	ADDR_TEL VARCHAR(32),
	-- 收获地址
	ADDR VARCHAR(256),
	-- 发货状态
	SHIP_STATUS VARCHAR(100),
	-- 付款状态
	PAY_STATUS VARCHAR(100),
	PRIMARY KEY (ID)
) WITHOUT OIDS;


CREATE TABLE PURCHASE_ITEM
(
	ID BIGSERIAL NOT NULL,
	PURCHASE_ID BIGINT NOT NULL,
	PRODUCT_ID BIGINT NOT NULL,
	-- 订购数量
	NUM INT,
	PRIMARY KEY (ID)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE PRODUCT
	ADD FOREIGN KEY (GOODS_ID)
	REFERENCES GOODS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE PURCHASE
	ADD FOREIGN KEY (MEMBER_ID)
	REFERENCES MEMBER (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE PURCHASE_ITEM
	ADD FOREIGN KEY (PRODUCT_ID)
	REFERENCES PRODUCT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE PURCHASE_ITEM
	ADD FOREIGN KEY (PURCHASE_ID)
	REFERENCES PURCHASE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON COLUMN GOODS.NAME IS '名称';
COMMENT ON COLUMN GOODS.STORE IS '库存';
COMMENT ON COLUMN GOODS.DESCRIPTION IS '描述';
COMMENT ON COLUMN GOODS.CODE IS '编码，比如条形码等';
COMMENT ON TABLE MEMBER IS '用户表';
COMMENT ON COLUMN MEMBER.USERNAME IS '用户名';
COMMENT ON COLUMN MEMBER.PASSWORD IS '密码';
COMMENT ON COLUMN MEMBER.EMAIL IS 'Email';
COMMENT ON COLUMN PRODUCT.NAME IS '名称';
COMMENT ON COLUMN PRODUCT.CODE IS '编码，比如货号';
COMMENT ON COLUMN PRODUCT.PRICE IS '销售价格';
COMMENT ON COLUMN PRODUCT.STORE IS '库存';
COMMENT ON COLUMN PRODUCT.DESCRIPTION IS '描述';
COMMENT ON TABLE PURCHASE IS '订单表';
COMMENT ON COLUMN PURCHASE.ADDR_NAME IS '收货人';
COMMENT ON COLUMN PURCHASE.ADDR_TEL IS '收货人电话';
COMMENT ON COLUMN PURCHASE.ADDR IS '收获地址';
COMMENT ON COLUMN PURCHASE.SHIP_STATUS IS '发货状态';
COMMENT ON COLUMN PURCHASE.PAY_STATUS IS '付款状态';
COMMENT ON COLUMN PURCHASE_ITEM.NUM IS '订购数量';


