package br.com.fiap.g.fase3ocr.domain.cupom;

import static br.com.fiap.g.fase3ocr.domain.ImageUtils.imageToBase64;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

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

    public CupomFiscalDocumentProcessed setBase64File(BufferedImage image) {
        try {
            this.base64File = imageToBase64(image);
        } catch (IOException e) {
            e.printStackTrace(); // add exception
        }
        return this;
    }

    public String getBase64File() {
        return base64File;
    }
}
