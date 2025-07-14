package com.mycompany.atividadescomplementares.domain;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Requerimento {
    private final UUID id;
    private final Aluno aluno;
    private StatusRequerimento status;
    private final LocalDate dataFechamento;
    private final List<ItemRequerimento> itens;

    public Requerimento(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno é obrigatório");
        }

        this.id = UUID.randomUUID();
        this.aluno = aluno;
        this.status = StatusRequerimento.ABERTO;
        this.dataFechamento = LocalDate.now();
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemRequerimento item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        if (!this.status.podeSerEditado()) {
            throw new IllegalStateException("Não é possível adicionar itens a um requerimento já processado");
        }
        this.itens.add(item);
    }

    public void removerItem(ItemRequerimento item) {
        if (!this.status.podeSerEditado()) {
            throw new IllegalStateException("Não é possível remover itens de um requerimento já processado");
        }
        this.itens.remove(item);
    }

    public void enviarParaAvaliacao() {
        if (this.itens.isEmpty()) {
            throw new IllegalStateException("Requerimento deve ter pelo menos um item para ser enviado");
        }
        this.status = StatusRequerimento.EM_AVALIACAO;
    }

    public void processar() {
        if (this.status != StatusRequerimento.EM_AVALIACAO) {
            enviarParaAvaliacao();
        }

        this.itens.forEach(ItemRequerimento::validarAutomaticamente);
        this.status = StatusRequerimento.FINALIZADO;
    }

    public boolean estaAprovado() {
        CursoConfig config = this.aluno.obterCursoConfig();
        Map<Modalidade, Integer> horasPorModalidade = calcularHorasPorModalidade();
        int totalHoras = calcularTotalHorasValidadas();

        return config.validarDistribuicaoHoras(horasPorModalidade, totalHoras);
    }

    public Map<Modalidade, Integer> calcularHorasPorModalidade() {
        Map<Modalidade, Integer> horasPorModalidade = new HashMap<>();

        for (Modalidade modalidade : Modalidade.values()) {
            int horas = this.itens.stream()
                    .filter(item -> item.obterAtividade().eDaModalidade(modalidade))
                    .mapToInt(ItemRequerimento::obterHorasValidadas)
                    .sum();
            horasPorModalidade.put(modalidade, horas);
        }

        return horasPorModalidade;
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

    public List<ItemRequerimento> obterItensPorModalidade(Modalidade modalidade) {
        return this.itens.stream()
                .filter(item -> item.obterAtividade().eDaModalidade(modalidade))
                .collect(Collectors.toList());
    }

    public UUID obterIdentificador() { return this.id; }
    public Aluno obterAluno() { return this.aluno; }
    public StatusRequerimento obterStatus() { return this.status; }
    public LocalDate obterDataFechamento() { return this.dataFechamento; }
    public List<ItemRequerimento> obterItens() { return new ArrayList<>(this.itens); }

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