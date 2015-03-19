package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="MetaDica")
public class MetaDica extends Dica {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MetaDica> metaDicas;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Dica> dicas;

    public MetaDica() {
        super();
        dicas = new ArrayList<>();
    }

    public MetaDica(String autor, String titulo, String conteudo) {
        super(autor, titulo, conteudo);
    }

    public void addMetaDicas(MetaDica dica) { metaDicas.add(dica); }

    public List<MetaDica> getMetaDicas() {
        return metaDicas;
    }

    public List<Dica> getDicas() {
        return dicas;
    }

    public void addDicas(Dica dica) { dicas.add(dica); }

}

