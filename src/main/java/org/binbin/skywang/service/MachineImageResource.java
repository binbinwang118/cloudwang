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

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import org.binbin.skywang.domain.CloudMachineImageVO;
import org.binbin.skywang.domain.MachineImageVO;
import org.dasein.cloud.AsynchronousTask;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.MachineImageSupport;
import org.dasein.cloud.compute.Platform;

@Path("/machineimage")
public class MachineImageResource  extends BaseCloudService{
	
	 private Logger logger = Logger.getLogger(MachineImageResource.class);
	
	private static MachineImageSupport machineImageSupport = null;
	
	public MachineImageResource(String cloudId) {
		super(cloudId);
		machineImageSupport = provider.getComputeServices().getImageSupport();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudMachineImageVO listMachineImage(@Context UriInfo info) {
		
		String accountNumber = null;
		String keyword = null;
		String strPlatform = null;
		String strArchitecture = null;
		Platform platform = null;
		Architecture architecture = null;
		
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

		cloudMachineImageVO.setMachineImageMethod("listMachineImage");
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
			
		return cloudMachineImageVO;
	}
	
	@GET
	@Path("{providerMachineImageId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudMachineImageVO getMachineImage(@PathParam("providerMachineImageId") String providerMachineImageId) {
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		MachineImageVO machineImageVO = null;
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		
		MachineImage machineImage;
		
		try {
			machineImage = machineImageSupport.getMachineImage(providerMachineImageId);
			machineImageVO = convertMachineImage(machineImage);
			machineImageVOList.add(machineImageVO);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cloudMachineImageVO.setMachineImageMethod("getMachineImage");
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
		
		return cloudMachineImageVO;
		
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudMachineImageVO createMachineImage(CloudMachineImageVO createMachineImage) {
		
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
		
		cloudMachineImageVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudMachineImageVO.setCloudName(provider.getCloudName());
		cloudMachineImageVO.setCloudProvider(provider.getProviderName());
		cloudMachineImageVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudMachineImageVO.setMachineImageMethod("createMachineImage");
		cloudMachineImageVO.setMachineImageVOList(machineImageVOList);
		
		return cloudMachineImageVO;
//		return Response.created(URI.create("/machineimage/" + taskMachineImage.getResult())).build();
		
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
		machineImageVO.setServerId("NULL");
		return machineImageVO;
		
	}

}
