CREATE TABLE IF NOT EXISTS `signup`.`user`(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(45) DEFAULT 'anonymous',
    password text NOT NULL,
    status varchar(10) DEFAULT 'INACTIVE'
);

CREATE TABLE IF NOT EXISTS `signup`.`otp`(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(45) NOT NULL,
    code varchar(6) NOT NULL
);