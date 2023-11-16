package client.ui;
import client.serverInteraction.BaseMovieDBServerInteraction;
import movies.Movie;
import movies.MovieGenre;
import myLogger.MyLogger;

import javax.swing.*;
import java.awt.event.*;

public class ClientUI
{
    private JPanel contentPane;
    private JButton readAllMovieGenresButton;
    private JButton readAllMoviesButton;
    private JTextField movieGenreCode;
    private JTextField movieGenreName;
    private JTextField movieCreationYear;
    private JTextField movieDuration;
    private JTextField movieName;
    private JTextField movieCode;
    private JButton createGenreButton;
    private JButton readGenreByCodeButton;
    private JButton removeGenreByCodeButton;
    private JButton updateGenreByCodeButton;
    private JButton createMovieButton;
    private JButton readMovieByCodeButton;
    private JButton updateMovieByCodeButton;
    private JButton removeMovieByCodeButton;
    public ClientUI(BaseMovieDBServerInteraction abstractMovieDB)
    {
        JDialog jDialog = new JDialog();
        jDialog.setContentPane(contentPane);
        jDialog.setResizable(false);
        jDialog.setModal(true);
        jDialog.pack();
        initUIElements(abstractMovieDB);
        jDialog.setVisible(true);
        System.exit(0);
    }
    private void initUIElements(BaseMovieDBServerInteraction abstractMovieDB)
    {
        readAllMovieGenresButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for (MovieGenre movieGenre : abstractMovieDB.getMovieGenres())
                {
                    MyLogger.printInfoMessage(movieGenre.toString());
                }
            }
        });
        readAllMoviesButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for (Movie movie : abstractMovieDB.getMovies())
                {
                    MyLogger.printInfoMessage(movie.toString());
                }
            }
        });
        createGenreButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MovieGenre movieGenre = new MovieGenre(Integer.parseInt(movieGenreCode.getText()), movieGenreName.getText());
                abstractMovieDB.addMovieGenre(movieGenre);
            }
        });
        createMovieButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MovieGenre movieGenre = abstractMovieDB.getGenreById(Integer.parseInt(movieGenreCode.getText()));
                if (movieGenre == null)
                {
                    movieGenre = new MovieGenre(Integer.parseInt(movieGenreCode.getText()), movieGenreName.getText());
                    abstractMovieDB.addMovieGenre(movieGenre);
                }
                Movie movie = new Movie(Integer.parseInt(movieCode.getText()), movieName.getText(), Integer.parseInt(movieCreationYear.getText()), Float.parseFloat(movieDuration.getText()), movieGenre);
                abstractMovieDB.addMovie(movie);
            }
        });
        readGenreByCodeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MyLogger.printInfoMessage(abstractMovieDB.getGenreById(Integer.parseInt(movieGenreCode.getText())).toString());
            }
        });
        readMovieByCodeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MyLogger.printInfoMessage(abstractMovieDB.getMovieById(Integer.parseInt(movieCode.getText())).toString());
            }
        });
        updateGenreByCodeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MovieGenre movieGenre = new MovieGenre(Integer.parseInt(movieGenreCode.getText()), movieGenreName.getText());
                abstractMovieDB.updateMovieGenre(movieGenre);
            }
        });
        updateMovieByCodeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MovieGenre movieGenre = abstractMovieDB.getGenreById(Integer.parseInt(movieGenreCode.getText()));
                Movie movie = new Movie(Integer.parseInt(movieCode.getText()), movieName.getText(), Integer.parseInt(movieCreationYear.getText()), Float.parseFloat(movieDuration.getText()), movieGenre);
                abstractMovieDB.updateMovie(movie);
            }
        });
        removeGenreByCodeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MovieGenre movieGenre = abstractMovieDB.getGenreById(Integer.parseInt(movieGenreCode.getText()));
                abstractMovieDB.removeMovieGenre(movieGenre);
            }
        });
        removeMovieByCodeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                MovieGenre movieGenre = abstractMovieDB.getGenreById(Integer.parseInt(movieGenreCode.getText()));
                Movie movie = new Movie(Integer.parseInt(movieCode.getText()), movieName.getText(), Integer.parseInt(movieCreationYear.getText()), Float.parseFloat(movieDuration.getText()), movieGenre);
                abstractMovieDB.removeMovie(movie);
            }
        });
    }
}
