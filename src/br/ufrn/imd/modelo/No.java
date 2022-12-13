package br.ufrn.imd.modelo;

public class No {

    private int id;

    private int numConexoes;

    private No noPai;

    private int rank;

    public No(int id) {
        this.id = id;
        this.numConexoes = 0;
    }

    public No getNoPai() {
        return noPai;
    }

    public int getRank() {
        return rank;
    }

    public int getNumConexoes() {
        return numConexoes;
    }

    public void setNoPai(No noPai) {
        this.noPai = noPai;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void maisUmaConexao() {
        this.numConexoes++;
    }

    public void resetNumConexoes() {
        this.numConexoes = 0;
    }

    @Override
    public String toString() {
        return "C" + id;
    }

}

