package com.its.httpserver.handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.httpserver.server.HttpServerImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

//basic controller
public abstract class HttpBasicController implements HttpHandler {
	protected ObjectMapper objectMapper = new ObjectMapper();
	private HttpServerImpl server = null;

	public HttpBasicController(HttpServerImpl server) {
		this.server = server;
	}

	public HttpServerImpl getServer() {
		return this.server;
	}

	// implement handle request come to controller
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		try {
			String payload = null;
			String contentLengthData = Optional.ofNullable(httpExchange.getRequestHeaders().getFirst("Content-Length"))
					.orElse("0");
			int contentLength = Integer.parseInt(contentLengthData);
			if (contentLength > 0) {
				System.out.println("Found data for this request");
				payload = readRequest(httpExchange, contentLength);
			} else {
				System.out.println("No data found for this request");
			}
			System.out
					.println("Payload: " + payload + ", controller request " + httpExchange.getRequestURI().getPath());
			process(httpExchange, payload);
		} catch (Exception exp) {
			buildResponse(500, "error process: " + exp.getMessage(), httpExchange);
		}
	}

	protected abstract void process(HttpExchange httpExchange, String payload) throws Exception;

	// response to client
	protected void buildResponse(int httpStatus, String message, HttpExchange response) {
		try {
			response.sendResponseHeaders(httpStatus, message.length());
			response.getResponseBody().write(message.getBytes());
			response.getResponseBody().flush();
			response.getResponseBody().close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	// read payload if have for request
	protected String readRequest(HttpExchange request, int byteAvailable) {
		StringBuilder body = new StringBuilder();
		InputStreamReader reader = null;

		try {
			reader = new InputStreamReader(request.getRequestBody(), "utf-8");
			char[] buffer = new char[byteAvailable];
			int read = 0;

			while ((read = reader.read(buffer)) != -1 && byteAvailable > 0) {
				body.append(buffer, 0, read);
				byteAvailable = byteAvailable - read;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
			reader = null;
		}
		return body.toString();
	}

	public Object convertStringToModel(String payload, Class c) throws Exception {
		return this.objectMapper.readValue(payload, c);
	}
}
