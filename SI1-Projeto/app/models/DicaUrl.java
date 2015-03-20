package models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="DicaUrl")
public class DicaUrl extends Dica{
    @Column
    private String conteudoUrl;

    public DicaUrl(){
        super();
    }

    public DicaUrl(String autor, String titulo, String conteudoUrl) throws Exception {
        if(!conteudoUrl.startsWith("http://")) throw new Exception("Deve começar com http:// .");
        if(!conteudoUrl.endsWith(".com") || !conteudoUrl.endsWith(".com.br") || !conteudoUrl.endsWith(".edu") || !conteudoUrl.endsWith(".edu.br"))
            throw new Exception("Deve terminar com: .com, .com.br, .edu ou .edu.br .");

        setAutor(autor);
        setTitulo(titulo);
        this.conteudoUrl = conteudoUrl;

    }

    public String getConteudoUrl() {
        return conteudoUrl;
    }

    public void setConteudoUrl(String conteudoUrl) {
        this.conteudoUrl = conteudoUrl;
    }
}
