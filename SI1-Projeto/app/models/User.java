package models;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String nome, email, senha;

    public User(){
    }

    public User(String nome, String email, String senha)throws Exception{
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome.equals("")){
            throw new Exception("Nome inválido");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception{
        if(email.equals("")){
            throw new Exception("email inválido");
        }
        this.email = email;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) throws Exception{
        if(senha.equals("")){
            throw new Exception("senha inválida");
        }
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!nome.equals(user.nome)) return false;
        if (!email.equals(user.email)) return false;
        if (!senha.equals(user.senha)) return false;
        return true;
    }
}
