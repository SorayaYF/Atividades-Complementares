package com.mycompany.atividadescomplementares.domain;

import java.time.LocalDate;
import java.util.Objects;

public class DocumentoComprobatorio {
    private final String nomeArquivo;
    private final String url;
    private final LocalDate dataUpload;

    public DocumentoComprobatorio(String nomeArquivo, String url) {
        if (nomeArquivo == null || nomeArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo é obrigatório");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL é obrigatória");
        }

        this.nomeArquivo = nomeArquivo.trim();
        this.url = url.trim();
        this.dataUpload = LocalDate.now();
    }

    public boolean ehPdf() {
        return this.nomeArquivo.toLowerCase().endsWith(".pdf");
    }

    public boolean ehImagem() {
        String nomeMinusculo = this.nomeArquivo.toLowerCase();
        return nomeMinusculo.endsWith(".jpg") ||
                nomeMinusculo.endsWith(".jpeg") ||
                nomeMinusculo.endsWith(".png") ||
                nomeMinusculo.endsWith(".gif");
    }

    public String obterExtensao() {
        int ultimoPonto = this.nomeArquivo.lastIndexOf('.');
        return ultimoPonto > 0 ? this.nomeArquivo.substring(ultimoPonto + 1).toLowerCase() : "";
    }

    public String obterNomeArquivo() {
        return this.nomeArquivo;
    }

    public String obterUrl() {
        return this.url;
    }

    public LocalDate obterDataUpload() {
        return this.dataUpload;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DocumentoComprobatorio that = (DocumentoComprobatorio) obj;
        return Objects.equals(nomeArquivo, that.nomeArquivo) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeArquivo, url);
    }
}