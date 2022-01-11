package br.com.fiap.g.fase3ocr.domain.cupom.regex;

public class CnpjFinder extends RegexFinder {

    private static final String CNPJ_REGEX = "\\d{2}(\\.|,|:)\\d{3}(\\.|,|:)\\d{3}\\/\\d{4}(-| )?\\d{2}";

    public CnpjFinder(String payload) {
        super(payload);
    }

    public String get() {
        return removeNonDigit(find(CNPJ_REGEX));
    }
}
