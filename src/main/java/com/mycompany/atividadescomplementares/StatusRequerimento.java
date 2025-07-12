package com.mycompany.atividadescomplementares;

public enum StatusRequerimento {
    ABERTO("Aberto para edição"),
    EM_AVALIACAO("Em avaliação pelo coordenador"),
    FINALIZADO("Avaliação concluída");

    private final String descricao;

    StatusRequerimento(String descricao) {
        this.descricao = descricao;
    }
    
    public boolean podeSerEditado() {
        return this == ABERTO;
    }

    public boolean podeMudarParaFinalizado() {
        return this == EM_AVALIACAO;
    }
  
    public boolean estaFinalizado() {
        return this == FINALIZADO;
    }

    public String descrever() {
        return this.descricao;
    }
}
