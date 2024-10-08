CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS basket_order(
id SERIAL PRIMARY KEY,
basket_id uuid NOT NULL,
order_no VARCHAR(30) NOT NULL,
order_status VARCHAR(30) NOT NULL
CONSTRAINT unique_order_configuration UNIQUE (basket_id)
);
