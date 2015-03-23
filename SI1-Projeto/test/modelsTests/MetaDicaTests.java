package modelsTests;

import models.Dica;
import models.MetaDica;
import models.dao.GenericDAO;
import org.junit.Test;
import test.abstractTest;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MetaDicaTests extends abstractTest{
    GenericDAO dao = new GenericDAO();
    List<MetaDica> dicas;

    @Test
    public void mustStartWithNoMetaTips() throws Exception {
        dicas = dao.findAllByClass(MetaDica.class);
        assertThat(dicas).isEmpty();
    }

    @Test
    public void mustAddMetaTipsToDB() throws Exception {
        MetaDica dica = new MetaDica("Erick", "Lab1", "Não deixe para repor");
        dao.persist(dica);

        dicas = dao.findAllByClass(MetaDica.class);
        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0)).isEqualTo(dicas);
    }

    @Test
    public void mustAddTipToMetaTip() throws Exception {
        MetaDica metaDica = new MetaDica("Erick", "Lab1", "Não deixe para repor");
        dao.persist(metaDica);

        Dica dica = new Dica("João", "Lab1", "Verdade, será ruim se você fizer isso");
        dao.persist(dica);

        metaDica.addDica(dica);

        assertThat(metaDica.getDicas().size()).isEqualTo(1);
        assertThat(metaDica.getDicas().get(0)).isEqualTo(dica);

    }
}
