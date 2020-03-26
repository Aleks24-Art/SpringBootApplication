package com.aleksenko.artemii.service;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.Track;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Aleksenko Artemii on 01.03.2020
 * @version 1.0
 */
@Component
@Service
public class MainService implements AppService {
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(MainService.class);

    public String getHttpResponseAboutAlbum(String artist, String album) {
        HttpURLConnection connection = null;
        String line = buildURI(artist, album);
        System.out.println(line);
        String url = buildURI(artist, album);
        StringBuilder sb = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

            }

        } catch (Throwable cause) {
            logger.error("Exception while http request ", cause);
        } finally {
            logger.info("Closing resources");
            if (connection != null) {
                connection.disconnect();
            }
        }
        return sb.toString();
    }

    public void createDocs(Album album) {
        try (FileOutputStream out = new FileOutputStream("album.doc")) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(album.getPoster(), Resource.class);
            InputStream inputStream = responseEntity.getBody().getInputStream();
            XWPFDocument doc = new XWPFDocument();
            doc.createParagraph().createRun().setText("Artist: " + album.getArtist());
            doc.createParagraph().createRun().setText("Album: " + album.getName());
            doc.createParagraph().createRun().setText("Genre: " + album.getGenre());
            XWPFTable table = doc.createTable();
            table.getRow(0).getCell(0).setText("Track");
            table.getRow(0).addNewTableCell().setText("Duration(s)");
            for (int i = 1; i < album.getTrackList().size(); i++) {
                Track currentTrack = album.getTrackList().get(i);
                XWPFTableRow curRow = table.createRow();
                curRow.getCell(0).setText(currentTrack.getName());
                curRow.getCell(1).setText("" + currentTrack.getDuration());
            }
            doc.createParagraph().createRun().setText("Poster:");
            doc.createParagraph().createRun().addPicture((inputStream), XWPFDocument.PICTURE_TYPE_JPEG, "", Units.toEMU(180), Units.toEMU(180));
            doc.write(out);
        } catch (IOException | InvalidFormatException e1) {
            logger.error("Exception while writing in file", e1);
        }
    }

    private String buildURI(String artist, String album) {
        return UriComponentsBuilder.newInstance()
                .scheme("http").host("ws.audioscrobbler.com").path("/2.0/")
                .query("method=album.getinfo")
                .query("api_key=310d22580d72a57938fc4d7f713a0fab")
                .query("artist=" + artist)
                .query("album=" + album)
                .query("format=json").build().toString();

    }

}
