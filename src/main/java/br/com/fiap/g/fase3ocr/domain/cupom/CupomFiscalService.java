package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.ocr.OcrService;
import br.com.fiap.g.fase3ocr.domain.opencv.OpenCvService;
import io.undertow.util.BadRequestException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

import static br.com.fiap.g.fase3ocr.domain.ImageUtils.base64ToImage;

@Service
public class CupomFiscalService {

    private final OcrService ocrService;
    private final OpenCvService openCvService;
    private final CupomFiscalParser cupomFiscalParser;

    public CupomFiscalService(OpenCvService openCvService, OcrService ocrService, CupomFiscalParser cupomFiscalParser) {
        this.openCvService = openCvService;
        this.ocrService = ocrService;
        this.cupomFiscalParser = cupomFiscalParser;
    }

    public CupomFiscal create(CupomFiscalDocument cupomFiscalDocument) {
        CupomFiscal cupomFiscal = new CupomFiscal();
        BufferedImage image = base64ToImage(cupomFiscalDocument.getBase64File());
        List<BufferedImage> processedImage = openCvService.processImage(image);

        processedImage.forEach(bufferedImage -> {
            String payload = ocrService.readImage(bufferedImage);
            CupomFiscal result = cupomFiscalParser.create(payload, bufferedImage);

            if (result != null) {
                cupomFiscal.merge(result);
            }
        });

        cupomFiscal.setCupomFiscalDocument(cupomFiscalDocument);
        // save to database

        return cupomFiscal;
    }
}
