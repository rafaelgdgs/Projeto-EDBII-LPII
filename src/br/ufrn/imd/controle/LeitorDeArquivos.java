package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.ufrn.imd.modelo.Aresta;
import br.ufrn.imd.modelo.Grafo;
import br.ufrn.imd.modelo.No;

public class LeitorDeArquivos {

	public static Grafo lerDoArquivo(String arquivo) {

        File file = new File(arquivo);
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        } catch (IOException e){
            System.out.println("Arquivo invalido!");
        }

        String linha = scanner.nextLine();
        String[] elementosLinha = linha.split(" ");

        if(elementosLinha.length != 2) {
            System.out.println("Arquivo com número inválido de argumentos.");
            return null;
        }

        List<Integer> lista = new ArrayList<>();

        for(int i = 0; i < elementosLinha.length; i++) {
            try{
                lista.add( Integer.parseInt(elementosLinha[i]));
            } catch (NumberFormatException e){
                System.out.println("Exceção: " + e.getMessage());
            }
        }

        int n = lista.get(0);
        int d = lista.get(1);
        System.out.println("n: " + n);
        System.out.println("d: " + d);

        List<Aresta<No>> listaArestas = new ArrayList<>();
        List<No> listaNos = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            listaNos.add(new No(i));
        }

        // O for a seguir percorre as linhas do arquivo de entrada para armazenar os custos das conexões em uma lista de arestas.
        for(int i = 0; i < n - 1; i++) {            // Vai da 2ª até a última linha do arquivo.
            linha = scanner.nextLine();             // Armazena a linha atual em uma String.
            elementosLinha = linha.split(" "); // Armazena os valores da linha em um vetor de Strings.

            // Percorre os valores da linha, cria arestas com esses valores e as adiciona a uma lista de arestas.
            for(int j = 0; j < elementosLinha.length; j++) {
                try{
                    listaArestas.add(new Aresta<>(listaNos.get(i), listaNos.get(i + j + 1), Double.parseDouble(elementosLinha[j])));
                } catch (NumberFormatException e){
                    System.out.println("Exceção: " + e.getMessage());
                }
            }
        }

        for(int i = 0; i < listaArestas.size(); i++) {
            System.out.println(listaArestas.get(i));
        }

        Grafo grafo = new Grafo(listaArestas, listaNos, n, d);
        return grafo;
    }
}
