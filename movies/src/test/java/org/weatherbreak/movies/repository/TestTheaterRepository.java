package org.weatherbreak.movies.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.entity.impl.TheaterImpl;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestTheaterRepository extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private TheaterRepository theaterRepository;

    private static long sfId;
    private static long sjId;
    private static long dcId;

    @Before
    public void beforeTestClass() {
        TheaterImpl sf = new TheaterImpl();
        sf.setName("San Francisco theater");
        sfId = theaterRepository.addTheater(sf);

        TheaterImpl sj = new TheaterImpl();
        sj.setName("San Jose theater");
        sjId = theaterRepository.addTheater(sj);

        TheaterImpl dc = new TheaterImpl();
        dc.setName("Daly City theater");
        dcId = theaterRepository.addTheater(dc);
    }

    @Test
    public void addTheater() {
        Assert.assertNotEquals(0, sfId);
        Assert.assertNotEquals(0, sjId);
        Assert.assertNotEquals(0, dcId);
    }

    @Test
    public void getTheater() {
        Theater found1 = theaterRepository.getTheater(sfId);
        Assert.assertEquals(sfId, found1.getId());
        Assert.assertEquals("San Francisco theater", found1.getName());

        Theater found2 = theaterRepository.getTheater(sjId);
        Assert.assertEquals(sjId, found2.getId());
        Assert.assertEquals("San Jose theater", found2.getName());

        Theater found3 = theaterRepository.getTheater(dcId);
        Assert.assertEquals(dcId, found3.getId());
        Assert.assertEquals("Daly City theater", found3.getName());
    }

    @Test
    public void getTheaters() {
        List<Theater> founds = theaterRepository.getTheaters();

        for (Theater found : founds) {
            if (found.getId() == sfId)
                Assert.assertEquals("San Francisco theater", found.getName());

            if (found.getId() == sjId)
                Assert.assertEquals("San Jose theater", found.getName());

            if (found.getId() == dcId)
                Assert.assertEquals("Daly City theater", found.getName());
        }
    }

    @Test
    public void getTheatersByName() {
        List<Theater> founds1 = theaterRepository.getTheatersByName("San");

        for (Theater found : founds1) {
            if (found.getId() == sfId)
                Assert.assertEquals("San Francisco theater", found.getName());

            if (found.getId() == sjId)
                Assert.assertEquals("San Jose theater", found.getName());
        }

        List<Theater> founds2 = theaterRepository.getTheatersByName("City");

        for (Theater found : founds2) {
            if (found.getId() == dcId)
                Assert.assertEquals("Daly City theater", found.getName());
        }
    }
}
