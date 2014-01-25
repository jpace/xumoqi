package org.incava.xumoqi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IO {
	public void readStream(InputStream is, Integer maxLength) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				int len = line.length();
				if (maxLength != null && len > maxLength) {
					break;
				}
				else {
					onRead(line, len);
				}
			}
        }
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void onRead(String str, Integer len) {
	
	}
}
