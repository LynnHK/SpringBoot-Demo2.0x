package com.example.demo.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {

	/**
	 * 用于存放controller写到body的数据
	 */
	private ByteArrayOutputStream buffer;
	
	private ServletOutputStream out;
    private PrintWriter writer;
	
	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		
		buffer = new ByteArrayOutputStream();
		out = getOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer));
	}
	
	public ServletOutputStream getOutputStream(ByteArrayOutputStream buffer) {
		return new ServletOutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				buffer.write(b);
			}
			@Override
			public boolean isReady() {
				return false;
			}
			@Override
			public void setWriteListener(WriteListener listener) {
			}
		};
	}
	
	@Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }
	
	@Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }
	
	@Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }
	
	@Override
	public void reset() {
	    buffer.reset();
	}
	
	/**
     * 获取响应Body
     * @param request request
     * @return String
     */
    public String getBodyAsString(String charset) {
        try {
        	//将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
        	flushBuffer();
        	return new String(buffer.toByteArray(), charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] getBodyAsByte() {
    	 try {
    		 flushBuffer();
    		 return buffer.toByteArray();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
    }
    
}
