package movies;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonCreator
    public Movie(@JsonProperty("movieId") Integer movieId, @JsonProperty("movieName") String movieName, @JsonProperty("creationYear") Integer creationYear, @JsonProperty("duration") Float duration, @JsonProperty("genreId") Integer genreId, @JsonProperty("genreName") String genreName)
    {
        this.movieId = movieId;
        this.movieName = movieName;
        this.creationYear = creationYear;
        this.duration = duration;
        this.genre = new MovieGenre(genreId, genreName);
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
    public void setGenre(int genreId) {this.genre = new MovieGenre(genreId, "");}
    public void setGenre(int genreId, String genreName) {this.genre = new MovieGenre(genreId, genreName);}
    public String toString()
    {
        return "----ID: " + movieId + " Name: " + movieName + " Creation Year: " + creationYear + " Duration: " + duration + "----";
    }
}
