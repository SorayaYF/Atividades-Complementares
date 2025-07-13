
package com.mycompany.atividadescomplementares;

import com.mycompany.atividadescomplementares.service.NucleoDocente;
import com.mycompany.atividadescomplementares.service.ValidadorPadrao;
import com.mycompany.atividadescomplementares.service.IAvaliador;
import com.mycompany.atividadescomplementares.service.CoordenadorCurso;
import com.mycompany.atividadescomplementares.service.ValidadorPorDocumento;
import com.mycompany.atividadescomplementares.domain.Modalidade;
import com.mycompany.atividadescomplementares.domain.DocumentoComprovatorio;
import com.mycompany.atividadescomplementares.domain.Atividade;
import com.mycompany.atividadescomplementares.domain.ItemRequerimento;

public class Main {
    public static void main(String[] args) {
        Atividade atividade = new Atividade( "ATV01", "Participação em palestra sobre Java", 20, Modalidade.EXTENSAO );

   
        DocumentoComprovatorio documento = new DocumentoComprovatorio( "certificado_java.pdf","http://manu/certificados/java"  );

        ItemRequerimento item = new ItemRequerimento(25, documento, atividade);

        IAvaliador nucleo = new NucleoDocente(new ValidadorPadrao());
        nucleo.avaliarItem(item);

        System.out.println("Nucleo Docente avaliou:");
        System.out.println("Horas validadas: " + item.obterHorasValidadas());
        System.out.println("Status: " + item.obterStatus().descrever());

        IAvaliador coordenador = new CoordenadorCurso("Prof. João", new ValidadorPorDocumento());
        coordenador.avaliarItem(item);

        System.out.println("\nCoordenador avaliou:");
        System.out.println("Horas validadas: " + item.obterHorasValidadas());
        System.out.println("Status: " + item.obterStatus().descrever());
    }
}
