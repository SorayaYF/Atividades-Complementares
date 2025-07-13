package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

import java.util.Objects;

/**
 *
 * @author Manoela Melo
 */
public class NucleoDocente implements IAvaliador {

    private final ValidadorHoras validador;
    private final String identificacao;

    public NucleoDocente(ValidadorHoras validador) {
        this(validador, "Núcleo Docente");
    }

    public NucleoDocente(ValidadorHoras validador, String identificacao) {
        if (validador == null) {
            throw new IllegalArgumentException("Validador de horas é obrigatório");
        }
        if (identificacao == null || identificacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Identificação do núcleo docente é obrigatória");
        }

        this.validador = validador;
        this.identificacao = identificacao.trim();
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
            item.reprovar("Item reprovado pelo " + this.identificacao + ": documento comprobatório não foi anexado");
            return;
        }

        try {
            int horasValidadas = validador.validar(item, atividade);
            String observacao = gerarObservacaoAvaliacao(item, horasValidadas);

            item.validarHoras(horasValidadas, observacao);

        } catch (Exception e) {
            item.reprovar("Erro na validação pelo " + this.identificacao + ": " + e.getMessage());
        }
    }

    private String gerarObservacaoAvaliacao(ItemRequerimento item, int horasValidadas) {
        StringBuilder observacao = new StringBuilder();
        observacao.append("Avaliado pelo ").append(this.identificacao).append(". ");

        if (horasValidadas == 0) {
            observacao.append("Item reprovado - não atende aos critérios estabelecidos para a atividade.");
        } else if (horasValidadas < item.obterHorasDeclaradas()) {
            observacao.append("Aprovado parcialmente - ")
                    .append(horasValidadas)
                    .append(" horas validadas de ")
                    .append(item.obterHorasDeclaradas())
                    .append(" horas declaradas. ")
                    .append("Ajuste conforme critérios do núcleo docente.");
        } else {
            observacao.append("Item aprovado integralmente conforme critérios do núcleo docente.");
        }

        return observacao.toString();
    }

    public void avaliarItemComJustificativa(ItemRequerimento item, Object atividade, String justificativa) {
        if (justificativa == null || justificativa.trim().isEmpty()) {
            throw new IllegalArgumentException("Justificativa é obrigatória para avaliação com justificativa");
        }

        avaliarItem(item, atividade);

        String observacaoAtual = item.obterObservacaoAvaliador();
        item.validarHoras(item.obterHorasValidadas(),
                observacaoAtual + " Justificativa: " + justificativa.trim());
    }

    public void reavaliarItem(ItemRequerimento item, Object atividade, String motivoReavaliacao) {
        if (item == null) {
            throw new IllegalArgumentException("Item de requerimento não pode ser nulo");
        }
        if (item.estaPendente()) {
            throw new IllegalStateException("Item ainda não foi avaliado inicialmente");
        }
        if (motivoReavaliacao == null || motivoReavaliacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da reavaliação é obrigatório");
        }

        try {
            int horasValidadas = validador.validar(item, atividade);
            String observacao = gerarObservacaoReavaliacao(item, horasValidadas, motivoReavaliacao);

            item.validarHoras(horasValidadas, observacao);

        } catch (Exception e) {
            item.reprovar("Erro na reavaliação pelo " + this.identificacao + ": " + e.getMessage());
        }
    }

    private String gerarObservacaoReavaliacao(ItemRequerimento item, int horasValidadas, String motivo) {
        StringBuilder observacao = new StringBuilder();
        observacao.append("REAVALIAÇÃO pelo ").append(this.identificacao).append(". ");
        observacao.append("Motivo: ").append(motivo).append(". ");

        if (horasValidadas == 0) {
            observacao.append("Item mantido como reprovado após reavaliação.");
        } else if (horasValidadas < item.obterHorasDeclaradas()) {
            observacao.append("Reavaliado para ")
                    .append(horasValidadas)
                    .append(" horas validadas de ")
                    .append(item.obterHorasDeclaradas())
                    .append(" horas declaradas.");
        } else {
            observacao.append("Item reavaliado e aprovado integralmente.");
        }

        return observacao.toString();
    }

    public boolean podeAvaliarAtividade(Object atividade) {
        return atividade != null;
    }

    public String obterIdentificacao() {
        return this.identificacao;
    }

    public ValidadorHoras obterValidador() {
        return this.validador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NucleoDocente that = (NucleoDocente) obj;
        return Objects.equals(identificacao, that.identificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacao);
    }

    @Override
    public String toString() {
        return "NucleoDocente{" +
                "identificacao='" + identificacao + '\'' +
                '}';
    }
}
