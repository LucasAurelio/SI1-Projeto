import play.*;

import models.Tema;
import models.dao.GenericDAO;
import play.Application;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {

                Tema geral = new Tema("Geral");
                Tema labs = new Tema("Laboratórios");
                Tema minitestes = new Tema("Minitestes");
                Tema projeto = new Tema("Projeto");
                Tema heroku = new Tema("Heroku");
                Tema padroes = new Tema("PadroesDeProjeto");
                Tema ferramentas = new Tema("Ferramentas");
                Tema design = new Tema("Design");

                dao.persist(geral);
                dao.persist(labs);
                dao.persist(minitestes);
                dao.persist(projeto);
                dao.persist(heroku);
                dao.persist(padroes);
                dao.persist(ferramentas);
                dao.persist(design);

                dao.merge(geral);
                dao.merge(labs);
                dao.merge(minitestes);
                dao.merge(projeto);
                dao.merge(heroku);
                dao.merge(padroes);
                dao.merge(ferramentas);
                dao.merge(design);

                dao.flush();
            }
        });
    }
}