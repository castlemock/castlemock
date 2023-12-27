package com.castlemock.model.core.utility;

import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Mustafa Kerim Yilmaz
 * @since 1.66
 */
public class XsltUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(XsltUtility.class);

    public static String transform(final String request, final String response) {
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        try {
            XsltExecutable stylesheet = compiler.compile(new StreamSource(new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8))));
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Serializer out = processor.newSerializer(outStream);
            // documentation: https://www.saxonica.com/html/documentation12/xsl-elements/output.html
            out.setOutputProperty(Serializer.Property.METHOD, "xml");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            out.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
            Xslt30Transformer transformer = stylesheet.load30();
            transformer.transform(new StreamSource(new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8))), out);
            return outStream.toString(StandardCharsets.UTF_8);
        } catch (SaxonApiException e) {
            LOGGER.error("Xslt error", e);
            return e.getMessage();
        }
    }
}
