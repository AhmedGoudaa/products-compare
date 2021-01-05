use my_db;
INSERT INTO category (name)
VALUES ('Mobiles, Tablets & Wearables'),
       ('Computers'),
       ('Cameras'),
       ('TV & Video'),
       ('Video Games & Consoles'),
       ('Audio'),
       ('Office Supplies'),
       ('Home Appliances'),
       ('Personal Care & Beauty'),
       ('Car Parts & Accessories'),
       ('Men Fashion'),
       ('Women Fashion'),
       ('Books'),
       ('Sports Equipment'),
       ('Furniture, Home and Garden'),
       ('Travel and Luggage'),
       ('Baby & Kids'),
       ('Health & Medical'),
       ('Watches'),
       ('Musical Instruments'),
       ('Arts & Crafts'),
       ('Groceries');


insert into data_source (name, logo)
VALUES ('amazon', 'amazon.jpg');

insert into product (name, logo, price, category_id, source_id)
VALUES ('iphone 20', 'i.jpg', 999, 1, 1),
       ('note 10', 'not10.jpg', 9999, 1, 1),
       ('note 20', 'not20.jpg', 1200, 1, 1),
       ('mac book pro 2015', 'mac2015.jpg', 20000, 2, 1),
       ('mac book pro 2020', 'mac2020.jpg', 20000, 2, 1);