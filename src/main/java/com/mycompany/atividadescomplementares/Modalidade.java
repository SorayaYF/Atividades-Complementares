package com.mycompany.atividadescomplementares;

public enum Modalidade {
    ENSINO("Atividades de Ensino"),
    PESQUISA("Atividades de Pesquisa"),
    EXTENSAO("Atividades de Extensão"),
    COMPLEMENTACAO("Atividades de Complementação Profissional");

    private final String descricao;

    Modalidade(String descricao) {
        this.descricao = descricao;
    }


    public String descrever() {
        return this.descricao;
    }
}
