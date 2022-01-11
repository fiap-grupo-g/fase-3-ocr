package br.com.fiap.g.fase3ocr.domain.cupom.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValorTotalFinder extends RegexFinder {

    private static final List<String> totalReg = new ArrayList<>();

    static {
        totalReg.addAll(Arrays.asList(
                "TOTAL\\s*R\\$\\s+(.*(\\.|,)\\d{2})",
                "Dinheiro\\s+(.*(\\.|,)\\d{2})",
                "Cheque\\s+(.*(\\.|,)\\d{2})"
        ));
    }

    public ValorTotalFinder(String payload) {
        super(payload);
    }

    public String get() {
        return normalizePriceAmount(findFirst(totalReg, 1));
    }
}
