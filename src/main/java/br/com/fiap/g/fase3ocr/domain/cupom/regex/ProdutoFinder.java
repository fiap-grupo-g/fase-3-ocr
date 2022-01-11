package br.com.fiap.g.fase3ocr.domain.cupom.regex;

import br.com.fiap.g.fase3ocr.domain.produto.Produto;
import java.util.regex.Matcher;

public class ProdutoFinder extends RegexFinder {

    private static final String PRODUTO_REGEX_1 = "(^\\d{3})\\s+(\\d*)\\s+([\\s\\S]*)(?=(\\s\\d*,\\d*Un))[\\s\\S]*(?=(\\s\\d*,\\d{2}))";
    private static final String PRODUTO_REGEX_2 = "(^\\d{3})\\s+([a-zA-Z0-9]*)\\s+([\\s\\S]*)[\\s\\S]*(?=(\\s\\d*,\\d{2}))";
    private static final String PRODUTO_REGEX_3 = "(^\\d{3})\\s+([^\\s]*)\\s+(.*)[\\s\\S]*[\\s\\S]*(\\d{1,3},\\d{2})";

    public ProdutoFinder(String payload) {
        super(payload);
    }

    public Produto get() {
        Produto produto = new Produto();
        Matcher m = matcher(PRODUTO_REGEX_1);
        if (m.find()) {
            produto.setItemN(Integer.parseInt(m.group(1)));
            produto.setCodigo(clampTo(10, m.group(2)));
            produto.setDescricao(clampTo(15, m.group(3)));
            produto.setQuantidade(Integer.parseInt(m.group(4).substring(0, m.group(4).indexOf(",")).replaceAll("\\r\\n|\\r|\\n", " ").replaceAll(" ", "")));
            produto.setValor(m.group(5));
            return produto;
        }

        Matcher m2 = matcher(PRODUTO_REGEX_2);
        if (m2.find()) {
            produto.setItemN(Integer.parseInt(m2.group(1)));
            produto.setCodigo(clampTo(10, m2.group(2)));
            produto.setDescricao(clampTo(15, m2.group(3)));
            produto.setQuantidade(1);
            produto.setValor(m2.group(4));
            return produto;
        }

        Matcher m3 = matcher(PRODUTO_REGEX_3);
        if (m3.find()) {
            produto.setItemN(Integer.parseInt(m3.group(1)));
            produto.setCodigo(clampTo(10, m3.group(2)));
            produto.setDescricao(clampTo(15, m3.group(3)));
            produto.setQuantidade(1);
            produto.setValor(m3.group(4));
            return produto;
        }
        return produto;
    }
}
