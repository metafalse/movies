package org.weatherbreak.movies.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class TestMovieBrowsingService extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MovieBrowsingService movieBrowsingService;

    @Test
    public void getTest() {
        Assert.assertEquals("test", movieBrowsingService.getTest());
    }
}
