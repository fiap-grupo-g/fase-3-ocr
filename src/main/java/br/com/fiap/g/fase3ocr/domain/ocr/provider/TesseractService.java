package br.com.fiap.g.fase3ocr.domain.ocr.provider;

import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

@Service
public class TesseractService implements OcrProviderService {

    private final static String DEFAULT_LANGUAGE = "por";
    private final static String TRAINED_DATA_PATH = "src/main/resources/tessdata";

    private ITesseract tesseract;

    public TesseractService() {
        tesseract = new Tesseract();
        tesseract.setLanguage(DEFAULT_LANGUAGE);
        tesseract.setDatapath(TRAINED_DATA_PATH);
    }

    @Override
    public String readImage(BufferedImage image) {
        String result = "";
        try {
            result = tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }
}
