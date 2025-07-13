package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.ItemRequerimento;
import com.mycompany.atividadescomplementares.domain.Requerimento;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeradorParecerConsole implements GeradorParecer {
    
    @Override
    public String gerarParecer(Requerimento requerimento) {
        StringBuilder parecer = new StringBuilder();
        
        parecer.append("=== PARECER DE VALIDAÇÃO ===\n");
        parecer.append(String.format("Matrícula: %s\n", requerimento.obterAluno().obterMatricula()));
        parecer.append(String.format("Data emissão: %s\n\n", 
                      requerimento.obterDataFechamento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        
        List<ItemRequerimento> itens = requerimento.obterItens();
        for (int i = 0; i < itens.size(); i++) {
            ItemRequerimento item = itens.get(i);
            parecer.append(String.format("Atividade %d:\n", i + 1));
            parecer.append(String.format("  Descrição:       %s\n", item.obterAtividade().obterDescricao()));
            parecer.append(String.format("  Horas declaradas: %dh\n", item.obterHorasDeclaradas()));
            parecer.append(String.format("  Limite Máximo:   %dh\n", item.obterAtividade().obterLimiteDeHoras()));
            parecer.append(String.format("  Horas validadas:  %dh\n", item.obterHorasValidadas()));
            parecer.append(String.format("  Observação:      %s\n\n", item.obterObservacaoAvaliador()));
        }
        
        parecer.append("Resumo geral:\n");
        parecer.append(String.format("  Total de horas declaradas: %dh\n", requerimento.calcularTotalHorasDeclaradas()));
        parecer.append(String.format("  Total de horas validadas:  %dh\n", requerimento.calcularTotalHorasValidadas()));
        
        return parecer.toString();
    }
}