package org.weatherbreak.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weatherbreak.movies.entity.Screening;
import org.weatherbreak.movies.repository.ScreeningRepository;
import org.weatherbreak.movies.service.ScreeningService;
import org.weatherbreak.movies.service.exception.InvalidFieldException;

import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    @Transactional
    public Screening addScreening(Screening screening) {
        if (screening.getMovie() == null) {
            throw new InvalidFieldException("movie is required");
        }

        if (screening.getTheater() == null) {
            throw new InvalidFieldException("theater is required");
        }

        if (screening.getShowtime() == null) {
            throw new InvalidFieldException("showtime is required");
        }

        long screeningId = screeningRepository.addScreening(screening);
        return getScreening(screeningId);
    }

    @Override
    @Transactional
    public Screening getScreening(long screeningId) {
        return screeningRepository.getScreening(screeningId);
    }

    @Override
    @Transactional
    public List<Screening> getScreenings() {
        return screeningRepository.getScreenings();
    }

    @Override
    @Transactional
    public List<Screening> getScreeningsByMovieId(long movieId) {
        return screeningRepository.getScreeningsByMovieId(movieId);
    }

    @Override
    @Transactional
    public List<Screening> getScreeningsByTheaterId(long theaterId) {
        return screeningRepository.getScreeningsByTheaterId(theaterId);
    }

    @Override
    @Transactional
    public List<Screening> getScreeningsByMovieIdAndTheaterId(long movieId, long theaterId) {
        return screeningRepository.getScreeningsByMovieIdAndTheaterId(movieId, theaterId);
    }
}
