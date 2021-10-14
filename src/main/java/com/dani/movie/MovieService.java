package com.dani.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dani.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

	private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
	
    private final MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public List<Movie> getMovies() {
    	logger.info("Entering getMovies()");
    	
        return movieDao.selectMovies();
    }

    public void addNewMovie(Movie movie) {
        // TODO: check if movie exists
        int result = movieDao.insertMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
            int result = movieDao.deleteMovie(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete movie");
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }

    public Movie getMovie(int id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }

    public void updateMovie(Integer id, Movie updatedMovie) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(oldMovie -> {
            int result = movieDao.updateMovie(id, updatedMovie);
            if (result != 1) {
                throw new IllegalStateException("oops could not update movie");
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }

}
