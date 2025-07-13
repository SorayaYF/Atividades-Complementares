
package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

import java.util.Objects;


public class ValidadorPorDocumento implements ValidadorHoras {

    private final boolean verificarTipoDocumento;

    public ValidadorPorDocumento() {
        this(false);
    }

    public ValidadorPorDocumento(boolean verificarTipoDocumento) {
        this.verificarTipoDocumento = verificarTipoDocumento;
    }

    @Override
    public int validar(ItemRequerimento item, Object atividadeObj) {
        if (item == null) {
            throw new IllegalArgumentException("Item de requerimento não pode ser nulo");
        }
        if (atividadeObj == null) {
            throw new IllegalArgumentException("Atividade não pode ser nula");
        }
        if (!(atividadeObj instanceof Atividade)) {
            throw new IllegalArgumentException("Objeto atividade deve implementar a interface Atividade");
        }

        Atividade atividade = (Atividade) atividadeObj;

        if (!item.possuiDocumento()) {
            return 0;
        }

        if (item.obterDocumento().obterUrl() == null ||
                item.obterDocumento().obterUrl().trim().isEmpty()) {
            return 0;
        }

        if (verificarTipoDocumento && !documentoEhValido(item)) {
            return 0;
        }

        int horasDeclaradas = item.obterHorasDeclaradas();
        int limiteAtividade = atividade.obterLimiteDeHoras();

        return Math.min(horasDeclaradas, limiteAtividade);
    }

    private boolean documentoEhValido(ItemRequerimento item) {
        if (!item.possuiDocumento()) {
            return false;
        }

        return item.obterDocumento().ehPdf() || item.obterDocumento().ehImagem();
    }

    public boolean verificaTipoDocumento() {
        return this.verificarTipoDocumento;
    }

    @Override
    public String obterDescricaoValidacao() {
        return "Validação por documento - exige documento comprobatório válido";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ValidadorPorDocumento that = (ValidadorPorDocumento) obj;
        return verificarTipoDocumento == that.verificarTipoDocumento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(verificarTipoDocumento);
    }
}
