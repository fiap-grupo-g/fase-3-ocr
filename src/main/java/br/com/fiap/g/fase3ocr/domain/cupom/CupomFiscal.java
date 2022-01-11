package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.produto.Produto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUPOM_FISCAL")
public class CupomFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "CUPOM_FISCAL_DOCUMENT_ID", nullable = false)
    private CupomFiscalDocument cupomFiscalDocument;

    @Column(name = "CNPJ_ESTABELECIMENTO")
    private String cnpjEstabelecimento;

    @Column(name = "DOCUMENTO_CONSUMIDOR")
    private String documentoConsumidor;

    @OneToMany(mappedBy = "cupomFiscal")
    private List<Produto> produtos;

    @Column(name = "VALOR_TOTAL")
    private String valorTotal;

    @Lob
    @Column(name = "RAW_VALUE", nullable = false)
    private String rawValue;

    public CupomFiscal() {
        this.produtos = new ArrayList<>();
    }

    public CupomFiscal setCupomFiscalDocument(
            CupomFiscalDocument cupomFiscalDocument) {
        this.cupomFiscalDocument = cupomFiscalDocument;
        return this;
    }

    public String getCnpjEstabelecimento() {
        return cnpjEstabelecimento;
    }

    public CupomFiscal setCnpjEstabelecimento(String cnpjEstabelecimento) {
        this.cnpjEstabelecimento = cnpjEstabelecimento;
        return this;
    }

    public String getDocumentoConsumidor() {
        return documentoConsumidor;
    }

    public CupomFiscal setDocumentoConsumidor(String documentoConsumidor) {
        this.documentoConsumidor = documentoConsumidor;
        return this;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public CupomFiscal setProdutos(
            List<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public CupomFiscal setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public String getRawValue() {
        return rawValue;
    }

    public CupomFiscal setRawValue(String rawValue) {
        this.rawValue = rawValue;
        return this;
    }

    public Boolean isComplete() {
        return cnpjEstabelecimento != null &&
                produtos != null &&
                valorTotal != null;
    }

    public void merge(CupomFiscal cupomFiscal) {
        if (cnpjEstabelecimento == null) cnpjEstabelecimento = cupomFiscal.getCnpjEstabelecimento();
        if (documentoConsumidor == null) documentoConsumidor = cupomFiscal.getDocumentoConsumidor();
        if (valorTotal == null) valorTotal = cupomFiscal.getValorTotal();
        if (produtos == null) produtos = cupomFiscal.getProdutos();
        else produtos.addAll(cupomFiscal.getProdutos());
    }

    public Boolean isMoreCompleteThan(CupomFiscal cupomFiscal) {
        if (isComplete()) return true;

        int fieldsFound = 0;
        int comparingFieldsFound = 0;

        fieldsFound += cnpjEstabelecimento != null ? 1 : 0;
        fieldsFound += documentoConsumidor != null ? 1 : 0;
        fieldsFound += valorTotal != null ? 1 : 0;
        fieldsFound += produtos != null ? produtos.size() : 0;

        comparingFieldsFound += cupomFiscal.cnpjEstabelecimento != null ? 1 : 0;
        comparingFieldsFound += cupomFiscal.documentoConsumidor != null ? 1 : 0;
        comparingFieldsFound += cupomFiscal.valorTotal != null ? 1 : 0;
        comparingFieldsFound += cupomFiscal.produtos != null ? cupomFiscal.produtos.size() : 0;

        return comparingFieldsFound > fieldsFound;
    }

    @Override
    public String toString() {
        return "CupomFiscal{" +
                "cnpjEstabelecimento='" + cnpjEstabelecimento + '\'' +
                ", documentoConsumidor='" + documentoConsumidor + '\'' +
                ", produtos=" + produtos +
                ", valorTotal='" + valorTotal + '\'' +
                '}';
    }
}
