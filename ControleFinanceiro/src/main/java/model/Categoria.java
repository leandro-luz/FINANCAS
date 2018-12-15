package model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private Integer idCategoria;
    private String descricao;
    private Integer habilitado;
    private Integer incremento;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
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

    public Integer getIncremento() {
        return incremento;
    }

    public void setIncremento(Integer incremento) {
        this.incremento = incremento;
    }
}
