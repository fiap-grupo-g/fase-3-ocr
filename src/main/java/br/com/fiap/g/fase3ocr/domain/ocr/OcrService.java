package br.com.fiap.g.fase3ocr.domain.ocr;

import br.com.fiap.g.fase3ocr.domain.ocr.provider.OcrProviderService;
import java.awt.image.BufferedImage;

public class OcrService {

    OcrProviderService ocrProviderService;
    OcrPayloadFactory ocrPayloadFactory;

    public OcrService(OcrProviderService ocrProviderService,
            OcrPayloadFactory ocrPayloadFactory) {
        this.ocrProviderService = ocrProviderService;
        this.ocrPayloadFactory = ocrPayloadFactory;
    }

    public OcrPayload read(BufferedImage image) {
        String payload = ocrProviderService.readImage(image);
        return ocrPayloadFactory.create(payload, image);
    }
}
