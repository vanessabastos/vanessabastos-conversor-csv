package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Classe conversor. */
public class Conversor {

  /** Função utilizada apenas para validação da solução do desafio.*/
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
  * Converte todos os arquivos CSV da pasta de entradas. Os resultados são
  * gerados na pasta de saídas, deixando os arquivos originais inalterados.
  *
  * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página
  *                        web.
  * @param pastaDeSaidas   Pasta em que serão colocados os arquivos gerados no
  *                        formato requerido pelo subsistema.
  *
  * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada
  *                     ou gravar os arquivos de saída.
  */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    File pastaSaida = null;
    FileReader leitorArquivo = null;
    FileWriter escritorArquivo = null;
    BufferedReader bufferedLeitor = null;
    BufferedWriter bufferedEscritor = null;

    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdir();
    }

    if (pastaDeEntradas.isDirectory() && pastaDeEntradas.canRead()) {
      for (File arquivo : pastaDeEntradas.listFiles()) {
        leitorArquivo = new FileReader(arquivo);
        bufferedLeitor = new BufferedReader(leitorArquivo);
        pastaSaida = new File(pastaDeSaidas + "/" + arquivo.getName());
        escritorArquivo = new FileWriter(pastaSaida);
        bufferedEscritor = new BufferedWriter(escritorArquivo);

        String informacoesLinha = bufferedLeitor.readLine();

        while (informacoesLinha != null) {
          if (!informacoesLinha.contains("Nome completo")) {
            String linhaFormatada = editarLinha(informacoesLinha);
            bufferedEscritor.write(linhaFormatada);
            bufferedEscritor.newLine();
            bufferedEscritor.flush();

          } else {
            bufferedEscritor.write(informacoesLinha);
            bufferedEscritor.newLine();
            bufferedEscritor.flush();
          }
          informacoesLinha = bufferedLeitor.readLine();
        }
      }
      leitorArquivo.close();
      bufferedLeitor.close();
      escritorArquivo.close();
      bufferedEscritor.close();
    }
  }

  private String editarLinha(String informacoesLinha) {
    String[] informacoesSeparadas = informacoesLinha.split(",");
    String nome = informacoesSeparadas[0].toUpperCase();
    String[] data = informacoesSeparadas[1].split("/");
    String dataFormatada = data[2] + "-" + data[1] + "-" + data[0];
    String email = informacoesSeparadas[2];
    String cpf = informacoesSeparadas[3]
            .replaceAll("^(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

    String formatoLinha = nome + "," + dataFormatada + "," + email + "," + cpf;

    return formatoLinha;
  }
}
