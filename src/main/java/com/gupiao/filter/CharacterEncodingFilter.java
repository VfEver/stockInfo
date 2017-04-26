package com.gupiao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupiao.util.StringUtils;

public class CharacterEncodingFilter implements Filter {
	
	private String encode = null;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		if (!StringUtils.isEmpty(encode)) {
			request.setCharacterEncoding(this.encode);
			response.setCharacterEncoding(this.encode);
		} else {
			request.setCharacterEncoding("utf8");
			response.setCharacterEncoding("utf8");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.encode = config.getInitParameter("encode");
	}

}
