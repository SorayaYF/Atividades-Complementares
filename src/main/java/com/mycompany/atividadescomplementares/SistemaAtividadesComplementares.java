package com.mycompany.atividadescomplementares;

import com.mycompany.atividadescomplementares.domain.Aluno;
import com.mycompany.atividadescomplementares.domain.Requerimento;
import com.mycompany.atividadescomplementares.presentation.MenuModalidades;
import com.mycompany.atividadescomplementares.repository.AtividadeRepositoryMemoria;
import com.mycompany.atividadescomplementares.service.AtividadeComplementarFacade;
import com.mycompany.atividadescomplementares.service.GeradorParecerConsole;

import java.util.Scanner;

public class SistemaAtividadesComplementares {
    
    public static void main(String[] args) {
        AtividadeRepositoryMemoria repository = new AtividadeRepositoryMemoria();
        GeradorParecerConsole geradorParecer = new GeradorParecerConsole();
        AtividadeComplementarFacade facade = new AtividadeComplementarFacade(repository, geradorParecer);
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            Aluno aluno = new Aluno("202500789", "João Silva");
            Requerimento requerimento = new Requerimento(aluno);

            new MenuModalidades(scanner, facade, requerimento).executar();
        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}