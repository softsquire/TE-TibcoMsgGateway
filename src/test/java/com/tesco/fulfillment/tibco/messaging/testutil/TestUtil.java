package com.tesco.fulfillment.tibco.messaging.testutil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class TestUtil {

	public static String getFile(String fileName) {
		StringBuffer appenderString = new StringBuffer();
		LineIterator iterator = null;
		try {
			File file = new File(ClassLoader.getSystemResource(fileName)
					.toURI());
			iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext()) {
				String line = iterator.nextLine();
				appenderString.append(line);
			}
		} catch (IOException | URISyntaxException exception) {
			
		} finally {
			LineIterator.closeQuietly(iterator);
		}
		return appenderString.toString();
	}

	
	
	
}
