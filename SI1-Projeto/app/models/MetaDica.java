package models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name="MetaDica")
public class MetaDica extends Dica {
    @OneToMany
    private List<Dica> dicas;

    public MetaDica() {
        super();
        dicas = new ArrayList<>();
    }

    public MetaDica(String autor, String titulo, String conteudo){
        super(autor, titulo, conteudo);
        dicas = new ArrayList<>();
    }

    public List<Dica> getDicas() {
        return dicas;
    }

    public void addDica(Dica dica){
        dicas.add(dica);
    }
}


