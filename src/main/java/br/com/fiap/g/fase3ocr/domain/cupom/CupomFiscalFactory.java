package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.ocr.OcrPayloadFactory;
import br.com.fiap.g.fase3ocr.domain.produto.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CupomFiscalFactory implements OcrPayloadFactory<CupomFiscal> {

    private static final String CNPJ_REGEX = "\\d{2}(\\.|,|:)\\d{3}(\\.|,|:)\\d{3}\\/\\d{4}(-| )?\\d{2}";
    private static final String CPF_REGEX = "\\d{3}(\\.|,|:)\\d{3}(\\.|,|:)\\d{3}(-| )\\d{2}";
    private static final String PRODUCT_LINES = "((ITEM|CÓDIGO|DESCRIÇÃO).*)([\\s\\S]*)(?=TOTAL\\s*)";
    private static final String PRODUCT_LINE_MIDDLE = "00\\d\\s+[a-zA-Z0-9]*\\s[\\s\\S]*(?=00\\d\\s+\\d*\\s)";
    private static final String PRODUCT_LINE_END = "00\\d\\s+([a-zA-Z0-9]*)\\s[\\s\\S]*";

    private static final String TOTAL_VALUE_1 = "TOTAL\\s*R\\$\\s+(.*(\\.|,)\\d{2})";
    private static final String TOTAL_VALUE_2 = "Dinheiro\\s+(.*(\\.|,)\\d{2})";
    private static final String TOTAL_VALUE_3 = "Cheque\\s+(.*(\\.|,)\\d{2})";

    private static final String PRODUTO_REGEX_1 = "(^\\d{3})\\s+(\\d*)\\s+([\\s\\S]*)(?=(\\s\\d*,\\d*Un))[\\s\\S]*(?=(\\s\\d*,\\d{2}))";
    private static final String PRODUTO_REGEX_2 = "(^\\d{3})\\s+([a-zA-Z0-9]*)\\s+([\\s\\S]*)[\\s\\S]*(?=(\\s\\d*,\\d{2}))";

    @Override
    public CupomFiscal create(String payload) {
        CupomFiscal cupomFiscal = new CupomFiscal();

        cupomFiscal.setCnpjEstabelecimento(findCnpjEstabelecimento(payload));
        cupomFiscal.setDocumentoConsumidor(findDocumentoConsumidor(payload));
        cupomFiscal.setProdutos(findProdutos(payload));
        cupomFiscal.setValorTotal(findValorTotal(payload));
        cupomFiscal.setRawValue(payload);

        return cupomFiscal;
    }

    private String findCnpjEstabelecimento(String payload) {
        Pattern p = Pattern.compile(CNPJ_REGEX);
        Matcher m = p.matcher(payload);
        return m.find() ? m.group(0) : null;
    }

    private String findDocumentoConsumidor(String payload) {
        Pattern p = Pattern.compile(CPF_REGEX);
        Matcher m = p.matcher(payload);
        return m.find() ? m.group(0) : null;
    }

    private String findValorTotal(String payload) {
        Pattern p1 = Pattern.compile(TOTAL_VALUE_1);
        Matcher m1 = p1.matcher(payload);
        if (m1.find()) {
            return m1.group(1);
        }
        Pattern p2 = Pattern.compile(TOTAL_VALUE_2);
        Matcher m2 = p2.matcher(payload);
        if (m2.find()) {
            return m2.group(1);
        }
        Pattern p3 = Pattern.compile(TOTAL_VALUE_3);
        Matcher m3 = p3.matcher(payload);
        if (m3.find()) {
            return m3.group(1);
        }
        return null;
    }

    private List<Produto> findProdutos(String payload) {
        Pattern p = Pattern.compile(PRODUCT_LINES);
        Matcher m = p.matcher(payload);
        if (m.find()) {
            String productLines = m.group(m.groupCount());
            List<Produto> produtos = new ArrayList<>();
            Boolean isLast = false;

            while (!isLast) {
                Pattern p2 = Pattern.compile(PRODUCT_LINE_MIDDLE);
                Matcher m2 = p2.matcher(productLines);
                if (m2.find()) {
                    produtos.add(createProduto(m2.group(0)));
                    productLines = m2.replaceFirst("");
                }
                else {
                    isLast = true;
                    Pattern p3 = Pattern.compile(PRODUCT_LINE_END);
                    Matcher m3 = p3.matcher(productLines);
                    if (m3.find()) {
                        produtos.add(createProduto(m3.group(0)));
                    }
                }
            }
            return produtos;
        }
        return null;
    }

    private Produto createProduto(String payload) {
        Produto produto = new Produto();
        Pattern p = Pattern.compile(PRODUTO_REGEX_1);
        Matcher m = p.matcher(payload);
        if (m.find()) {
            produto.setItemN(Integer.parseInt(m.group(1)));
            produto.setCodigo(m.group(2));
            produto.setDescricao(m.group(3));
            produto.setQuantidade(Integer.parseInt(m.group(4).substring(0, m.group(4).indexOf(",")).replaceAll("\\r\\n|\\r|\\n", " ").replaceAll(" ", "")));
            produto.setValor(m.group(5));
            return produto;
        }
        Pattern p2 = Pattern.compile(PRODUTO_REGEX_2);
        Matcher m2 = p2.matcher(payload);
        if (m2.find()) {
            produto.setItemN(Integer.parseInt(m2.group(1)));
            produto.setCodigo(m2.group(2));
            produto.setDescricao(m2.group(3));
            produto.setQuantidade(1);
            produto.setValor(m2.group(4));
            return produto;
        }
        return produto;
    }
}
