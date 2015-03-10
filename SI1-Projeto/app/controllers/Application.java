package controllers;

import models.User;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.forum;
import views.html.index;

import java.util.List;

public class Application extends Controller {
    private static final GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result index() {
        List<User> users = dao.findAllByClassName(User.class.getName());
        return ok(index.render(users,""));
    }

    @Transactional
    public static Result criaUsuario() throws Exception {
        DynamicForm form = Form.form().bindFromRequest();

        String nome = form.get("Nome");
        String email = form.get("Email");
        String senha = form.get("Senha");
        String dataNascimento = form.get("DataNascimento");

        User user = new User(nome, email, senha, dataNascimento);
        dao.persist(user);

        List<User> users = dao.findAllByClassName(User.class.getName());
        return ok(index.render(users,"Seu cadastro foi feito com sucesso!"));
    }

    @Transactional
    public static Result fazLogin(){
        DynamicForm form = Form.form().bindFromRequest();

        String nome = form.get("Nome");
        String senha = form.get("Senha");

        List<User> users = dao.findAllByClassName(User.class.getName());

        if (users.size()==0){
            return ok(index.render(users,"Nome de cadastro inválido"));
        }

        boolean notFound = true;

        for (User user: users){
            if(user.getNome().equals(nome)){
                notFound = false;
                if(user.getSenha().equals(senha)){
                    return ok(forum.render(user,""));
                }else{
                    return ok(index.render(users,"Senha inválida"));
                }
            }else if(notFound){
                return ok(index.render(users, "Nome de cadastro inválido"));
            }
        }
        return null;
    }

}
