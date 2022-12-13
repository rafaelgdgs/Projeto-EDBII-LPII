package br.ufrn.imd.modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class Grafo {

    private List<Aresta<No>> listaArestas;      // Lista contendo todas as arestas do grafo completo.

    private List<No> listaNos;                  // Lista de nós, cada qual representando uma casas.

    private List<Partition> listaPartition;      // Lista para armazenar as partições.

    private int numCasas;           // Número de casas.

    private int maxConexoes;        // Máximo de conexões permitidas por casa.

    private double custoTotal;      // Custo total da árvore.

    private int arestasConectadas;  // Número de arestas conectadas.

    private boolean extrapolou;     // Indica se o máximo de conexões permitidas foi extrapolado.

    private IndexCusto menorCusto; // Índice da arvore de menor custo.

    public Grafo(List<Aresta<No>> listaArestas, List<No> listaNos, int numCasas, int maxConexoes) {
        this.listaArestas = listaArestas;
        this.listaNos = listaNos;
        this.numCasas = numCasas;
        this.maxConexoes = maxConexoes;
        this.custoTotal = 0;
        this.arestasConectadas = 0;
        this.extrapolou = false;
        listaPartition = new ArrayList<>();
        this.menorCusto = new IndexCusto(0, 0);
    }

    public List<Aresta<No>> Kruskal() {
        List<Aresta<No>> A = new ArrayList<>();
        ConjuntoDisjunto c = new ConjuntoDisjunto();

        for (int i = 0; i < numCasas; i++) {
            c.MakeSet(listaNos.get(i));
        }

        listaArestas.sort(null);

        for (var aresta : listaArestas) {
            No origem = aresta.getOrigem();
            No destino = aresta.getDestino();
            if( c.FindSet(origem) !=  c.FindSet(destino) ) {
                A.add(aresta);
                c.Union(origem, destino);
                custoTotal += aresta.getPeso();
                origem.maisUmaConexao();
                destino.maisUmaConexao();
                if(origem.getNumConexoes() > maxConexoes || destino.getNumConexoes() > maxConexoes) extrapolou = true;
            }
        }
        System.out.println("Custo total: " + custoTotal);
        System.out.println("Extrapolou: " + extrapolou);
        return A;
    }

    public void calculaGrafos() {

        int numArestas = listaArestas.size();       // Quantidade total de arestas possíveis.
        long comb = (long) Math.pow(2, numArestas );  // Número de combinações possíveis de arestas ativas.
        System.out.println("Num de comb: " + comb);
        String[] ativas;                            // Array para indicar quais arestas estão ativas.
        ConjuntoDisjunto c = new ConjuntoDisjunto();
        String string;

        for (long i = 0; i < comb; i++) {

//            if(i % 10_000_000 == 0) System.out.println((double) i/comb * 100 + " %");

            this.custoTotal = 0;
            this.arestasConectadas = 0;

            // Verifica se o número de bits '1' é igual a (numArestas-1).
            if(Long.bitCount(i) == numCasas-1) {

                // Cria os conjuntos disjuntos para cada nó e reseta o número de conexões dos nós.
                for (var no : listaNos) {
                    c.MakeSet(no);
                    no.resetNumConexoes();
                }

                string = Long.toBinaryString(i);
                while(string.length() < numArestas) { string = "0" + string;}
                ativas = string.split("");
                
                for (int j = 0; j < numArestas; j++) {
                    if( j < ativas.length && Objects.equals(ativas[j], "1") ) {
                        Aresta<No> aresta = listaArestas.get(j);
                        No origem = aresta.getOrigem();
                        No destino = aresta.getDestino();
                        if(aresta.getOrigem().getNumConexoes() < maxConexoes && aresta.getDestino().getNumConexoes() < maxConexoes){
                            if( c.FindSet(origem) !=  c.FindSet(destino) ) {
                                c.Union(origem, destino);
                                custoTotal += aresta.getPeso();
                                origem.maisUmaConexao();
                                destino.maisUmaConexao();
                                arestasConectadas++;
                                if(origem.getNumConexoes() > maxConexoes || destino.getNumConexoes() > maxConexoes){
                                    extrapolou = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(extrapolou) {
                    extrapolou = false;
                    continue;
                }
                if(arestasConectadas == numCasas-1) {
                    if(menorCusto.getCusto() == 0) menorCusto = new IndexCusto(i, custoTotal);
                    else if(custoTotal < menorCusto.getCusto()) {
                        menorCusto = new IndexCusto(i, custoTotal);
                    }
                }
            }
        }

        string = Long.toBinaryString(menorCusto.getIndex());
        while(string.length() < numArestas) { string = "0" + string;}
        ativas = string.split("");
        
        try {
        	FileWriter wr = new FileWriter("resultado.txt");
        	wr.write("Arestas adicionadas: ");
        	for (int j = 0; j < numArestas; j++) {
                if( j < ativas.length && Objects.equals(ativas[j], "1") ) {
                	wr.write("\n" + listaArestas.get(j).toString());
                }
            }
        	wr.write("\nMenor custo: " + menorCusto.getCusto());
        	wr.close();
        } catch (IOException e) {
        	System.out.println("Problem writing result!");
        	e.printStackTrace();
        }
        
        System.out.println("Arestas adicionadas: ");
        for (int j = 0; j < numArestas; j++) {
            if( j < ativas.length && Objects.equals(ativas[j], "1") ) {
                System.out.println(listaArestas.get(j));
            }
        }
        System.out.println("Menor custo: " + menorCusto.getCusto());
        System.out.println("Indice do menor custo: " + menorCusto.getIndex());
    }


    public List<Aresta<No>> MST(Partition p) {

        List<Aresta<No>> A = new ArrayList<>();
        ConjuntoDisjunto c = new ConjuntoDisjunto();
        var inclusas = p.getArestasInclusas();
        var exclusas = p.getArestasExclusas();

        for (int i = 0; i < numCasas; i++) {
            c.MakeSet(listaNos.get(i));
            listaNos.get(i).resetNumConexoes();
        }

        // Adiciona as arestas inclusas.
        for(var aresta : p.getArestasInclusas()) {
            No origem = aresta.getOrigem();
            No destino = aresta.getDestino();
//            if( c.FindSet(origem) !=  c.FindSet(destino) ) {
            A.add(aresta);
            c.Union(origem, destino);
            p.atualizaCusto(aresta.getPeso());
            arestasConectadas++;
            origem.maisUmaConexao();
            destino.maisUmaConexao();
            if(origem.getNumConexoes() > maxConexoes || destino.getNumConexoes() > maxConexoes) {
                p.setExtrapolou(true);
            }
//            }
        }

        // Adiciona as arestas abertas por ordem de custo crescente.
        for (Aresta<No> aresta : listaArestas) {
            if (!exclusas.contains(aresta) && !inclusas.contains(aresta)) {
                No origem = aresta.getOrigem();
                No destino = aresta.getDestino();
                if (c.FindSet(origem) != c.FindSet(destino)) {
                    A.add(aresta);
                    c.Union(origem, destino);
                    p.atualizaCusto(aresta.getPeso());
                    arestasConectadas++;
                    origem.maisUmaConexao();
                    destino.maisUmaConexao();
                    if(origem.getNumConexoes() > maxConexoes || destino.getNumConexoes() > maxConexoes) {
                        p.setExtrapolou(true);
                    }
                }
            }
            if(arestasConectadas == numCasas-1) break;
        }
        return A;
    }

    public void doPartition (Partition p, List<Aresta<No>> mst) {
        Partition p1 = new Partition(p.getArestasInclusas(), p.getArestasExclusas(), 0, false);
        Partition p2 = new Partition(p.getArestasInclusas(), p.getArestasExclusas(), 0, false);
        var inclusas = p.getArestasInclusas();
        var exclusas = p.getArestasExclusas();

        for (var aresta : mst) {
            if (!exclusas.contains(aresta) && !inclusas.contains(aresta)) {
                p1.excluiAresta(aresta);    // Faz aresta ser excluída de p1.
                p2.incluiAresta(aresta);    // Faz aresta ser incluída em p2.
                MST(p1);
                if(arestasConectadas == numCasas-1) {
                    listaPartition.add(new Partition(p1));
                    listaPartition.sort(null);
                }
                arestasConectadas = 0;
                p1.copia(p2);
            }
        }
    }

    public void kBest() {

        List<Aresta<No>> mst = Kruskal();
        if(!extrapolou) {
        	try {
            	FileWriter wr = new FileWriter("resultado.txt");
            	wr.write("Arestas adicionadas: ");
            	for (var arestas : mst) {
            		wr.write("\n" + arestas.toString());
                }
            	wr.write("\nCusto final: " + custoTotal);
            	wr.close();
            } catch (IOException e) {
            	System.out.println("Problem writing result!");
            	e.printStackTrace();
            }
        	return;
        }
        Partition p = new Partition();
        extrapolou = false;

        listaPartition.add(p);

//        Queue<Partition> fila = new LinkedList<>(listaPartition);
        Partition atual = new Partition();

        while (!listaPartition.isEmpty()){
//            p.copia(fila.remove());
            p = listaPartition.remove(0);
            mst = MST(p);
            arestasConectadas = 0;
            p.setCustoTotal(0);
            doPartition(p, mst);
//            fila.addAll(listaPartition);
//            atual = fila.peek();
            atual = listaPartition.get(0);
            if(!atual.isExtrapolado()) {
                System.out.println("Custo final: " + atual.getCustoTotal());
                System.out.println("Arestas adicionadas: ");
            	try {
                	FileWriter wr = new FileWriter("resultado.txt");
                	wr.write("Custo final: " + atual.getCustoTotal());
                	mst = MST(atual);
                    mst.sort(null);
                	wr.write("\nArestas adicionadas: ");
                	for (var arestas : mst) {
                		wr.write("\n" + arestas.toString());
                    }
                	wr.close();
                } catch (IOException e) {
                	System.out.println("Problem writing result!");
                	e.printStackTrace();
                }
                mst = MST(atual);
                mst.sort(null);
                for (var arestas : mst) {
                    System.out.println(arestas);
                }
                return;
            }
        }
    }

}

