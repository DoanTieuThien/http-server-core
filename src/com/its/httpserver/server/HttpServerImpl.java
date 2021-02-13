package com.its.httpserver.server;

import java.net.InetSocketAddress;

import com.its.httpserver.handler.HttpBasicController;
import com.sun.net.httpserver.HttpServer;

//implement http server
public class HttpServerImpl {
	private HttpServer httpServer = null;

	//start server 
	public void startHttpServer(int port, String serverType) throws Exception {
		
		if (serverType.equalsIgnoreCase("HTTP")) {
			this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
			//create controller
			this.httpServer.createContext("/test", new HttpBasicController());
			this.httpServer.start();
			return;
		}

		if (serverType.equalsIgnoreCase("HTTPS")) {
			//https for next time
			return;
		}
		System.err.println("SERVER TYPE IS NOT SUPPORT");
	}
	
	//this for next time
	public void createControllerOline(String controllerName) {
		this.httpServer.createContext(controllerName, new HttpBasicController());
	}

	//stop server
	public void stop() {
		if (this.httpServer != null) {
			this.httpServer.stop(20);
		}
	}
}
