CREATE TABLE PRODUCT_IMG
(
    ID BIGSERIAL NOT NULL,
    -- 图片名称
    IMAGE_URL VARCHAR(256) NOT NULL,
    -- 产品ID
    PRODUCT_ID BIGINT,
    PRIMARY KEY (ID)
) WITHOUT OIDS;