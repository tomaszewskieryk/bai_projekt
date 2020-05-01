DROP DATABASE IF EXISTS shoppinglist;
CREATE DATABASE shoppinglist;
USE shoppinglist;

CREATE TABLE users (
  userID         INT PRIMARY KEY AUTO_INCREMENT,
  nickname       VARCHAR(50) NOT NULL,
  email          VARCHAR(100) NOT NULL,
  password       VARCHAR(100) NOT NULL
);

CREATE TABLE units (
                    unitID          INT PRIMARY KEY AUTO_INCREMENT,
                    unitName        VARCHAR(50)
);

CREATE TABLE fridgeStates (
  fridgeStateID  INT PRIMARY KEY AUTO_INCREMENT,
  fridgeName     VARCHAR(100) NOT NULL,
  isActual       BIT(1),
  userID         INT,
  CONSTRAINT fk_fridgeState_user FOREIGN KEY (userID) REFERENCES users (userID)
);

CREATE TABLE products (
                       productID       INT PRIMARY KEY AUTO_INCREMENT,
                       unitID          INT,
                       productName     VARCHAR(50) NOT NULL,
                       price           DECIMAL(10,2) NOT NULL,
                       CONSTRAINT fk_product_unit FOREIGN KEY (unitID) REFERENCES units (unitID)
);

CREATE TABLE productStates (
  productStateID  INT PRIMARY KEY AUTO_INCREMENT,
  productID       INT,
  amount          INT,
  CONSTRAINT fk_productState_fridgeState FOREIGN KEY (productID) REFERENCES products (productID)
);

CREATE TABLE productLists (
  productlistID   INT PRIMARY KEY AUTO_INCREMENT,
  productID       INT,
  amount          INT,
  CONSTRAINT fk_productList_product FOREIGN KEY (productID) REFERENCES products (productID)
);

CREATE TABLE shoppingLists (
  shoppingListID  INT PRIMARY KEY AUTO_INCREMENT
);

-- Sample user's password '$2a$10$9d5AC2CrUGaWSgwRHbtZV.TbKiuixWQh3EzJhZ7tHt0AeifE2AxCq' is a hashed version of password 'password'
INSERT INTO users (nickname, email, password) VALUES ('izs95', 'izab.sta@gmail.com', '$2a$04$OBXJHGVqDiFEF3r.B29Kz.zWaDx/OrTsnX0t5f.m0Q254zSHb/.l2');
INSERT INTO users (nickname, email, password) VALUES ('super_rafik', 'lewicki_r@wp.pl', '$2a$04$02xjZD12dXSWD2fNCbDgu.j17CNYbBsmdxyqKA34KmNMgxseCo2.i');
INSERT INTO users (nickname, email, password) VALUES ('test', 'test@gmail.com', '$2a$04$cGFcZSEuyVg/bSUeWHbV4.MQOgX/2FeHPRa6A9rF4g/24nljp9Qm6');

INSERT INTO fridgeStates (fridgeName, isActual, userID) VALUES ('rodzinna', true, 1);
INSERT INTO fridgeStates (fridgeName, isActual, userID) VALUES ('imprezowa', true, 2);
INSERT INTO fridgeStates (fridgeName, isActual, userID) VALUES ('codzienna', true, 3);

INSERT INTO units (unitName) VALUES ('kg');
INSERT INTO units (unitName) VALUES ('szt.');
INSERT INTO units (unitName) VALUES ('l');

INSERT INTO products (unitID, productName, price) VALUES (1, 'pomidory', 5.33);
INSERT INTO products (unitID, productName, price) VALUES (2, 'herbatniki', 2.99);
INSERT INTO products (unitID, productName, price) VALUES (3, 'woda gazowana', 1.39);

INSERT INTO productStates (productID, amount) VALUES (1, 4);
INSERT INTO productStates (productID, amount) VALUES (2, 5);
INSERT INTO productStates (productID, amount) VALUES (3, 6);

INSERT INTO productLists (productID, amount) VALUES (1, 3);
INSERT INTO productLists (productID, amount) VALUES (2, 5);
INSERT INTO productLists (productID, amount) VALUES (3, 7);
