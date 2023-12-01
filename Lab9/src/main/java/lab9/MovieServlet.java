package lab9;
import dbController.MovieDataBaseControlling;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab9.ServerConstants;
import movies.Movie;
import movies.MovieGenre;
import myLogger.MyLogger;

import java.io.IOException;
import java.util.List;

public class MovieServlet extends HttpServlet
{
    private MovieDataBaseControlling movieDataBaseControlling;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String action = req.getParameter("searchAction");
        if (movieDataBaseControlling == null)
            movieDataBaseControlling = new MovieDataBaseControlling(ServerConstants.DB_URL, ServerConstants.DB_USER, ServerConstants.DB_PASSWORD);
        if (action != null)
        {
            switch (action)
            {
                case "searchMovieById":
                    searchMovieById(req, resp);
                    break;
            }
        }
        else
        {
            List<Movie> result = movieDataBaseControlling.getMovies();
            forwardListEmployees(req, resp, result);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String action = req.getParameter("action");
        MyLogger.logger.info(action);
        switch (action)
        {
            case "add":
                addMovieAction(req, resp);
                break;
            case "edit":
                editMovieAction(req, resp);
                break;
            default:
                List<Movie> result = movieDataBaseControlling.getMovies();
                forwardListEmployees(req, resp, result);
                break;
        }

    }
    private void searchMovieById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int movieId = Integer.parseInt(req.getParameter("idMovie"));
        Movie movie = null;
        try
        {
            movie = movieDataBaseControlling.getMovieById(movieId);
        }
        catch (Exception ex)
        {
            MyLogger.logger.error(ex.getMessage());
        }
        req.setAttribute("movie", movie);
        String nextJSP = "jsp/new-movie.jsp";
        req.getRequestDispatcher(nextJSP).forward(req, resp);
    }
    private void addMovieAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String name = req.getParameter("name");
        int creationYear = Integer.parseInt(req.getParameter("creationYear"));
        float duration = Float.parseFloat(req.getParameter("duration"));
        int movieGenreId = Integer.parseInt(req.getParameter("movieGenreId"));
        MovieGenre movieGenre = movieDataBaseControlling.getMovieGenreById(movieGenreId);
        if (movieGenre == null)
        {
            movieGenre = new MovieGenre(movieGenreId, req.getParameter("movieGenreName"));
            movieDataBaseControlling.addMovieGenre(movieGenre);
        }
        Movie movie = new Movie(0, name, creationYear, duration, movieGenre);
        movieDataBaseControlling.addMovie(movie);
        String message = "The new movie has been successfully created.";
        req.getSession().setAttribute("message", message);
        resp.sendRedirect(req.getContextPath() + "/movie");
    }
    private void editMovieAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int movieId = Integer.parseInt(req.getParameter("movieId"));
        String name = req.getParameter("name");
        int creationYear = Integer.parseInt(req.getParameter("creationYear"));
        float duration = Float.parseFloat(req.getParameter("duration"));
        int movieGenreId = Integer.parseInt(req.getParameter("movieGenreId"));
        MovieGenre movieGenre = movieDataBaseControlling.getMovieGenreById(movieGenreId);
        Movie movie = new Movie(movieId, name, creationYear, duration, movieGenre);
        movieDataBaseControlling.updateMovie(movie);
        String message = "The employee has been successfully updated.";
        req.getSession().setAttribute("message", message);
        resp.sendRedirect(req.getContextPath() + "/movie");
    }
    private void forwardListEmployees(HttpServletRequest req, HttpServletResponse resp, List employeeList) throws ServletException, IOException
    {
        String message = (String) req.getSession().getAttribute("message");
        req.getSession().removeAttribute("successMessage");
        req.setAttribute("message", message);
        String nextJSP = "jsp/list-movies.jsp";
        req.setAttribute("movies", employeeList);
        req.getRequestDispatcher(nextJSP).forward(req, resp);
    }
}
