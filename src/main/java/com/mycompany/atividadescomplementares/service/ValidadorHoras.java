
package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;


interface ValidadorHoras {
    int validar(ItemRequerimento item, Object atividade);

    default String obterDescricaoValidacao() {
        return this.getClass().getSimpleName();
    }
}

   