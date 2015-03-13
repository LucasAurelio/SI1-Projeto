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

import static play.data.Form.form;

public class Application extends Controller {

    private static GenericDAO dao = new GenericDAO();
    static Form<User> registroForm = form(User.class).bindFromRequest();
    static Form<User> loginForm = form(User.class).bindFromRequest();

    public static Result index() {
        return ok(index.render("Meu Forum", loginForm, registroForm));
    }

    @Transactional
    public static Result show(){
        if (session().get("user") != null){
            return ok(forum.render("Meu Forum"));
        }
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result registrar() {

        User u = registroForm.bindFromRequest().get();

        if (registroForm.hasErrors() || !(validateRegistro(u.getEmail()))) {
            flash("fail", "Email já está em uso");
            return redirect(routes.Application.index());
        } else {
            dao.persist(u);
            return redirect(routes.Application.show());
        }
    }


    private static boolean validateRegistro(String email) {
        List<User> users = dao.findAllByClassName(User.class.getName());
        for (User sameEmailUser: users){
            if (sameEmailUser.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    @Transactional
    public static Result logout() {
        session().clear();
        return show();
    }

    @Transactional
    public static Result authenticate() {

        User u = loginForm.bindFromRequest().get();

        String email = u.getEmail();
        String senha = u.getSenha();

        if (loginForm.hasErrors() || !validateLogin(email, senha)) {
            flash("fail", "Email ou Senha Inválidos");
            return redirect(routes.Application.show());
        } else {
            User user = (User) dao.findByAttributeName("User", "email", u.getEmail()).get(0);
            session().clear();
            session("user", user.getNome());
            return ok(forum.render("Meu Forum"));
        }
    }

    private static boolean validateLogin(String email, String senha) {
        List<User> u = dao.findByAttributeName("User", "email", email);
        if (u == null || u.isEmpty()) {
            return false;
        }
        if (!(u.get(0).getSenha().equals(senha))) {
            return false;
        }
        return true;
    }
}