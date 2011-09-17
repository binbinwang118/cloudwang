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
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Snapshot;
import org.dasein.cloud.compute.SnapshotSupport;
import org.binbin.skywang.domain.SnapshotVO;
import org.binbin.skywang.domain.CloudSnapshotVO;
import org.binbin.skywang.service.BaseCloudService;

import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

@Path("/snapshot")
public class SnapshotResource extends BaseCloudService {
	
	private static SnapshotSupport snapshotSupport = null;
	
	public SnapshotResource(String cloudId) {
		super(cloudId);
		snapshotSupport = provider.getComputeServices().getSnapshotSupport();
	}
	
	/** can filter with account number */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudSnapshotVO listSnapshot(@Context UriInfo info) {
		
		CloudSnapshotVO cloudSnapshotVO = new CloudSnapshotVO();	
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>();
		
		if(info.getQueryParameters().containsKey("asynch")) {
			System.out.println("Only POST method can be invoked asynchronously!");
			return cloudSnapshotVO;
		}
		
		try {
			Iterable<Snapshot> snapshot = snapshotSupport.listSnapshots();		
			for(Snapshot tmpSnapshot : snapshot) {
				snapshotVOList.add(convertSnapshot(tmpSnapshot));
			}
		} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (CloudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

		cloudSnapshotVO.setCloudProvider(provider.getProviderName());
		cloudSnapshotVO.setSnapshotMethod("listSnapshot");
		cloudSnapshotVO.setSnapshotVOList(snapshotVOList);
			
		return cloudSnapshotVO;
					
	}
	
	@GET
	@Path("{providerSnapshotId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudSnapshotVO getSnapshot(@PathParam("providerSnapshotId") String providerSnapshotId, @Context UriInfo info) {
		
		CloudSnapshotVO cloudSnapshotVO = new CloudSnapshotVO();	
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>(); 
		
		Snapshot snapshotDasein = null;
		SnapshotVO snapshotVO = new SnapshotVO();

		if(info.getQueryParameters().containsKey("asynch")) {
			System.out.println("Only POST method can be invoked asynchronously!");
			return cloudSnapshotVO;
		}
		
		try {
			snapshotDasein = snapshotSupport.getSnapshot(providerSnapshotId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		snapshotVO = convertSnapshot(snapshotDasein);
		snapshotVOList.add(snapshotVO);
		
		cloudSnapshotVO.setCloudProvider(provider.getProviderName());
		cloudSnapshotVO.setSnapshotMethod("getSnapshot");
		cloudSnapshotVO.setSnapshotVOList(snapshotVOList);
		
		return cloudSnapshotVO;
	}
	
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSnapshotHead(@Context UriInfo uriInfo) {
		return null;	
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudSnapshotVO createSnapshot(CloudSnapshotVO createSnapshot) {
		
		CloudSnapshotVO results = new CloudSnapshotVO();
		String providerSnapshotId = null;
		SnapshotVO snapshotVO = new SnapshotVO();
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>();
		
		String ofVolume = createSnapshot.getSnapshotVOList().get(0).getVolumeId();
		String description = createSnapshot.getSnapshotVOList().get(0).getDescription();
		
		try {
			providerSnapshotId = snapshotSupport.create(ofVolume, description);
			snapshotVO = convertSnapshot(snapshotSupport.getSnapshot(providerSnapshotId));
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		snapshotVOList.add(snapshotVO);
		results.setSnapshotMethod("createSnapshot");
		results.setCloudProvider(provider.getCloudName());
		results.setSnapshotVOList(snapshotVOList);
		
		return results;
	
//		return Response.created(URI.create("/snapshot/" + snapshotVO.getProviderSnapshotId())).build();
		
	}
	
	@DELETE
	@Path("{providerSnapshotId}")
	public void removeSnapshot(@PathParam("providerSnapshotId") String providerSnapshotId) {
		
		try {
			snapshotSupport.remove(providerSnapshotId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SnapshotVO convertSnapshot(Snapshot snapshot) {
		SnapshotVO snapshotVO = new SnapshotVO();
		
		snapshotVO.setArray(null);

		if(snapshot.getCurrentState().toString().equals("AVAILABLE")) {
			snapshotVO.setAvailable(true);
		}else {
			snapshotVO.setAvailable(false);
		}
		snapshotVO.setBudget(0);
		snapshotVO.setCloud(provider.getCloudName());
		snapshotVO.setCurrentState(snapshot.getCurrentState());
		snapshotVO.setCustomer(null);
		snapshotVO.setDescription(snapshot.getDescription());
		snapshotVO.setEncrypted(false);
		snapshotVO.setLabel(null);
		snapshotVO.setName(null);
		snapshotVO.setOwningAccount(null);
		snapshotVO.setOwningCloudAccountId(0);
		snapshotVO.setOwningCloudAccountNumber(provider.getContext().getAccountNumber());
		snapshotVO.setOwningGroups(null);
		snapshotVO.setOwningUser(null);
		snapshotVO.setProgress(snapshot.getProgress());
		snapshotVO.setProviderId(provider.getContext().getProviderName());
		snapshotVO.setProviderSnapshotId(snapshot.getProviderSnapshotId());
		
		try {
			boolean publics = snapshotSupport.isPublic(snapshot.getProviderSnapshotId());
			snapshotVO.setPublics(publics);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		snapshotVO.setRegionId(snapshot.getRegionId());
		
		/**
		 * todo, removable depending on 1) owned by me, 2) used by existing template 
		 * */
		snapshotVO.setRemovable(false);
		snapshotVO.setSharable(true);
		snapshotVO.setSizeInGb(snapshot.getSizeInGb());
		snapshotVO.setSnapshotId(0);
		snapshotVO.setSnapshotTimestamp(snapshot.getSnapshotTimestamp());
		snapshotVO.setVolumeId(snapshot.getVolumeId());
		
		return snapshotVO;
	}

}
