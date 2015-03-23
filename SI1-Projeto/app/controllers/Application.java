package controllers;

import models.*;
import models.User;
import models.dao.GenericDAO;
import org.h2.engine.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    private static final GenericDAO dao = new GenericDAO();
    static Form<User> registroForm = form(User.class).bindFromRequest();
    static Form<User> loginForm = form(User.class).bindFromRequest();
    private static List<Tema> temas = null;
    private static List<Dica> tips = null;
    private static List<MetaDica> metaTips = null;
    private static double mediaDificuldadeDoTemaEmQuestao = 0.0;


    @Transactional
    public static Result index() {
        if (session().get("user") != null){
            session().clear();
            return ok(index.render("Portal Do Leite", loginForm, registroForm));
        }
        return ok(index.render("Portal Do Leite", loginForm, registroForm));
    }

    @Transactional
    public static Result metaInformation(Long id) {
        metaTips = dao.findAllByClass(MetaDica.class);
        for(MetaDica metatip: metaTips){
            if(metatip.isVerMais()) metatip.setVerMais(false);
        }
        MetaDica metaTip = dao.findByEntityId(MetaDica.class, id);
        metaTip.setVerMais(true);

        return redirect(routes.Application.show());
    }
    
    @Transactional
    public static Result conteudoOfensivo(Long id) {
        Dica tip = dao.findByEntityId(Dica.class, id);

        String userDandoFlag= session().get("user");
        List<User> allUSers = dao.findAllByClass(User.class);
        User userFlagging = new User();

        for(User user: allUSers){
            if(user.getNome().equals(userDandoFlag)){
                userFlagging = user;
            }
        }

        if(usuarioJaDeuFlag(userFlagging,tip.getId())){
            flash("fail", "Você já denunciou essa dica!");
        }else{
            tip.addConteudoInapropriado();
            userFlagging.addDicaFlag(tip);
        }
        if (tip.getConteudoInapropriado()==3){
            dao.removeById(Dica.class,id);
        }

        String nomeDaClasse = "";
        List<Tema> allTemas = dao.findAllByClass(Tema.class);
        for(Tema ttt: allTemas){
            if(ttt.getTemaFiltro()){
                nomeDaClasse = ttt.getNome();
            }
        }
        return verificaView(nomeDaClasse);
    }

    @Transactional
    public static Result show(){
        if (session().get("user") != null){
            metaTips = new ArrayList<>();
            tips = new ArrayList<>();


            temas = dao.findAllByClass(Tema.class);
            for (Tema tema: temas){
                if (tema.getNome().equals("Geral")){
                    metaTips = tema.getMetadicas();
                    tema.setTemaFiltroTrue();
                }else{
                    tema.setTemaFiltroFalse();
                }
            }
            mediaDificuldadeDoTemaEmQuestao = 0.0;
            return ok(forum.render("Meu Forum", metaTips, tips,mediaDificuldadeDoTemaEmQuestao));
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
            dao.merge(u);
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
            metaTips = new ArrayList<>();
            tips = new ArrayList<>();

            temas = dao.findAllByClass(Tema.class);
            for (Tema tema: temas){
                if (tema.getNome().equals("Geral")){
                    metaTips = tema.getMetadicas();
                    tema.setTemaFiltroTrue();
                }else{
                    tema.setTemaFiltroFalse();
                }
            }
            mediaDificuldadeDoTemaEmQuestao = 0.0;
            return ok(forum.render("Meu Forum", metaTips, tips,mediaDificuldadeDoTemaEmQuestao));
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
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Laboratórios")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips,mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result showMinitestes() {
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Minitestes")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips, mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result showProjeto() {
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Projeto")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips,mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result showHeroku() {
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Heroku")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips, mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result showPadroesDeProjeto() {
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("PadroesDeProjeto")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips,mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result showFerramentas() {
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Ferramentas")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips,mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result showDesign() {
        tips = new ArrayList<>();
        metaTips = null;

        temas = dao.findAllByClass(Tema.class);
        for (Tema tema: temas){
            if (tema.getNome().equals("Design")){
                tips = tema.getDicas();
                tema.setTemaFiltroTrue();
                mediaDificuldadeDoTemaEmQuestao = tema.getMediaDificuldade();
            }else{
                tema.setTemaFiltroFalse();
            }
        }
        Collections.sort(tips);
        return ok(forum.render("Meu Forum", metaTips, tips, mediaDificuldadeDoTemaEmQuestao));
    }

    @Transactional
    public static Result newMainTip() throws Exception {
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = form.get("titulo");
        String descricao = form.get("descricao");
        String tema = form.get("topico");

        MetaDica newMainTip = new MetaDica(autor,titulo,descricao);
        Dica newTip = new Dica(autor, titulo, descricao);

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
    public static Result newTip() {
        DynamicForm form = Form.form().bindFromRequest();
        metaTips = dao.findAllByClass(MetaDica.class);
        MetaDica mainTip = new MetaDica();

        for(MetaDica metaDica: metaTips){
            if(metaDica.isVerMais()) mainTip = metaDica;
        }

        String autor = session().get("user");
        String titulo = form.get("titulo");
        String descricao = form.get("descricao");

        Dica newTip = new Dica(autor, titulo, descricao);
        dao.persist(newTip);

        mainTip.addDica(newTip);
        dao.merge(mainTip);

        return redirect(routes.Application.show());
    }

    @Transactional
    public static Result newMainLink(){
        DynamicForm form = Form.form().bindFromRequest();

        String autor = session().get("user");
        String titulo = form.get("titulo");
        String url = form.get("url");
        String tema = form.get("topico");

        if(validateURL(url)){
            flash("fail", "O link deve terminar com: .com, .com.br, .edu ou .edu.br ");
            return redirect(routes.Application.show());
        }

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

    private static boolean validateURL(String url){
        if((url.endsWith(".com")) || (url.endsWith(".com.br")) || (url.endsWith(".edu")) || (url.endsWith(".edu.br"))){
            return false;
        }
        return true;
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

    @Transactional
    public static Result cookieUP(Long id){
        String nomeDaClasse = "";
        List<Tema> allTemas = dao.findAllByClass(Tema.class);
        for(Tema ttt: allTemas){
            if(ttt.getTemaFiltro()){
                nomeDaClasse = ttt.getNome();
            }
        }

        String userVotando = session().get("user");
        List<User> allUSers = dao.findAllByClass(User.class);
        User userComVoto = new User();

        for(User user: allUSers){
            if(user.getNome().equals(userVotando)){
                userComVoto = user;
            }
        }

        if(nomeDaClasse.equals("Geral")){
            MetaDica tip = dao.findByEntityId(MetaDica.class, id);
            if (tip.getFechada()) {
                flash("fail", "Este dica está fechada para avaliação");
                return verificaView(nomeDaClasse);
            }
            if (verificaUsuarioComVotoMeta(userComVoto, id)) {
                flash("fail", "Você já avaliou essa dica!");
                return verificaView(nomeDaClasse);
            }else{
                userComVoto.addDicaVotadasMeta(tip);
                tip.addConcordancia();
            }
        }else{
            Dica tip = dao.findByEntityId(Dica.class, id);
            if (tip.getFechada()) {
                flash("fail", "Este dica está fechada para avaliação");
                return verificaView(nomeDaClasse);
            }
            if (verificaUsuarioComVoto(userComVoto, id)) {
                flash("fail", "Você já avaliou essa dica!");
                return verificaView(nomeDaClasse);
            }else{
                userComVoto.addDicaVotada(tip);
                tip.addConcordancia();
            }
        }

        return verificaView(nomeDaClasse);
    }

    @Transactional
    public static Result flyDown(Long id){
        DynamicForm form = Form.form().bindFromRequest();
        String plus = form.get("justificativa");

        String nomeDaClasse = "";
        List<Tema> allTemas = dao.findAllByClass(Tema.class);
        for(Tema ttt: allTemas){
            if(ttt.getTemaFiltro()){
                nomeDaClasse = ttt.getNome();
            }
        }

        String userVotando = session().get("user");
        List<User> allUSers = dao.findAllByClass(User.class);
        User userComVoto = new User();

        for(User user: allUSers){
            if(user.getNome().equals(userVotando)){
                userComVoto = user;
            }
        }

        if(nomeDaClasse.equals("Geral")){
            MetaDica tip = dao.findByEntityId(MetaDica.class,id);
            if (tip.getFechada()) {
                flash("fail", "Este dica está fechada para avaliação");
                return verificaView(nomeDaClasse);
            }
            if (verificaUsuarioComVotoMeta(userComVoto, id)) {
                flash("fail", "Você já avaliou essa dica!");
                return verificaView(nomeDaClasse);
            }else{
                userComVoto.addDicaVotadasMeta(tip);
                tip.addDiscordancia();
                tip.addJustificativa(plus);
            }
        }else{
            Dica tip = dao.findByEntityId(Dica.class,id);
            if (tip.getFechada()) {
                flash("fail", "Este dica está fechada para avaliação");
                return verificaView(nomeDaClasse);
            }
            if (verificaUsuarioComVoto(userComVoto, id)) {
                flash("fail", "Você já avaliou essa dica!");
                return verificaView(nomeDaClasse);
            }else{
                userComVoto.addDicaVotada(tip);
                tip.addDiscordancia();
                tip.addJustificativa(plus);
            }
        }

        return verificaView(nomeDaClasse);
    }

    private static boolean verificaUsuarioComVoto(User userVotando, Long id){
        List<Dica> dicasVotadas = userVotando.getDicasVotadas();

        for(Dica dica: dicasVotadas){
            if(dica.getId()==id){
                return true;
            }
        }
        return false;
    }

    private static boolean verificaUsuarioComVotoMeta(User userVotando, Long id){
        List<MetaDica> dicasVotadas = userVotando.getDicasVotadasMeta();

        for(MetaDica dica: dicasVotadas){
            if(dica.getId()==id){
                return true;
            }
        }
        return false;
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

    @Transactional
    public static Result dificuldade() {
        DynamicForm form = Form.form().bindFromRequest();
        int dif = Integer.parseInt(form.get("quantity"));


        Tema temaASerAvaliado = new Tema();
        List<Tema> allTemas = dao.findAllByClass(Tema.class);
        for(Tema ttt: allTemas){
            if(ttt.getTemaFiltro()){
                temaASerAvaliado = ttt;
            }
        }

        String userVotando = session().get("user");
        List<User> allUSers = dao.findAllByClass(User.class);
        User userComVoto = new User();

        for(User user: allUSers){
            if(user.getNome().equals(userVotando)){
                userComVoto = user;
            }
        }

        if(usuarioJaAvaliou(userComVoto,temaASerAvaliado.getId())){
            flash("fail", "Você já avaliou esse tema!");
        }else{
            temaASerAvaliado.addDificuldade(dif);
            userComVoto.addTemaAvaliado(temaASerAvaliado);
        }

        return verificaView(temaASerAvaliado.getNome());
    }

    private static boolean usuarioJaAvaliou(User usuarioAvaliando, Long id){
        List<Tema> temasAvaliados = usuarioAvaliando.getTemasAvaliados();

        for(Tema t: temasAvaliados){
            if(t.getId()==id){
                return true;
            }
        }
        return false;
    }

    private static boolean usuarioJaDeuFlag(User userDandoFlag, Long id){
        List<Dica> dicasFlag = userDandoFlag.getDicasFlag();

        for(Dica di: dicasFlag){
            if(di.getId()==id){
                return true;
            }
        }
        return false;
    }
}