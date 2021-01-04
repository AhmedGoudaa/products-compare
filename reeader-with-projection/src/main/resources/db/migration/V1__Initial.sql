CREATE DATABASE if not exists my_db;

use my_db;

create table if not exists category(
    id         int auto_increment primary key,
     name varchar(255) not null,
    updated_at         timestamp default CURRENT_TIMESTAMP ,
    created_at         timestamp default CURRENT_TIMESTAMP

)ENGINE=InnoDB;


create table if not exists data_source(
    id         int auto_increment primary key,
     name varchar(255) not null,
     logo varchar(255) null,
    updated_at         timestamp default CURRENT_TIMESTAMP ,
    created_at         timestamp default CURRENT_TIMESTAMP
)ENGINE=InnoDB;


create table if not exists product(
    id   BIGINT auto_increment primary key,
     name varchar(500) not null,
     logo varchar(255) null,
-- total digit will be 10 and two digits will be after decimal point.
    price DECIMAL(10,2) not null ,
    category_id int not null,
    source_id int not null,
-- last update price date
    updated_at         timestamp default CURRENT_TIMESTAMP ,
    created_at         timestamp default CURRENT_TIMESTAMP ,

    FULLTEXT (name),
    foreign key (category_id) references category (id),
    foreign key (source_id) references data_source (id)
)ENGINE=InnoDB;


-- docker run --name=product-mysqldb -d -p 3306:3306 -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=root -e MYSQL_PASSWORD=root -e MYSQL_DATABASE=my_db  mysql/mysql-server
-- mysql -h 127.0.0.1 -P 3307 --protocol=tcp -u root -p