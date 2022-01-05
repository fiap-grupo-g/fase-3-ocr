CREATE TABLE produto(
      id bigint(20) NOT NULL AUTO_INCREMENT,
      cupom_fiscal_id bigint(20) NOT NULL,
      item_n int(3),
      codigo varchar(50),
      descricao varchar(255),
      quantidade int(3),
      valor varchar(12),
      PRIMARY KEY(id),
      FOREIGN KEY(cupom_fiscal_id) REFERENCES cupom_fiscal(id)
) DEFAULT CHARSET=UTF8;
