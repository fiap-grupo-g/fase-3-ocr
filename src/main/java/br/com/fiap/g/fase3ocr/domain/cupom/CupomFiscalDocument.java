package br.com.fiap.g.fase3ocr.domain.cupom;

import javax.persistence.*;

@Entity
@Table(name = "CUPOM_FISCAL_DOCUMENT")
public class CupomFiscalDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "BASE64_FILE", nullable = false)
    private String base64File;

    public String getBase64File() {
        return base64File;
    }

    public CupomFiscalDocument setBase64File(String base64File) {
        this.base64File = base64File;
        return this;
    }
}
