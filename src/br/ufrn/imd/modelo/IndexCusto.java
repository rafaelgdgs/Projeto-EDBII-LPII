package br.ufrn.imd.modelo;

public class IndexCusto extends IC {

    public IndexCusto(long index, double custo) {
        this.index = index;
        this.custo = custo;
    }

    public long getIndex() {
        return index;
    }

    public double getCusto() {
        return custo;
    }
}
