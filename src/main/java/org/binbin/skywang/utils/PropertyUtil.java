/**
 * Copyright (C) 2011 Binbin Wang <binbinwang118@gmail.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.binbin.skywang.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

import org.jboss.resteasy.logging.Logger;

public class PropertyUtil {

	private static final String CLOUDWANG_PROPERTY_FILE_NAME = "cloudwang.properties";
	private static final Logger log = Logger.getLogger(PropertyUtil.class);
	private static PropertyUtil INSTANCE;

	private Properties properties;

	public static PropertyUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertyUtil();
			INSTANCE.init();
		}
		return INSTANCE;
	}

	private void init() {
		properties = new Properties();
		InputStream cloudwangInput = null;
		try {
			cloudwangInput = this.getClass().getClassLoader().getResourceAsStream("META-INF/cloudwang.properties");
			properties.load(cloudwangInput);

			InputStream classPathPropertiesStream = this.getClass().getClassLoader().getResourceAsStream(CLOUDWANG_PROPERTY_FILE_NAME);
			if (classPathPropertiesStream != null) {
			    try {
				properties.load(classPathPropertiesStream);
			    } finally {
				classPathPropertiesStream.close();
			    }
			}

			String userHome = System.getProperty("user.home");
			if (userHome != null) {
				File cloudwangFile = new File(userHome, CLOUDWANG_PROPERTY_FILE_NAME);
				if (cloudwangFile != null && cloudwangFile.exists()) {
				    InputStream istream = new FileInputStream(cloudwangFile);
				    if (istream != null) {
					try {
					    properties.load(istream);
					} finally {
					    istream.close();
					}
				    } else {
				    	log.error("Unable to open cloudwang configuration file: " + cloudwangFile);
				    }
				}
			}
		} catch (FileNotFoundException e) {
			log.error("Unable to find cloudwang configuration file: " + e);
		} catch (IOException e) {
			log.error("IO Error while reading cloudwang configuration file: " + e);
		} finally {
			if (cloudwangInput != null) {
				try {
					cloudwangInput.close();
				} catch (IOException e) {
					log.error("Unable to close cloudwang configuration file input stream: " + e);
				}
			}
		}
	}

	public String getPropertyByKey(String key) {
		return properties.getProperty(key);
	}

}
