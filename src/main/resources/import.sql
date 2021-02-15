INSERT INTO users (username, password, enabled) VALUES ('lucas', '$2a$10$aqCJf.ZkEBjYrwVO7ncyW.IVgexw7hq3SAawcNnlFj5Z1/4YNUJTS', 1);
INSERT INTO users (username, password, enabled) VALUES ('maca', '$2a$10$2n/c6txrikUrU3ph11tWP.isV//Zh0n.YQX62ofsHO5dJY4kn5U8m', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
