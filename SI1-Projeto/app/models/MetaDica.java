package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name="MetaDica")
public class MetaDica extends Dica {
    @OneToMany
    private List<Dica> dicas;
    @Column
    private boolean verMais;

    public MetaDica() {
        super();
        dicas = new ArrayList<>();
        verMais = false;
    }

    public MetaDica(String autor, String titulo, String conteudo){
        super(autor, titulo, conteudo);
        dicas = new ArrayList<>();
        verMais = false;
    }

    public List<Dica> getDicas() {
        return dicas;
    }

    public void addDica(Dica dica){
        dicas.add(dica);
    }

    public boolean isVerMais() {
        return verMais;
    }

    public void setVerMais(boolean verMais) {
        this.verMais = verMais;
    }
}


