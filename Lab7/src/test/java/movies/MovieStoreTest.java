package movies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovieStoreTest
{

    private static final String xmlPath1 = "src\\main\\resources\\movieStoreTest1.xml";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/movie_store";
    private static final String dbUser = "root";
    private static final String dbPassword = "";

    @Test
    public void testAddNewMovieToXML() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre horrorGenre = new MovieGenre(1, "Horror");
        Movie newMovie = new Movie(6, "Test Movie", 2023, 1.5f, horrorGenre);
        assertTrue(movieStore.addNewMovieToXML(newMovie));
        Movie retrievedMovie = movieStore.getMovieFromXML(6);
        assertNotNull(retrievedMovie);
        assertEquals(newMovie.getMovieName(), retrievedMovie.getMovieName());
        movieStore.removeMovieXML(6);
    }

    @Test
    public void testAddNewMovieToDB() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre actionGenre = new MovieGenre(2, "Action");
        Movie newMovie = new Movie(7, "Test Movie", 2023, 1.5f, actionGenre);
        assertTrue(movieStore.addNewMovieToDB(newMovie));
        Movie retrievedMovie = movieStore.getMovieFromDB(7);
        assertNotNull(retrievedMovie);
        assertEquals(newMovie.getMovieName(), retrievedMovie.getMovieName());
        movieStore.removeMovieDB(retrievedMovie);
    }

    @Test
    public void testAddNewMovieGenreToXML() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre newMovieGenre = new MovieGenre(4, "Test Genre");
        assertTrue(movieStore.addNewMovieGenreToXML(newMovieGenre));
        MovieGenre retrievedMovieGenre = movieStore.getMovieGenreFromXML(4);
        assertNotNull(retrievedMovieGenre);
        assertEquals(newMovieGenre.getMovieGenreName(), retrievedMovieGenre.getMovieGenreName());
        movieStore.removeMovieGenreXML(4);
    }

    @Test
    public void testAddNewMovieGenreToDB() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre newMovieGenre = new MovieGenre(4, "Test Genre");
        assertTrue(movieStore.addNewMovieGenreToDB(newMovieGenre));
        MovieGenre retrievedMovieGenre = movieStore.getMovieGenreFromDB(4);
        assertNotNull(retrievedMovieGenre);
        assertEquals(newMovieGenre.getMovieGenreName(), retrievedMovieGenre.getMovieGenreName());
        movieStore.removeMovieGenreDB(retrievedMovieGenre);
    }

    @Test
    public void testUpdateMovieXML() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre horrorGenre = new MovieGenre(1, "Horror");
        Movie originMovie = movieStore.getMovieFromXML(1);
        Movie updatedMovie = new Movie(1, "Updated Movie", 1980, 2.5f, horrorGenre);
        assertTrue(movieStore.updateMovieXML(1, updatedMovie));
        Movie retrievedMovie = movieStore.getMovieFromXML(1);
        assertNotNull(retrievedMovie);
        assertEquals(updatedMovie.getMovieName(), retrievedMovie.getMovieName());
        assertTrue(movieStore.updateMovieXML(1, originMovie));
        retrievedMovie = movieStore.getMovieFromXML(1);
        assertEquals(originMovie.getMovieName(), retrievedMovie.getMovieName());
    }

    @Test
    public void testUpdateMovieDB() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre actionGenre = new MovieGenre(2, "Action");
        Movie originMovie = movieStore.getMovieFromDB(3);
        Movie updatedMovie = new Movie(3, "Updated Movie", 1988, 2.1f, actionGenre);
        assertTrue(movieStore.updateMovieDB(updatedMovie));
        Movie retrievedMovie = movieStore.getMovieFromDB(3);
        assertNotNull(retrievedMovie);
        assertEquals(updatedMovie.getMovieName(), retrievedMovie.getMovieName());
        assertTrue(movieStore.updateMovieDB(originMovie));
        retrievedMovie = movieStore.getMovieFromDB(3);
        assertEquals(originMovie.getMovieName(), retrievedMovie.getMovieName());
    }

    @Test
    public void testRemoveMovieXML() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        Movie originMovie = movieStore.getMovieFromXML(1);
        assertTrue(movieStore.removeMovieXML(1));
        Movie retrievedMovie = movieStore.getMovieFromXML(1);
        assertNull(retrievedMovie);
        assertTrue(movieStore.addNewMovieToXML(originMovie));
        retrievedMovie = movieStore.getMovieFromXML(1);
        assertNotNull(retrievedMovie);
    }

    @Test
    public void testRemoveMovieDB() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        Movie originMovie = movieStore.getMovieFromDB(2);
        Movie movieToRemove = new Movie(2, "The Thing", 1982, 1.5f, new MovieGenre(1, "Horror"));
        assertTrue(movieStore.removeMovieDB(movieToRemove));
        Movie retrievedMovie = movieStore.getMovieFromDB(2);
        assertNull(retrievedMovie);
        movieStore.addNewMovieToDB(originMovie);
        retrievedMovie = movieStore.getMovieFromDB(2);
        assertNotNull(retrievedMovie);
    }

    @Test
    public void testRemoveMovieGenreXML() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre originMovieGenre = movieStore.getMovieGenreFromXML(1);
        ArrayList<Movie> originMovies = movieStore.getMoviesByGenreXML(1);
        assertTrue(movieStore.removeMovieGenreXML(1));
        MovieGenre retrievedMovieGenre = movieStore.getMovieGenreFromXML(1);
        assertNull(retrievedMovieGenre);
        assertTrue(movieStore.addNewMovieGenreToXML(originMovieGenre));
        retrievedMovieGenre = movieStore.getMovieGenreFromXML(1);
        assertNotNull(retrievedMovieGenre);
        for (Movie movie: originMovies)
        {
            assertTrue(movieStore.addNewMovieToXML(movie));
            Movie retrievedMovie = movieStore.getMovieFromXML(movie.getMovieId());
            assertNotNull(retrievedMovie);
        }
    }

    @Test
    public void testRemoveMovieGenreDB() {
        MovieStore movieStore = new MovieStore(xmlPath1, dbUrl, dbUser, dbPassword);
        MovieGenre movieGenreToRemove = new MovieGenre(2, "Action");
        MovieGenre originMovieGenre = movieStore.getMovieGenreFromDB(2);
        ArrayList<Movie> originMovies = movieStore.getMoviesByGenreDB(2);
        assertTrue(movieStore.removeMovieGenreDB(movieGenreToRemove));
        MovieGenre retrievedMovieGenre = movieStore.getMovieGenreFromDB(2);
        assertNull(retrievedMovieGenre);

        assertTrue(movieStore.addNewMovieGenreToDB(originMovieGenre));
        retrievedMovieGenre = movieStore.getMovieGenreFromDB(2);
        assertNotNull(retrievedMovieGenre);
        for (Movie movie: originMovies)
        {
            assertTrue(movieStore.addNewMovieToDB(movie));
            Movie retrievedMovie = movieStore.getMovieFromDB(movie.getMovieId());
            assertNotNull(retrievedMovie);
        }
    }
}