package modelsTests;

import models.Dica;
import models.MetaDica;
import models.Tema;
import models.dao.GenericDAO;
import org.junit.Test;
import test.abstractTest;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TemaTests extends abstractTest {

    private final GenericDAO dao = new GenericDAO();
    private List<Tema> temas;

    @Test
    public void mustAddThemeToDB() throws Exception {
        Tema tema = new Tema("Geral");
        dao.persist(tema);

        temas = dao.findAllByClass(Tema.class);
        assertThat(temas.size()).isEqualTo(1);
        assertThat(temas.get(0)).isEqualTo(tema);
    }

    @Test
    public void mustAddTipToTheme() throws Exception {
        Tema tema = new Tema("Geral");
        dao.persist(tema);

        Dica dica = new Dica("Erick", "Lab1", "Não deixe de fazer o lab 1!");
        dao.persist(dica);

        tema.addDica(dica);

        assertThat(tema.getDicas().size()).isEqualTo(1);
        assertThat(tema.getDicas().get(0)).isEqualTo(dica);

    }

    @Test
    public void mustAddMetaTipToTheme() throws Exception {
        Tema tema = new Tema("Geral");
        dao.persist(tema);

        MetaDica dica = new MetaDica("Erick", "Lab1", "Não deixe de fazer o lab 1!");
        dao.persist(dica);

        tema.addDica(dica);

        assertThat(tema.getDicas().size()).isEqualTo(1);
        assertThat(tema.getDicas().get(0)).isEqualTo(dica);

    }

}
