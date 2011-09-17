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

package org.binbin.skywang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class VolumeResourceTest {
	
	   @Test
	   public void testVolumeResource() throws Exception
	   {
		   HttpURLConnection connection;
		   OutputStream os;
		      System.out.println("*** Create a new Volume ***");
		      // Create a new volume
		      String newVolume = "<cloudVolumes>" 
		    	    + "<volumeMethod>createVolume</volumeMethod>" 
		    	    + "<volume>"
		    		+ "<sizeInGb>21</sizeInGb>"
		    		+ "<dataCenterId>us-east-1b</dataCenterId>"
		    		+ "</volume>"
		    		+ "</cloudVolumes>";
		      		      
//		      URL postUrl = new URL("http://localhost:9095/rs/volume/");
		      URL postUrl = new URL("http://localhost:8080/cloudwang/rs/volume");
		      connection = (HttpURLConnection) postUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("POST");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      os = connection.getOutputStream();
		      os.write(newVolume.getBytes());
		      os.flush();
		      Assert.assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
		      System.out.println("Location: " + connection.getHeaderField("Location"));
		      connection.disconnect();
		      
		      // Get volume
		      System.out.println("*** get volume with XML format **");
//		      URL getUrl = new URL("http://localhost:9095/customers/1");
		      URL getUrl = new URL("http://localhost:8080/cloudwang/rs/volume/vol-a2b4d5c8");
		      connection = (HttpURLConnection) getUrl.openConnection();
		      connection.setRequestMethod("GET");
		      connection.setRequestProperty("Accept", "application/xml");
		      System.out.println("Content-Type: " + connection.getContentType());

		      BufferedReader reader = new BufferedReader(new
		              InputStreamReader(connection.getInputStream()));

		      String line = reader.readLine();
		      while (line != null)
		      {
		         System.out.println(line);
		         line = reader.readLine();
		      }
		      Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
		      System.out.println("*** get volume successfully **");
		      connection.disconnect();
		      
		      // Get volume with JSON format
		      System.out.println("*** get volume with JSON format **");
		      connection = (HttpURLConnection) getUrl.openConnection();
		      connection.setRequestMethod("GET");
		      connection.setRequestProperty("Accept", "application/json");
		      System.out.println("Content-Type: " + connection.getContentType());

		      BufferedReader reader1 = new BufferedReader(new
		              InputStreamReader(connection.getInputStream()));

		      String line1 = reader1.readLine();
		      while (line1 != null)
		      {
		         System.out.println(line1);
		         line1 = reader1.readLine();
		      }
		      Assert.assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
		      System.out.println("*** get volume successfully **");
		      connection.disconnect();
		      
//		       attach volume
		      System.out.println("\n*** attach volume to server ***");
		      String attachVolume = "<cloudVolumes>" 
		    	    + "<volumeMethod>attachVolume</volumeMethod>" 
		    	    + "<volume>"
		    		+ "<volumeName>vol-a2b4d5c8</volumeName>"
		    		+ "<serverId>i-e2f85a82</serverId>"
		    		+ "<deviceId>/dev/sdl</deviceId>"
		    		+ "</volume>"
		    		+ "</cloudVolumes>";
		      
		      connection = (HttpURLConnection) getUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setRequestMethod("PUT");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      os = connection.getOutputStream();
		      os.write(attachVolume.getBytes());
		      os.flush();
		      Assert.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, connection.getResponseCode());
		      System.out.println("*** attach volume to server successfully ***");
		      connection.disconnect();
		      
		      // detach volume
		      System.out.println("\n*** detach volume from server ***");
		      String detachVolume = "<cloudVolumes>" 
		    	    + "<volumeMethod>detachVolume</volumeMethod>" 
		    		+ "</cloudVolumes>";
		      connection = (HttpURLConnection) getUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setRequestMethod("PUT");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      os = connection.getOutputStream();
		      os.write(detachVolume.getBytes());
		      os.flush();
		      Assert.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, connection.getResponseCode());
		      System.out.println("*** detach volume from server successfully ***");
		      connection.disconnect();

	   }

}
