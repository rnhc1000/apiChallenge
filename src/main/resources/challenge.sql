 CREATE DATABASE challenge;
 use challenge;
 CREATE USER 'adminChallenge'@'%' identified by '@ch4ll3ng3@';
 GRANT ALL PRIVILEGES on challenge.* to 'adminChallenge'@'%' with grant option;
 CREATE TABLE `tb_roles` (

    `role_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(50) NOT NULL

);
INSERT INTO tb_roles(role_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO tb_roles(role_id, role) VALUES (2, 'ROLE_USER');