package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="MetaDica")
public class MetaDica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titulo, conteudo;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<MetaDica> dicas;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<Integer> notas;

    public MetaDica(){
        dicas = new ArrayList<>();
        notas = new ArrayList<>();
    }

    public MetaDica(String titulo, String conteudo){
        setTitulo(titulo);
        setConteudo(conteudo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void addDicas(MetaDica dica){
        dicas.add(dica);
    }

    public List<MetaDica> getDicas(){
        return dicas;
    }

    public double getNota(){
        double media = 0;
        for(Integer nota: notas){
            media += nota;
        }

        return media/notas.size();
    }

    public void addNota(int nota){
        notas.add(nota);
    }
}

