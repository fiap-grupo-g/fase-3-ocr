package br.com.fiap.g.fase3ocr.infrastructure.config;

import br.com.fiap.g.fase3ocr.domain.ocr.OcrService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrConfig {

    @Value("${tesseract.lang}")
    private String lang;

    @Value("${tesseract.trained.path}")
    private String path;

    @Bean
    public OcrService ocrService(ITesseract tesseract) {
        return new OcrService(tesseract);
    }

    @Bean
    public ITesseract tesseract() {
        var tesseract = new Tesseract();
        tesseract.setLanguage(lang);
        tesseract.setDatapath(path);

        return tesseract;
    }
}
