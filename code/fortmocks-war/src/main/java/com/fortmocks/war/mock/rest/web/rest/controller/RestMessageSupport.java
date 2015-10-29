package com.fortmocks.war.mock.rest.web.rest.controller;

import com.fortmocks.war.mock.soap.model.SoapException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestMessageSupport {

    protected static final String DIVIDER = ":";
    protected static final String VARIABLE = "#";
    private static final String BODY = "Body";
    private static final Logger LOGGER = Logger.getLogger(RestMessageSupport.class);


    /**
     * The getBody method is used to extract the body from the incoming request
     * @param httpServletRequest The incoming request that contains the request body
     * @return The request body as a String
     */
    public static String getBody(HttpServletRequest httpServletRequest) {
        try {
            final StringBuilder buffer = new StringBuilder();
            final BufferedReader reader = httpServletRequest.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            LOGGER.error("Unable to read the incoming file", e);
            throw new SoapException("Unable to extract the request body");
        }
    }
}
