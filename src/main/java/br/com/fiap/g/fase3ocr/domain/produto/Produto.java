package br.com.fiap.g.fase3ocr.domain.produto;

import br.com.fiap.g.fase3ocr.domain.cupom.CupomFiscal;

import javax.persistence.*;

@Entity
@Table(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUPOM_FISCAL_ID")
    private CupomFiscal cupomFiscal;

    @Column(name = "ITEM_N")
    private int itemN;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "QUANTIDADE")
    private int quantidade;

    @Column(name = "VALOR")
    private String valor;

    public Produto() {
    }

    public Produto setCupomFiscal(CupomFiscal cupomFiscal) {
        this.cupomFiscal = cupomFiscal;
        return this;
    }

    public int getItemN() {
        return itemN;
    }

    public Produto setItemN(int itemN) {
        this.itemN = itemN;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public Produto setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public String getValor() {
        return valor;
    }

    public Produto setValor(String valor) {
        this.valor = valor;
        return this;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "itemN=" + itemN +
                ", codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", valor='" + valor + '\'' +
                '}';
    }
}
