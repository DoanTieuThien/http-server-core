package com.its.httpserver.main;

import com.its.httpserver.server.HttpServerImpl;

public class HttpServerManager {
	//main start server
	public static void main(String[] args) {
		try {
			HttpServerImpl httpServerImpl = new HttpServerImpl();
			httpServerImpl.startHttpServer(8080, "HTTPS","privatekey.jks","123456","TLsV1.2");
		} catch (Exception exp) {
			exp.printStackTrace();
			System.exit(0);
		}
	}
}
