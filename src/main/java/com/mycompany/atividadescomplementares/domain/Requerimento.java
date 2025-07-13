package com.mycompany.atividadescomplementares.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Requerimento {
    private final UUID id;
    private final String matricula;
    private final String nome;
    private StatusItem status;
    private final LocalDate dataFechamento;
    private final List<ItemRequerimento> itens;
    private int totalHorasModalidade;

    public Requerimento(String matricula, String nome, int totalHorasModalidade) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula é obrigatória");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (totalHorasModalidade <= 0) {
            throw new IllegalArgumentException("Total de horas da modalidade deve ser positivo");
        }

        this.id = UUID.randomUUID();
        this.matricula = matricula.trim();
        this.nome = nome.trim();
        this.status = StatusItem.PENDENTE;
        this.dataFechamento = LocalDate.now();
        this.itens = new ArrayList<>();
        this.totalHorasModalidade = totalHorasModalidade;
    }

    public void adicionarItem(ItemRequerimento item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        if (this.status != StatusItem.PENDENTE) {
            throw new IllegalStateException("Não é possível adicionar itens a um requerimento já processado");
        }
        this.itens.add(item);
    }

    public void removerItem(ItemRequerimento item) {
        if (this.status != StatusItem.PENDENTE) {
            throw new IllegalStateException("Não é possível remover itens de um requerimento já processado");
        }
        this.itens.remove(item);
    }

    public void processar() {
        if (this.itens.isEmpty()) {
            throw new IllegalStateException("Requerimento deve ter pelo menos um item para ser processado");
        }

        int totalHorasValidadas = calcularTotalHorasValidadas();
        
        if (totalHorasValidadas == 0) {
            this.status = StatusItem.REPROVADO;
        } else if (totalHorasValidadas >= this.totalHorasModalidade) {
            this.status = StatusItem.APROVADO;
        } else {
            this.status = StatusItem.APROVADO_PARCIALMENTE;
        }
    }

    public int calcularTotalHorasValidadas() {
        return this.itens.stream()
                .mapToInt(ItemRequerimento::obterHorasValidadas)
                .sum();
    }

    public int calcularTotalHorasDeclaradas() {
        return this.itens.stream()
                .mapToInt(ItemRequerimento::obterHorasDeclaradas)
                .sum();
    }

    public boolean estaCompleto() {
        return calcularTotalHorasValidadas() >= this.totalHorasModalidade;
    }

    public boolean possuiItensReprovados() {
        return this.itens.stream()
                .anyMatch(item -> item.obterStatus() == StatusItem.REPROVADO);
    }

    public boolean possuiItensPendentes() {
        return this.itens.stream()
                .anyMatch(ItemRequerimento::estaPendente);
    }

    public List<ItemRequerimento> obterItensReprovados() {
        return this.itens.stream()
                .filter(item -> item.obterStatus() == StatusItem.REPROVADO)
                .toList();
    }

    public List<ItemRequerimento> obterItensAprovados() {
        return this.itens.stream()
                .filter(ItemRequerimento::estaAprovado)
                .toList();
    }

    public double calcularPercentualCumprimento() {
        if (this.totalHorasModalidade == 0) return 0.0;
        return (double) calcularTotalHorasValidadas() / this.totalHorasModalidade * 100;
    }

    public UUID obterIdentificador() {
        return this.id;
    }

    public String obterMatricula() {
        return this.matricula;
    }

    public String obterNome() {
        return this.nome;
    }

    public StatusItem obterStatus() {
        return this.status;
    }

    public LocalDate obterDataFechamento() {
        return this.dataFechamento;
    }

    public List<ItemRequerimento> obterItens() {
        return new ArrayList<>(this.itens);
    }

    public int obterTotalHorasModalidade() {
        return this.totalHorasModalidade;
    }

    public void definirTotalHorasModalidade(int totalHorasModalidade) {
        if (totalHorasModalidade <= 0) {
            throw new IllegalArgumentException("Total de horas da modalidade deve ser positivo");
        }
        this.totalHorasModalidade = totalHorasModalidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Requerimento that = (Requerimento) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}