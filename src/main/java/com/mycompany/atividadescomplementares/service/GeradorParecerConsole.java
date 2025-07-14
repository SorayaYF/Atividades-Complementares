package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.CursoConfig;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;
import com.mycompany.atividadescomplementares.domain.Modalidade;
import com.mycompany.atividadescomplementares.domain.Requerimento;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class GeradorParecerConsole implements GeradorParecer {

    @Override
    public String gerarParecer(Requerimento requerimento) {
        StringBuilder parecer = new StringBuilder();

        parecer.append("=== PARECER DE VALIDAÇÃO ===\n");
        parecer.append(String.format("Matrícula: %s\n", requerimento.obterAluno().obterMatricula()));
        parecer.append(String.format("Nome: %s\n", requerimento.obterAluno().obterNome()));
        parecer.append(String.format("Data emissão: %s\n\n",
                requerimento.obterDataFechamento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        List<ItemRequerimento> itens = requerimento.obterItens();
        for (int i = 0; i < itens.size(); i++) {
            ItemRequerimento item = itens.get(i);
            parecer.append(String.format("Atividade %d:\n", i + 1));
            parecer.append(String.format("  Descrição:       %s\n", item.obterAtividade().obterDescricao()));
            parecer.append(String.format("  Modalidade:      %s\n", item.obterAtividade().obterModalidade().descrever()));
            parecer.append(String.format("  Horas declaradas: %dh\n", item.obterHorasDeclaradas()));
            parecer.append(String.format("  Limite Máximo:   %dh\n", item.obterAtividade().obterLimiteDeHoras()));
            parecer.append(String.format("  Horas validadas:  %dh\n", item.obterHorasValidadas()));
            parecer.append(String.format("  Status:          %s\n", item.obterStatus().descrever()));
            parecer.append(String.format("  Observação:      %s\n\n", item.obterObservacaoAvaliador()));
        }

        parecer.append("=== RESUMO POR MODALIDADE ===\n");
        Map<Modalidade, Integer> horasPorModalidade = requerimento.calcularHorasPorModalidade();
        CursoConfig config = requerimento.obterAluno().obterCursoConfig();

        for (Modalidade modalidade : Modalidade.values()) {
            int horas = horasPorModalidade.getOrDefault(modalidade, 0);
            int limite = config.obterHorasMinimasPorModalidade(modalidade);
            double percentual = requerimento.calcularTotalHorasValidadas() > 0 ?
                    (double) horas / requerimento.calcularTotalHorasValidadas() * 100 : 0;
            double percentualMaximo = modalidade.obterPercentualMaximo() * 100;

            parecer.append(String.format("%s: %dh (%.1f%% - máx: %.0f%%)\n",
                    modalidade.descrever(), horas, percentual, percentualMaximo));
        }

        parecer.append("\n=== RESUMO GERAL ===\n");
        parecer.append(String.format("Total de horas declaradas: %dh\n", requerimento.calcularTotalHorasDeclaradas()));
        parecer.append(String.format("Total de horas validadas:  %dh\n", requerimento.calcularTotalHorasValidadas()));
        parecer.append(String.format("Horas mínimas exigidas:    %dh\n", config.obterHorasMinimasTotal()));

        boolean aprovado = requerimento.estaAprovado();
        parecer.append(String.format("RESULTADO FINAL: %s\n", aprovado ? "APROVADO" : "REPROVADO"));

        if (!aprovado) {
            parecer.append("\nMOTIVOS DA REPROVAÇÃO:\n");
            if (requerimento.calcularTotalHorasValidadas() < config.obterHorasMinimasTotal()) {
                parecer.append("- Total de horas insuficiente\n");
            }

            for (Modalidade modalidade : Modalidade.values()) {
                int horas = horasPorModalidade.getOrDefault(modalidade, 0);
                double percentual = (double) horas / requerimento.calcularTotalHorasValidadas();
                if (percentual > modalidade.obterPercentualMaximo()) {
                    parecer.append(String.format("- Excesso de horas na modalidade %s\n", modalidade.descrever()));
                }
            }
        }

        return parecer.toString();
    }
}