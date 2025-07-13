package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.Modalidade;
import com.mycompany.atividadescomplementares.domain.Requerimento;
import com.mycompany.atividadescomplementares.repository.AtividadeRepository;

import java.util.List;

public class AtividadeComplementarFacade {
    private final AtividadeRepository atividadeRepository;
    private final GeradorParecer geradorParecer;
    
    public AtividadeComplementarFacade(AtividadeRepository atividadeRepository, GeradorParecer geradorParecer) {
        this.atividadeRepository = atividadeRepository;
        this.geradorParecer = geradorParecer;
    }
    
    public List<Atividade> listarAtividadesPorModalidade(Modalidade modalidade) {
        return atividadeRepository.buscarPorModalidade(modalidade);
    }
    
    public Atividade buscarAtividade(String codigo) {
        return atividadeRepository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada: " + codigo));
    }
    
    public String gerarParecer(Requerimento requerimento) {
        if (!requerimento.obterStatus().estaFinalizado()) {
            requerimento.processar();
        }
        return geradorParecer.gerarParecer(requerimento);
    }
}