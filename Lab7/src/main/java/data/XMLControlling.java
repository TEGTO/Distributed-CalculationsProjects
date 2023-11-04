package data;
import movies.Movie;
import movies.MovieFields;
import movies.MovieGenre;
import movies.MovieStore;
import myLogger.MyLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import parsers.DomParser;
import parsers.MovieStoreHandler;
import parsers.XMLValidator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XMLControlling
{
    private XMLValidator xmlValidator = new XMLValidator();
    private String xmlValidatorPath = "src\\main\\resources\\movieStore.xsd";
    private String url;
    public XMLControlling(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public void saveToFile(ArrayList<MovieGenre> movieGenres, ArrayList<Movie> movies)
    {
        try
        {
            DocumentBuilder db = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement(MovieFields.ROOT_EL);
            doc.appendChild(root);
            for (MovieGenre element : movieGenres)
            {
                Element movieGenre = doc.createElement(MovieFields.MOVIE_GENRE);
                movieGenre.setAttribute(MovieFields.MOVIE_GENRE_ID, element.getMovieGenreId().toString());
                movieGenre.setAttribute(MovieFields.MOVIE_GENRE_NAME, element.getMovieGenreName());
                root.appendChild(movieGenre);
                List<Movie> moviesWithSameGenre = movies.stream().filter(movie -> movie.getGenre() == element).collect(Collectors.toList());
                for (Movie movieEl : moviesWithSameGenre)
                {
                    Element movie = doc.createElement(MovieFields.MOVIE);
                    movie.setAttribute(MovieFields.MOVIE_ID, movieEl.getMovieId().toString());
                    movie.setAttribute(MovieFields.MOVIE_NAME, movieEl.getMovieName().toString());
                    movie.setAttribute(MovieFields.CREATION_YEAR, movieEl.getCreationYear().toString());
                    movie.setAttribute(MovieFields.DURATION, movieEl.getDuration().toString());
                    movieGenre.appendChild(movie);
                }
            }
            Source domSource = new DOMSource(doc);
            Result fileResult = new StreamResult(new File(url));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.transform(domSource, fileResult);
        }
        catch (Exception ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
        }
    }
    public MovieStore loadMovieStore()
    {
        if (xmlValidator.validate(url, xmlValidatorPath))
        {
            try
            {
                DomParser domParser = new DomParser<MovieStore>();
                List<MovieStore> movieStores = domParser.parseDOM(new File(url), new MovieStoreHandler());
                return movieStores.get(0);
            }
            catch (Exception ex)
            {
                MyLogger.printErrorMessage(ex.getMessage());
            }
        }
        return null;
    }
    public Boolean addNewMovie(Movie newMovie)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(url));

            // Find the MovieGenre element where you want to add the Movie
            Element targetMovieGenre = findMovieGenreById(doc, newMovie.getGenre().getMovieGenreId().toString()); // Replace with the desired movieGenreId

            if (targetMovieGenre != null)
            {
                // Create a new Movie element
                Element newMovieEl = doc.createElement(MovieFields.MOVIE);
                newMovieEl.setAttribute(MovieFields.MOVIE_ID, newMovie.getMovieId().toString()); // Replace with the appropriate movieId
                newMovieEl.setAttribute(MovieFields.MOVIE_NAME, newMovie.getMovieName());
                newMovieEl.setAttribute(MovieFields.CREATION_YEAR, newMovie.getCreationYear().toString());
                newMovieEl.setAttribute(MovieFields.DURATION, newMovie.getDuration().toString());
                targetMovieGenre.appendChild(newMovieEl);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(url));
                transformer.transform(source, result);
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (ParserConfigurationException | IOException | TransformerException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
            return false;
        }
    }
    public boolean addNewMovieGenre(MovieGenre newMovieGenre)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(new File(url));
            Element root = doc.getDocumentElement();
            Element movieGenre = doc.createElement(MovieFields.MOVIE_GENRE);
            movieGenre.setAttribute(MovieFields.MOVIE_GENRE_ID, newMovieGenre.getMovieGenreId().toString());
            movieGenre.setAttribute(MovieFields.MOVIE_GENRE_NAME, newMovieGenre.getMovieGenreName());
            root.appendChild(movieGenre);
            Source domSource = new DOMSource(doc);
            Result fileResult = new StreamResult(new File(url));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.transform(domSource, fileResult);
            return true;
        }
        catch (Exception ex)
        {
            MyLogger.printErrorMessage(ex.getMessage());
            return false;
        }
    }
    public boolean removeMovie(String movieIdToRemove)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);

            // Find and remove the Movie element with the specified movieId
            NodeList movieGenres = doc.getElementsByTagName(MovieFields.MOVIE_GENRE);
            for (int i = 0; i < movieGenres.getLength(); i++)
            {
                Element movieGenreElement = (Element) movieGenres.item(i);
                NodeList movieList = movieGenreElement.getElementsByTagName(MovieFields.MOVIE);
                for (int j = 0; j < movieList.getLength(); j++)
                {
                    Element movieElement = (Element) movieList.item(j);
                    String movieId = movieElement.getAttribute(MovieFields.MOVIE_ID);
                    if (movieId.equals(movieIdToRemove))
                    {
                        movieGenreElement.removeChild(movieElement);
                    }
                }
            }

            // Save the updated XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(url));
            transformer.transform(source, result);
            return true; // Movie removed successfully
        }
        catch (ParserConfigurationException | IOException | TransformerException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
            return false; // Failed to remove the movie
        }
    }
    public boolean removeMovieGenre(String movieGenreIdToRemove)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            // Find and remove the MovieGenre element with the specified movieGenreId
            Element rootElement = doc.getDocumentElement();
            NodeList movieGenreList = doc.getElementsByTagName(MovieFields.MOVIE_GENRE);
            for (int i = 0; i < movieGenreList.getLength(); i++)
            {
                Element movieGenreElement = (Element) movieGenreList.item(i);
                String movieGenreId = movieGenreElement.getAttribute(MovieFields.MOVIE_GENRE_ID);
                if (movieGenreId.equals(movieGenreIdToRemove))
                    rootElement.removeChild(movieGenreElement);
            }
            // Save the updated XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(url));
            transformer.transform(source, result);
            return true; // MovieGenre removed successfully
        }
        catch (ParserConfigurationException | IOException | TransformerException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
            return false; // Failed to remove the MovieGenre
        }
    }
    public boolean updateMovieById(int movieIdToUpdate, Movie newMovie)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            // Find and update the Movie element with the specified movieId
            NodeList movieList = doc.getElementsByTagName(MovieFields.MOVIE);
            for (int i = 0; i < movieList.getLength(); i++)
            {
                Element movieElement = (Element) movieList.item(i);
                int movieId = Integer.parseInt(movieElement.getAttribute(MovieFields.MOVIE_ID));
                if (movieId == movieIdToUpdate)
                {
                    // Update the movie attributes using the provided Movie object
                    movieElement.setAttribute(MovieFields.MOVIE_NAME, newMovie.getMovieName());
                    movieElement.setAttribute(MovieFields.CREATION_YEAR, String.valueOf(newMovie.getCreationYear()));
                    movieElement.setAttribute(MovieFields.DURATION, String.valueOf(newMovie.getDuration()));
                }
            }
            // Save the updated XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(url));
            transformer.transform(source, result);
            return true; // Movie updated successfully
        }
        catch (ParserConfigurationException | IOException | TransformerException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
            return false; // Failed to update the movie
        }
    }
    public boolean updateMovieGenreById(int movieGenreIdToUpdate, MovieGenre newMovieGenre)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            // Find and update the MovieGenre element with the specified movieGenreId
            NodeList movieGenreList = doc.getElementsByTagName(MovieFields.MOVIE_GENRE);
            for (int i = 0; i < movieGenreList.getLength(); i++)
            {
                Element movieGenreElement = (Element) movieGenreList.item(i);
                int movieGenreId = Integer.parseInt(movieGenreElement.getAttribute(MovieFields.MOVIE_GENRE_ID));
                if (movieGenreId == movieGenreIdToUpdate)
                {
                    // Update the MovieGenre attributes using the provided MovieGenre object
                    movieGenreElement.setAttribute(MovieFields.MOVIE_GENRE_NAME, newMovieGenre.getMovieGenreName());
                }
            }
            // Save the updated XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(url));
            transformer.transform(source, result);
            return true; // MovieGenre updated successfully
        }
        catch (ParserConfigurationException | IOException | TransformerException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
            return false; // Failed to update the MovieGenre
        }
    }

    public Movie getMovieById(int movieIdToFind)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);

            // Find the Movie element with the specified movieId
            NodeList movieList = doc.getElementsByTagName(MovieFields.MOVIE);

            for (int i = 0; i < movieList.getLength(); i++)
            {
                Element movieElement = (Element) movieList.item(i);
                int movieId = Integer.parseInt(movieElement.getAttribute(MovieFields.MOVIE_ID));
                if (movieId == movieIdToFind)
                {
                    // Extract the movie attributes
                    String movieName = movieElement.getAttribute(MovieFields.MOVIE_NAME);
                    int creationYear = Integer.parseInt(movieElement.getAttribute(MovieFields.CREATION_YEAR));
                    float duration = Float.parseFloat(movieElement.getAttribute(MovieFields.DURATION));
                    // Create a Movie object and return it
                    return new Movie(movieId, movieName, creationYear, duration, findGenreByMovieId(movieId));
                }
            }

        }
        catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
        }

        return null; // Movie not found
    }
    public MovieGenre getMovieGenreById(int movieGenreIdToFind)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            // Find the MovieGenre element with the specified movieGenreId
            NodeList movieGenreList = doc.getElementsByTagName(MovieFields.MOVIE_GENRE);
            for (int i = 0; i < movieGenreList.getLength(); i++)
            {
                Element movieGenreElement = (Element) movieGenreList.item(i);
                int movieGenreId = Integer.parseInt(movieGenreElement.getAttribute(MovieFields.MOVIE_GENRE_ID));
                if (movieGenreId == movieGenreIdToFind)
                {
                    String movieGenreName = movieGenreElement.getAttribute(MovieFields.MOVIE_GENRE_NAME);
                    return new MovieGenre(movieGenreId, movieGenreName);
                }
            }
        }
        catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
        }
        return null; // MovieGenre not found
    }
    public Element findMovieGenreById(Document doc, String movieGenreId)
    {
        NodeList movieGenreList = doc.getElementsByTagName(MovieFields.MOVIE_GENRE);
        for (int i = 0; i < movieGenreList.getLength(); i++)
        {
            Element movieGenre = (Element) movieGenreList.item(i);
            String id = movieGenre.getAttribute(MovieFields.MOVIE_GENRE_ID);
            if (id.equals(movieGenreId))
                return movieGenre;
        }
        return null;
    }
    public MovieGenre findGenreByMovieId(int movieIdToFind)
    {
        try
        {
            // Load the existing XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            // Find the Movie element with the specified movieId
            NodeList movieList = doc.getElementsByTagName(MovieFields.MOVIE);
            for (int i = 0; i < movieList.getLength(); i++)
            {
                Element movieElement = (Element) movieList.item(i);
                int movieId = Integer.parseInt(movieElement.getAttribute(MovieFields.MOVIE_ID));
                if (movieId == movieIdToFind)
                {
                    // Extract the genre information by navigating to the parent MovieGenre element
                    Element movieGenreElement = (Element) movieElement.getParentNode();
                    int movieGenreId = Integer.parseInt(movieGenreElement.getAttribute(MovieFields.MOVIE_GENRE_ID));
                    String movieGenreName = movieGenreElement.getAttribute(MovieFields.MOVIE_GENRE_NAME);
                    // Create a MovieGenre object and return it
                    return new MovieGenre(movieGenreId, movieGenreName);
                }
            }
        }
        catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
        }
        return null; // Movie not found or genre not found
    }
    public ArrayList<Movie> getMoviesByGenreId(String movieGenreId)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            ArrayList<Movie> moviesInGenre = new ArrayList<>();
            Element movieGenreElement = findMovieGenreById(doc, movieGenreId);
            MovieGenre movieGenre = getMovieGenreById(Integer.parseInt(movieGenreId));
            if (movieGenreElement != null)
            {
                NodeList movieList = movieGenreElement.getElementsByTagName(MovieFields.MOVIE);
                for (int i = 0; i < movieList.getLength(); i++)
                {
                    Element movieElement = (Element) movieList.item(i);
                    int movieId = Integer.parseInt(movieElement.getAttribute(MovieFields.MOVIE_ID));
                    String movieName = movieElement.getAttribute(MovieFields.MOVIE_NAME);
                    int creationYear = Integer.parseInt(movieElement.getAttribute(MovieFields.CREATION_YEAR));
                    float duration = Float.parseFloat(movieElement.getAttribute(MovieFields.DURATION));

                    // Create a Movie object and add it to the list
                    Movie movie = new Movie(movieId, movieName, creationYear, duration, movieGenre);
                    moviesInGenre.add(movie);
                }
            }
            return moviesInGenre;
        }
        catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e)
        {
            MyLogger.printErrorMessage(e.getMessage());
        }
        return null;
    }
}
