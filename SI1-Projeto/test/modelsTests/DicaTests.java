package modelsTests;

import models.Dica;
import models.dao.GenericDAO;
import org.junit.Test;
import test.abstractTest;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class DicaTests extends abstractTest {
    GenericDAO dao = new GenericDAO();
    List<Dica> dicas;

    @Test
    public void mustStartWithNoTips() throws Exception {
        dicas = dao.findAllByClass(Dica.class);
        assertThat(dicas).isEmpty();
    }

    @Test
    public void mustAddMetaTipsToDB() throws Exception {
        Dica dica = new Dica("Erick", "Lab1", "Não deixe para repor");
        dao.persist(dica);

        dicas = dao.findAllByClass(Dica.class);
        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0)).isEqualTo(dicas);
    }

    @Test
    public void mustAddJustification() throws Exception {
        Dica dica = new Dica("Erick", "Lab1", "Não deixe para repor");
        dao.persist(dica);

        dica.addJustificativa("é melhor deixar ele para repor");
        assertThat(dica.getJustificativas()).isEqualTo("\n" + "discordo pois" + ":\n" + "é melhor deixar ele para repor" + "\n");
    }
}
