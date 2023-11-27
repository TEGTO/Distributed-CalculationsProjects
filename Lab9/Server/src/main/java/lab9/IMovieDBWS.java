package lab9;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface IMovieDBWS
{
    @WebMethod
    public String getMovieById(@WebParam(name = "movieId")Integer id);
    @WebMethod
    public String addNewMovie(
            @WebParam(name = "movieName") String movieName,
            @WebParam(name = "creationYear") Integer creationYear,
            @WebParam(name = "duration") Float duration,
            @WebParam(name = "movieGenreId") Integer movieGenreId,
            @WebParam(name = "movieGenreName") String movieGenreName);
}
