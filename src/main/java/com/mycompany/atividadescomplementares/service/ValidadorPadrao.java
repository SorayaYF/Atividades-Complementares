package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;


public class ValidadorPadrao implements ValidadorHoras {
    @Override
    public int validar(ItemRequerimento item, Atividade atividade) {
        return Math.min(item.obterHorasDeclaradas(), atividade.obterLimiteDeHoras());
    }
}
