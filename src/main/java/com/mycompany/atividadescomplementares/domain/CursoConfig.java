package com.mycompany.atividadescomplementares.domain;

import java.util.HashMap;
import java.util.Map;

public class CursoConfig {
    private final String codigoCurso;
    private final int horasMinimasTotal;
    private final Map<Modalidade, Integer> horasMinimasPorModalidade;

    public CursoConfig(String codigoCurso, int horasMinimasTotal) {
        this.codigoCurso = codigoCurso;
        this.horasMinimasTotal = horasMinimasTotal;
        this.horasMinimasPorModalidade = new HashMap<>();
        calcularHorasMinimasPorModalidade();
    }

    private void calcularHorasMinimasPorModalidade() {
        for (Modalidade modalidade : Modalidade.values()) {
            int horasMinimas = (int) (horasMinimasTotal * modalidade.obterPercentualMaximo());
            horasMinimasPorModalidade.put(modalidade, horasMinimas);
        }
    }

    public int obterHorasMinimasTotal() {
        return horasMinimasTotal;
    }

    public int obterHorasMinimasPorModalidade(Modalidade modalidade) {
        return horasMinimasPorModalidade.getOrDefault(modalidade, 0);
    }

    public boolean validarDistribuicaoHoras(Map<Modalidade, Integer> horasPorModalidade, int totalHoras) {
        for (Map.Entry<Modalidade, Integer> entry : horasPorModalidade.entrySet()) {
            Modalidade modalidade = entry.getKey();
            Integer horas = entry.getValue();
            
            double percentualAtual = (double) horas / totalHoras;
            if (percentualAtual > modalidade.obterPercentualMaximo()) {
                return false;
            }
        }
        return totalHoras >= horasMinimasTotal;
    }

    public String obterCodigoCurso() {
        return codigoCurso;
    }
}