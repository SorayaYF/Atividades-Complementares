package com.mycompany.atividadescomplementares.domain;

import java.util.Objects;

public class Aluno {
    private String matricula;
    private String nome;

    public Aluno(String matricula, String nome) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula é obrigatória.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        this.matricula = matricula;
        this.nome = nome;
    }

    public String obterMatricula() {
        return matricula;
    }

    public String obterNome() {
        return nome;
    }
    
    public void definirMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void definirNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return matricula.equals(aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}