create table banks
(
    id             bigint auto_increment
        primary key,
    balance        decimal(10, 2) null,
    name           varchar(255)   null,
    total_transfer int            null
)
    engine = MyISAM;

INSERT INTO db_financial.banks (id, balance, name, total_transfer) VALUES (1, 23452345.34, 'Bank Tic', 3000);
INSERT INTO db_financial.banks (id, balance, name, total_transfer) VALUES (2, 23452345.34, 'Bank Tic', 3000);