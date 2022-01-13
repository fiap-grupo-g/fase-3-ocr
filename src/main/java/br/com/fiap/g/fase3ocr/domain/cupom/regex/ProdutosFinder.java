package br.com.fiap.g.fase3ocr.domain.cupom.regex;

import br.com.fiap.g.fase3ocr.domain.produto.Produto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProdutosFinder extends RegexFinder {

    private static final String PRODUCT_LINES = "(?i)((ITEM|CÓDIGO|CODIGO|DESCRIÇÃO|DESC).*)([\\s\\S]*)(?=(TOTAL|Total)\\s*)";
    private static final String PRODUCT_LINE_MIDDLE = "00\\d\\s+[\\s\\S]*?(?=00\\d\\s+)";
    private static final String PRODUCT_LINE_END = "00\\d\\s+([a-zA-Z0-9]*)\\s[\\s\\S]*";

    public ProdutosFinder(String payload) {
        super(payload);
    }

    public List<Produto> get() {
        List<Produto> produtos = new ArrayList<>();
        Matcher m = matcher(PRODUCT_LINES);
        if (m.find()) {
            String productLines = m.group(m.groupCount() - 1);
            Boolean isLast = false;
            while (!isLast) {
                Pattern p2 = Pattern.compile(PRODUCT_LINE_MIDDLE);
                Matcher m2 = p2.matcher(productLines);
                if (m2.find()) {
                    produtos.add(new ProdutoFinder(m2.group(0)).get());
                    productLines = m2.replaceFirst("");
                }
                else {
                    isLast = true;
                    Pattern p3 = Pattern.compile(PRODUCT_LINE_END);
                    Matcher m3 = p3.matcher(productLines);
                    if (m3.find()) {
                        produtos.add(new ProdutoFinder(m3.group(0)).get());
                    }
                }
            }
            return produtos;
        }
        return produtos;
    }
}
