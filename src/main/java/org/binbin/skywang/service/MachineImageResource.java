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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import org.binbin.skywang.domain.CloudMachineImageVO;
import org.binbin.skywang.domain.MachineImageVO;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.MachineImageSupport;

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
		if(info.getQueryParameters().containsKey("accountNumber")) {
			accountNumber = info.getQueryParameters().getFirst("accountNumber");
		}
		
		CloudMachineImageVO cloudMachineImageVO = new CloudMachineImageVO();
		List<MachineImageVO> machineImageVOList = new LinkedList<MachineImageVO>();
		
		try {
			Iterable<MachineImage> machineImage;
			if(accountNumber == null) {
				machineImage = machineImageSupport.listMachineImages();
			} else {
				machineImage = machineImageSupport.listMachineImagesOwnedBy(accountNumber);
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
	public CloudMachineImageVO getMachineImage(@PathParam("providerMachineImageId") String providerMachineImageId, @Context UriInfo info) {
		
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
