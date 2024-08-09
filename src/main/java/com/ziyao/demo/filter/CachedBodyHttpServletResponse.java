package com.ziyao.demo.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private PrintWriter writer;
    private ServletOutputStreamCopier copier;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        copier = new ServletOutputStreamCopier(super.getOutputStream());
        return copier;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            copier = new ServletOutputStreamCopier(super.getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(copier, getCharacterEncoding()), true);
        }

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        if (writer != null) {
            writer.flush();
        } else if (copier != null) {
            copier.flush();
        }
    }

    public static class ServletOutputStreamCopier extends ServletOutputStream {

        private final ServletOutputStream original;
        private final ByteArrayOutputStream copy;

        public ServletOutputStreamCopier(ServletOutputStream servletOutputStream) {
            this.original = servletOutputStream;
            this.copy = new ByteArrayOutputStream(1024);
        }

        @Override
        public void write(int b) throws IOException {
            original.write(b);
            copy.write(b);
        }

        public byte[] getCopy() {
            return copy.toByteArray();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }
    }

    public String getBody() {
        if (copier != null) {
            return new String(copier.getCopy());
        } else {
            return new String(new byte[0]);
        }
    }

    public String getAllHeader() {
        try {
            flushBuffer();
        } catch (Exception ignored) {

        }
        List<String> headers = new ArrayList<>();
        Collection<String> headerNames = super.getHeaderNames();

        if (headerNames != null) {
            headers = headerNames.stream()
                    .map(headerName -> headerName + ": " + super.getHeader(headerName))
                    .toList();
        }
        return String.join("; ", headers);
    }
}
