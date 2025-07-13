/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atividadescomplementares.domain;

public class ItemRequerimento {

    private int horasDeclaradas;
    private int horasValidadas;
    private StatusItem status;
    private DocumentoComprovatorio documento;
    private Atividade atividade;

    public ItemRequerimento(int horasDeclaradas, DocumentoComprovatorio documento, Atividade atividade) {
        this.horasDeclaradas = horasDeclaradas;
        this.documento = documento;
        this.atividade = atividade;
        this.status = StatusItem.PENDENTE;
    }

    public int obterHorasDeclaradas() {
        return horasDeclaradas;
    }

    public int obterHorasValidadas() {
        return horasValidadas;
    }

    public void definirHorasValidadas(int horasValidadas) {
        this.horasValidadas = horasValidadas;
    }

    public StatusItem obterStatus() {
        return status;
    }

    public void definirStatus(StatusItem status) {
        this.status = status;
    }

    public DocumentoComprovatorio obterDocumento() {
        return documento;
    }

    public Atividade obterAtividade() {
        return atividade;
    }
}
