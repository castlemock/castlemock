/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.basis.support;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.basis.model.http.dto.HttpParameterDto;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * The class provides functionality and support methods specifiably for SOAP messages.
 * @author Karl Dahlgren
 * @since 1.0
 */
public class HttpMessageSupport {

    private static final String DIVIDER = ":";
    private static final String VARIABLE = "#";
    private static final String BODY = "Body";
    private static final String TRANSFER_ENCODING = "Transfer-Encoding";
    private static final Logger LOGGER = Logger.getLogger(HttpMessageSupport.class);
    private static final String EMPTY = "";
    private static final Integer OK_RESPONSE = 200;
    private static final String NEW_LINE = System.lineSeparator();

    /**
     * The default constructor for SoapMessageSupport. It is marked as private
     * to prohibit creation of instances of this class.
     */
    private HttpMessageSupport(){
        // The constructor should be empty
    }

    /**
     * The method extract the operation name from the SOAP body
     * @param body The body that contains the operation name
     * @return The extracted operation name
     */
    public static String extractSoapRequestName(final String body){
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final InputSource inputSource = new InputSource(new StringReader(body));
            final Document document = documentBuilder.parse(inputSource);
            final String rootName = document.getDocumentElement().getNodeName();
            final String prefix = getElement(rootName, 0);

            final NodeList nodeList = document.getElementsByTagName(prefix + DIVIDER + BODY);

            final Node bodyNode = nodeList.item(0);
            final NodeList bodyChildren = bodyNode.getChildNodes();

            if (bodyChildren.getLength() == 0) {
                throw new IllegalStateException("Invalid count of body children");
            }

            String serviceNameWithPrefix = null;
            for (int index = 0; index < bodyChildren.getLength(); index++) {
                if (!bodyChildren.item(index).getNodeName().contains(VARIABLE)) {
                    serviceNameWithPrefix = bodyChildren.item(index).getNodeName();
                }
            }
            if (serviceNameWithPrefix == null) {
                throw new IllegalStateException("Unable to extract the service name");
            }

            return getElement(serviceNameWithPrefix, 1);
        }catch(Exception exception){
            LOGGER.error("Unable to extract SOAP request name", exception);
            throw new IllegalStateException(exception.getMessage());
        }
    }

    /**
     * Returns the element name or prefix
     * @param element The element with both the name and namespace
     * @param index Index is used to indicate what should be retrieved. Index 0 = namespace, Index 1 = element name
     * @return Either the element name or namespace
     */
    public static String getElement(String element, int index){
        Preconditions.checkArgument(index >= 0, "The index can't be less than zero");
        Preconditions.checkArgument(index <= 1, "The index can't be more than one");

        final String[] elementDivided = element.split(DIVIDER);

        if (elementDivided.length == 1) {
            return elementDivided[0];
        }
        if (elementDivided.length == 2) {
            return elementDivided[index];
        }

        throw new IllegalArgumentException("Unable to find the name or prefix in the XML element");
    }

    /**
     * The getBody method is used to extract the body from the incoming request
     * @param httpServletRequest The incoming request that contains the request body
     * @return The request body as a String
     */
    public static String getBody(final HttpServletRequest httpServletRequest) {
        BufferedReader reader = null;
        try {
            final Deque<String> lines = new ArrayDeque<String>();
            reader = httpServletRequest.getReader();
            String line;

            // Read each lines and add them to the lines list
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            final StringBuilder builder = new StringBuilder();
            final Iterator<String> linesIterator = lines.iterator();

            while (linesIterator.hasNext()){
                line  = linesIterator.next();
                builder.append(line);

                if(linesIterator.hasNext()){
                    // Add a new line. Mainly required to parse MTOM requests.
                    builder.append(System.lineSeparator());
                }
            }

            return builder.toString();
        } catch (IOException e) {
            LOGGER.error("Unable to read the incoming file", e);
            throw new IllegalStateException("Unable to extract the request body");
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close the buffered reader", e);
                }
            }
        }
    }

    /**
     * Extract HTTP headers from provided Http Servlet Request
     * @param httpServletRequest Incoming Http Servlet Request that contains the headers which will be extracted
     * @return A list of HTTP headers extracted from the provided httpServletRequest
     */
    public static List<HttpHeaderDto> extractHttpHeaders(final HttpServletRequest httpServletRequest){
        final List<HttpHeaderDto> httpHeaders = new ArrayList<HttpHeaderDto>();
        final Enumeration<String> headers = httpServletRequest.getHeaderNames();
        while(headers.hasMoreElements()){
            final String headerName = headers.nextElement();
            final String headerValue = httpServletRequest.getHeader(headerName);
            final HttpHeaderDto httpHeader = new HttpHeaderDto();
            httpHeader.setName(headerName);
            httpHeader.setValue(headerValue);
            httpHeaders.add(httpHeader);
        }
        return httpHeaders;
    }

    /**
     * Extract HTTP headers from provided Http URL connection
     * @param connection Incoming Http URL connection that contains the headers which will be extracted
     * @return A list of HTTP headers extracted from the provided connection
     */
    public static List<HttpHeaderDto> extractHttpHeaders(final HttpURLConnection connection){
        final List<HttpHeaderDto> httpHeaders = new ArrayList<HttpHeaderDto>();
        for(String headerName : connection.getHeaderFields().keySet()){
            if(headerName == null){
                continue;
            }
            if(headerName.equalsIgnoreCase(TRANSFER_ENCODING)){
                continue;
            }
            final String headerValue = connection.getHeaderField(headerName);
            final HttpHeaderDto httpHeader = new HttpHeaderDto();
            httpHeader.setName(headerName);
            httpHeader.setValue(headerValue);
            httpHeaders.add(httpHeader);
        }
        return httpHeaders;
    }

    /**
     * Extract all the incoming parameters and stores them in a Map. The parameter name will
     * act as the key and the parameter value will be the Map value
     * @param httpServletRequest The incoming request which contains all the parameters
     * @return A map with the extracted parameters
     */
    public static List<HttpParameterDto> extractParameters(final HttpServletRequest httpServletRequest){
        final List<HttpParameterDto> httpParameters = new ArrayList<HttpParameterDto>();

        final Enumeration<String> enumeration = httpServletRequest.getParameterNames();
        while(enumeration.hasMoreElements()){
            final HttpParameterDto httpParameter = new HttpParameterDto();
            final String parameterName = enumeration.nextElement();
            final String parameterValue = httpServletRequest.getParameter(parameterName);
            httpParameter.setName(parameterName);
            httpParameter.setValue(parameterValue);
            httpParameters.add(httpParameter);
        }
        return httpParameters;
    }


    /**
     * Builds a parameter URL string passed on the provided parameter map.
     * Example on the output: ?name1=value1{@literal &}name2=value2
     * @param httpParameters The Map of parameters that will be used to build the parameter URI
     * @return A URI that contains the parameters from the provided Map
     */
    public static String buildParameterUri(List<HttpParameterDto> httpParameters){
        if(httpParameters.isEmpty()){
            return EMPTY;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        for(int index = 0; index < httpParameters.size(); index++){
            HttpParameterDto httpParameter = httpParameters.get(index);
            String parameterName = httpParameter.getName();
            String parameterValue = httpParameter.getValue();
            stringBuilder.append(parameterName + "=" + parameterValue);

            // Add a & (and) character if the Http parameter is not the last one
            if(index < httpParameters.size() - 1){
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }


    /**
     * The method will establish a connection towards a particular <code>endpoint</code> and send
     * a request towards the endpoint.
     * The method will return a {@link HttpURLConnection}, which is the established connection
     * towards the endpoint. Please note that the returned connection has to be closed after being used.
     * @param endpoint The connection endpoint.
     * @param httpMethod The HTTP method that the request will be sent to.
     * @param body The body that will be sent in the request. No body will be sent if the value <code>null</code> has been provided.
     * @param headers The headers that will be added to the request.
     * @return An established connection towards the endpoint.
     * @throws IOException
     * @since 1.18
     */
    public static HttpURLConnection establishConnection(final String endpoint,
                                                        final HttpMethod httpMethod,
                                                        final String body,
                                                        final List<HttpHeaderDto> headers) throws IOException {
        OutputStream outputStream = null;
        try {
            final URL url = new URL(endpoint);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(httpMethod.name());
            for (HttpHeaderDto httpHeader : headers) {
                connection.addRequestProperty(httpHeader.getName(), httpHeader.getValue());
            }

            if(body != null){
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes());
                outputStream.flush();
            }

            return connection;
        }catch (Exception e){
            LOGGER.error("Unable to establish connection towards " + endpoint, e);
            throw e;
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException exception) {
                    LOGGER.error("Unable to close output stream", exception);
                }
            }
        }
    }

    /**
     * The method extracts an HTTP body from an already established {@link HttpURLConnection}.
     * @param connection The connection that the body will be extracted from.
     * @param encodings The encoding that will be used to parse and decode the body.
     * @return The decoded body in String format.
     * @throws IOException
     * @since 1.18
     */
    public static String extractHttpBody(final HttpURLConnection connection,
                                         final List<ContentEncoding> encodings) throws IOException {

        final InputStream inputStream;
        BufferedReader bufferedReader = null;
        try {

            if (connection.getResponseCode() == OK_RESPONSE) {
                // The connection returned a response with HTTP status 200.
                // Use the input stream.
                inputStream = connection.getInputStream();

            } else {
                // The connection returned a response with a HTTP status
                // that is not 200 (500 error code).
                // Use the error stream instead.
                inputStream = connection.getErrorStream();
            }

            // Check if the content is encoded
            if (encodings.contains(ContentEncoding.GZIP)) {
                // The content is GZIP encoded.
                // Create a decoder and parse the response.
                final InputStream gzipStream = new GZIPInputStream(inputStream);
                final Reader decoder = new InputStreamReader(gzipStream);
                bufferedReader = new BufferedReader(decoder);
            } else if(encodings.contains(ContentEncoding.DEFLATE)){
                // The content is DEFLATE encoded.
                // Create a decoder and parse the response.
                final InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
                final Reader decoder = new InputStreamReader(inflaterInputStream);
                bufferedReader = new BufferedReader(decoder);
            } else {
                // No encoding was used. Simply parse the response.
                final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
            }

            final StringBuilder stringBuilder = new StringBuilder();
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
                stringBuilder.append(NEW_LINE);
            }
            return stringBuilder.toString();
        } catch(Exception e) {
            LOGGER.error("Error occurred when extracting HTTP response body", e);
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    LOGGER.error("Unable to close buffered reader", exception);
                }
            }
        }

    }

    /**
     * Encode the provided <code>body</code> with a particular {@link ContentEncoding}.
     * @param body The body that will be encoded.
     * @param encoding The encoding the body will be encoded with.
     * @return Encoded value of the body.
     * @since 1.18
     */
    public static String encodeBody(final String body, final ContentEncoding encoding) {

        ByteArrayOutputStream outputStream = null;
        OutputStream encodingStream = null;
        try {
            if (ContentEncoding.GZIP.equals(encoding)) {
                outputStream = new ByteArrayOutputStream();
                encodingStream = new GZIPOutputStream(outputStream);
            } else if (ContentEncoding.DEFLATE.equals(encoding)) {
                outputStream = new ByteArrayOutputStream();
                encodingStream = new DeflaterOutputStream(outputStream);
            }

            if(encodingStream == null){
                LOGGER.warn("Unable to match the HTTP encoding to an encoder");
                return body;
            }

            encodingStream.write(body.getBytes());
            encodingStream.flush();

            return new String( outputStream.toByteArray(), "UTF-8" );
        }catch (Exception e){
            LOGGER.error("Unable to encode the body", e);
            throw new RuntimeException(e);
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.warn("Unable to close the byte array output stream");
                }
            }
            if(encodingStream != null){
                try {
                    encodingStream.close();
                } catch (IOException e) {
                    LOGGER.warn("Unable to close the encoding stream");
                }
            }
        }
    }

    /**
     * The method will extract all the encodings (Content-Encoding) from an established
     * {@link HttpURLConnection}.
     * @param connection The connection that the encodings will be extracted from.
     * @return A list of {@link ContentEncoding} extracted from the provided {@link HttpURLConnection}.
     * @since 1.18
     */
    public static List<ContentEncoding> extractContentEncoding(final HttpURLConnection connection){
        final List<ContentEncoding> encodings = new ArrayList<>();
        // Extract the content encoding
        String connectionContentEncoding = connection.getContentEncoding();

        if(connectionContentEncoding != null){
            connectionContentEncoding = connectionContentEncoding.toUpperCase();
            for(ContentEncoding contentEncoding : ContentEncoding.values()){
                int index = connectionContentEncoding.indexOf(contentEncoding.name());
                if(index != -1){
                    encodings.add(contentEncoding);
                }
            }
        }

        return encodings;
    }
}
