package com.aleksenko.artemii.util;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.Track;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 * Generate .doc-file
 */
@Service
public class UtilDocGenerator {

    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(UtilDocGenerator.class);

    /**
     * Create .doc-file
     * @param album POJO which written to file
     * @return doc in byte array
     */
    public byte[] createDocs(Album album) {
        byte[] array = null;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(album.getPoster(), Resource.class);
        XWPFDocument doc = new XWPFDocument();
        /*Creating paragraphs for main fields*/
        doc.createParagraph().createRun().setText("Artist: " + album.getArtist());
        doc.createParagraph().createRun().setText("Album: " + album.getName());
        doc.createParagraph().createRun().setText("Genre: " + album.getGenre());
        /*Creating table for tracks*/
        XWPFTable table = doc.createTable();
        table.getRow(0).getCell(0).setText("Track");
        table.getRow(0).addNewTableCell().setText("Duration(s)");
        table.getRow(0).addNewTableCell().setText("URL");
        for (int i = 1; i < album.getTrackList().size(); i++) {
            Track currentTrack = album.getTrackList().get(i);
            XWPFTableRow curRow = table.createRow();
            curRow.getCell(0).setText(currentTrack.getName());
            curRow.getCell(1).setText("" + currentTrack.getDuration());
            curRow.getCell(2).setText("" + currentTrack.getUrl());
        }
        /*Image (poster) output*/
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            InputStream inputStream = responseEntity.getBody().getInputStream();
            doc.createParagraph().createRun().setText("Poster:");
            doc.createParagraph().createRun().addPicture((inputStream), XWPFDocument.PICTURE_TYPE_JPEG, "", Units.toEMU(180), Units.toEMU(180));
            doc.write(out);
            array = out.toByteArray();
        } catch (IOException | InvalidFormatException e1) {
            logger.error("Exception while writing in file", e1);
        }
        return array;
    }

}
