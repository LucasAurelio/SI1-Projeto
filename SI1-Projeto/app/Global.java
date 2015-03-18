import play.*;
import models.Tema;
import models.dao.GenericDAO;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(() -> {
            dao.persist(new Tema("Geral"));
            dao.persist(new Tema("Laboratorios"));
            dao.persist(new Tema("Minitestes"));
            dao.persist(new Tema("Projeto"));
            dao.persist(new Tema("Heroku"));
            dao.persist(new Tema("PadroesDeProjeto"));
            dao.persist(new Tema("Ferramentas"));
        });
    }
}