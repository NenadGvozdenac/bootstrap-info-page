package com.website.hosting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashMap;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpSrv {
  
	static class HtmlHandler implements HttpHandler {	
		
		String path;
		
		HtmlHandler(String path) {
			this.path = path;
		}

        @Override
        public void handle(HttpExchange t) throws IOException {						
			loadHtml(path, t);
        }
    }

	static class CssHandler implements HttpHandler {

		String path;
		
		CssHandler(String path) {
			this.path = path;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			loadCss(path, exchange);
		}
	}

	static class JsHandler implements HttpHandler {

		String path;
		
		JsHandler(String path) {
			this.path = path;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			loadJs(path, exchange);
		}
	}

	static class ImageHandler implements HttpHandler {
		String path;
		
		ImageHandler(String path) {
			this.path = path;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			loadImage(path, exchange);
		}
	}

	public static HttpServer startServerThread(Integer port, HashMap<String, String> listOfHtmlPages, HashMap<String, String> listOfCssPages, HashMap<String, String> listOfJsPages, HashMap<String, String> listOfImages) {
        try {
			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

			listOfHtmlPages.forEach((websitePath, directorypath) -> {
				server.createContext(websitePath, new HtmlHandler(directorypath));
			});

			listOfCssPages.forEach((websitePath, directorypath) -> {
				server.createContext(websitePath, new CssHandler(directorypath));
			});

			listOfJsPages.forEach((websitePath, directorypath) -> {
				server.createContext(websitePath, new JsHandler(directorypath));
			});

			listOfImages.forEach((websitePath, directorypath) -> {
				server.createContext(websitePath, new ImageHandler(directorypath));
			});

            server.setExecutor(null); 
            server.start();

            return server;
        } catch (IOException e) {
            System.out.println("Adresa se vec koristi! Thread servera nije poceo!");

            return null;
        }
	}

	public static void loadImage(String path, HttpExchange exchange) throws IOException {

		Headers h = exchange.getResponseHeaders();

		File newFile = new File(path);

		exchange.sendResponseHeaders(200, newFile.length());

		h.add("Content-Type", (newFile.getName().contains(".jpg") || newFile.getName().contains(".jpeg")) ? "image/jpeg" : "image/png");

		OutputStream os = exchange.getResponseBody();
		Files.copy(newFile.toPath(), os);
		os.close();
	}

	public static void loadJs(String path, HttpExchange exchange) throws IOException {

		Headers h = exchange.getResponseHeaders();

		String line;
		String resp = "";

		File newFile = new File(path);
				
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

		while ((line = bufferedReader.readLine()) != null) {
			resp += line;
		}
		bufferedReader.close();
		
		h.add("Content-Type", "text/javascript");

		exchange.sendResponseHeaders(200, 0);

		OutputStream os = exchange.getResponseBody();
		os.write(resp.getBytes());
		os.close();
	}

	public static void loadCss(String path, HttpExchange exchange) throws IOException {
		
		Headers h = exchange.getResponseHeaders();

		String line;
		String resp = "";

		File newFile = new File(path);
				
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

		while ((line = bufferedReader.readLine()) != null) {
			resp += line;
		}

		bufferedReader.close();

		h.add("Content-Type", "text/css");

		exchange.sendResponseHeaders(200, 0);
		OutputStream os = exchange.getResponseBody();
		os.write(resp.getBytes());
		os.close();
	}

	public static void loadHtml(String pathToHtmlPage, HttpExchange t) throws IOException {

		Headers h = t.getResponseHeaders();

		String line;
        String resp = "";

        File newFile = new File(pathToHtmlPage);
				
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

        while ((line = bufferedReader.readLine()) != null) {
            resp += line;
        }

        bufferedReader.close();

        h.add("Content-Type", "text/html");

        t.sendResponseHeaders(200, 0);
        OutputStream os = t.getResponseBody();

        os.write(resp.getBytes());
        os.close();
	}
}