package br.com.fiap.g.fase3ocr.domain.ocr;

import java.awt.image.BufferedImage;

public interface OcrPayloadFactory<T extends OcrPayload> {

    T create(String payload, BufferedImage image);
}
