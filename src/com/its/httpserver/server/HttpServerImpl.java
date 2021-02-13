package com.its.httpserver.server;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.its.httpserver.handler.HttpController;
import com.its.httpserver.handler.HttpCreateControllerOnline;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

//implement http server
public class HttpServerImpl {
	private HttpServer httpServer = null;
	private HttpsServer httpsServer = null;

	// start server
	public void startHttpServer(int port, String serverType, String privateKeyPath, String privateKeyPassword,
			String sslVersion) throws Exception {

		if (serverType.equalsIgnoreCase("HTTP")) {
			System.out.println("start http");
			this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
			// create controller
			this.httpsServer.createContext("/create-controller", new HttpCreateControllerOnline(this));
			this.httpServer.createContext("/test", new HttpController(this));
			this.httpServer.start();
			return;
		}

		if (serverType.equalsIgnoreCase("HTTPS")) {
			System.out.println("start https");
			char[] password = privateKeyPassword.toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			FileInputStream fis = new FileInputStream(privateKeyPath);
			ks.load(fis, password);

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, password);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);
			SSLContext sc = SSLContext.getInstance(sslVersion);
			sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			this.httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
			this.httpsServer.setHttpsConfigurator(new HttpsConfigurator(sc));

			//CONTROLLER CHUNG CHO TAT CA NGHIEP VU
			this.httpsServer.createContext("/test", new HttpController(this));
			
			//CONTROLLER DAP UNG VIEC TAO DONG NGHIEP VU
			this.httpsServer.createContext("/create-controller", new HttpCreateControllerOnline(this));
			this.httpsServer.start();
			return;
		}
		System.err.println("SERVER TYPE IS NOT SUPPORT");
	}

	// this for next time
	public void createControllerOline(String controllerName) {
		if (this.httpServer != null) {
			this.httpServer.createContext(controllerName, new HttpController(this));
		}

		if (this.httpsServer != null) {
			this.httpsServer.createContext(controllerName, new HttpController(this));
		}
	}

	// stop server
	public void stop() {
		if (this.httpServer != null) {
			this.httpServer.stop(20);
		}

		if (this.httpsServer != null) {
			this.httpsServer.stop(20);
		}
	}
}
