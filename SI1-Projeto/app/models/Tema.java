package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Tema")
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MetaDica> metadicas;
    private int nota, quant;

    public Tema() {
        nota = 0;
        quant = 0;
        metadicas = new ArrayList<>();
    }

    public Tema(String nome){
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addNota(int nota) {
        this.nota += nota;
        quant += 1;
    }

    public double mediaNotas(){
        if(quant != 0){
            return nota/quant;
        }

        return 0.0;
    }

    public List<MetaDica> getMetadicas(){
        return metadicas;
    }

    public void addMetaDica(MetaDica metaDica){
        metadicas.add(metaDica);
    }
}
