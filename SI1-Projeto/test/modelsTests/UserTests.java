package modelsTests;

import models.User;
import models.dao.GenericDAO;
import org.junit.Test;
import test.abstractTest;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class UserTests extends abstractTest{
    GenericDAO dao = new GenericDAO();
    List<User> users;

    @Test
    public void mustStartWithNoUsers() throws Exception {
        users = dao.findAllByClass(User.class);
        assertThat(users).isEmpty();
    }

    @Test
    public void mustAddUserToDB() throws Exception {
        User user = new User("Erick", "ericksantanardgs01@gmail.com", "12345");
        dao.persist(user);

        users = dao.findAllByClass(User.class);
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).isEqualTo(user);
    }
}
