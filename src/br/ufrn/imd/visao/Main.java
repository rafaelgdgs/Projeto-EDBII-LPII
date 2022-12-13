package br.ufrn.imd.visao;

import javax.swing.JFileChooser;

import br.ufrn.imd.controle.LeitorDeArquivos;
import br.ufrn.imd.modelo.Grafo;

public class Main {

	public static void main(String[] args) {
		
		//seção 1
//		JFileChooser chooser = new JFileChooser();
//		chooser.setDialogTitle("Selecione o arquivo");
//        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
//            return;
//
//        String arquivo = chooser.getSelectedFile().getAbsolutePath();
//
//		Grafo grafo = LeitorDeArquivos.lerDoArquivo(arquivo);
		//fim seção 1
		
		
		//seção 2
		Grafo grafo = LeitorDeArquivos.lerDoArquivo("C:\\Users\\N\\IdeaProjects\\Projeto - EDB2-LP2\\Entrada3");
		//fim seção 2

        // Mude o valor da chave abaixo para escolher o método que testa todas as possibilidades (1) ou escolha outro
        // valor para usar o método que cálcula as árvores por ordem de custo crescente.
        int chave = 2;
        if(chave == 1) grafo.calculaGrafos();
        else grafo.kBest();

    }

}