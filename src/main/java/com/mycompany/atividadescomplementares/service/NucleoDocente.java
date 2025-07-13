
package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.StatusItem;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

/**
 *
 * @author Manoela Melo
 */

public class NucleoDocente implements IAvaliador {
    private ValidadorHoras validador;

    public NucleoDocente(ValidadorHoras validador) {
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
