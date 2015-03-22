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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Dica> dicas;
    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL )
    private List<User> usersQueDeramNota;
    @JoinColumn
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> usersQueDeramAvaliacao;
    @Column
    private int quant, dificuldade;
    @Column
    private boolean temaFiltro;

    public Tema() {
        quant = 0;
        dificuldade = 0;
        metadicas = new ArrayList<>();
        dicas = new ArrayList<>();
        usersQueDeramNota = new ArrayList<>();
        usersQueDeramAvaliacao = new ArrayList<>();
        temaFiltro = false;
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

    public double getMediaDificuldade(){
        if(quant != 0){
            return dificuldade/quant;
        }

        return 0.0;
    }



    public List<MetaDica> getMetadicas(){
        return metadicas;
    }

    public void addMetaDica(MetaDica metaDica){
        metadicas.add(metaDica);
    }

    public List<Dica> getDicas() {
        return dicas;
    }

    public void addDica(Dica dica) {
        dicas.add(dica);
    }

    public void addDificuldade(int dificuldade) {
        this.dificuldade += dificuldade;
        this.quant++;
    }

    public List<User> getUsersQueDeramNota(){
        return usersQueDeramNota;
    }

    public void addUserQueDeuNota(User user){
        usersQueDeramNota.add(user);
    }

    public List<User> getUsersQueDeramAvaliacao(){
        return usersQueDeramAvaliacao;
    }

    public void addUserQueDeuAvaliacao(User user){
        usersQueDeramAvaliacao.add(user);
    }

    public void setTemaFiltroFalse(){
        temaFiltro = false;
    }

    public void setTemaFiltroTrue(){
        temaFiltro = true;
    }

    public boolean getTemaFiltro(){
        return temaFiltro;
    }
}
