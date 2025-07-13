package com.mycompany.atividadescomplementares.presentation;

import com.mycompany.atividadescomplementares.domain.Modalidade;
import com.mycompany.atividadescomplementares.domain.Requerimento;
import com.mycompany.atividadescomplementares.service.AtividadeComplementarFacade;

import java.util.Scanner;

public class MenuModalidades extends MenuTemplate {
    private final AtividadeComplementarFacade facade;
    private final Requerimento requerimento;
    private boolean continuar = true;
    
    public MenuModalidades(Scanner scanner, AtividadeComplementarFacade facade, Requerimento requerimento) {
        super(scanner);
        this.facade = facade;
        this.requerimento = requerimento;
    }
    
    @Override
    protected void inicializar() {
        System.out.println("Matrícula do aluno: " + requerimento.obterAluno().obterMatricula());
    }
    
    @Override
    protected boolean deveExecutar() {
        return continuar;
    }
    
    @Override
    protected void exibirOpcoes() {
        System.out.println("\n== Menu de Modalidades ==");
        System.out.println("1) Ensino");
        System.out.println("2) Pesquisa");
        System.out.println("3) Extensão");
        System.out.println("4) Complementação");
        System.out.println("0) Finalizar e emitir parecer");
        System.out.print("Escolha a modalidade (0-finalizar): ");
    }
    
    @Override
    protected int lerOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, digite um número válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> new MenuAtividades(scanner, facade, requerimento, Modalidade.ENSINO).executar();
            case 2 -> new MenuAtividades(scanner, facade, requerimento, Modalidade.PESQUISA).executar();
            case 3 -> new MenuAtividades(scanner, facade, requerimento, Modalidade.EXTENSAO).executar();
            case 4 -> new MenuAtividades(scanner, facade, requerimento, Modalidade.COMPLEMENTACAO).executar();
            case 0 -> {
                System.out.println(facade.gerarParecer(requerimento));
                continuar = false;
            }
            default -> System.out.println("Opção inválida!");
        }
    }
    
    @Override
    protected void finalizar() {
        System.out.println("Sistema finalizado.");
    }
}