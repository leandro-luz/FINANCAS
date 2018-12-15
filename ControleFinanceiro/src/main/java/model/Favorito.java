package model;

import java.io.Serializable;

public class Favorito implements Serializable {
    private Integer idFavorito;
    private String descricao;
    private String opcao;
    private Integer idOpcao;

    private String categoriaNome;
    private String tipoNome;
    private String itemNome;
    private String subItemNome;
    private String elementoNome;
    private String bancoNome;
    private String contaNome;

    private Integer idCategoria;
    private Integer idTipo;
    private Integer idItem;
    private Integer idSubItem;
    private Integer idElemento;
    private Integer idBanco;
    private Integer idConta;

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public Integer getIdOpcao() {
        return idOpcao;
    }

    public void setIdOpcao(Integer idOpcao) {
        this.idOpcao = idOpcao;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public String getBancoNome() {
        return bancoNome;
    }

    public void setBancoNome(String bancoNome) {
        this.bancoNome = bancoNome;
    }

    public String getContaNome() {
        return contaNome;
    }

    public void setContaNome(String contaNome) {
        this.contaNome = contaNome;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public Integer getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(Integer idFavorito) {
        this.idFavorito = idFavorito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public String getTipoNome() {
        return tipoNome;
    }

    public void setTipoNome(String tipoNome) {
        this.tipoNome = tipoNome;
    }

    public String getItemNome() {
        return itemNome;
    }

    public void setItemNome(String itemNome) {
        this.itemNome = itemNome;
    }

    public String getSubItemNome() {
        return subItemNome;
    }

    public void setSubItemNome(String subItemNome) {
        this.subItemNome = subItemNome;
    }

    public String getElementoNome() {
        return elementoNome;
    }

    public void setElementoNome(String elementoNome) {
        this.elementoNome = elementoNome;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdSubItem() {
        return idSubItem;
    }

    public void setIdSubItem(Integer idSubItem) {
        this.idSubItem = idSubItem;
    }


}
