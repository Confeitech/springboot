package br.com.confeitech.application.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class EscritorCSV {



    public static void Escrever(String boloMaisVendido, Integer totalEncomendas) {

        try {

            OutputStream outputStream = new FileOutputStream("relatorio.csv");

            BufferedWriter escritor = new BufferedWriter(

                    new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
            );

            escritor.write("%s;%s;%s\n".formatted("Data", "BoloMaisVendido", "TotalDeEncomendas")
            );

            escritor.write("%s;%s;%d;\n".formatted(String.valueOf(LocalDate.now()), boloMaisVendido, totalEncomendas));

            escritor.close();
            outputStream.close();

            } catch (IOException e) {
                System.out.println("Erro ao escrever arquivo");
            }


        }

}

