package com.mycompany.atividadescomplementares.domain;

import java.util.Objects;

public class Aluno {
    private final String matricula;
    private String nome;
    private final CursoConfig cursoConfig;

    public Aluno(String matricula, String nome, CursoConfig cursoConfig) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula é obrigatória.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (cursoConfig == null) {
            throw new IllegalArgumentException("Configuração do curso é obrigatória.");
        }

        this.matricula = matricula.trim();
        this.nome = nome.trim();
        this.cursoConfig = cursoConfig;
    }

    public void atualizarNome(String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = novoNome.trim();
    }

    public String obterMatricula() {
        return matricula;
    }

    public String obterNome() {
        return nome;
    }

    public CursoConfig obterCursoConfig() {
        return cursoConfig;
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