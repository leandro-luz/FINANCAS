package model;

import java.io.Serializable;

public class Carteira implements Serializable{
    private Integer idCarteira;
    private Banco banco;
    private Conta conta;

    public Integer getIdCarteira()  {
        return idCarteira;
    }

    public void setIdCarteira(Integer idCarteira) {
        this.idCarteira = idCarteira;
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
}
