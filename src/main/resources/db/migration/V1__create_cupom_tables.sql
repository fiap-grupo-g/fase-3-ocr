CREATE TABLE cupom_fiscal_document(
     id bigint(20) NOT NULL AUTO_INCREMENT,
     base64_file MEDIUMBLOB NOT NULL,
     PRIMARY KEY(id)
) DEFAULT CHARSET=UTF8;

CREATE TABLE cupom_fiscal(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    cupom_fiscal_document_id bigint(20) NOT NULL,
    cnpj_estabelecimento varchar(14),
    documento_consumidor varchar(14),
    valor_total varchar(12),
    raw_value BLOB,
    PRIMARY KEY(id),
    FOREIGN KEY(cupom_fiscal_document_id) REFERENCES cupom_fiscal_document(id)
) DEFAULT CHARSET=UTF8;
