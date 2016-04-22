package com.galaxii.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

class StreamGobbler extends Thread {
	InputStream is;
	OutputStream os;

	StreamGobbler(InputStream is, OutputStream redirect) {
		this.is = is;
		this.os = redirect;
	}
	public void run() {
		PrintWriter pw = new PrintWriter(os);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				pw.println(line);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		pw.flush();
	}
}
