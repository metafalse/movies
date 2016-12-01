package org.weatherbreak.movies.service.impl;

import org.springframework.stereotype.Service;
import org.weatherbreak.movies.service.MovieBrowsingService;

@Service
public class MovieBrowsingServiceImpl implements MovieBrowsingService {

    public String getTest() {
        return "test";
    }
}
