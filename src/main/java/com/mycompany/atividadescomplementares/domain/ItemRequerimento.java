package com.mycompany.atividadescomplementares.domain;

import java.util.Objects;
import java.util.UUID;

public class ItemRequerimento {
    private final UUID id;
    private final Atividade atividade;
    private final int horasDeclaradas;
    private int horasValidadas;
    private String observacaoAvaliador;
    private StatusItem status;
    private DocumentoComprobatorio documento;

    public ItemRequerimento(Atividade atividade, int horasDeclaradas) {
        if (atividade == null) {
            throw new IllegalArgumentException("Atividade é obrigatória");
        }
        if (horasDeclaradas <= 0) {
            throw new IllegalArgumentException("Horas declaradas devem ser positivas");
        }

        this.id = UUID.randomUUID();
        this.atividade = atividade;
        this.horasDeclaradas = horasDeclaradas;
        this.horasValidadas = 0;
        this.status = StatusItem.PENDENTE;
        this.observacaoAvaliador = "";
    }

    public void anexarDocumento(String nomeArquivo, String url) {
        if (nomeArquivo == null || nomeArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo não pode estar vazio");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL do documento não pode estar vazia");
        }
        this.documento = new DocumentoComprobatorio(nomeArquivo, url);
    }

    public void validarAutomaticamente() {
        int horasCalculadas = this.atividade.calcularHorasValidas(this.horasDeclaradas);
        String observacao = gerarObservacaoAutomatica(horasCalculadas);
        validarHoras(horasCalculadas, observacao);
    }

    private String gerarObservacaoAutomatica(int horasCalculadas) {
        if (horasCalculadas < this.horasDeclaradas) {
            return String.format("Horas declaradas (%dh) excedem o limite (%dh); ajustadas para %dh.",
                    this.horasDeclaradas, this.atividade.obterLimiteDeHoras(), horasCalculadas);
        }
        return "-- (sem ajuste)";
    }

    public void validarHoras(int horasValidadas, String observacao) {
        if (horasValidadas < 0) {
            throw new IllegalArgumentException("Horas validadas não podem ser negativas");
        }
        if (horasValidadas > this.horasDeclaradas) {
            throw new IllegalArgumentException("Horas validadas não podem exceder as horas declaradas");
        }

        this.horasValidadas = horasValidadas;
        this.observacaoAvaliador = observacao != null ? observacao : "";

        if (horasValidadas == 0) {
            this.status = StatusItem.REPROVADO;
        } else if (horasValidadas < this.horasDeclaradas) {
            this.status = StatusItem.APROVADO_PARCIALMENTE;
        } else {
            this.status = StatusItem.APROVADO;
        }
    }

    public void reprovar(String observacao) {
        this.horasValidadas = 0;
        this.observacaoAvaliador = observacao != null ? observacao : "";
        this.status = StatusItem.REPROVADO;
    }

    public boolean possuiDocumento() {
        return this.documento != null;
    }

    public boolean estaAprovado() {
        return this.status == StatusItem.APROVADO;
    }

    public boolean estaAprovadoParcialmente() {
        return this.status == StatusItem.APROVADO_PARCIALMENTE;
    }

    public boolean estaPendente() {
        return this.status == StatusItem.PENDENTE;
    }

    public UUID obterIdentificador() { return this.id; }
    public Atividade obterAtividade() { return this.atividade; }
    public int obterHorasDeclaradas() { return this.horasDeclaradas; }
    public int obterHorasValidadas() { return this.horasValidadas; }
    public String obterObservacaoAvaliador() { return this.observacaoAvaliador; }
    public StatusItem obterStatus() { return this.status; }
    public DocumentoComprobatorio obterDocumento() { return this.documento; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemRequerimento that = (ItemRequerimento) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}