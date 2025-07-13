
package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;


public class ValidadorPorDocumento implements ValidadorHoras {
    @Override
    public int validar(ItemRequerimento item, Atividade atividade) {
        if (item.obterDocumento() != null && item.obterDocumento().obterUrl() != null) {
            return Math.min(item.obterHorasDeclaradas(), atividade.obterLimiteDeHoras());
        }
        return 0;
    }
}
