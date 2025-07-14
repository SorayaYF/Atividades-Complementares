package com.mycompany.atividadescomplementares.presentation;

import com.mycompany.atividadescomplementares.domain.ItemRequerimento;
import com.mycompany.atividadescomplementares.domain.Modalidade;
import com.mycompany.atividadescomplementares.domain.Requerimento;
import com.mycompany.atividadescomplementares.service.AtividadeComplementarFacade;

import java.util.List;
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
        System.out.println("=== SISTEMA DE ATIVIDADES COMPLEMENTARES - IFSC ===");
        System.out.println("Aluno: " + requerimento.obterAluno().obterNome());
        System.out.println("Matrícula: " + requerimento.obterAluno().obterMatricula());
        System.out.println("Horas mínimas exigidas: " + requerimento.obterAluno().obterCursoConfig().obterHorasMinimasTotal() + "h");
        exibirLimitesModalidade();
    }

    private void exibirLimitesModalidade() {
        System.out.println("\nLimites por modalidade:");
        for (Modalidade modalidade : Modalidade.values()) {
            System.out.printf("- %s: máximo %.0f%%\n",
                    modalidade.descrever(),
                    modalidade.obterPercentualMaximo() * 100);
        }
    }

    @Override
    protected boolean deveExecutar() {
        return continuar;
    }

    @Override
    protected void exibirOpcoes() {
        System.out.println("\n== Menu de Modalidades ==");
        System.out.println("1) Ensino");
        System.out.println("2) Pesquisa e Inovação");
        System.out.println("3) Extensão");
        System.out.println("4) Complementação");
        System.out.println("5) Visualizar requerimento atual");
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
            case 5 -> exibirRequerimentoAtual();
            case 0 -> {
                if (!facade.validarDocumentacaoObrigatoria(requerimento)) {
                    System.out.println("\nATENÇÃO: Existem atividades que requerem documentação obrigatória!");
                    System.out.print("Deseja continuar mesmo assim? (s/n): ");
                    String resposta = scanner.next();
                    if (!resposta.equalsIgnoreCase("s")) {
                        return;
                    }
                }
                System.out.println(facade.gerarParecer(requerimento));
                continuar = false;
            }
            default -> System.out.println("Opção inválida!");
        }
    }

    private void exibirRequerimentoAtual() {
        if (requerimento.obterItens().isEmpty()) {
            System.out.println("\nNenhuma atividade foi adicionada ainda.");
            return;
        }

        System.out.println("\n=== REQUERIMENTO ATUAL ===");
        List<ItemRequerimento> itens = requerimento.obterItens();
        for (int i = 0; i < itens.size(); i++) {
            ItemRequerimento item = itens.get(i);
            System.out.printf("%d. %s (%s) - %dh%s\n",
                    i + 1,
                    item.obterAtividade().obterDescricao(),
                    item.obterAtividade().obterModalidade().descrever(),
                    item.obterHorasDeclaradas(),
                    item.obterAtividade().requerDocumento() && !item.possuiDocumento() ? " [SEM DOCUMENTO]" : "");
        }
        System.out.printf("\nTotal de horas declaradas: %dh\n", requerimento.calcularTotalHorasDeclaradas());
    }

    @Override
    protected void finalizar() {
        System.out.println("Sistema finalizado.");
    }
}