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

package org.binbin.skywang.service;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Volume;
import org.dasein.cloud.compute.VolumeSupport;
import org.jboss.logging.Logger;

import org.binbin.skywang.domain.CloudVolumeVO;
import org.binbin.skywang.domain.VolumeVO;
import org.binbin.skywang.service.BaseCloudService;

import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/volume")
public class VolumeResource extends BaseCloudService {
	
	private Logger logger = Logger.getLogger(VolumeResource.class);
	
	private static VolumeSupport volumeSupport = null;

	public VolumeResource(String cloudId) {
		super(cloudId);
		volumeSupport = provider.getComputeServices().getVolumeSupport();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudVolumeVO listVolume(@Context UriInfo info) {
		
		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();

		if(info.getQueryParameters().containsKey("asynch")) {
			logger.error("Only POST method can be invoked asynchronously!");
			return cloudVolumeVO;
		}
		
		try {
			Iterable<Volume> volume = volumeSupport.listVolumes();
			for(Volume tmpVolume : volume) {
				volumeVOList.add(convertVolume(tmpVolume));
				}
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CloudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cloudVolumeVO.setCloudProvider(provider.getProviderName());
			cloudVolumeVO.setVolumeMethod("listVolume");
			cloudVolumeVO.setVolumeVOList(volumeVOList);
			
			return cloudVolumeVO;
					
	}
   
	@GET
	@Path("{volumeName}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CloudVolumeVO getVolume(@PathParam("volumeName") String volumeName, @Context UriInfo info) {
		
		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
        Volume volumeDasein = null;
        VolumeVO volumeVO = new VolumeVO();
        
		if(info.getQueryParameters().containsKey("asynch")) {
			logger.error("Only POST method can be invoked asynchronously!");
			return cloudVolumeVO;
		}
        
        try {
			volumeDasein = volumeSupport.getVolume(volumeName);
			volumeVO = convertVolume(volumeDasein);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		volumeVOList.add(volumeVO);
		
		cloudVolumeVO.setCloudProvider(provider.getProviderName());
		cloudVolumeVO.setVolumeMethod("getVolume");
		cloudVolumeVO.setVolumeVOList(volumeVOList);
		
		return cloudVolumeVO;
	
	}
	
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getVolumeHeaders(@Context UriInfo uriInfo) {
		return null;	
	}
	
	@PUT
	@Path("{volumeName}")
	@Consumes({"application/xml", "application/json"})
	public void updateVolume(@PathParam("volumeName") String volumeName, CloudVolumeVO cloudVolumeVO, @Context UriInfo info) {
		
		if(info.getQueryParameters().containsKey("asynch")) {
			logger.error("Only POST method can be invoked asynchronously!");
			return;
		}
		
		if(cloudVolumeVO.getVolumeMethod().equals("attachVolume")) {
			attchVolumeToServer(volumeName, cloudVolumeVO);
		}
		else if(cloudVolumeVO.getVolumeMethod().equals("detachVolume")) {
			detachVolumeFromServer(volumeName, cloudVolumeVO);
		}
		else {
			logger.info("Unsupported volume PUT methods: " + cloudVolumeVO.getVolumeMethod());
		}
	}
	
	public void attchVolumeToServer(String volumeName, CloudVolumeVO attachVolume) {
		
		VolumeVO volumeVO = new VolumeVO();
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
		Volume volumeDasein = null;
		String serverId = attachVolume.getVolumeVOList().get(0).getServerId();
		String deviceId = attachVolume.getVolumeVOList().get(0).getDeviceId();
		try {
			volumeSupport.attach(volumeName, serverId, deviceId);
			volumeDasein = volumeSupport.getVolume(volumeName);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    volumeVO = convertVolume(volumeDasein);
		volumeVOList.add(volumeVO);
		attachVolume.setVolumeVOList(volumeVOList);
	}
	
	public void detachVolumeFromServer(String volumeName, CloudVolumeVO detachVolume) {

		VolumeVO volumeVO = new VolumeVO();
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
		Volume volumeDasein = null;
		
		try {
			volumeSupport.detach(volumeName);
			volumeDasein = volumeSupport.getVolume(volumeName);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logger.info(volumeVO);
		}
		
		volumeVO = convertVolume(volumeDasein);
		volumeVOList.add(volumeVO);
		detachVolume.setVolumeVOList(volumeVOList);
		
	}
	
	@DELETE
	@Path("{volumeName}")
	public void removeVolume(@PathParam("volumeName") String volumeName, @Context UriInfo info) {
		
		if(info.getQueryParameters().containsKey("asynch")) {
			logger.error("Only POST method can be invoked asynchronously!");
			return;
		}
		
		try {
			volumeSupport.remove(volumeName);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// todo, modified the volume status locally.
		// CloudVolumeVO removeVolume;
	}
	   
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createVolume(CloudVolumeVO createVolume) throws InternalException, CloudException {
		
		VolumeVO volumeVO = new VolumeVO();
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
		
		String fromSnapshot = null;		
		String volumeName = null;
		int volumeSize = createVolume.getVolumeVOList().get(0).getSizeInGb();
		String dataCenterId = createVolume.getVolumeVOList().get(0).getDataCenterId();
		
		try {
			volumeName = volumeSupport.create(fromSnapshot, volumeSize, dataCenterId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		volumeVO = convertVolume(volumeSupport.getVolume(volumeName));
		volumeVOList.add(volumeVO);
		createVolume.setVolumeVOList(volumeVOList);
		
		return Response.created(URI.create("/volume/" + volumeVO.getVolumeName())).build();
		
	}
	
	public VolumeVO convertVolume(Volume volume) {
		
		VolumeVO volumeObj = new VolumeVO();
		
		volumeObj.setVolumeName(volume.getName());
		volumeObj.setDescription(volume.getName());
		volumeObj.setSizeInGb(volume.getSizeInGigabytes());
		volumeObj.setCreationTimestamp(volume.getCreationTimestamp());
		volumeObj.setVolumeStatus(volume.getCurrentState());
		volumeObj.setSnapshotId(volume.getProviderSnapshotId());
		volumeObj.setServerId(volume.getProviderVirtualMachineId());
		volumeObj.setDeviceId(volume.getDeviceId());
		volumeObj.setCloud(provider.getCloudName());
		volumeObj.setDataCenterId(volume.getProviderDataCenterId());
		volumeObj.setProviderRegionId(volume.getProviderRegionId());
			
		if(volume.getProviderVirtualMachineId() == null) {
			volumeObj.setRemovable(true);
		} else {
			volumeObj.setRemovable(false);
		}
		
		if((volume.getCurrentState().equals("AVAILABLE")) && (volume.getProviderVirtualMachineId() != null)) {
			volumeObj.setAvailable(true);
		} else {
			volumeObj.setAvailable(false);
		}
		
		return volumeObj;
		
	}


}
