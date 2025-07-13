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
        // Não há inicialização específica
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
            System.out.printf("%d) %-25s (limite %dh)\n", 
                             i + 1, atividade.obterDescricao(), atividade.obterLimiteDeHoras());
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
        System.out.printf("Horas declaradas para '%s': ", atividadeSelecionada.obterDescricao());
        
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
        requerimento.adicionarItem(item);
        System.out.println("Atividade adicionada ao requerimento.");
    }
    
    @Override
    protected void finalizar() {
        // Não há finalização específica
    }
}
