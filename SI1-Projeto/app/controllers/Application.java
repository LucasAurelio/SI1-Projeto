package controllers;

import models.*;
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
    private static List<Dica> tips = null;
    private static List<MetaDica> metaTips = null;
    //----- mexi aqui -----//
    private static List<DicaUrl> urlTips = null;


    @Transactional
    public static Result index() {
        temas = dao.findAllByClass(Tema.class);
        if (session().get("user") != null){
            return redirect(routes.Application.show());
        }
        return ok(index.render("Portal Do Leite", loginForm, registroForm));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result show(){
        if (session().get("user") != null){
            metaTips = new ArrayList<>();
            tips = new ArrayList<>();
            urlTips = new ArrayList<>();


            temas = dao.findAllByClass(Tema.class);
            for (Tema tema: temas){
                if (tema.getNome().equals("Geral")){
                    metaTips = tema.getMetadicas();
                    urlTips = tema.getUrldicas();
                }
            }
            return ok(forum.render("Meu Forum",metaTips,tips, urlTips));
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

    //----- mexi aqui -----//
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
            metaTips = new ArrayList<>();
            tips = new ArrayList<>();
            urlTips = new ArrayList<>();

            temas = dao.findAllByClass(Tema.class);
            for (Tema tema: temas){
                if (tema.getNome().equals("Geral")){
                    metaTips = tema.getMetadicas();
                    urlTips = tema.getUrldicas();
                }
            }
            return ok(forum.render("Meu Forum",metaTips,tips, urlTips));
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

    //----- mexi aqui -----//
    @Transactional
    public static Result showLabs() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Laboratórios")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result showMinitestes() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Minitestes")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result showProjeto() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Projeto")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result showHeroku() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Heroku")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result showPadroesDeProjeto() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("PadroesDeProjeto")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result showFerramentas() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Ferramentas")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- mexi aqui -----//
    @Transactional
    public static Result showDesign() {
        tips = new ArrayList<>();
        urlTips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Design")){
                tips = tema.getDicas();
                urlTips = tema.getUrldicas();
            }
        }
        return ok(forum.render("Meu Forum", metaTips, tips, urlTips));
    }

    //----- tem que mexer aqui, eu acho -----//
    @Transactional
    public static Result newMainTip() throws Exception {
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = form.get("titulo");
        String descricao = form.get("descricao");
        String tema = form.get("topico");

        MetaDica newMainTip = new MetaDica(autor,titulo,descricao);
        Dica newTip = new Dica(autor, titulo, descricao);
        DicaUrl newUrlTip = new DicaUrl(autor, titulo, descricao);

        temas = dao.findAllByClass(Tema.class);
        for (Tema theme: temas){
            if (theme.getNome().equals(tema)){
                if(tema.equals("Geral")){
                    theme.addMetaDica(newMainTip);

                }else{
                    theme.addDica(newTip);
                }
            }
        }

        return verificaView(tema);
    }

    @Transactional
    public static Result newMainLink(){
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = form.get("titulo");
        String url = "<a href='" + form.get("url") +"'>"+form.get("url")+"</a>";
        String tema = form.get("topico");

        MetaDica newMainTip = new MetaDica(autor,titulo,url);
        Dica newTip = new Dica(autor, titulo, url);

        temas = dao.findAllByClass(Tema.class);
        for (Tema theme: temas){
            if (theme.getNome().equals(tema)){
                if(tema.equals("Geral")){
                    theme.addMetaDica(newMainTip);

                }else{
                    theme.addDica(newTip);
                }
            }
        }

        return verificaView(tema);
    }

    @Transactional
    public static Result newMainDisciplina(){
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = form.get("disciplina");
        String conteudo = form.get("porque");
        String tema = form.get("topico");

        MetaDica newMainTip = new MetaDica(autor,titulo,conteudo);
        Dica newTip = new Dica(autor, titulo, conteudo);

        temas = dao.findAllByClass(Tema.class);
        for (Tema theme: temas){
            if (theme.getNome().equals(tema)){
                if(tema.equals("Geral")){
                    theme.addMetaDica(newMainTip);

                }else{
                    theme.addDica(newTip);
                }
            }
        }

        return verificaView(tema);
    }

    @Transactional
    public static Result newMainAssunto(){
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = "É preciso saber para não ter dificuldades:";
        String assunto = form.get("assunto");
        String tema = form.get("topico");

        MetaDica newMainTip = new MetaDica(autor,titulo,assunto);
        Dica newTip = new Dica(autor, titulo, assunto);

        temas = dao.findAllByClass(Tema.class);
        for (Tema theme: temas){
            if (theme.getNome().equals(tema)){
                if(tema.equals("Geral")){
                    theme.addMetaDica(newMainTip);

                }else{
                    theme.addDica(newTip);
                }
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