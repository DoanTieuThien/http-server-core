package com.its.httpserver.handler;

import com.its.httpserver.server.HttpServerImpl;
import com.sun.net.httpserver.HttpExchange;

public class HttpController extends HttpBasicController {

	public HttpController(HttpServerImpl server) {
		super(server);
	}

	@Override
	protected void process(HttpExchange httpExchange, String payload) throws Exception {
		System.out.println(payload);
		buildResponse(200, "SUCCESSED REQUEST", httpExchange);
	}

}
