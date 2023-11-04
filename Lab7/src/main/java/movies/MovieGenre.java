package movies;
public class MovieGenre
{
    private Integer movieGenreId;
    private String movieGenreName;
    public  MovieGenre ()
    {}
    public MovieGenre(int code, String name)
    {
        this.movieGenreId = code;
        this.movieGenreName = name;
    }
    public String getMovieGenreName()
    {
        return movieGenreName;
    }
    public void setMovieGenreName(String movieGenreName)
    {
        this.movieGenreName = movieGenreName;
    }
    public Integer getMovieGenreId()
    {
        return movieGenreId;
    }
    public void setMovieGenreId(Integer movieGenreId)
    {
        this.movieGenreId = movieGenreId;
    }
}
