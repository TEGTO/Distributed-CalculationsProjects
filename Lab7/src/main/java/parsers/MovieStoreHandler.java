package parsers;
import movies.MovieFields;
import movies.Movie;
import movies.MovieGenre;
import movies.MovieStore;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieStoreHandler extends MyHandler<MovieStore>
{
    private StringBuilder elementValue;
    private MovieStore movieStore = new MovieStore();
    private MovieGenre latestMovieGenre;
    private Movie latestMovie;
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (elementValue == null)
            elementValue = new StringBuilder();
        else
            elementValue.append(ch, start, length);
    }
    @Override
    public void startDocument() throws SAXException
    {
        MovieStore movieStore = new MovieStore();
    }
    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case MovieFields.MOVIE_GENRE:
                latestMovieGenre = new MovieGenre();
                latestMovieGenre.setMovieGenreId(Integer.parseInt(attr.getValue(MovieFields.MOVIE_GENRE_ID)));
                latestMovieGenre.setMovieGenreName(attr.getValue(MovieFields.MOVIE_GENRE_NAME));
                movieStore.addMovieGenre(latestMovieGenre);
                break;
            case MovieFields.MOVIE:
                latestMovie = new Movie();
                latestMovie.setMovieId(Integer.parseInt(attr.getValue(MovieFields.MOVIE_ID)));
                latestMovie.setMovieName(attr.getValue(MovieFields.MOVIE_NAME));
                latestMovie.setCreationYear(Integer.parseInt(attr.getValue(MovieFields.CREATION_YEAR)));
                latestMovie.setDuration(Float.parseFloat(attr.getValue(MovieFields.DURATION)));
                latestMovie.setGenre(latestMovieGenre);
                movieStore.addMovie(latestMovie);
                break;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        switch (qName)
        {
            case MovieFields.MOVIE_ID:
                latestMovie.setMovieId(Integer.parseInt(elementValue.toString().trim()));
                break;
            case MovieFields.MOVIE_GENRE_ID:
                latestMovieGenre.setMovieGenreId(Integer.parseInt(elementValue.toString().trim()));
                break;
            case MovieFields.MOVIE_GENRE_NAME:
                latestMovieGenre.setMovieGenreName(elementValue.toString().trim());
                break;
            case MovieFields.MOVIE_NAME:
                latestMovie.setMovieName(elementValue.toString().trim());
                break;
            case MovieFields.CREATION_YEAR:
                latestMovie.setCreationYear(Integer.parseInt(elementValue.toString().trim()));
                break;
            case MovieFields.DURATION:
                latestMovie.setDuration(Float.parseFloat(elementValue.toString().trim()));
                break;
        }
        elementValue.setLength(0);
    }
    @Override
    public void setField(String propName, String propData, Map<String, String> attributes) {
        switch (propName) {
            case MovieFields.MOVIE_GENRE:
                latestMovieGenre = new MovieGenre();
                latestMovieGenre.setMovieGenreId(Integer.parseInt(attributes.get(MovieFields.MOVIE_GENRE_ID)));
                latestMovieGenre.setMovieGenreName(attributes.get(MovieFields.MOVIE_GENRE_NAME));
                movieStore.addMovieGenre(latestMovieGenre);
                break;
            case MovieFields.MOVIE:
                latestMovie = new Movie();
                latestMovie.setMovieId(Integer.parseInt(attributes.get(MovieFields.MOVIE_ID)));
                latestMovie.setMovieName(attributes.get(MovieFields.MOVIE_NAME));
                latestMovie.setCreationYear(Integer.parseInt(attributes.get(MovieFields.CREATION_YEAR)));
                latestMovie.setDuration(Float.parseFloat(attributes.get(MovieFields.DURATION)));
                latestMovie.setGenre(latestMovieGenre);
                movieStore.addMovie(latestMovie);
                break;
        }
    }
    @Override
    public List<MovieStore> getHandledList()
    {
        ArrayList<MovieStore> movieStores = new ArrayList<MovieStore>();
        movieStores.add(movieStore);
        return movieStores;
    }
    @Override
    public String getRootElName() {return MovieFields.MOVIE_GENRE;}
}
