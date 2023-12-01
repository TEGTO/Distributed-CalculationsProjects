package lab9.rest;
import dbController.MovieDataBaseControlling;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lab9.ServerConstants;
import movies.Movie;
import movies.MovieGenre;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/movie")
public class MovieDBRest
{
    private final MovieDataBaseControlling movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list()
    {
        List<Movie> listMovies = movieDataBaseControlling.getMovies();
        if (listMovies.isEmpty())
        {
            return Response.noContent().build();
        }
        return Response.ok(listMovies).build();
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id)
    {
        Movie movie = movieDataBaseControlling.getMovieById(id);
        if (movie != null)
        {
            return Response.ok(movie, MediaType.APPLICATION_JSON).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Movie movie) throws URISyntaxException
    {
        MovieGenre movieGenre = movieDataBaseControlling.getMovieGenreById(movie.getGenre().getMovieGenreId());
        if (movieGenre == null)
        {
            movieGenre = new MovieGenre(movie.getGenre().getMovieGenreId(), movie.getGenre().getMovieGenreName());
            movieDataBaseControlling.addMovieGenre(movieGenre);
        }
        movie.setGenre(movieGenre);
        int newMovieId = movieDataBaseControlling.addMovie(movie);
        URI uri = new URI("/movie/" + newMovieId);
        return Response.created(uri).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") int id, Movie movie)
    {
        movie.setMovieId(id);
        if (movieDataBaseControlling.updateMovie(movie))
            return Response.ok().build();
        else
            return Response.notModified().build();
    }
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id)
    {
        if (movieDataBaseControlling.removeMovie(id))
            return Response.ok().build();
        else
            return Response.notModified().build();
    }
}
