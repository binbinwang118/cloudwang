package org.binbin.skywang;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class MachineImageResourceTest {
	   @Test
	   public void testMachineImageResource() throws Exception
	   {
		   HttpURLConnection connection;
		   OutputStream os;
		      System.out.println("*** Create a new MachineImage ***");
		      // Create a new machine image
		      String newVolume = "<cloudMachineImage>" 
		    	    + "<machineImage>"
		    		+ "<description>Test</description>"
		    		+ "<name>test createMachineImage</name>"
		    		+ "<serverId>i-b242c3d2</serverId>"
		    		+ "</machineImage>"
		    		+ "</cloudMachineImage>";
		      		      
		      URL postUrl = new URL("http://localhost:8080/cloudwang/rs/machineimage?asynch=true");
		      connection = (HttpURLConnection) postUrl.openConnection();
		      connection.setDoOutput(true);
		      connection.setInstanceFollowRedirects(false);
		      connection.setRequestMethod("POST");
		      connection.setRequestProperty("Content-Type", "application/xml");
		      os = connection.getOutputStream();
		      os.write(newVolume.getBytes());
		      os.flush();
		      Assert.assertEquals(HttpURLConnection.HTTP_ACCEPTED, connection.getResponseCode());
		      System.out.println("Location: " + connection.getHeaderField("Location"));
		      connection.disconnect();
	   }
}
