package com.mycompany.atividadescomplementares.presentation;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;
import com.mycompany.atividadescomplementares.domain.Modalidade;
import com.mycompany.atividadescomplementares.domain.Requerimento;
import com.mycompany.atividadescomplementares.service.AtividadeComplementarFacade;

import java.util.List;
import java.util.Scanner;

public class MenuAtividades extends MenuTemplate {
    private final AtividadeComplementarFacade facade;
    private final Requerimento requerimento;
    private final Modalidade modalidade;
    private final List<Atividade> atividades;
    private boolean continuar = true;

    public MenuAtividades(Scanner scanner, AtividadeComplementarFacade facade,
                          Requerimento requerimento, Modalidade modalidade) {
        super(scanner);
        this.facade = facade;
        this.requerimento = requerimento;
        this.modalidade = modalidade;
        this.atividades = facade.listarAtividadesPorModalidade(modalidade);
    }

    @Override
    protected void inicializar() {
        System.out.printf("\nMáximo permitido para %s: %.0f%% do total de horas\n",
                modalidade.descrever(),
                modalidade.obterPercentualMaximo() * 100);
    }

    @Override
    protected boolean deveExecutar() {
        return continuar;
    }

    @Override
    protected void exibirOpcoes() {
        System.out.println("\n-- Atividades em " + modalidade.descrever().replace("Atividades de ", "") + " --");
        for (int i = 0; i < atividades.size(); i++) {
            Atividade atividade = atividades.get(i);
            String documentacao = atividade.requerDocumento() ? " [DOC OBRIGATÓRIO]" : "";
            System.out.printf("%d) %-60s (limite %dh)%s\n",
                    i + 1, atividade.obterDescricao(), atividade.obterLimiteDeHoras(), documentacao);
        }
        System.out.println("0) Voltar ao menu de modalidades");
        System.out.print("Escolha a atividade (0-voltar): ");
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
        if (opcao == 0) {
            continuar = false;
            return;
        }

        if (opcao < 1 || opcao > atividades.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Atividade atividadeSelecionada = atividades.get(opcao - 1);

        System.out.printf("\nAtividade selecionada: %s\n", atividadeSelecionada.obterDescricao());
        System.out.printf("Documentação necessária: %s\n", atividadeSelecionada.obterDocumentacaoComprobatoria());
        System.out.printf("Limite máximo: %dh\n", atividadeSelecionada.obterLimiteDeHoras());

        System.out.printf("Horas declaradas para esta atividade: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, digite um número válido: ");
            scanner.next();
        }
        int horasDeclaradas = scanner.nextInt();

        if (horasDeclaradas <= 0) {
            System.out.println("Horas declaradas devem ser positivas!");
            return;
        }

        ItemRequerimento item = new ItemRequerimento(atividadeSelecionada, horasDeclaradas);

        if (atividadeSelecionada.requerDocumento()) {
            System.out.print("Deseja anexar documento agora? (s/n): ");
            String resposta = scanner.next();
            if (resposta.equalsIgnoreCase("s")) {
                System.out.print("Nome do arquivo: ");
                scanner.nextLine();
                String nomeArquivo = scanner.nextLine();
                System.out.print("URL do documento: ");
                String url = scanner.nextLine();

                try {
                    item.anexarDocumento(nomeArquivo, url);
                    System.out.println("Documento anexado com sucesso!");
                } catch (Exception e) {
                    System.out.println("Erro ao anexar documento: " + e.getMessage());
                }
            } else {
                System.out.println("ATENÇÃO: Esta atividade requer documentação obrigatória!");
            }
        }

        requerimento.adicionarItem(item);
        System.out.println("Atividade adicionada ao requerimento.");

        int horasValidadas = Math.min(horasDeclaradas, atividadeSelecionada.obterLimiteDeHoras());
        if (horasValidadas < horasDeclaradas) {
            System.out.printf("AVISO: Horas serão limitadas a %dh (limite da atividade)\n", horasValidadas);
        }
    }

    @Override
    protected void finalizar() {
    }
}