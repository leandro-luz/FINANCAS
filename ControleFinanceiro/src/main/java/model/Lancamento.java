package model;

import java.io.Serializable;
import java.util.Date;

public class Lancamento implements Serializable {
    private Integer idLancamento;
    private String codigo;
    private Categoria categoria;
    private Tipo tipo;
    private Item item;
    private SubItem subitem;
    private Elemento elemento;
    private Integer habilitado;
    private Banco banco;
    private Conta conta;
    private String data;
    private Float valor;
    private Integer idFavoritoEstrutura;
    private String favoritoEstruturaNome;
    private Integer idFavoritoConta;
    private String favoritoContaNome;

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SubItem getSubitem() {
        return subitem;
    }

    public void setSubitem(SubItem subitem) {
        this.subitem = subitem;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public Integer getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Integer habilitado) {
        this.habilitado = habilitado;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Integer getIdFavoritoEstrutura() {
        return idFavoritoEstrutura;
    }

    public void setIdFavoritoEstrutura(Integer idFavoritoEstrutura) {
        this.idFavoritoEstrutura = idFavoritoEstrutura;
    }

    public String getFavoritoEstruturaNome() {
        return favoritoEstruturaNome;
    }

    public void setFavoritoEstruturaNome(String favoritoEstruturaNome) {
        this.favoritoEstruturaNome = favoritoEstruturaNome;
    }

    public Integer getIdFavoritoConta() {
        return idFavoritoConta;
    }

    public void setIdFavoritoConta(Integer idFavoritoConta) {
        this.idFavoritoConta = idFavoritoConta;
    }

    public String getFavoritoContaNome() {
        return favoritoContaNome;
    }

    public void setFavoritoContaNome(String favoritoContaNome) {
        this.favoritoContaNome = favoritoContaNome;
    }
}
