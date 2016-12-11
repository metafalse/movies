package org.weatherbreak.movies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.UserImpl;

import java.util.List;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestUserRepository extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UserRepository userRepository;

    private static String newUser1Name;
    private static String newUser2Name;
    private static long user1Id;
    private static long user2Id;

    @Before
    public void beforeTestClass() {
        UserImpl newUser1 = new UserImpl();
        newUser1Name = "user" + new Random().nextInt(99999);
        newUser1.setName(newUser1Name);
        newUser1.setPassword("1234");
        user1Id = userRepository.addUser(newUser1);

        UserImpl newUser2 = new UserImpl();
        newUser2Name = "se" + new Random().nextInt(99999);
        newUser2.setName(newUser2Name);
        newUser2.setPassword("1234");
        user2Id = userRepository.addUser(newUser2);
    }

    @Test
    public void addUser() {
        Assert.assertNotEquals(0, user1Id);
        Assert.assertNotEquals(0, user2Id);
    }

    @Test
    public void getUser() {
        User found = userRepository.getUser(user1Id);

        Assert.assertEquals(user1Id,      found.getId());
        Assert.assertEquals(newUser1Name, found.getName());
        Assert.assertEquals("1234",       found.getPassword());
    }

    @Test
    public void getUsers() {
        List<User> founds = userRepository.getUsers();

        for (User found : founds) {
            if (found.getId() == user1Id) {
                Assert.assertEquals(user1Id,      found.getId());
                Assert.assertEquals(newUser1Name, found.getName());
                Assert.assertEquals("1234",       found.getPassword());
            }

            if (found.getId() == user2Id) {
                Assert.assertEquals(user2Id,      found.getId());
                Assert.assertEquals(newUser2Name, found.getName());
                Assert.assertEquals("1234",       found.getPassword());
            }
        }
    }

    @Test
    public void getUsersByName() {
        List<User> founds = userRepository.getUsersByName("user");

        boolean user2IsFound = false;
        for (User found : founds) {
            if (found.getId() == user1Id) {
                Assert.assertEquals(newUser1Name, found.getName());
                Assert.assertEquals("1234", found.getPassword());
            }

            if (found.getId() == user2Id)
                user2IsFound = true;
        }
        Assert.assertFalse(user2IsFound);

        List<User> notFound = userRepository.getUsersByName("notfounduser");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void update() {
        User user1 = userRepository.getUser(user1Id);
        User user2 = userRepository.getUser(user2Id);

        Assert.assertNotEquals(user1.getName(), user2.getName());

        UserImpl updatedUser2 = (UserImpl) user2;
        updatedUser2.setName(newUser1Name);
        userRepository.update(updatedUser2);

        Assert.assertEquals(user1.getName(), user2.getName());
    }
}
