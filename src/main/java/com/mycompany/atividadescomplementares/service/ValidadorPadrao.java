package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

import java.util.Objects;

public class ValidadorPadrao implements ValidadorHoras {

    private final boolean considerarDocumento;
    private final double fatorReducao;

    public ValidadorPadrao() {
        this(true, 1.0);
    }

    public ValidadorPadrao(boolean considerarDocumento) {
        this(considerarDocumento, 1.0);
    }

    public ValidadorPadrao(boolean considerarDocumento, double fatorReducao) {
        if (fatorReducao <= 0 || fatorReducao > 1.0) {
            throw new IllegalArgumentException("Fator de redução deve estar entre 0 e 1");
        }

        this.considerarDocumento = considerarDocumento;
        this.fatorReducao = fatorReducao;
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

        int horasDeclaradas = item.obterHorasDeclaradas();
        int limiteAtividade = atividade.obterLimiteDeHoras();

        if (considerarDocumento && !item.possuiDocumento()) {
            return 0;
        }

        int horasValidadas = Math.min(horasDeclaradas, limiteAtividade);

        if (fatorReducao < 1.0) {
            horasValidadas = (int) Math.floor(horasValidadas * fatorReducao);
        }

        if (considerarDocumento && !item.possuiDocumento()) {
            horasValidadas = (int) Math.floor(horasValidadas * 0.8);
        }

        return horasValidadas;
    }

    public boolean consideraDocumento() {
        return this.considerarDocumento;
    }

    public double obterFatorReducao() {
        return this.fatorReducao;
    }

    @Override
    public String obterDescricaoValidacao() {
        StringBuilder descricao = new StringBuilder("Validação padrão");

        if (fatorReducao < 1.0) {
            descricao.append(" com fator de redução de ")
                    .append(String.format("%.0f%%", fatorReducao * 100));
        }

        if (considerarDocumento) {
            descricao.append(" - considera documento comprobatório");
        }

        return descricao.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ValidadorPadrao that = (ValidadorPadrao) obj;
        return considerarDocumento == that.considerarDocumento &&
                Double.compare(that.fatorReducao, fatorReducao) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(considerarDocumento, fatorReducao);
    }
}