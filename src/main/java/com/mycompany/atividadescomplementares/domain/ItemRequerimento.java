package com.mycompany.atividadescomplementares.domain;

import java.util.Objects;
import java.util.UUID;

public class ItemRequerimento {
    private final UUID id;
    private int horasDeclaradas;
    private int horasValidadas;
    private String observacaoAvaliador;
    private StatusItem status;
    private DocumentoComprobatorio documento;

    public ItemRequerimento(int horasDeclaradas) {
        this.id = UUID.randomUUID();
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

    public void validarHoras(int horasValidadas, String observacao) {
        if (horasValidadas < 0) {
            throw new IllegalArgumentException("Horas validadas não podem ser negativas");
        }
        if (horasValidadas > this.horasDeclaradas) {
            throw new IllegalArgumentException("Horas validadas não podem exceder as horas declaradas");
        }

        this.horasValidadas = horasValidadas;
        this.observacaoAvaliador = observacao != null ? observacao : "";
        this.status = horasValidadas > 0 ? StatusItem.APROVADO : StatusItem.REPROVADO;
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

    public boolean estaPendente() {
        return this.status == StatusItem.PENDENTE;
    }

    public int obterHorasDeclaradas() {
        return this.horasDeclaradas;
    }

    public int obterHorasValidadas() {
        return this.horasValidadas;
    }

    public String obterObservacaoAvaliador() {
        return this.observacaoAvaliador;
    }

    public StatusItem obterStatus() {
        return this.status;
    }

    public DocumentoComprobatorio obterDocumento() {
        return this.documento;
    }

    public UUID obterIdentificador() {
        return this.id;
    }

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
