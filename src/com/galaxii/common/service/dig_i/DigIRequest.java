package com.galaxii.common.service.dig_i;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DigIRequest {
	
	private String endpoint;
	private Parameter[] parameters;
	private Method method = Method.GET;
	
	public enum Method {
		GET, POST, DELETE, PUT;
	}

	public DigIRequest(String endpoint, Parameter... parameters) {
		this.endpoint = endpoint;
		this.parameters = parameters;
		this.method = Method.GET;
	}
	
	public Object makeRequest() throws IOException {
		HttpURLConnection con = null;
		if(method == Method.GET) {
			con = buildGetConnection();
		}
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String httpSource = new String();
		String str;
		while ( null != ( str = bufferReader.readLine() ) ) {
		    httpSource = httpSource + str;
		}
		bufferReader.close();
		con.disconnect();
		return httpSource;
	}
	
	private HttpURLConnection buildGetConnection() throws IOException {
		StringBuilder sb = new StringBuilder();
		if(parameters != null && 0 < parameters.length) {
			sb.append("?");
			for(Parameter p : parameters) {
				sb.append(p.name);
				sb.append("=");
				sb.append(p.value);
				sb.append("&");
			}
		}
		URL url = new URL(endpoint + sb.toString());
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		return connection;
	}
}
