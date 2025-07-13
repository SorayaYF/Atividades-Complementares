/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.StatusItem;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

public class CoordenadorCurso implements IAvaliador {

    private String nome;
    private ValidadorHoras validador;

    public CoordenadorCurso(String nome, ValidadorHoras validador) {
        this.nome = nome;
        this.validador = validador;
    }

    @Override
    public void avaliarItem(ItemRequerimento item) {
        int horasValidadas = validador.validar(item, item.obterAtividade());
        item.definirHorasValidadas(horasValidadas);

        if (horasValidadas == 0) {
            item.definirStatus(StatusItem.REPROVADO);
        } else if (horasValidadas < item.obterHorasDeclaradas()) {
            item.definirStatus(StatusItem.APROVADO_PARCIALMENTE);
        } else {
            item.definirStatus(StatusItem.APROVADO);
        }
    }
}
