package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.Screening;

import java.util.List;

public interface ScreeningService {

    Screening addScreening(Screening screening);

    Screening getScreening(long screeningId);

    List<Screening> getScreenings();

    List<Screening> getScreeningsByMovieId(long movieId);

    List<Screening> getScreeningsByTheaterId(long theaterId);

    List<Screening> getScreeningsByMovieIdAndTheaterId(long movieId, long theaterId);
}
