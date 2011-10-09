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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.binbin.skywang.domain.CloudFirewallVO;
import org.binbin.skywang.domain.FirewallRuleVO;
import org.binbin.skywang.domain.FirewallVO;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.network.Firewall;
import org.dasein.cloud.network.FirewallRule;
import org.dasein.cloud.network.FirewallSupport;
import org.dasein.cloud.network.Protocol;

import org.jboss.logging.Logger;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

@Path("/firewall")
public class FirewallResource extends BaseCloudService {

	private Logger logger = Logger.getLogger(FirewallResource.class);
	
	private static FirewallSupport firewallSupport = null;
	
	public FirewallResource(String cloudId) {
		super(cloudId);
		firewallSupport = provider.getNetworkServices().getFirewallSupport();
	}
	
	@AddLinks
	@LinkResource(value = CloudFirewallVO.class, rel = "list")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listFirewall() {
		
		CloudFirewallVO cloudFirewallVO = new CloudFirewallVO();
		List<FirewallVO> firewallVOList = new LinkedList<FirewallVO>();
		List<Firewall> firewall 		= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			firewall = (List<Firewall>) firewallSupport.list();
			
			if (firewall == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
			
			for(Firewall tmpFirewall : firewall) {
				firewallVOList.add(convertFirewall(tmpFirewall));
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudFirewallVO.setProviderFirewallId("providerFirewallId");
		cloudFirewallVO.setFirewallMethod("listFirewall");
		cloudFirewallVO.setCloudProvider(provider.getProviderName());
		cloudFirewallVO.setCloudName(provider.getCloudName());
		cloudFirewallVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudFirewallVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudFirewallVO.setFirewallVOList(firewallVOList);

		builder.status(Response.Status.OK);
		builder.entity(cloudFirewallVO);
		Response response = builder.build();
		
		return response;
	}
	
	@AddLinks
	@LinkResource(value = CloudFirewallVO.class, rel = "self")
	@GET
	@Path("{providerFirewallId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getFirewall(@PathParam("providerFirewallId") String providerFirewallId) {
		
		CloudFirewallVO cloudFirewallVO = new CloudFirewallVO();
		List<FirewallVO> firewallVOList = new LinkedList<FirewallVO>();
		Firewall firewall 				= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			firewall = firewallSupport.getFirewall(providerFirewallId);
			
			if (firewall == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			} else {
				firewallVOList.add(convertFirewall(firewall));
			}
			
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudFirewallVO.setProviderFirewallId(providerFirewallId);
		cloudFirewallVO.setFirewallMethod("getFirewall");
		cloudFirewallVO.setCloudProvider(provider.getProviderName());
		cloudFirewallVO.setCloudName(provider.getCloudName());
		cloudFirewallVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudFirewallVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudFirewallVO.setFirewallVOList(firewallVOList);

		builder.status(Response.Status.OK);
		builder.entity(cloudFirewallVO);
		Response response = builder.build();
		
		return response;
	}
	
	@LinkResource(value = CloudFirewallVO.class, rel = "HEAD Firewall")
	@HEAD
	@Path("{providerFirewallId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getFirewallHead(@PathParam("providerFirewallId") String providerFirewallId) {

		Firewall firewall 				= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		try {
			firewall = firewallSupport.getFirewall(providerFirewallId);
			
			if (firewall == null) {
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
	
	@LinkResource(value = CloudFirewallVO.class, rel = "HEAD FirewallList")
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getFirewallListHead() {
		
		List<Firewall> firewall 		= null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		try {
			firewall = (List<Firewall>) firewallSupport.list();
			
			if (firewall == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				int firewallNumber = firewall.size();
				builder.header("Firewall Number", firewallNumber);
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
	
	@LinkResource(value = CloudFirewallVO.class, rel = "update")
	@PUT
	@Path("{providerFirewallId}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response putMachineImage(@PathParam("providerFirewallId") String providerFirewallId, CloudFirewallVO putFirewall) {
		
		CloudFirewallVO cloudFirewallVO = new CloudFirewallVO();
		FirewallVO firewallVO = null;
		List<FirewallVO> firewallVOList = new LinkedList<FirewallVO>();
		Firewall firewall = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		FirewallRuleVO firewallRuleVO = putFirewall.getFirewallVOList().get(0).getFirewallRuleVOList().get(0);
		String cidr = firewallRuleVO.getCidr();
		Protocol protocol = firewallRuleVO.getProtocol();
		int startPort = firewallRuleVO.getStartPort();
		int endPort = firewallRuleVO.getEndPort();
		
		String firewallMethod =  putFirewall.getFirewallMethod();
		
		if(firewallMethod.equals("addFirewallRules")) {
			addFirewallRules(providerFirewallId, cidr, protocol, startPort, endPort);
		}
		else if(firewallMethod.equals("removeFirewallRules")) {
			removeFirewallRules(providerFirewallId, cidr, protocol, startPort, endPort);
		} else {
			builder.type(MediaType.TEXT_PLAIN);
			builder.entity("Un-supported Firewall PUT method!");
			builder.status(Response.Status.BAD_REQUEST);
			response = builder.build();
			throw new WebApplicationException(response);
		}
		
		try {
			firewall = firewallSupport.getFirewall(providerFirewallId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		firewallVO = convertFirewall(firewall);
		firewallVOList.add(firewallVO);
		
		cloudFirewallVO.setProviderFirewallId(providerFirewallId);
		cloudFirewallVO.setFirewallMethod(firewallMethod);
		cloudFirewallVO.setCloudProvider(provider.getProviderName());
		cloudFirewallVO.setCloudName(provider.getCloudName());
		cloudFirewallVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudFirewallVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudFirewallVO.setFirewallVOList(firewallVOList);

		builder.status(Response.Status.OK);
		builder.entity(cloudFirewallVO);
		response = builder.build();
		
		return response;
		
	}
	
	private void addFirewallRules(String providerFirewallId, String cidr, Protocol protocol, int startPort, int endPort) {
		
		try {
			firewallSupport.authorize(providerFirewallId, cidr, protocol, startPort, endPort);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeFirewallRules(String providerFirewallId, String cidr, Protocol protocol, int startPort, int endPort) {
		
		try {
			firewallSupport.revoke(providerFirewallId, cidr, protocol, startPort, endPort);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@LinkResource(value = CloudFirewallVO.class, rel = "add")
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createFirewall(CloudFirewallVO createFirewallVO) {
		
		CloudFirewallVO cloudFirewallVO = new CloudFirewallVO();
		FirewallVO firewallVO = new FirewallVO();
		List<FirewallVO> firewallVOList = new LinkedList<FirewallVO>();
		Firewall firewall = null;
		String firewallId = null;
		
		String name = createFirewallVO.getFirewallVOList().get(0).getName();
		String description = createFirewallVO.getFirewallVOList().get(0).getDescription();
		
		try {
			firewallId = firewallSupport.create(name, description);
			firewall = firewallSupport.getFirewall(firewallId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		firewallVO = convertFirewall(firewall);
		firewallVOList.add(firewallVO);
		
		cloudFirewallVO.setProviderFirewallId(firewallId);
		cloudFirewallVO.setFirewallMethod("createFirewall");
		cloudFirewallVO.setCloudProvider(provider.getProviderName());
		cloudFirewallVO.setCloudName(provider.getCloudName());
		cloudFirewallVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudFirewallVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudFirewallVO.setFirewallVOList(firewallVOList);

		return Response.created(URI.create("/firewall/" + firewallId)).build();
		
	}
	
	@LinkResource(value = CloudFirewallVO.class, rel = "remove")
	@DELETE
	@Path("{providerFirewallId}")
	public void removeFirewall(@PathParam("providerFirewallId") String providerFirewallId) {
		
		try {
			firewallSupport.delete(providerFirewallId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private FirewallVO convertFirewall(Firewall firewall) {
		
		FirewallVO firewallVO = new FirewallVO();
		
		firewallVO.setFirewallId("tbd");
		firewallVO.setBudget(0);
		firewallVO.setActive(firewall.isActive());
		firewallVO.setAvailable(firewall.isAvailable());
		
		
		firewallVO.setDescription(firewall.getDescription());
		firewallVO.setName(firewall.getName());
		firewallVO.setProviderFirewallId(firewall.getProviderFirewallId());
		firewallVO.setProviderVlanId(firewall.getProviderVlanId());
		
		List<FirewallRule> firewallRuleList = null;
		List <FirewallRuleVO> firewallRuleVOList = new LinkedList<FirewallRuleVO>();
		try {
			firewallRuleList = (List<FirewallRule>) firewallSupport.getRules(firewall.getProviderFirewallId());
			for(FirewallRule tmpFirewallRule : firewallRuleList) {
				firewallRuleVOList.add(convertFirewallRule(tmpFirewallRule));
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		firewallVO.setFirewallRuleVOList(firewallRuleVOList);
		
		return firewallVO;
	}
	
	private FirewallRuleVO convertFirewallRule(FirewallRule firewallRule) {
		
		FirewallRuleVO firewallRuleVO = new FirewallRuleVO();
		
		firewallRuleVO.setCidr(firewallRule.getCidr());
		firewallRuleVO.setDirection(firewallRule.getDirection());
		firewallRuleVO.setEndPort(firewallRule.getEndPort());
		firewallRuleVO.setProviderFirewallId(firewallRule.getFirewallId());
		firewallRuleVO.setPermission(firewallRule.getPermission());
		firewallRuleVO.setProtocol(firewallRule.getProtocol());
		firewallRuleVO.setProviderRuleId(firewallRule.getProviderRuleId());
		firewallRuleVO.setStartPort(firewallRule.getStartPort());
		
		return firewallRuleVO;
	}
	
}
