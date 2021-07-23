create table accounts
(
    id      bigint auto_increment
        primary key,
    balance decimal(10, 2) null,
    person  varchar(255)   null
)
    engine = MyISAM;

INSERT INTO db_financial.accounts (id, balance, person) VALUES (1, 2345.56, 'Anna Can');
INSERT INTO db_financial.accounts (id, balance, person) VALUES (2, 12344.67, 'William Luss');
INSERT INTO db_financial.accounts (id, balance, person) VALUES (3, 645345.23, 'Mia Mirren');
INSERT INTO db_financial.accounts (id, balance, person) VALUES (4, 8978.45, 'Thomas Smith');
INSERT INTO db_financial.accounts (id, balance, person) VALUES (5,567.21, 'Emma Rowe');