package com.alphatek.tylt.web.support;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * @author  jason.dimeo
 * Date: 3/23/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServletOutputStreamCopier extends ServletOutputStream {
	private OutputStream outputStream;
	private ByteArrayOutputStream copy;

	public ServletOutputStreamCopier(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.copy = new ByteArrayOutputStream(1024);
	}

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
		copy.write(b);
	}

	public byte[] getCopy() {
		return copy.toByteArray();
	}

	@Override public boolean isReady() {
		return false;
	}

	@Override public void setWriteListener(WriteListener writeListener) {

	}
}
