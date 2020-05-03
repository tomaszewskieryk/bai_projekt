DROP DATABASE IF EXISTS shoppinglist;
CREATE DATABASE shoppinglist;
USE shoppinglist;

CREATE TABLE users (
  user_id         INT PRIMARY KEY AUTO_INCREMENT,
  nickname       VARCHAR(50) NOT NULL,
  email          VARCHAR(100) NOT NULL,
  password       VARCHAR(100) NOT NULL
);

CREATE TABLE fridge_states (
  fridge_state_id   INT PRIMARY KEY AUTO_INCREMENT,
  fridge_name       VARCHAR(100) NOT NULL,
  actual            BIT(1),
  user_id           INT NOT NULL,
  CONSTRAINT fk_fridgeState_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE products (
                       product_id       INT PRIMARY KEY AUTO_INCREMENT,
                       unit             VARCHAR(2),
                       product_name     VARCHAR(50) NOT NULL,
                       price            DECIMAL(10,2) NOT NULL
);

CREATE TABLE shopping_lists (
    shopping_list_id    INT PRIMARY KEY AUTO_INCREMENT,
    shopping_list_name  VARCHAR(50) NOT NULL,
    user_id             INT NOT NULL
);

CREATE TABLE product_fridge_state (
  fridge_state_id  INT,
  product_id       INT,
  amount           DECIMAL(10,2),
  CONSTRAINT pk_product_fridge_state PRIMARY KEY (fridge_state_id, product_id),
  CONSTRAINT fk_fridge_state FOREIGN KEY (fridge_state_id) REFERENCES fridge_states (fridge_state_id),
  CONSTRAINT fk_product_fridge_state FOREIGN KEY (product_id) REFERENCES products (product_id)
);

CREATE TABLE product_shopping_list (
  shopping_list_id  INT,
  product_id        INT,
  amount            DECIMAL(10,2),
  CONSTRAINT pk_product_shopping_list PRIMARY KEY (shopping_list_id, product_id),
  CONSTRAINT fk_product_shopping_list FOREIGN KEY (product_id) REFERENCES products (product_id),
  CONSTRAINT fk_shopping_list FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists (shopping_list_id)
);


# password: "qwe123"
INSERT INTO shoppinglist.users (user_id, nickname, email, password) VALUES (1, 'Mati', 'mati@test.com', '$2a$10$y1FQR/6s4HIbI7rIsR4AOO2h86kHS7BaECX91pq5HrAWkgOjgrX2e');
# password: "password"
INSERT INTO shoppinglist.users (user_id, nickname, email, password) VALUES (2, 'Eryk', 'eryk@test.com', '$2a$10$gLaWKLPgyz/abMFo5TDAPOb4oCPMMSDCwaD0e/aprRFdQh6MVoZF6');
# password: "password"
INSERT INTO shoppinglist.users (user_id, nickname, email, password) VALUES (3, 'Iza', 'iza@test.com', '$2a$10$NQooyYqpYY0FqxZ54tYlZO.EQRZ0434cJF4.hkBh2oZwZeFjtrhyW');

INSERT INTO fridge_states (fridge_state_id, fridge_name, actual, user_id) VALUES (1, 'aktualna', true, 1);
INSERT INTO fridge_states (fridge_state_id, fridge_name, actual, user_id) VALUES (2, 'imprezowa', false, 1);
INSERT INTO fridge_states (fridge_state_id, fridge_name, actual, user_id) VALUES (3, 'aktualna', true, 2);
INSERT INTO fridge_states (fridge_state_id, fridge_name, actual, user_id) VALUES (4, 'aktualna', true, 3);

INSERT INTO products (product_id, unit, product_name, price) VALUES (1, 'kg', 'pomidory', 5.33);
INSERT INTO products (product_id, unit, product_name, price) VALUES (2, 'p', 'herbatniki', 2.99);
INSERT INTO products (product_id, unit, product_name, price) VALUES (3, 'p', 'woda gazowana', 1.39);

INSERT INTO shopping_lists (shopping_list_id, shopping_list_name, user_id) VALUES (1, 'imprezowa shopping list', 1);

INSERT INTO product_fridge_state (fridge_state_id, product_id, amount) VALUES (1, 1, 4);
INSERT INTO product_fridge_state (fridge_state_id, product_id, amount) VALUES (1, 2, 5);
INSERT INTO product_fridge_state (fridge_state_id, product_id, amount) VALUES (1, 3, 6);

INSERT INTO product_shopping_list (shopping_list_id, product_id, amount) VALUES (1, 1, 3);
INSERT INTO product_shopping_list (shopping_list_id, product_id, amount) VALUES (1, 2, 5);
INSERT INTO product_shopping_list (shopping_list_id, product_id, amount) VALUES (1, 3, 7);
