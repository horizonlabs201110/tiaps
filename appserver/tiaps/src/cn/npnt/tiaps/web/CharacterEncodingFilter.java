package cn.npnt.tiaps.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
	private FilterConfig config;
	private String encoding = "UTF-8";
	
	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
//		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		chain.doFilter(request, response);
		System.err.println(request.getServletContext().getRealPath("/"));
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		encoding = config.getInitParameter("encoding");
	}

	public FilterConfig getConfig() {
		return config;
	}

	public void setConfig(FilterConfig config) {
		this.config = config;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
}
