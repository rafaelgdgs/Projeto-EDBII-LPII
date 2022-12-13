package br.ufrn.imd.modelo;

public class Aresta<T> implements Comparable<Aresta<T>>{

    private T origem;
    private T destino;
    private double peso;

    public Aresta(T origem, T destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public T getOrigem() {
        return origem;
    }

    public T getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origem + " ---- " + peso + " ---- " + destino;
    }

    @Override
    public int compareTo(Aresta<T> outra) { return Double.compare(this.peso, outra.peso); }
}

