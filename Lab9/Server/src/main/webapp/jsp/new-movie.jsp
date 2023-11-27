<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form action="/movie" method="post" role="form" data-toggle="validator">
        <c:if test="${empty action}">
            <c:choose>
                <c:when test="${not empty movie}">
                    <c:set var="action" value="edit"/>
                </c:when>
                <c:otherwise>
                    <c:set var="action" value="add"/>
                </c:otherwise>
            </c:choose>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="movieId" name="movieId" value="${movie.getMovieId()}">
        <h2>Movie</h2>
        <div class="form-group col-xs-4">
            <label for="name" class="control-label col-xs-4">Name:</label>
            <input type="text" name="name" id="name" class="form-control" value="${movie.getMovieName()}"
                   required="true"/>

            <label for="creationYear" class="control-label col-xs-4">Creation Year</label>
            <input type="number" pattern="[0-9]+([\.,][0-9]+)?" step="0.01" name="creationYear" id="creationYear"
                   class="form-control" value="${movie.getCreationYear()}" required="true"/>

            <label for="duration" class="control-label col-xs-4">Duration:</label>
            <input type="number" pattern="[0-9]+([\.,][0-9]+)?" step="0.01" name="duration" id="duration"
                   class="form-control"
                   value="${movie.getDuration()}" required="true"/>

            <label for="movieGenreName" class="control-label col-xs-4">Movie Genre Name:</label>
            <input type="text" name="movieGenreName" id="movieGenreName"
                   class="form-control" value="${movie.getGenre().getMovieGenreName()}" required="true"/>
            <label for="movieGenreId" class="control-label col-xs-4">Movie Genre Id:</label>
            <input type="number" pattern="[0-9]+([\.,][0-9]+)?" name="movieGenreId" id="movieGenreId"
                   class="form-control" value="${movie.getGenre().getMovieGenreId()}" required="true"/>
            <br></br>
            <button type="submit" class="btn btn-primary  btn-md">Accept</button>
        </div>
    </form>
</div>
</body>
</html>