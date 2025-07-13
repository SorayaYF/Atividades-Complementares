package com.mycompany.atividadescomplementares.domain;

public class DocumentoComprovatorio {

    private String nomeArquivo;
    private String url;

    public DocumentoComprovatorio(String nomeArquivo, String url) {
        this.nomeArquivo = nomeArquivo;
        this.url = url;
    }

    public String obterUrl() {
        return url;
    }
}
