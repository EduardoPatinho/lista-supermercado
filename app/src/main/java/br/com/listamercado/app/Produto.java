package br.com.listamercado.app;

import com.orm.SugarRecord;

/**
 * Created by 16254844 on 25/10/2017.
 */

public class Produto extends SugarRecord{

    private String nome;
    private boolean ativo;

    //Construtoras ; Obrigatório por causa da ORM

    private Produto(){}

    public Produto(String nome, boolean ativo){
        this.nome = nome;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
