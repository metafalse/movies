package org.weatherbreak.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.entity.Theater;
import org.weatherbreak.movies.repository.ScreeningRepository;
import org.weatherbreak.movies.repository.TheaterRepository;
import org.weatherbreak.movies.service.TheaterService;
import org.weatherbreak.movies.service.exception.ErrorCode;
import org.weatherbreak.movies.service.exception.InvalidFieldException;
import org.weatherbreak.movies.service.exception.MoviesException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {

    private static final int MAX_NAME_LENGTH = 45;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    @Transactional
    public Theater addTheater(Theater theater) {
        if (StringUtils.isEmpty(theater.getName()) || theater.getName().length() > MAX_NAME_LENGTH) {
            throw new InvalidFieldException("name is invalid");
        }

        long theaterId = theaterRepository.addTheater(theater);
        return getTheater(theaterId);
    }

    @Override
    @Transactional
    public Theater getTheater(long theaterId) {
        return theaterRepository.getTheater(theaterId);
    }

    @Override
    @Transactional
    public List<Theater> getTheaters() {
        return theaterRepository.getTheaters();
    }

    @Override
    @Transactional
    public List<Theater> getTheatersByName(String theaterName) {
        if (StringUtils.isEmpty(theaterName)){
            throw new MoviesException(ErrorCode.MISSING_DATA, "no search parameter provided");
        }
        return theaterRepository.getTheatersByName(theaterName);
    }

    @Override
    @Transactional
    public List<Theater> getTheatersByMovieId(long movieId) {
        List<Theater> theaters = new ArrayList<>();
        List<Screening> screenings = screeningRepository.getScreeningsByMovieId(movieId);
        for (Screening screening : screenings) {
            theaters.add(screening.getTheater());
        }
        return theaters;
    }
}
