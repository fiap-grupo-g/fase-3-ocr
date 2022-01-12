package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.cupom.regex.CnpjFinder;
import br.com.fiap.g.fase3ocr.domain.cupom.regex.CpfFinder;
import br.com.fiap.g.fase3ocr.domain.cupom.regex.ProdutosFinder;
import br.com.fiap.g.fase3ocr.domain.cupom.regex.ValorTotalFinder;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class CupomFiscalParser {

    private CnpjFinder cnpjFinder;
    private CpfFinder cpfFinder;
    private ValorTotalFinder valorTotalFinder;
    private ProdutosFinder produtosFinder;

    public CupomFiscal create(String payload, BufferedImage image) {
        CupomFiscal cupomFiscal = new CupomFiscal();
        cnpjFinder = new CnpjFinder(payload);
        cpfFinder = new CpfFinder(payload);
        valorTotalFinder = new ValorTotalFinder(payload);
        produtosFinder = new ProdutosFinder(payload);

        cupomFiscal.setCnpjEstabelecimento(cnpjFinder.get());
        cupomFiscal.setDocumentoConsumidor(cpfFinder.get());
        cupomFiscal.setValorTotal(valorTotalFinder.get());
        cupomFiscal.setProdutos(produtosFinder.get());
        cupomFiscal.setRawValue(payload);

        CupomFiscalDocumentProcessed processedImage = new CupomFiscalDocumentProcessed();
        processedImage.setBase64File(image);
        cupomFiscal.setDocumentProcessed(processedImage);

        return cupomFiscal;
    }
}