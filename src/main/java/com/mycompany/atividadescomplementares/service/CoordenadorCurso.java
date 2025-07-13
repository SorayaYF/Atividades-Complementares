package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

import java.util.Objects;

public class CoordenadorCurso implements IAvaliador {

    private final String nome;
    private final ValidadorHoras validador;

    public CoordenadorCurso(String nome, ValidadorHoras validador) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do coordenador é obrigatório");
        }
        if (validador == null) {
            throw new IllegalArgumentException("Validador de horas é obrigatório");
        }

        this.nome = nome.trim();
        this.validador = validador;
    }

    @Override
    public void avaliarItem(ItemRequerimento item, Object atividade) {
        if (item == null) {
            throw new IllegalArgumentException("Item de requerimento não pode ser nulo");
        }
        if (atividade == null) {
            throw new IllegalArgumentException("Atividade não pode ser nula");
        }

        if (!item.estaPendente()) {
            throw new IllegalStateException("Item já foi avaliado anteriormente");
        }

        if (!item.possuiDocumento()) {
            item.reprovar("Item reprovado: documento comprobatório não foi anexado");
            return;
        }

        try {
            int horasValidadas = validador.validar(item, atividade);
            String observacao = gerarObservacaoAvaliacao(item, horasValidadas);

            item.validarHoras(horasValidadas, observacao);

        } catch (Exception e) {
            item.reprovar("Erro na validação: " + e.getMessage());
        }
    }

    private String gerarObservacaoAvaliacao(ItemRequerimento item, int horasValidadas) {
        StringBuilder observacao = new StringBuilder();
        observacao.append("Avaliado por: ").append(this.nome).append(". ");

        if (horasValidadas == 0) {
            observacao.append("Item reprovado - não atende aos critérios da atividade.");
        } else if (horasValidadas < item.obterHorasDeclaradas()) {
            observacao.append("Aprovado parcialmente - ")
                    .append(horasValidadas)
                    .append(" horas validadas de ")
                    .append(item.obterHorasDeclaradas())
                    .append(" horas declaradas.");
        } else {
            observacao.append("Item aprovado integralmente.");
        }

        return observacao.toString();
    }

    public void avaliarItemComObservacao(ItemRequerimento item, Object atividade, String observacaoAdicional) {
        avaliarItem(item, atividade);

        if (observacaoAdicional != null && !observacaoAdicional.trim().isEmpty()) {
            String observacaoAtual = item.obterObservacaoAvaliador();
            item.validarHoras(item.obterHorasValidadas(),
                    observacaoAtual + " Observação adicional: " + observacaoAdicional);
        }
    }

    public String obterNome() {
        return this.nome;
    }

    public ValidadorHoras obterValidador() {
        return this.validador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CoordenadorCurso that = (CoordenadorCurso) obj;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
