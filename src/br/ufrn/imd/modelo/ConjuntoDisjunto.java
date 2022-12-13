package br.ufrn.imd.modelo;

public class ConjuntoDisjunto {

    public void MakeSet(No no) {
        no.setNoPai(no);
        no.setRank(0);
    }

    public void Union(No x, No y) {
        Link( FindSet(x), FindSet(y));
    }

    public void Link(No x, No y) {
        if (x.getRank() > y.getRank()) y.setNoPai(x);
        else {
            x.setNoPai(y);
            if (x.getRank() == y.getRank()) y.setRank(y.getRank()+1);
        }
    }

    public No FindSet(No x) {
        if(x != x.getNoPai()) x.setNoPai( FindSet( x.getNoPai() ) );
        return x.getNoPai();
    }

}
