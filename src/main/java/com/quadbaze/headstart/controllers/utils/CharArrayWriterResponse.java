package com.quadbaze.headstart.controllers.utils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: Olatunji O. Longe
 * @created: 17 Oct, 2016, 12:36 AM
 */
public class CharArrayWriterResponse extends HttpServletResponseWrapper {

    private final CharArrayWriter charArray = new CharArrayWriter();

    public CharArrayWriterResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(charArray);
    }

    public String getOutput() {
        return charArray.toString();
    }
}
