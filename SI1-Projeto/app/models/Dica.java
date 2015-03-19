package models;

import javax.persistence.*;

@Entity(name="Dica")
public class Dica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    protected String titulo, conteudo, autor;

    public Dica(){
    }

    public Dica(String autor, String titulo, String conteudo) {
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


