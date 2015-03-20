package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="Dica")
public class Dica {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String titulo, conteudo, autor;

    public Dica(){
    }

    public Dica(String autor, String titulo, String conteudo) {
        this();
        setAutor(autor);
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


}


