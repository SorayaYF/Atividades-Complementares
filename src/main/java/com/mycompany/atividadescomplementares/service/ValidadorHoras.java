
package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;


public interface ValidadorHoras {
    
    int validar(ItemRequerimento item, Atividade atividade);
}

   