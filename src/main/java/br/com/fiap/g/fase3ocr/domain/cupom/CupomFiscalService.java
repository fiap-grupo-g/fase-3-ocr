package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.ocr.OcrService;
import br.com.fiap.g.fase3ocr.domain.opencv.OpenCvService;
import io.undertow.util.BadRequestException;
import org.springframework.stereotype.Service;

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

    public CupomFiscal create(CupomFiscalDocument cupomFiscalDocument) throws BadRequestException {
        var cupomFiscal = new CupomFiscal();
        var image = base64ToImage(cupomFiscalDocument.getBase64File());
        var processedImage = openCvService.processImage(image);


        processedImage.forEach(bufferedImage -> {
            var payload = ocrService.readImage(bufferedImage);
            var result = cupomFiscalParser.create(payload);

            if (result != null) {
                cupomFiscal.merge(result);
            }
        });

//        if (!cupomFiscal.isComplete()) {
//            throw new BadRequestException("Cupom inválido ou qualidade baixa");
//        }

        cupomFiscal.setCupomFiscalDocument(cupomFiscalDocument);
        // save to database

        return cupomFiscal;
    }
}
