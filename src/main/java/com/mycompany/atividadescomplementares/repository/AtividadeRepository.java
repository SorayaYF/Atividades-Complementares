package com.mycompany.atividadescomplementares.repository;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.Modalidade;

import java.util.List;
import java.util.Optional;

public interface AtividadeRepository {
    List<Atividade> buscarPorModalidade(Modalidade modalidade);
    Optional<Atividade> buscarPorCodigo(String codigo);
    List<Atividade> buscarTodas();
}