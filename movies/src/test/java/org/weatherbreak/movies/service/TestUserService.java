package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.User;
import org.weatherbreak.movies.entity.impl.UserImpl;

import java.util.List;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestUserService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private UserService userService;

    private static String newUser1Name;
    private static User added1;
    private static User added2;

    @Before
    public void beforeTestClass() {
        UserImpl newUser1 = new UserImpl();
        newUser1Name = "user" + new Random().nextInt(99999);
        newUser1.setName(newUser1Name);
        newUser1.setPassword("1234");
        added1 = userService.addUser(newUser1);

        UserImpl newUser2 = new UserImpl();
        String newUser2Name = "se" + new Random().nextInt(99999);
        newUser2.setName(newUser2Name);
        newUser2.setPassword("1234");
        added2 = userService.addUser(newUser2);
    }

    @Test
    public void addUser() {
        Assert.assertNotEquals(0, added1.getId());
        Assert.assertNotEquals(0, added2.getId());
    }

    @Test
    public void getUser() {
        User found = userService.getUser(added1.getId());

        Assert.assertEquals(added1.getId(),       found.getId());
        Assert.assertEquals(added1.getName(),     found.getName());
        Assert.assertEquals(added1.getPassword(), found.getPassword());
    }

    @Test
    public void getUsers() {
        List<User> founds = userService.getUsers();

        for (User found : founds) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getName(),     found.getName());
                Assert.assertEquals(added1.getPassword(), found.getPassword());
            }

            if (found.getId() == added2.getId()) {
                Assert.assertEquals(added2.getName(),     found.getName());
                Assert.assertEquals(added2.getPassword(), found.getPassword());
            }
        }
    }

    @Test
    public void getUsersByName() {
        List<User> founds = userService.getUsersByName("user");

        boolean added2IsFound = false;
        for (User found : founds) {
            if (found.getId() == added1.getId()) {
                Assert.assertEquals(added1.getName(),     found.getName());
                Assert.assertEquals(added1.getPassword(), found.getPassword());
            }

            if (found.getId() == added2.getId())
                added2IsFound = true;
        }
        Assert.assertFalse(added2IsFound);

        List<User> notFound = userService.getUsersByName("notfounduser");
        Assert.assertEquals(0, notFound.size());
    }

    @Test
    public void updateUser() {
        Assert.assertNotEquals(added1.getName(), userService.getUser(added2.getId()).getName());

        UserImpl updatedUser2 = (UserImpl) added2;
        updatedUser2.setName(newUser1Name);
        userService.updateUser(updatedUser2);

        Assert.assertEquals(added1.getName(), userService.getUser(added2.getId()).getName());
    }

    @Test
    public void isValidPassword() {
        Assert.assertTrue(userService.isValidPassword(added1.getId(), "1234"));
        Assert.assertFalse(userService.isValidPassword(added1.getId(), "1235"));
    }
}
