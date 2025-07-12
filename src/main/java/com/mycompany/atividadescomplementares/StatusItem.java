package com.mycompany.atividadescomplementares;

public enum StatusItem {
    PENDENTE("Pendente de avaliação"),
    APROVADO("Aprovado"),
    APROVADO_PARCIALMENTE("Aprovado com ajuste de horas"),
    REPROVADO("Reprovado");

    private final String descricao;

    StatusItem(String descricao) {
        this.descricao = descricao;
    }

    public String descrever() {
        return this.descricao;
    }
}