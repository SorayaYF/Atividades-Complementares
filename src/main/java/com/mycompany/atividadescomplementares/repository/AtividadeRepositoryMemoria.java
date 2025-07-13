package com.mycompany.atividadescomplementares.repository;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.Modalidade;

import java.util.*;
import java.util.stream.Collectors;

public class AtividadeRepositoryMemoria implements AtividadeRepository {
    private final Map<String, Atividade> atividades = new HashMap<>();
    
    public AtividadeRepositoryMemoria() {
        inicializarAtividades();
    }
    
    private void inicializarAtividades() {
        // Ensino
        atividades.put("ENS001", new Atividade("ENS001", "Ministrar aula de reforço", 10, Modalidade.ENSINO));
        atividades.put("ENS002", new Atividade("ENS002", "Monitoria de laboratório", 15, Modalidade.ENSINO));
        
        // Pesquisa
        atividades.put("PES001", new Atividade("PES001", "Iniciação científica", 30, Modalidade.PESQUISA));
        atividades.put("PES002", new Atividade("PES002", "Publicação de artigo", 20, Modalidade.PESQUISA));
        
        // Extensão
        atividades.put("EXT001", new Atividade("EXT001", "Projeto social", 25, Modalidade.EXTENSAO));
        atividades.put("EXT002", new Atividade("EXT002", "Curso para comunidade", 12, Modalidade.EXTENSAO));
        
        // Complementação
        atividades.put("COM001", new Atividade("COM001", "Palestra técnica", 5, Modalidade.COMPLEMENTACAO));
        atividades.put("COM002", new Atividade("COM002", "Visita técnica", 8, Modalidade.COMPLEMENTACAO));
    }
    
    @Override
    public List<Atividade> buscarPorModalidade(Modalidade modalidade) {
        return atividades.values().stream()
                .filter(atividade -> atividade.eDaModalidade(modalidade))
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Atividade> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(atividades.get(codigo));
    }
    
    @Override
    public List<Atividade> buscarTodas() {
        return new ArrayList<>(atividades.values());
    }
}