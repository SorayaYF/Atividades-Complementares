package com.mycompany.atividadescomplementares.repository;

import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.Modalidade;

import java.util.*;
import java.util.stream.Collectors;

public class AtividadeRepositoryMemoria implements AtividadeRepository {
    private final Map<String, Atividade> atividades = new HashMap<>();

    public AtividadeRepositoryMemoria() {
        inicializarAtividadesRegulamento();
    }

    private void inicializarAtividadesRegulamento() {
        adicionarAtividadesEnsino();
        adicionarAtividadesExtensao();
        adicionarAtividadesPesquisa();
        adicionarAtividadesComplementacao();
    }

    private void adicionarAtividadesEnsino() {
        atividades.put("ENS001", new Atividade("ENS001", "Disciplinas cursadas com aproveitamento, não previstas no currículo do curso", 80, Modalidade.ENSINO, "Certificado ou declaração de conclusão", true));
        atividades.put("ENS002", new Atividade("ENS002", "Semana acadêmica dos cursos, quando não obrigatória", 40, Modalidade.ENSINO, "Certificado ou declaração de participação", true));
        atividades.put("ENS003", new Atividade("ENS003", "Participação em atividades de monitoria", 70, Modalidade.ENSINO, "Declaração de participação", true));
        atividades.put("ENS004", new Atividade("ENS004", "Atividades realizadas em laboratórios e/ou oficinas do Instituto", 40, Modalidade.ENSINO, "Declaração de participação", true));
        atividades.put("ENS005", new Atividade("ENS005", "Visita Técnica relacionada à área", 40, Modalidade.ENSINO, "Declaração de participação", true));
        atividades.put("ENS006", new Atividade("ENS006", "Participação em cursos de qualificação na área afim do curso com certificado de aproveitamento", 80, Modalidade.ENSINO, "Certificado de participação", true));
        atividades.put("ENS007", new Atividade("ENS007", "Participação como ouvintes em bancas de projetos integradores de assuntos relacionados à área", 20, Modalidade.ENSINO, "Documento assinado pelo presidente da banca ou coordenador de curso", true));
        atividades.put("ENS008", new Atividade("ENS008", "Participação como ouvintes em bancas de TCC, dissertações ou teses de assuntos relacionados à área", 20, Modalidade.ENSINO, "Documento assinado pelo presidente da banca ou coordenador de curso", true));
        atividades.put("ENS009", new Atividade("ENS009", "Desenvolvimento de material didático ou instrucional", 30, Modalidade.ENSINO, "Declaração da instituição promotora", true));
        atividades.put("ENS010", new Atividade("ENS010", "Instrutor de cursos abertos à comunidade", 70, Modalidade.ENSINO, "Declaração da instituição promotora", true));
    }

    private void adicionarAtividadesExtensao() {
        atividades.put("EXT001", new Atividade("EXT001", "Participação em programa ou projeto de extensão", 60, Modalidade.EXTENSAO, "Declaração de participação", true));
        atividades.put("EXT002", new Atividade("EXT002", "Apresentação de projeto de extensão", 20, Modalidade.EXTENSAO, "Declaração de apresentação", true));
        atividades.put("EXT003", new Atividade("EXT003", "Participação em ações sociais cívicas e comunitárias", 40, Modalidade.EXTENSAO, "Declaração de participação", true));
        atividades.put("EXT004", new Atividade("EXT004", "Texto em jornal ou revista da área", 40, Modalidade.EXTENSAO, "Texto", true));
        atividades.put("EXT005", new Atividade("EXT005", "Intercâmbio com instituições de ensino no Brasil ou no exterior", 100, Modalidade.EXTENSAO, "Declaração de participação", true));
        atividades.put("EXT006", new Atividade("EXT006", "Estágio não-obrigatório na área do curso, formalizado pelo IFSC", 100, Modalidade.EXTENSAO, "Declaração do empregador", true));
        atividades.put("EXT007", new Atividade("EXT007", "Exercício profissional com vínculo empregatício, desde que na área do curso", 100, Modalidade.EXTENSAO, "Declaração do empregador ou Carteira Profissional", true));
    }

    private void adicionarAtividadesPesquisa() {
        atividades.put("PES001", new Atividade("PES001", "Participação em programa ou projeto de pesquisa relacionados a área", 60, Modalidade.PESQUISA, "Declaração de participação", true));
        atividades.put("PES002", new Atividade("PES002", "Apresentação de projeto de pesquisa relacionado à área", 20, Modalidade.PESQUISA, "Declaração de apresentação", true));
        atividades.put("PES003", new Atividade("PES003", "Autoria e coautoria em artigo publicado em Periódico na área afim", 30, Modalidade.PESQUISA, "Capa do artigo ou aceite", true));
        atividades.put("PES004", new Atividade("PES004", "Livro na área afim", 100, Modalidade.PESQUISA, "Cópia da ficha catalográfica", true));
        atividades.put("PES005", new Atividade("PES005", "Capítulo de livro na área afim", 30, Modalidade.PESQUISA, "Cópia da ficha catalográfica e capa do capítulo", true));
        atividades.put("PES006", new Atividade("PES006", "Publicação em Anais de Evento Técnico Científico", 30, Modalidade.PESQUISA, "Cópia do artigo", true));
        atividades.put("PES007", new Atividade("PES007", "Apresentação de trabalho em Evento Técnico Científico", 20, Modalidade.PESQUISA, "Certificado ou declaração de participação", true));
        atividades.put("PES008", new Atividade("PES008", "Participação de Programa de Iniciação Científica", 60, Modalidade.PESQUISA, "Declaração de participação", true));
        atividades.put("PES009", new Atividade("PES009", "Participação como palestrante, conferencista, integrante de mesa-redonda, ministrante de mini-curso em evento científico", 60, Modalidade.PESQUISA, "Certificado ou declaração de participação", true));
        atividades.put("PES010", new Atividade("PES010", "Prêmios concedidos por instituições acadêmicas, científicas e profissionais", 60, Modalidade.PESQUISA, "Declaração de premiação", true));
        atividades.put("PES011", new Atividade("PES011", "Participação na criação de Produto ou Processo Tecnológico com propriedade intelectual registrada", 200, Modalidade.PESQUISA, "Registro da propriedade intelectual", true));
        atividades.put("PES012", new Atividade("PES012", "Participação em grupo de pesquisa na área", 60, Modalidade.PESQUISA, "Declaração do líder do grupo", true));
    }

    private void adicionarAtividadesComplementacao() {
        atividades.put("COM001", new Atividade("COM001", "Participação em congressos, jornadas, simpósios, fóruns, seminários, encontros, palestras, festivais e similares", 20, Modalidade.COMPLEMENTACAO, "Declaração de participação", true));
        atividades.put("COM002", new Atividade("COM002", "Comissão organizadora de congressos, jornadas, simpósios, fóruns, seminários, encontros, palestras, festivais e similares", 20, Modalidade.COMPLEMENTACAO, "Declaração de instituição promotora", true));
        atividades.put("COM003", new Atividade("COM003", "Premiação em eventos que tenha relação com os objetos de estudo do curso", 30, Modalidade.COMPLEMENTACAO, "Declaração de participação", true));
        atividades.put("COM004", new Atividade("COM004", "Curso de língua estrangeira", 80, Modalidade.COMPLEMENTACAO, "Certificado ou declaração de participação", true));
        atividades.put("COM005", new Atividade("COM005", "Premiação em atividades esportivas como representante do Instituto", 60, Modalidade.COMPLEMENTACAO, "Cópia da declaração", true));
        atividades.put("COM006", new Atividade("COM006", "Representação estudantil em colegiado, grêmio estudantil, centro acadêmico, comissão de formatura, associação esportiva e afins", 40, Modalidade.COMPLEMENTACAO, "Declaração da instituição", true));
        atividades.put("COM007", new Atividade("COM007", "Representação de turma (inclui a participação em conselhos de classe)", 30, Modalidade.COMPLEMENTACAO, "Declaração do Coordenador do Curso", true));
        atividades.put("COM008", new Atividade("COM008", "Participação em Empresa Júnior", 40, Modalidade.COMPLEMENTACAO, "Declaração do dirigente da empresa", true));
        atividades.put("COM009", new Atividade("COM009", "Classificação em concursos culturais", 20, Modalidade.COMPLEMENTACAO, "Certificado, declaração e/ou endereço eletrônico do resultado classificatório", true));
        atividades.put("COM010", new Atividade("COM010", "Participação em projetos sociais, trabalho voluntário em entidades vinculadas a compromissos sociopolíticos", 20, Modalidade.COMPLEMENTACAO, "Declaração assinada pelo responsável", true));
        atividades.put("COM011", new Atividade("COM011", "Desenvolvimento de atividades socioculturais, artísticas e esportivas (coral, música, dança, bandas, vídeos, cinema, cineclubes, teatro, campeonatos esportivos etc)", 20, Modalidade.COMPLEMENTACAO, "Declaração assinada pelo responsável", true));
    }

    @Override
    public List<Atividade> buscarPorModalidade(Modalidade modalidade) {
        return atividades.values().stream()
                .filter(atividade -> atividade.eDaModalidade(modalidade))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Atividade> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(atividades.get(codigo));
    }

    @Override
    public List<Atividade> buscarTodas() {
        return new ArrayList<>(atividades.values());
    }
}