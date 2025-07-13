package com.mycompany.atividadescomplementares.presentation;

import java.util.Scanner;

public abstract class MenuTemplate {
    protected final Scanner scanner;
    
    public MenuTemplate(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public final void executar() {
        inicializar();
        while (deveExecutar()) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        finalizar();
    }
    
    protected abstract void inicializar();
    protected abstract boolean deveExecutar();
    protected abstract void exibirOpcoes();
    protected abstract int lerOpcao();
    protected abstract void processarOpcao(int opcao);
    protected abstract void finalizar();
}