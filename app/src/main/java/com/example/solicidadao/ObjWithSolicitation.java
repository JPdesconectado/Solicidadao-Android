package com.example.solicidadao;

import java.util.List;

public class ObjWithSolicitation {
    int count;
    String next;
    String previous;
    List<SolicitacaoEducacao> results;

    public ObjWithSolicitation(int count, String next, String previous, List<SolicitacaoEducacao> results){
        this.count = count;
        this.next = next;
        this.previous =previous;
        this.results = results;
    }

    public List<SolicitacaoEducacao> getResults(){
        return results;
    }
}
