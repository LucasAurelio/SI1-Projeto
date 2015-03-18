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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MetaDica> dicas;

    public MetaDica() {
        dicas = new ArrayList<>();
    }

    public MetaDica(String titulo, String conteudo) {
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

    public void addDicas(MetaDica dica) {
        dicas.add(dica);
    }

    public List<MetaDica> getDicas() {
        return dicas;
    }
}

