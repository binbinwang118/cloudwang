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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.binbin.skywang.domain.CloudVolumeVO;
import org.binbin.skywang.domain.VolumeVO;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Volume;
import org.dasein.cloud.compute.VolumeSupport;

import org.jboss.logging.Logger;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;


@Path("/volume")
public class VolumeResource extends BaseCloudService {
	
	private Logger logger = Logger.getLogger(VolumeResource.class);
	
	private static VolumeSupport volumeSupport = null;

	public VolumeResource(String cloudId) {
		super(cloudId);
		volumeSupport = provider.getComputeServices().getVolumeSupport();
	}
	
	@AddLinks
	@LinkResource(value = CloudVolumeVO.class, rel = "list")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listVolume() {
		
		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
		Iterable<Volume> volume     = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			volume = volumeSupport.listVolumes();
			
			if (volume == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
			
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
			
			cloudVolumeVO.setProviderVolumeId("providerVolumeId");
			cloudVolumeVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
			cloudVolumeVO.setCloudName(provider.getCloudName());
			cloudVolumeVO.setCloudProvider(provider.getProviderName());
			cloudVolumeVO.setCloudRegionId(provider.getContext().getRegionId());
			cloudVolumeVO.setVolumeMethod("listVolume");
			cloudVolumeVO.setVolumeVOList(volumeVOList);
		    
			builder.status(Response.Status.OK);
			builder.entity(cloudVolumeVO);
			Response response = builder.build();
			return response;
					
	}
	
	@AddLinks
	@LinkResource(value = CloudVolumeVO.class, rel = "self")
	@GET
	@Path("{providerVolumeId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getVolume(@PathParam("providerVolumeId") String providerVolumeId, @Context UriInfo info) {
		
		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
        Volume volume = null;
        VolumeVO volumeVO = new VolumeVO();
        
        ResponseBuilderImpl builder = new ResponseBuilderImpl();
        
        try {
			volume = volumeSupport.getVolume(providerVolumeId);
			
			if (volume == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			} else {
				volumeVO = convertVolume(volume);
				volumeVOList.add(volumeVO);
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		cloudVolumeVO.setProviderVolumeId(volumeVO.getProviderVolumeId());
		cloudVolumeVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudVolumeVO.setCloudName(provider.getCloudName());
		cloudVolumeVO.setCloudProvider(provider.getProviderName());
		cloudVolumeVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudVolumeVO.setVolumeMethod("getVolume");
		cloudVolumeVO.setVolumeVOList(volumeVOList);
		
		builder.entity(cloudVolumeVO);
		builder.status(Response.Status.OK);
		Response response = builder.build();
		return response;
	
	}
	
	@LinkResource(value = CloudVolumeVO.class, rel = "headVolume")
	@HEAD
	@Path("{providerVolumeId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getVolumeHeaders(@PathParam("providerVolumeId") String providerVolumeId, @Context UriInfo uriInfo) {
		
		Volume volume = null;
        ResponseBuilderImpl builder = new ResponseBuilderImpl();
        Response response = null;
        
        try {
			volume = volumeSupport.getVolume(providerVolumeId);
			
			if (volume == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				builder.status(Response.Status.OK);
				response = builder.build();
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;	
	}
	
	@LinkResource(value = CloudVolumeVO.class, rel = "headVolumeList")
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getVolumeListHeaders(@Context UriInfo uriInfo) {
		
		List<Volume> volume     = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response = null;
		
		try {
			volume = (List<Volume>) volumeSupport.listVolumes();
			if (volume == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				int volumeNumber = volume.size();
				builder.header("volumeNumber", volumeNumber);
				builder.status(Response.Status.OK);
				response = builder.build();
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;	
		
	}

	
	@LinkResource(value = CloudVolumeVO.class, rel = "update")
	@PUT
	@Path("{providerVolumeId}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void updateVolume(@PathParam("providerVolumeId") String providerVolumeId, CloudVolumeVO cloudVolumeVO, @Context UriInfo info) {
		
		CloudVolumeVO updateCloudVolumeVO = null;
		
		if(cloudVolumeVO.getVolumeMethod().equals("attachVolume")) {
			updateCloudVolumeVO = attchVolumeToServer(providerVolumeId, cloudVolumeVO);
		}
		else if(cloudVolumeVO.getVolumeMethod().equals("detachVolume")) {
			updateCloudVolumeVO = detachVolumeFromServer(providerVolumeId, cloudVolumeVO);
		}
		else {
			logger.info("Unsupported volume PUT methods: " + cloudVolumeVO.getVolumeMethod());
		}
		
	}
	
	public CloudVolumeVO attchVolumeToServer(String providerVolumeId, CloudVolumeVO attachVolume) {
		
		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
        Volume volume = null;
        VolumeVO volumeVO = new VolumeVO();
        
		String serverId = attachVolume.getVolumeVOList().get(0).getServerId();
		String deviceId = attachVolume.getVolumeVOList().get(0).getDeviceId();
		try {
			volumeSupport.attach(providerVolumeId, serverId, deviceId);
			volume = volumeSupport.getVolume(providerVolumeId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    volumeVO = convertVolume(volume);
		volumeVOList.add(volumeVO);
		
		cloudVolumeVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudVolumeVO.setCloudName(provider.getCloudName());
		cloudVolumeVO.setCloudProvider(provider.getProviderName());
		cloudVolumeVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudVolumeVO.setVolumeMethod("attachVolume");
		cloudVolumeVO.setVolumeVOList(volumeVOList);
		
		return cloudVolumeVO;
	}
	
	public CloudVolumeVO detachVolumeFromServer(String providerVolumeId, CloudVolumeVO detachVolume) {

		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
        Volume volume = null;
        VolumeVO volumeVO = new VolumeVO();
		
		try {
			volumeSupport.detach(providerVolumeId);
			volume = volumeSupport.getVolume(providerVolumeId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logger.info(volumeVO);
		}
		
		volumeVO = convertVolume(volume);
		volumeVOList.add(volumeVO);
		
		cloudVolumeVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudVolumeVO.setCloudName(provider.getCloudName());
		cloudVolumeVO.setCloudProvider(provider.getProviderName());
		cloudVolumeVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudVolumeVO.setVolumeMethod("detachVolume");
		cloudVolumeVO.setVolumeVOList(volumeVOList);
		
		return cloudVolumeVO;
	}
	
	@LinkResource(value = CloudVolumeVO.class, rel = "add")
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createVolume(CloudVolumeVO createVolume) throws InternalException, CloudException {
		
		CloudVolumeVO cloudVolumeVO = new CloudVolumeVO();	
		List<VolumeVO> volumeVOList = new LinkedList<VolumeVO>();
		VolumeVO volumeVO = new VolumeVO();
		
		String fromSnapshot = null;		
		String volumeName = null;
		int volumeSize = createVolume.getVolumeVOList().get(0).getSizeInGb();
		String dataCenterId = createVolume.getVolumeVOList().get(0).getZoneId();
		
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
		
		cloudVolumeVO.setProviderVolumeId(volumeVO.getProviderVolumeId());
		cloudVolumeVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudVolumeVO.setCloudName(provider.getCloudName());
		cloudVolumeVO.setCloudProvider(provider.getProviderName());
		cloudVolumeVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudVolumeVO.setVolumeMethod("createVolume");
		cloudVolumeVO.setVolumeVOList(volumeVOList);
		
		return Response.created(URI.create("/volume/" + volumeVO.getProviderVolumeId())).build();
		
	}
	
	@LinkResource(value = CloudVolumeVO.class, rel = "remove")
	@DELETE
	@Path("{providerVolumeId}")
	public void removeVolume(@PathParam("providerVolumeId") String providerVolumeId, @Context UriInfo info) {
		
		try {
			volumeSupport.remove(providerVolumeId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	   
	public VolumeVO convertVolume(Volume volume) {
		
		VolumeVO volumeVO = new VolumeVO();
		
		volumeVO.setVolumeId("tbd");
		volumeVO.setEncrypted(false);
		
		if(volume.getProviderVirtualMachineId() != null) {
			volumeVO.setRemovable(false);
			volumeVO.setVolumeStatus("IN-USE");
		} else {
			volumeVO.setRemovable(true);
			volumeVO.setVolumeStatus("AVAILABLE");
		}
		
		/** TODO, tbd */
		volumeVO.setBudget(0);
		
		volumeVO.setProviderVolumeId(volume.getProviderVolumeId());
		volumeVO.setVolumeName(volume.getName());
		volumeVO.setSizeInGb(volume.getSizeInGigabytes());
		volumeVO.setCreationTimestamp(volume.getCreationTimestamp());
		volumeVO.setDescription(volume.getName());
		volumeVO.setServerId(volume.getProviderVirtualMachineId());
		volumeVO.setDeviceId(volume.getDeviceId());
		volumeVO.setSnapshotId(volume.getProviderSnapshotId());
		volumeVO.setZoneId(volume.getProviderDataCenterId());
		
		return volumeVO;
	}

}
