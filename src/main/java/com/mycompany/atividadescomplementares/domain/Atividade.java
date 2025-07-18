package com.mycompany.atividadescomplementares.domain;

import java.util.Objects;

public class Atividade {
    private final String codigo;
    private final String descricao;
    private final int limiteDeHoras;
    private final Modalidade modalidade;
    private final String documentacaoComprobatoria;
    private final boolean requerDocumento;

    public Atividade(String codigo, String descricao, int limiteDeHoras, Modalidade modalidade,
                     String documentacaoComprobatoria, boolean requerDocumento) {
        validarArgumentos(codigo, descricao, limiteDeHoras, modalidade);
        this.codigo = codigo;
        this.descricao = descricao;
        this.limiteDeHoras = limiteDeHoras;
        this.modalidade = modalidade;
        this.documentacaoComprobatoria = documentacaoComprobatoria;
        this.requerDocumento = requerDocumento;
    }

    private void validarArgumentos(String codigo, String descricao, int limiteDeHoras, Modalidade modalidade) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código da atividade não pode ser nulo ou vazio.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da atividade não pode ser nula ou vazia.");
        }
        if (limiteDeHoras <= 0) {
            throw new IllegalArgumentException("O limite de horas deve ser um número positivo.");
        }
        if (modalidade == null) {
            throw new IllegalArgumentException("A modalidade não pode ser nula.");
        }
    }

    public int calcularHorasValidas(int horasDeclaradas) {
        return Math.min(horasDeclaradas, this.limiteDeHoras);
    }

    public boolean eDaModalidade(Modalidade modalidade) {
        return this.modalidade.equals(modalidade);
    }

    public String obterCodigo() {
        return codigo;
    }

    public String obterDescricao() {
        return descricao;
    }

    public int obterLimiteDeHoras() {
        return limiteDeHoras;
    }

    public Modalidade obterModalidade() {
        return modalidade;
    }

    public String obterDocumentacaoComprobatoria() {
        return documentacaoComprobatoria;
    }

    public boolean requerDocumento() {
        return requerDocumento;
    }

    @Override
    public String toString() {
        return "Atividade '" + this.descricao + "' (Código: " + this.codigo + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividade atividade = (Atividade) o;
        return Objects.equals(codigo, atividade.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
