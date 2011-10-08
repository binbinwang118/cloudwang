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

import org.binbin.skywang.domain.CloudSnapshotVO;
import org.binbin.skywang.domain.SnapshotVO;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Snapshot;
import org.dasein.cloud.compute.SnapshotSupport;

import org.jboss.logging.Logger;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

@Path("/snapshot")
public class SnapshotResource extends BaseCloudService {
	
	private Logger logger = Logger.getLogger(SnapshotResource.class);
	
	private static SnapshotSupport snapshotSupport = null;
	
	public SnapshotResource(String cloudId) {
		super(cloudId);
		snapshotSupport = provider.getComputeServices().getSnapshotSupport();
	}
	
	@AddLinks
	@LinkResource(value = CloudSnapshotVO.class, rel = "list")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listSnapshot(@Context UriInfo info) {
		
		CloudSnapshotVO cloudSnapshotVO = new CloudSnapshotVO();	
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>();
		Iterable<Snapshot> snapshot		= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			snapshot = snapshotSupport.listSnapshots();		
			
			if (snapshot == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
			
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

		cloudSnapshotVO.setProviderSnapshotId("providerSnapshotId");
		cloudSnapshotVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudSnapshotVO.setCloudName(provider.getCloudName());
		cloudSnapshotVO.setCloudProvider(provider.getProviderName());
		cloudSnapshotVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudSnapshotVO.setSnapshotMethod("listSnapshot");
		cloudSnapshotVO.setSnapshotVOList(snapshotVOList);
			
		builder.status(Response.Status.OK);
		builder.entity(cloudSnapshotVO);
		Response response = builder.build();
		return response;
		
	}
	
	@AddLinks
	@LinkResource(value = CloudSnapshotVO.class, rel = "self")
	@GET
	@Path("{providerSnapshotId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSnapshot(@PathParam("providerSnapshotId") String providerSnapshotId, @Context UriInfo info) {
		
		CloudSnapshotVO cloudSnapshotVO = new CloudSnapshotVO();	
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>(); 
		Snapshot snapshot = null;
		SnapshotVO snapshotVO = new SnapshotVO();
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			snapshot = snapshotSupport.getSnapshot(providerSnapshotId);
			
			if (snapshot == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			} else {
				snapshotVO = convertSnapshot(snapshot);
				snapshotVOList.add(snapshotVO);
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudSnapshotVO.setProviderSnapshotId(providerSnapshotId);
		cloudSnapshotVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudSnapshotVO.setCloudName(provider.getCloudName());
		cloudSnapshotVO.setCloudProvider(provider.getProviderName());
		cloudSnapshotVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudSnapshotVO.setSnapshotMethod("getSnapshot");
		cloudSnapshotVO.setSnapshotVOList(snapshotVOList);
			
		builder.status(Response.Status.OK);
		builder.entity(cloudSnapshotVO);
		Response response = builder.build();
		return response;
	}
	
	@LinkResource(value = CloudSnapshotVO.class, rel = "headSnapshot")
	@HEAD
	@Path("{providerSnapshotId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSnapshotHead(@PathParam("providerSnapshotId") String providerSnapshotId, @Context UriInfo info) {
		
		Snapshot snapshot			= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		try {
			snapshot = snapshotSupport.getSnapshot(providerSnapshotId);
			
			if (snapshot == null) {
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
	
	@LinkResource(value = CloudSnapshotVO.class, rel = "headSnapshotList")
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSnapshotListHead(@Context UriInfo info) {
		
		List<Snapshot> snapshot		= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		try {
			snapshot = (List<Snapshot>) snapshotSupport.listSnapshots();		
			
			if (snapshot == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				int snapshotNumber = snapshot.size();
				builder.header("snapshotNumber", snapshotNumber);
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
	
	@LinkResource(value = CloudSnapshotVO.class, rel = "update")
	@PUT
	@Path("{providerSnapshotId}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void putSnapshot(@PathParam("providerSnapshotId") String providerSnapshotId, CloudSnapshotVO putSnapshot) {
		
		CloudSnapshotVO cloudSnapshotVO = new CloudSnapshotVO();
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>();
		
		SnapshotVO snapshotVO	= null;
		Snapshot snapshot 		= null;
		String snapshotMethod 	= null;

		String snapshotId 		= providerSnapshotId;
		boolean isPublic 		= putSnapshot.getSnapshotVOList().get(0).isPublic();
		String shareAccount 	=  putSnapshot.getSnapshotVOList().get(0).getShareProviderAccounts();
		
		try {
			snapshot = snapshotSupport.getSnapshot(snapshotId);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		snapshotVO 					  		   = convertSnapshot(snapshot);
		Iterable<String> shareProviderAccounts = null;
		
		if(shareAccount == null) {
			if(isPublic) {
				snapshotMethod = "makeSnapshotPublic";
				makeSnapshotPublic(snapshotId);
				snapshotVO.setPublic(true);
			} else {
				snapshotMethod = "makeSnapshotPrivate";
				makeSnapshotPrivate(snapshotId);
				snapshotVO.setPublic(false);
			}
		} else {
			try {
				if(isPublic) {
					snapshotMethod = "addSnapshotShareAccount";
					addSnapshotShareAccount(snapshotId, shareAccount);
					shareProviderAccounts = snapshotSupport.listShares(snapshotId);
					snapshotVO.setShareProviderAccounts(shareProviderAccounts.toString());
				} else {
					snapshotMethod = "removeSnapshotShareAccount";
					removeSnapshotShareAccount(snapshotId, shareAccount);
					shareProviderAccounts = snapshotSupport.listShares(snapshotId);
					snapshotVO.setShareProviderAccounts(shareProviderAccounts.toString());
				}
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CloudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		snapshotVOList.add(snapshotVO);
		
		cloudSnapshotVO.setProviderSnapshotId(providerSnapshotId);
		cloudSnapshotVO.setSnapshotMethod(snapshotMethod);
		cloudSnapshotVO.setCloudProvider(provider.getProviderName());
		cloudSnapshotVO.setCloudName(provider.getCloudName());
		cloudSnapshotVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudSnapshotVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudSnapshotVO.setSnapshotVOList(snapshotVOList);
		
	}
	
	private void makeSnapshotPublic(String providerSnapshotId) {
		
		try {
			snapshotSupport.shareSnapshot(providerSnapshotId, null, true);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeSnapshotPrivate(String providerSnapshotId) {
		
		try {
			snapshotSupport.shareSnapshot(providerSnapshotId, null, false);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addSnapshotShareAccount(String providerSnapshotId, String accountNumber) {
		
		try {
			snapshotSupport.shareSnapshot(providerSnapshotId, accountNumber, true);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void removeSnapshotShareAccount(String providerSnapshotId, String accountNumber) {
		
		try {
			snapshotSupport.shareSnapshot(providerSnapshotId, accountNumber, false);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@LinkResource(value = CloudSnapshotVO.class, rel = "add")
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createSnapshot(CloudSnapshotVO createSnapshot) {
		
		CloudSnapshotVO cloudSnapshotVO = new CloudSnapshotVO();
		SnapshotVO snapshotVO = new SnapshotVO();
		List<SnapshotVO> snapshotVOList = new LinkedList<SnapshotVO>();
		
		String providerSnapshotId = null;
		String ofVolume = createSnapshot.getSnapshotVOList().get(0).getVolumeId();
		String description = createSnapshot.getSnapshotVOList().get(0).getDescription();
		
		try {
			providerSnapshotId = snapshotSupport.create(ofVolume, description);
			snapshotVO = convertSnapshot(snapshotSupport.getSnapshot(providerSnapshotId));
			snapshotVOList.add(snapshotVO);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudSnapshotVO.setProviderSnapshotId(snapshotVO.getProviderSnapshotId());
		cloudSnapshotVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudSnapshotVO.setCloudName(provider.getCloudName());
		cloudSnapshotVO.setCloudProvider(provider.getProviderName());
		cloudSnapshotVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudSnapshotVO.setSnapshotMethod("createSnapshot");
		cloudSnapshotVO.setSnapshotVOList(snapshotVOList);
		
		return Response.created(URI.create("/snapshot/" + snapshotVO.getProviderSnapshotId())).build();
	
	}
	
	@LinkResource(value = CloudSnapshotVO.class, rel = "remove")
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
		
		snapshotVO.setSnapshotId("tbd");
		snapshotVO.setEncrypted(false);
		
		/**
		 * todo, removable depending on 
		 * 1) owned by me, 
		 * 2) is not used by any existing machineImage (template) 
		 * */
		snapshotVO.setRemovable(false);
		
		/** TODO, tbd */
		snapshotVO.setBudget(0);
		
		snapshotVO.setProviderSnapshotId(snapshot.getProviderSnapshotId());
		snapshotVO.setName(snapshot.getName());
		snapshotVO.setDescription(snapshot.getDescription());
		snapshotVO.setSizeInGb(snapshot.getSizeInGb());
		snapshotVO.setSnapshotTimestamp(snapshot.getSnapshotTimestamp());
		snapshotVO.setCurrentState(snapshot.getCurrentState());
		snapshotVO.setProgress(snapshot.getProgress());
		
		/** TODO, tbd */
		snapshotVO.setSharable(true);
		
		boolean isPublic = false;
		String shareProviderAccounts = null;
		try {
			isPublic = snapshotSupport.isPublic(snapshot.getProviderSnapshotId());
			shareProviderAccounts = snapshotSupport.listShares(snapshot.getProviderSnapshotId()).toString();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		snapshotVO.setPublic(isPublic);
		snapshotVO.setShareProviderAccounts(shareProviderAccounts);
		snapshotVO.setProviderOwnerId(snapshot.getOwner());
		snapshotVO.setVolumeId(snapshot.getVolumeId());
		
		return snapshotVO;
	}

}
