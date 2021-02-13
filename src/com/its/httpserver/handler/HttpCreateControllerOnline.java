package com.its.httpserver.handler;

import com.its.httpserver.models.CreateControllerRequestModel;
import com.its.httpserver.server.HttpServerImpl;
import com.sun.net.httpserver.HttpExchange;

public class HttpCreateControllerOnline extends HttpBasicController {

	public HttpCreateControllerOnline(HttpServerImpl server) {
		super(server);
	}

	@Override
	protected void process(HttpExchange httpExchange, String payload) throws Exception {
		if (payload == null || payload.trim().equalsIgnoreCase("")) {
			buildResponse(204, "Bad request", httpExchange);
			return;
		}
		//
		CreateControllerRequestModel request = (CreateControllerRequestModel) convertStringToModel(payload,
				CreateControllerRequestModel.class);
		System.out.println("create controller conline with path: " + request.getControllerPath());
		
		//TAO DONG CONTROLLER
		getServer().createControllerOline(request.getControllerPath());
		
		
		buildResponse(200, "SUCCESSED REQUEST", httpExchange);
	}

}
