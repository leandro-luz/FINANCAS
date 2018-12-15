package model;

import java.io.Serializable;

public class Elemento implements Serializable {
    private Integer idElemento;
    private Integer idCategoria;
    private String categoriaNome;
    private Integer idTipo;
    private String tipoNome;
    private Integer idItem;
    private String itemNome;
    private Integer idSubItem;
    private String subitemNome;
    private String descricao;
    private Integer habilitado;
    private Integer favorito;

    public Integer getFavorito() {
        return favorito;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
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

    public String getSubitemNome() {
        return subitemNome;
    }

    public void setSubitemNome(String subitemNome) {
        this.subitemNome = subitemNome;
    }

    public Integer getIdSubItem() {
        return idSubItem;
    }

    public void setIdSubItem(Integer idSubItem) {
        this.idSubItem = idSubItem;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Integer habilitado) {
        this.habilitado = habilitado;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }
}
