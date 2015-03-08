package controllers;

import models.User;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

public class Application extends Controller {
    private static final GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result index() {
        List<User> users = dao.findAllByClassName(User.class.getName());
        return ok(index.render(users));
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
        return ok(index.render(users));
    }

}
