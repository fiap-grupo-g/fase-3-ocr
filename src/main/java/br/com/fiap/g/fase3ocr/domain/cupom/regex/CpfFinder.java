package br.com.fiap.g.fase3ocr.domain.cupom.regex;

public class CpfFinder extends RegexFinder {

    private static final String CPF_REGEX = "\\d{3}(\\.|,|:|\\/)\\d{3}(\\.|,|:|\\/)\\d{3}(-| |\\/)\\d{2}";

    public CpfFinder(String payload) {
        super(payload);
    }

    public String get() {
        return removeNonDigit(find(CPF_REGEX));
    }
}
