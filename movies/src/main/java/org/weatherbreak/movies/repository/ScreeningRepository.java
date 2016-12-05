package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.Screening;

import java.util.List;

public interface ScreeningRepository {

    List<Screening> getScreeningsByTheaterId(long theaterId);

    List<Screening> getScreeningsByMovieId(long movieId);
}
