package com.aleksenko.artemii.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 * Class with request method
 */
@Service
public class Request {
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(Request.class);

    /**
     * Make connection and get JSON response with all needed info
     * @param url url to connect
     * @return JSON info
     */
    public String getHttpResponse(String url) {
        HttpURLConnection connection = null;
        StringBuilder sb = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            logger.debug("\nCreating connection with url: " + url);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.connect();
            logger.debug("Starting URL connection" +
                    "\nCurrent thread " + Thread.currentThread().getId());
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                while ((url = reader.readLine()) != null) {
                    sb.append(url);
                    sb.append("\n");
                }
            }
            logger.debug("Successful connection");
        } catch (Throwable cause) {
            logger.error("Exception while connection to url: " + url + "", cause);
        } finally {
            logger.info("Closing connection resources\n");
            if (connection != null) {
                connection.disconnect();
            }
        }
        return sb.toString();
    }
}
