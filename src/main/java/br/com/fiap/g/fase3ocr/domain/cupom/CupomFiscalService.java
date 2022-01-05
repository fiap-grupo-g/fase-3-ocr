package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.ImageUtils;
import br.com.fiap.g.fase3ocr.domain.cv.CvImage;
import br.com.fiap.g.fase3ocr.domain.cv.CvService;
import br.com.fiap.g.fase3ocr.domain.ocr.OcrService;
import br.com.fiap.g.fase3ocr.domain.ocr.provider.OcrProviderService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CupomFiscalService {

    private CvService cvService;
    private OcrService ocrService;

    @Autowired
    public CupomFiscalService(
            OcrProviderService ocrProviderService,
            CvService cvService) {
        this.cvService = cvService;
        this.ocrService = new OcrService(ocrProviderService, new CupomFiscalFactory());
    }

    public CupomFiscal create(CupomFiscalDocument cupomFiscalDocument) {
        BufferedImage image = base64FileToImage(cupomFiscalDocument.getBase64File());
        CvImage cvImage = cvService.createCvImage(image);

        CupomFiscal cupomFiscal = null;

        while (
            cvImage.hasNext() &&                                    // has more processing options
            (cupomFiscal == null || !cupomFiscal.isComplete())       // hasn't found complete payload
        ) {
            CupomFiscal result = (CupomFiscal) ocrService.read(cvImage.getProcessedImage());

            if (cupomFiscal == null) cupomFiscal = result;
            else cupomFiscal.merge(result);
        }

        cupomFiscal.setCupomFiscalDocument(cupomFiscalDocument);
        // save to database

        return cupomFiscal;
    }

    private BufferedImage base64FileToImage(String base64File) {
        try {
            return ImageUtils.base64ToImage(base64File);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // throw exception
        }
    }
}
