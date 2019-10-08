package br.ifsc.edu.bloco;

public class Nota {
    int id;
    String nome;
    Long dataCriacao;
    Long dataModificacao;

    public Nota() {

    }

    public Nota(int id, String nome, Long dataCriacao, Long dataModificacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Long dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Long dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}
