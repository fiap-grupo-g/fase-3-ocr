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

    @OneToOne
    @JoinColumn(name = "CUPOM_FISCAL_DOCUMENT_PROCESSED_ID", nullable = false)
    private CupomFiscalDocumentProcessed documentProcessed;

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

    public CupomFiscalDocumentProcessed getDocumentProcessed() {
        return documentProcessed;
    }

    public CupomFiscal setDocumentProcessed(
            CupomFiscalDocumentProcessed documentProcessed) {
        this.documentProcessed = documentProcessed;
        return this;
    }

    public void merge(CupomFiscal cupomFiscal) {
        if (cnpjEstabelecimento == null) cnpjEstabelecimento = cupomFiscal.cnpjEstabelecimento;
        if (documentoConsumidor == null) documentoConsumidor = cupomFiscal.documentoConsumidor;
        if (valorTotal == null) valorTotal = cupomFiscal.valorTotal;
        if (produtos.isEmpty()) produtos = cupomFiscal.produtos;
        if (cupomFiscal.documentProcessed != null) documentProcessed = cupomFiscal.documentProcessed;
    }

    @Override
    public String toString() {
        return "CupomFiscal{" +
                "cupomFiscalDocumentProcessed=" + documentProcessed +
                ", cnpjEstabelecimento='" + cnpjEstabelecimento + '\'' +
                ", documentoConsumidor='" + documentoConsumidor + '\'' +
                ", produtos=" + produtos +
                ", valorTotal='" + valorTotal + '\'' +
                ", rawValue='" + rawValue + '\'' +
                '}';
    }
}