package modelsTests;

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
    public void mustStartWithNoSeries() throws Exception {
        dicas = dao.findAllByClass(MetaDica.class);
        assertThat(dicas).isEmpty();
    }

    @Test
    public void mustAddSeriesToDB() throws Exception {
        MetaDica dica = new MetaDica("Lab1", "Não deixe para repor");
        dao.persist(dica);

        dicas = dao.findAllByClass(MetaDica.class);
        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0)).isEqualTo(dicas);
    }
}
