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

import org.binbin.skywang.domain.CloudMachineImageVO;
import org.binbin.skywang.domain.CloudSnapshotVO;
import org.binbin.skywang.domain.MachineImageVO;

import org.dasein.cloud.AsynchronousTask;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.MachineImageSupport;
import org.dasein.cloud.compute.Platform;

import org.jboss.logging.Logger;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

@Path("/machineimage")
public class MachineImageResource extends BaseCloudService {
	
	private Logger logger = Logger.getLogger(MachineImageResource.class);
	
	private static MachineImageSupport machineImageSupport = null;
	
	public MachineImageResource(String cloudId) {
		super(cloudId);
		machineImageSupport = provider.getComputeServices().getImageSupport();
	}
	
	@AddLinks
	@LinkResource(value = CloudMachineImageVO.class, rel = "list")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listMachineImage(@Context UriInfo info) {
		
		String accountNumber = null;
		String keyword = null;
		String strPlatform = null;
		String strArchitecture = null;
		Platform platform = null;
		Architecture architecture = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		if(info.getQueryParameters().containsKey("accountNumber")) {
			accountNumber = info.getQueryParameters().getFirst("accountNumber");
		}
		else if(info.getQueryParameters().containsKey("keyword")) {
			keyword = info.getQueryParameters().getFirst("keyword");
		}
		else if(info.getQueryParameters().containsKey("platform")) {
			strPlatform = info.getQueryParameters().getFirst("platform");
			platform = Platform.guess(strPlatform);
		}
		else if(info.getQueryParameters().containsKey("architecture")) {
			strArchitecture = info.getQueryParameters().getFirst("architecture");
			if(strArchitecture.contains("I32")) {
				architecture = Architecture.I32;
			}
			else if(strArchitecture.contains("I64")) {
				architecture = Architecture.I64;
			}
		}
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		
		try {
			Iterable<MachineImage> machineImage = null;
			if((accountNumber == null) && (keyword == null)) { 
				machineImage = machineImageSupport.listMachineImages();
			} 
			else if((accountNumber != null) && (keyword == null)) {
				machineImage = machineImageSupport.listMachineImagesOwnedBy(accountNumber);
			}
			else if((accountNumber == null) && (keyword != null)) {
				machineImage = machineImageSupport.searchMachineImages(keyword, platform, architecture);
			}
			
			if (machineImage == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
			
			for(MachineImage tmpMachineImage : machineImage) {
				machineImageVOList.add(convertMachineImage(tmpMachineImage));
			}
		} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (CloudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

		cloudMachineImageVO.setProviderMachineImageId("providerMachineImageId");
		cloudMachineImageVO.setMachineImageMethod("listMachineImage");
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
			
		builder.status(Response.Status.OK);
		builder.entity(cloudMachineImageVO);
		Response response = builder.build();
		return response;
	}
	
	@AddLinks
	@LinkResource(value = CloudMachineImageVO.class, rel = "self")
	@GET
	@Path("{providerMachineImageId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getMachineImage(@PathParam("providerMachineImageId") String providerMachineImageId) {
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		MachineImageVO machineImageVO		    = null;
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		MachineImage machineImage 	= null;
		
		try {
			machineImage = machineImageSupport.getMachineImage(providerMachineImageId);
			if (machineImage == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			} else {
				machineImageVO = convertMachineImage(machineImage);
				machineImageVOList.add(machineImageVO);
			}
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cloudMachineImageVO.setProviderMachineImageId(providerMachineImageId);
		cloudMachineImageVO.setMachineImageMethod("getMachineImage");
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
		
		builder.status(Response.Status.OK);
		builder.entity(cloudMachineImageVO);
		Response response = builder.build();
		return response;
		
	}
	
	@LinkResource(value = CloudMachineImageVO.class, rel = "GET machineImage share")
	@GET
	@Path("{providerMachineImageId}/share")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getMachineImageShare(@PathParam("providerMachineImageId") String providerMachineImageId) {
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		MachineImageVO machineImageVO = new MachineImageVO();
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		
		Iterable<String> results	= null;
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			results = machineImageSupport.listShares(providerMachineImageId);
			if (results == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		machineImageVO.setShare(results.toString());
		machineImageVO.setPublic(true);
		machineImageVO.setSharable(true);
		machineImageVOList.add(machineImageVO);
		
		cloudMachineImageVO.setProviderMachineImageId(providerMachineImageId);
		cloudMachineImageVO.setMachineImageMethod("getMachineImageShare");
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
		
		builder.status(Response.Status.OK);
		builder.entity(cloudMachineImageVO);
		Response response = builder.build();
		return response;
	}
	
	@LinkResource(value = CloudMachineImageVO.class, rel = "headMachineImage")
	@HEAD
	@Path("{providerMachineImageId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getMachineImageHead(@PathParam("providerMachineImageId") String providerMachineImageId, @Context UriInfo info) {
	
		MachineImage machineImage 	= null;
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		try {
			machineImage = machineImageSupport.getMachineImage(providerMachineImageId);
			if (machineImage == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				builder.status(Response.Status.OK);
				response = builder.build();
			}
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		
	}
	
	@LinkResource(value = CloudMachineImageVO.class, rel = "headMachineImageList")
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getMachineImageListHead(@Context UriInfo info) {
		
		String accountNumber = null;
		String keyword = null;
		String strPlatform = null;
		String strArchitecture = null;
		Platform platform = null;
		Architecture architecture = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		if(info.getQueryParameters().containsKey("accountNumber")) {
			accountNumber = info.getQueryParameters().getFirst("accountNumber");
		}
		else if(info.getQueryParameters().containsKey("keyword")) {
			keyword = info.getQueryParameters().getFirst("keyword");
		}
		else if(info.getQueryParameters().containsKey("platform")) {
			strPlatform = info.getQueryParameters().getFirst("platform");
			platform = Platform.guess(strPlatform);
		}
		else if(info.getQueryParameters().containsKey("architecture")) {
			strArchitecture = info.getQueryParameters().getFirst("architecture");
			if(strArchitecture.contains("I32")) {
				architecture = Architecture.I32;
			}
			else if(strArchitecture.contains("I64")) {
				architecture = Architecture.I64;
			}
		}
		
		try {
			List<MachineImage> machineImage = null;
			if((accountNumber == null) && (keyword == null)) { 
				machineImage = (List<MachineImage>) machineImageSupport.listMachineImages();
			} 
			else if((accountNumber != null) && (keyword == null)) {
				machineImage = (List<MachineImage>) machineImageSupport.listMachineImagesOwnedBy(accountNumber);
			}
			else if((accountNumber == null) && (keyword != null)) {
				machineImage = (List<MachineImage>) machineImageSupport.searchMachineImages(keyword, platform, architecture);
			}
			
			if (machineImage == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			}else {
				int machineImageNumber = machineImage.size();
				builder.header("MachineImage Number", machineImageNumber);
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
	
	@LinkResource(value = CloudMachineImageVO.class, rel = "update")
	@PUT
	@Path("{providerMachineImageId}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void putMachineImage(@PathParam("providerMachineImageId") String providerMachineImageId, CloudMachineImageVO putMachineImage) throws CloudException, InternalException {
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		MachineImageVO machineImageVO = null;
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		MachineImage machineImage = null;
		String machineImageMethod = null;

		String machineImageId = providerMachineImageId;
		boolean isPublic = putMachineImage.getMachineImageVOList().get(0).isPublic();
		String share = putMachineImage.getMachineImageVOList().get(0).getShare();
		
		try {
			machineImage = machineImageSupport.getMachineImage(machineImageId);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		machineImageVO = convertMachineImage(machineImage);
		
		if(share == null) {
			if(isPublic) {
				machineImageMethod = "makeMachineImagePublic";
				makeMachineImagePublic(machineImageId);
				machineImageVO.setPublic(true);
			} else {
				machineImageMethod = "makeMachineImagePrivate";
				makeMachineImagePrivate(machineImageId);
				machineImageVO.setPublic(false);
			}
		} else {
			if(isPublic) {
				machineImageMethod = "addMachineImageShare";
				addMachineImageShare(machineImageId, share);
				Iterable<String> shareAccounts = machineImageSupport.listShares(machineImageId);
				machineImageVO.setShare(shareAccounts.toString());
			} else {
				machineImageMethod = "removeMachineImageShare";
				removeMachineImageShare(machineImageId, share);
				Iterable<String> shareAccounts = machineImageSupport.listShares(machineImageId);
				machineImageVO.setShare(shareAccounts.toString());
			}
		}
		
		machineImageVOList.add(machineImageVO);
		
		cloudMachineImageVO.setProviderMachineImageId(providerMachineImageId);
		cloudMachineImageVO.setMachineImageMethod(machineImageMethod);
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
		
	}
	
	private void makeMachineImagePublic(String providerMachineImageId) {
		
		try {
			machineImageSupport.shareMachineImage(providerMachineImageId, null, true);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeMachineImagePrivate(String providerMachineImageId) {
		
		try {
			machineImageSupport.shareMachineImage(providerMachineImageId, null, false);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addMachineImageShare(String providerMachineImageId, String accountNumber) {
		
		try {
			machineImageSupport.shareMachineImage(providerMachineImageId, accountNumber, true);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void removeMachineImageShare(String providerMachineImageId, String accountNumber) {
		
		try {
			machineImageSupport.shareMachineImage(providerMachineImageId, accountNumber, false);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@LinkResource(value = CloudMachineImageVO.class, rel = "add")
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createMachineImage(CloudMachineImageVO createMachineImage) {
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		MachineImage machineMage = null;
		MachineImageVO machineImageVO = null;
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		
		String serverId = createMachineImage.getMachineImageVOList().get(0).getServerId();
		String machineImageName = createMachineImage.getMachineImageVOList().get(0).getName();
		String description = createMachineImage.getMachineImageVOList().get(0).getDescription();
		
		logger.debug(serverId);
		logger.debug(machineImageName);
		logger.debug(description);
		
		AsynchronousTask<String> taskMachineImage = null;
		
		try {
			taskMachineImage = machineImageSupport.imageVirtualMachine(serverId, machineImageName, description);
			machineMage = machineImageSupport.getMachineImage(taskMachineImage.getResult());
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		machineImageVO = convertMachineImage(machineMage);
		machineImageVO.setServerId(serverId);
		machineImageVOList.add(machineImageVO);
		
		cloudMachineImageVO.setProviderMachineImageId(taskMachineImage.getResult());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudMachineImageVO.setMachineImageMethod("createMachineImage");
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
		
		return Response.created(URI.create("/machineimage/" + taskMachineImage.getResult())).build();
	}
	
	@LinkResource(value = CloudSnapshotVO.class, rel = "remove")
	@DELETE
	@Path("{providerMachineImageId}")
	public void removeMachineImage(@PathParam("providerMachineImageId") String providerMachineImageId, @Context UriInfo info) {
		
		try {
			machineImageSupport.remove(providerMachineImageId);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public MachineImageVO convertMachineImage(MachineImage machineImage) {
		
		MachineImageVO machineImageVO = new MachineImageVO();
		
		machineImageVO.setMachineImageId("NULL");
		machineImageVO.setStatus(machineImage.getCurrentState());
		
		if(machineImage.getProviderOwnerId().equals(provider.getContext().getAccountNumber())) {
			machineImageVO.setRemovable(true);
		} else {
			machineImageVO.setRemovable(false);
		}
		
		machineImageVO.setBudget(0);
		
		machineImageVO.setProvidermachineImageId(machineImage.getProviderMachineImageId());
		machineImageVO.setName(machineImage.getName());
		machineImageVO.setDescription(machineImage.getDescription());
		machineImageVO.setMachineImageType(machineImage.getType());
		
		if(machineImage.getTags().get("public").equals("true")) {
			machineImageVO.setPublic(true);
			machineImageVO.setSharable(true);
		} else {
			machineImageVO.setPublic(false);
			machineImageVO.setSharable(false);
		}
		machineImageVO.setArchitecture(machineImage.getArchitecture());
		machineImageVO.setPlatform(machineImage.getPlatform());
		machineImageVO.setProviderOwnerId(machineImage.getProviderOwnerId());
		
		machineImageVO.setAgentName("NULL");
		machineImageVO.setSoftware("NULL");
		return machineImageVO;
		
	}

}
