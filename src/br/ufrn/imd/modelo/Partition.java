package br.ufrn.imd.modelo;

import java.util.HashSet;
import java.util.Set;

public class Partition implements Comparable<Partition>{

    private Set<Aresta<No>> arestasInclusas;
    private Set<Aresta<No>> arestasExclusas;

    private double custoTotal;

    private boolean extrapolou;

    public Partition() {
        arestasInclusas = new HashSet<>();
        arestasExclusas = new HashSet<>();
        custoTotal = 0;
        extrapolou = false;
    }

    public Partition(double custoTotal, boolean extrapolou) {
        arestasInclusas = new HashSet<>();
        arestasExclusas = new HashSet<>();
        this.custoTotal = custoTotal;
        this.extrapolou = extrapolou;
    }

    public Partition(Partition partition) {
        this.arestasInclusas = new HashSet<>(partition.getArestasInclusas());
        this.arestasExclusas = new HashSet<>(partition.getArestasExclusas());
        this.custoTotal = partition.getCustoTotal();
        this.extrapolou = partition.isExtrapolado();
    }

    public Partition(Set<Aresta<No>> arestasInclusas,
                     Set<Aresta<No>> arestasExclusas, double custoTotal, boolean extrapolou) {
        this.arestasInclusas = new HashSet<>(arestasInclusas);
        this.arestasExclusas = new HashSet<>(arestasExclusas);
        this.custoTotal = custoTotal;
        this.extrapolou = extrapolou;
    }

    public void incluiAresta(Aresta<No> aresta) {
        this.arestasInclusas.add(aresta);
    }

    public void excluiAresta(Aresta<No> aresta) {
        this.arestasExclusas.add(aresta);
    }

    public Set<Aresta<No>> getArestasInclusas() {
        return arestasInclusas;
    }

    public Set<Aresta<No>> getArestasExclusas() {
        return arestasExclusas;
    }

    public void atualizaCusto(double custo) {
        this.custoTotal += custo;
    }

    public double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public boolean isExtrapolado() {
        return extrapolou;
    }

    public void setExtrapolou(boolean extrapolou) {
        this.extrapolou = extrapolou;
    }

    public void copia(Partition partition) {
        this.arestasInclusas.clear();
        this.arestasInclusas.addAll(partition.arestasInclusas);
        this.arestasExclusas.clear();
        this.arestasExclusas.addAll(partition.arestasExclusas);
        this.custoTotal = partition.custoTotal;
        this.extrapolou = partition.extrapolou;
    }

    @Override
    public int compareTo(Partition outra) {
        return Double.compare(this.custoTotal, outra.custoTotal);
    }
}

