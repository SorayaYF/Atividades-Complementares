package com.mycompany.atividadescomplementares.service;

import com.mycompany.atividadescomplementares.domain.ItemRequerimento;
import com.mycompany.atividadescomplementares.domain.Requerimento;
import com.mycompany.atividadescomplementares.domain.StatusItem;

public class ParecerServico {

    public void avaliarRequerimento(Requerimento requerimento) {
        if (requerimento.obterStatus() != StatusItem.PENDENTE) {
            throw new IllegalStateException("O requerimento já foi processado e seu status é: " + requerimento.obterStatus());
        }
        if (requerimento.obterItens().isEmpty()){
            throw new IllegalStateException("Não é possível avaliar um requerimento sem itens.");
        }
        
        System.out.println("Iniciando avaliação para o aluno: " + requerimento.obterNome());

        for (ItemRequerimento item : requerimento.obterItens()) {
            
            if (item.possuiDocumento()) {
                int horasParaValidar = (int) (item.obterHorasDeclaradas() * 0.75);
                item.validarHoras(horasParaValidar, "Horas validadas pela avaliação padrão do serviço.");
                System.out.println("Item " + item.obterIdentificador() + " validado com " + horasParaValidar + " horas.");
            } else {
                item.reprovar("Reprovado por falta de documento comprobatório.");
                 System.out.println("Item " + item.obterIdentificador() + " reprovado.");
            }
        }

        System.out.println("Avaliando o resultado final do requerimento...");
        requerimento.processar();

        System.out.println("Avaliação concluída! Status final do requerimento: " + requerimento.obterStatus());
    }
}