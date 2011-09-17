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

public class SnapshotResourceTest {
	
	   @Test
	   public void testVolumeResource() throws Exception
	   {
		   HttpURLConnection connection;
		   OutputStream os;
		      System.out.println("*** Create a new Snapshot ***");
		      // Create a new snapshot
		      String newSnapshot = "<cloudSnapshots>" 
		    	    + "<snapshotMethod>createSnapshot</snapshotMethod>" 
		    	    + "<snapshot>"
		    		+ "<volumeId>vol-8c5f37e6</volumeId>"
		    		+ "<description>TEST</description>"
		    		+ "</snapshot>"
		    		+ "</cloudSnapshots>";
		      		      
		      URL postUrl = new URL("http://localhost:8080/cloudwang/rs/snapshot?asynch=true");
		      connection = (HttpURLConnection) postUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("POST");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      os = connection.getOutputStream();
		      os.write(newSnapshot.getBytes());
		      os.flush();

		      Assert.assertEquals(HttpURLConnection.HTTP_ACCEPTED, connection.getResponseCode());
		      System.out.println("The create snapshot request has been accepted");
		      System.out.println("Job Location: " + connection.getHeaderField("Location"));
		      
		      Thread.sleep(12000);
		      System.out.println("Sleep 12s and get the created snapshot!");
		      URL jobUrl = new URL(connection.getHeaderField("Location"));
		      connection = (HttpURLConnection) jobUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("GET");

		      BufferedReader reader0 = new BufferedReader(new
		              InputStreamReader(connection.getInputStream()));

		      String line0 = reader0.readLine();
		      while (line0 != null)
		      {
		         System.out.println(line0);
		         line0 = reader0.readLine();
		      }
		      
		      System.out.println("*** Create a new Snapshot successfully ***");
		      connection.disconnect();
		      
		      // Get snapshot
		      System.out.println("*** get Snapshot with XML format **");
		      URL getUrl = new URL("http://localhost:8080/cloudwang/rs/snapshot/snap-11dc0e71");
		      connection = (HttpURLConnection) getUrl.openConnection();
		      connection.setRequestMethod("GET");
		      connection.setRequestProperty("Accept", "application/xml");
		      System.out.println("Location: " + connection.getHeaderField("Location"));
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
		      System.out.println("*** get Snapshot (XML) successfully **");
		      connection.disconnect();
		      
		      // Get snapshot with JSON format
		      System.out.println("*** get Snapshot with JSON format **");
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
		      System.out.println("*** get Snapshot (JSON) successfully **");
		      connection.disconnect();
	   }
}
