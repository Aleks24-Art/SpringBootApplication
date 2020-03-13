package com.aleksenko.artemii.service;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    public String getHttpResponseAboutAlbum(UriComponents uri) {
        HttpURLConnection connection = null;
        String line;
        String url = uri.toString();
        StringBuilder sb = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream() , StandardCharsets.UTF_8));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

            }

        } catch (Throwable cause) {
            logger.error("Exception while http request ", cause);
        } finally {
            logger.info("Closing resources");
            if(connection != null) {
                connection.disconnect();
            }
        }
        return sb.toString();
    }

}
