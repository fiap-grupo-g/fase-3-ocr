package br.com.fiap.g.fase3ocr.domain.ocr;

public interface OcrPayloadFactory<T extends OcrPayload> {

    T create(String payload);
}
