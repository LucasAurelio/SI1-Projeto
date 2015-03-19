package controllers;

import models.Dica;
import models.MetaDica;
import models.Tema;
import models.User;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    private static final GenericDAO dao = new GenericDAO();
    static Form<User> registroForm = form(User.class).bindFromRequest();
    static Form<User> loginForm = form(User.class).bindFromRequest();
    private static List<Tema> temas = null;

    @Transactional
    public static Result index() {
        temas = dao.findAllByClass(Tema.class);
        return ok(index.render("Meu Forum", loginForm, registroForm));
    }

    @Transactional
    public static Result show(){
        if (session().get("user") != null){
            List<MetaDica> tips = new ArrayList<>();

            temas = dao.findAllByClass(Tema.class);
            for (Tema tema: temas){
                if (tema.getNome().equals("Geral")){
                    tips = tema.getMetadicas();
                }
            }
            return ok(forum.render("Meu Forum",tips));
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
        List<User> u = dao.findByAttributeName("User", "email", email);
        if (u == null || u.isEmpty()) {
            return true;
        }

        for(int i = 0; i < u.size(); i++) {
            if(u.get(i).getEmail().equals(email)){
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
        String senha = u.getPassword();

        if (loginForm.hasErrors() || !validateLogin(email, senha)) {
            flash("fail", "Email ou Senha Inválidos");
            return redirect(routes.Application.show());
        } else {
            User user = (User) dao.findByAttributeName("User", "email", u.getEmail()).get(0);
            session().clear();
            session("user", user.getNome());
            List<MetaDica> tips = new ArrayList<>();

            temas = dao.findAllByClass(Tema.class);
            for (Tema tema: temas){
                if (tema.getNome().equals("Geral")){
                    tips = tema.getMetadicas();
                }
            }
            return ok(forum.render("Meu Forum",tips));
        }
    }

    private static boolean validateLogin(String email, String senha) {
        List<User> u = dao.findByAttributeName("User", "email", email);
        if (u == null || u.isEmpty()) {
            return false;
        }
        for(int i = 0; i < u.size(); i++) {
            if (!u.get(i).getPassword().equals(senha)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public static Result showLabs() {

        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Laboratórios")){
                tips = tema.getDicas();
            }
        }
        return ok(labs.render("Meu Lab",tips));
    }

    @Transactional
    public static Result showMinitestes() {
        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Minitestes")){
                tips = tema.getDicas();
            }
        }
        return ok(minitestes.render("Meu Miniteste",tips));
    }

    @Transactional
    public static Result showProjeto() {
        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Projeto")){
                tips = tema.getDicas();
            }
        }
        return ok(projeto.render("Meu Projeto",tips));
    }

    @Transactional
    public static Result showHeroku() {
        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Heroku")){
                tips = tema.getDicas();
            }
        }
        return ok( heroku.render("Meu Heroku",tips));
    }

    @Transactional
    public static Result showPadroesDeProjeto() {
        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("PadroesDeProjeto")){
                tips = tema.getDicas();
            }
        }
        return ok(padroesDeProjeto.render("Meu Padrao",tips));
    }

    @Transactional
    public static Result showFerramentas() {
        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Ferramentas")){
                tips = tema.getDicas();
            }
        }
        return ok(ferramentas.render("Minha Ferramenta",tips));
    }

    @Transactional
    public static Result showDesign() {
        List<Dica> tips = new ArrayList<>();

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Design")){
                tips = tema.getDicas();
            }
        }
        return ok(design.render("Meu Design",tips));
    }

    @Transactional
    public static Result newMainTip(){
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = form.get("titulo");
        String descricao = form.get("descricao");
        String tema = form.get("topico");

        MetaDica newMainTip = new MetaDica(autor,titulo,descricao);

        temas = dao.findAllByClass(Tema.class);
        for (Tema theme: temas){
            if (theme.getNome().equals(tema)){
                theme.addMetaDica(newMainTip);
            }
        }

        return verificaView(tema);
    }

    private static Result verificaView(String tema){
        if(tema.equals("Geral")){
            return redirect(routes.Application.show());
        }else if(tema.equals("Laboratórios")){
            return redirect(routes.Application.showLabs());
        }else if(tema.equals("Minitestes")) {
            return redirect(routes.Application.showMinitestes());
        }else if(tema.equals("Projeto")) {
            return redirect(routes.Application.showProjeto());
        }else if(tema.equals("Heroku")) {
            return redirect(routes.Application.showHeroku());
        }else if(tema.equals("PadroesDeProjeto")) {
            return redirect(routes.Application.showPadroesDeProjeto());
        }else if(tema.equals("Ferramentas")) {
            return redirect(routes.Application.showFerramentas());
        }else if(tema.equals("Design")) {
            return redirect(routes.Application.showDesign());
        }
        return redirect(routes.Application.show());
    }
}