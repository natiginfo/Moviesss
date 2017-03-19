package com.koonat.moviesss.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Natig on 3/19/17.
 */
public class MovieTest {

    Movie movie;

    @Before
    public void setup() {
        movie = new Movie();
    }

    @Test
    public void shouldPass() {
        assertEquals(1, 1);
    }

    @Test
    public void getYearWithCorrectFormatPasses() {
        movie.setReleaseDate("2016-06-16");
        assertEquals("2016", movie.getYear());
    }

    @Test
    public void getYearWithWrongFormatFails() {
        movie.setReleaseDate("2016/06/16");
        assertEquals(null, movie.getYear());
    }

    @Test
    public void getImageUrlWithBackdropPath() {
        movie.setBackdropPath("/iWRKYHTFlsrxQtfQqFOQyceL83P.jpg");
        assertEquals("https://image.tmdb.org/t/p/w500/iWRKYHTFlsrxQtfQqFOQyceL83P.jpg", movie.getImageUrl());
    }

    @Test
    public void getImageUrlWithoutBackdropPath1() {
        movie.setBackdropPath("");
        assertEquals(null, movie.getImageUrl());
    }

    @Test
    public void getImageUrlWithoutBackdropPath2() {
        movie.setBackdropPath(null);
        assertEquals(null, movie.getImageUrl());
    }
}