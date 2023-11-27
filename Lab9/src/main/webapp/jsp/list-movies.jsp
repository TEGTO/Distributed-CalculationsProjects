<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <div class="container">
        <h2>Movies</h2>
        <!--Search Form -->
        <form action="/movie" method="get" id="seachMoviesForm" role="form" >
            <input type="hidden" id="searchAction" name="searchAction" value="searchMovieById"/>
            <div class="form-group col-xs-5">
                <input type="text" name="idMovie" id="idMovie" class="form-control" required="true"
                       placeholder="Type the ID of a movie"/>
            </div>
            <button type="submit" class="btn btn-info">
                <span class="glyphicon glyphicon-search"></span> Search
            </button>
            <br></br>
            <br></br>
        </form>
    </div>
    <c:if test="${not empty message}">
        <div class="alert alert-success">
                ${message}
        </div>
    </c:if>
    <form action="/movie" method="post" id="movieForm" role="form" >
        <input type="hidden" id="movieId" name="movieId">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty movies}">
                <table  class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td>Creation Year</td>
                        <td>Duration</td>
                        <td>Genre Name</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="movie" items="${movies}">
                        <c:set var="classSucess" value=""/>
                        <c:if test ="${movieId == movie.getMovieId()}">
                            <c:set var="classSucess" value="info"/>
                        </c:if>
                        <tr class="${classSucess}">
                            <td>${movie.getMovieId()}</td>
                            <td>${movie.getMovieName()}</td>
                            <td>${movie.getCreationYear()}</td>
                            <td>${movie.getDuration()}</td>
                            <td>${movie.getGenre().getMovieGenreName()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br>
                <div class="alert alert-info">
                    No movies found matching your search criteria
                </div>
            </c:otherwise>
        </c:choose>
    </form>
</div>
</body>
</html>