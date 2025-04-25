--liquibase formatted sql
--changeset zuku:1

CREATE TABLE orders (
order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
product_name VARCHAR(1000) NOT NULL,
post_date DATE DEFAULT (CURRENT_DATE),
cost_usd DECIMAL(8,2) DEFAULT 0.0,
cost_pln DECIMAL(8,2),
CONSTRAINT ck_cost CHECK (cost_usd >= 0.0));