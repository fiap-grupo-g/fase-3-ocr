package br.com.fiap.g.fase3ocr.domain.cupom;


import javax.persistence.*;
import java.awt.image.BufferedImage;

import static br.com.fiap.g.fase3ocr.domain.ImageUtils.imageToBase64;

@Entity
@Table(name = "CUPOM_FISCAL_DOCUMENT_PROCESSED")
public class CupomFiscalDocumentProcessed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "BASE64_FILE", nullable = false)
    private String base64File;

    public CupomFiscalDocumentProcessed() {
    }

    public String getBase64File() {
        return base64File;
    }

    public CupomFiscalDocumentProcessed setBase64File(BufferedImage image) {
        this.base64File = imageToBase64(image);
        return this;
    }
}
