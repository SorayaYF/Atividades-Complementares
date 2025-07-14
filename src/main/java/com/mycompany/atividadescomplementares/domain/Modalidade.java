package com.mycompany.atividadescomplementares.domain;

public enum Modalidade {
    ENSINO("Atividades de Ensino", 0.40),
    PESQUISA("Atividades de Pesquisa e Inovação", 0.40),
    EXTENSAO("Atividades de Extensão", 0.40),
    COMPLEMENTACAO("Atividades de Complementação Profissional", 0.20);

    private final String descricao;
    private final double percentualMaximo;

    Modalidade(String descricao, double percentualMaximo) {
        this.descricao = descricao;
        this.percentualMaximo = percentualMaximo;
    }

    public String descrever() {
        return this.descricao;
    }

    public double obterPercentualMaximo() {
        return this.percentualMaximo;
    }
}
