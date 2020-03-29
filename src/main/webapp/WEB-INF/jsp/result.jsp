<%@ page import="org.springframework.ui.Model" %>
<%@ page import="com.aleksenko.artemii.model.Album" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <title>First JSP</title>
</head>
    <body>
        <% Album album = (Album) request.getAttribute("answer");
            String artist = album.getArtist();
            String name = album.getName();
            String genre = album.getGenre();
            List tracks = album.getTrackList();
        %>
    <%= "<p>" + "Album = " + name + "</p>"%>
    <%= "<p>" + "Artist = " + artist + "</p>"%>
    <%= "<p>" + "Genre = " + genre + "</p>"%>
    <%
        for (int i = 0; i < tracks.size(); i++) {
            out.println("<p>" + (i + 1) + ". " + tracks.get(i) + "</p>");
        }
    %>
   <%-- <%= request.getAttribute("textWithOutPars")%>--%>
    </body>
</html>
