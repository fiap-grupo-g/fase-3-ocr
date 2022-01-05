package br.com.fiap.g.fase3ocr.domain.ocr.provider;

import java.awt.image.BufferedImage;

public interface OcrProviderService {

    String readImage(BufferedImage image);
}
