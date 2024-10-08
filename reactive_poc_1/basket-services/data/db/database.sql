CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS basket(
basket_id uuid DEFAULT uuid_generate_v4(),
order_no VARCHAR(30),
order_date VARCHAR(30),
resource_state VARCHAR(30),
product_items JSONB,
locale VARCHAR(30),
customer_information JSONB,
billing_address JSONB,
payment_instrument JSONB
);

INSERT INTO basket (order_no, order_date, resource_state, event_id, product_items, locale, customer_information, billing_address, payment_instrument)�
VALUES�
('12345', '2022-04-08 14:30:00+05:30', 'processed', 'evt_123', '{"modelNumber": "LLB41","productName":"Shoe", "productColor": "Black", "quantity": 1, "price": 2000 }', 'en_US', '{"firstname": "John","lastName":"dsouza","email": "john@example.com"}', '{"streetaddress1": "123 Main St", "city": "Anytown", "state": "CA", "zipcode": "12345"}', '{"payment_instrument_id": "123456789",
"payment_method_id": "1","payment_method_name": "Credit Card","amount": 50.00,"payment_status": "Completed","payment_provider_authorization_code": "ABC123","payment_provider_transaction_id": "XYZ789"}');
select * from basket;