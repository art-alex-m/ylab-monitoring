package io.ylab.monitoring.app.servlets.support;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.IOException;
import java.util.Iterator;

/**
 * Реализация ServletInputStream для тестирования
 */
public class TestServletInputStream extends ServletInputStream {

    private final ByteIterator byteIterator;

    public TestServletInputStream(String message) {
        super();
        this.byteIterator = new ByteIterator(message);
    }

    @Override
    public boolean isFinished() {
        return !byteIterator.hasNext();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        if (!byteIterator.hasNext()) return -1;
        return byteIterator.next() & 0xFF;
    }


    private static class ByteIterator implements Iterator<Byte> {

        private final byte[] message;

        private int current = 0;

        private ByteIterator(String message) {
            this.message = message.getBytes();
        }

        @Override
        public boolean hasNext() {
            return current < message.length;
        }

        @Override
        public Byte next() {
            return message[current++];
        }
    }
}
