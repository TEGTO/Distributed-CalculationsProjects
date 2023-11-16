package movies;
import java.io.Serializable;

public class Movie implements Serializable
{
    private Integer movieId;
    private String movieName;
    private Integer creationYear;
    private Float duration;
    private MovieGenre genre;
    public Movie()
    {}
    public Movie(Integer code, String name, Integer creationYear, Float duration, MovieGenre genre)
    {
        this.movieId = code;
        this.movieName = name;
        this.creationYear = creationYear;
        this.duration = duration;
        this.genre = genre;
    }

    public Integer getMovieId()
    {
        return movieId;
    }
    public void setMovieId(Integer movieId)
    {
        this.movieId = movieId;
    }
    public String getMovieName()
    {
        return movieName;
    }
    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }
    public Integer getCreationYear()
    {
        return creationYear;
    }
    public void setCreationYear(Integer creationYear)
    {
        this.creationYear = creationYear;
    }
    public Float getDuration()
    {
        return duration;
    }
    public void setDuration(Float duration)
    {
        this.duration = duration;
    }
    public MovieGenre getGenre()
    {
        return genre;
    }
    public void setGenre(MovieGenre genre)
    {
        this.genre = genre;
    }
    public String toString()
    {
        return "----ID: " + movieId + " Name: " + movieName + " Creation Year: " + creationYear + " Duration: " + duration + "----";
    }
}
