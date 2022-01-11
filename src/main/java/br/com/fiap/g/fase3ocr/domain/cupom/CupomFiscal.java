package br.com.fiap.g.fase3ocr.domain.cupom;

import br.com.fiap.g.fase3ocr.domain.ocr.OcrPayload;
import br.com.fiap.g.fase3ocr.domain.produto.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CUPOM_FISCAL")
public class CupomFiscal implements OcrPayload {

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

    public CupomFiscal setDocumentProcessed(
            CupomFiscalDocumentProcessed documentProcessed) {
        this.documentProcessed = documentProcessed;
        return this;
    }

    public CupomFiscal setCnpjEstabelecimento(String cnpjEstabelecimento) {
        this.cnpjEstabelecimento = cnpjEstabelecimento;
        return this;
    }

    public CupomFiscal setDocumentoConsumidor(String documentoConsumidor) {
        this.documentoConsumidor = documentoConsumidor;
        return this;
    }

    public CupomFiscal setProdutos(
            List<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public CupomFiscal setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public CupomFiscal setRawValue(String rawValue) {
        this.rawValue = rawValue;
        return this;
    }

    public String getCnpjEstabelecimento() {
        return cnpjEstabelecimento;
    }

    public String getDocumentoConsumidor() {
        return documentoConsumidor;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public String getRawValue() {
        return rawValue;
    }

    public CupomFiscalDocumentProcessed getDocumentProcessed() {
        return documentProcessed;
    }

    public Boolean isComplete() {
        return cnpjEstabelecimento != null &&
                produtos != null &&
                valorTotal != null;
    }

    public void merge(CupomFiscal cupomFiscal) {
        if (cnpjEstabelecimento == null) cnpjEstabelecimento = cupomFiscal.cnpjEstabelecimento;
        if (documentoConsumidor == null) documentoConsumidor = cupomFiscal.documentoConsumidor;
        if (valorTotal == null) valorTotal = cupomFiscal.valorTotal;
        if (produtos == null) produtos = cupomFiscal.produtos;
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
