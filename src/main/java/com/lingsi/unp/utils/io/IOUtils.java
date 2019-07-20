package com.lingsi.unp.utils.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    // inputStream转outputStream
    public ByteArrayOutputStream parse(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    // outputStream转inputStream
    public ByteArrayInputStream parse(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }

    // inputStream转String
    public String parseInputStream2String(final InputStream in) throws Exception {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream.toString();
    }

    // OutputStream 转String
    public String parseOutputStream2String(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream.toString();
    }

    // String转inputStream
    public ByteArrayInputStream parse_inputStream(final String in) throws Exception {
        final ByteArrayInputStream input = new ByteArrayInputStream(in.getBytes());
        return input;
    }

    // String 转outputStream
    public ByteArrayOutputStream parse_outputStream(final String in) throws Exception {
        return parse(parse_inputStream(in));
    }
}