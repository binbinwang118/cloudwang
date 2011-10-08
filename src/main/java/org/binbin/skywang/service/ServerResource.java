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

import org.binbin.skywang.domain.CloudServerVO;
import org.binbin.skywang.domain.ServerVO;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.VirtualMachine;
import org.dasein.cloud.compute.VirtualMachineProduct;
import org.dasein.cloud.compute.VirtualMachineSupport;
import org.dasein.cloud.compute.VmStatistics;

import org.jboss.logging.Logger;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

@Path("/server")
public class ServerResource extends BaseCloudService {
	
	private Logger logger = Logger.getLogger(ServerResource.class);
	
	private static VirtualMachineSupport serverSupport = null;
	
	public ServerResource(String cloudId) {
		super(cloudId);
		serverSupport = provider.getComputeServices().getVirtualMachineSupport();
	}
	
	@AddLinks
	@LinkResource(value = CloudServerVO.class, rel = "list")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listServer() {
		
		CloudServerVO cloudServerVO = new CloudServerVO();
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		Iterable<VirtualMachine> server = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			server = serverSupport.listVirtualMachines();
			
			if (server == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
			
			for(VirtualMachine tmpServer : server) {
				serverVOList.add(convertServer(tmpServer));
			}
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudServerVO.setProviderServerId("providerServerId");
		cloudServerVO.setServerMethod("listServer");
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);

		builder.status(Response.Status.OK);
		builder.entity(cloudServerVO);
		Response response = builder.build();
		return response;
	}
	
	@AddLinks
	@LinkResource(value = CloudServerVO.class, rel = "self")
	@GET
	@Path("{providerServerId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServer(@PathParam("providerServerId") String providerServerId) {
		
		CloudServerVO cloudServerVO = new CloudServerVO();
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		
		VirtualMachine server = null;
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		try {
			server = serverSupport.getVirtualMachine(providerServerId);
			
			if (server == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			} else {
				serverVOList.add(convertServer(server));
			}
			
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudServerVO.setProviderServerId(providerServerId);
		cloudServerVO.setServerMethod("getServer");
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);
		
		builder.status(Response.Status.OK);
		builder.entity(cloudServerVO);
		Response response = builder.build();
		return response;
		
	}
	
	@LinkResource(value = CloudServerVO.class, rel = "HEAD Server")
	@HEAD
	@Path("{providerServerId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServereHead(@PathParam("providerServerId") String providerServerId, @Context UriInfo info) {
		
		VirtualMachine server = null;
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		try {
			server = serverSupport.getVirtualMachine(providerServerId);
			
			if (server == null) {
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
	
	@LinkResource(value = CloudServerVO.class, rel = "HEAD ServerList")
	@HEAD
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServerListHead(@Context UriInfo info) {
		
		List<VirtualMachine> server = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		try {
			server = (List<VirtualMachine>) serverSupport.listVirtualMachines();
			
			if (server == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				int serverNumber = server.size();
				builder.header("Server Number", serverNumber);
				builder.status(Response.Status.OK);
				response = builder.build();
			}
		} catch (InternalException e) {
			e.printStackTrace();
		} catch (CloudException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@LinkResource(value = CloudServerVO.class, rel = "GET ServerConsole")
	@GET
	@Path("{providerServerId}/console")
	@Produces({MediaType.TEXT_PLAIN})
	public Response getServerOutput(@PathParam("providerServerId") String providerServerId) {
		
		String output = null;
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		Response response 			= null;
		
		try {
			output = serverSupport.getConsoleOutput(providerServerId);
			
			if (output == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				response = builder.build();
				throw new WebApplicationException(response);
			} else {
				builder.status(Response.Status.OK);
				builder.entity(output);
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
	
	@LinkResource(value = CloudServerVO.class, rel = "GET ServerMetrics")
	@GET
	@Path("{providerServerId}/metrics")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServerMetrics(@PathParam("providerServerId") String providerServerId, @Context UriInfo info) {
		
		CloudServerVO cloudServerVO = new CloudServerVO();
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		VirtualMachine server = null;
		ServerVO serverVO = null;
		List <VmStatistics> vmStatistics = new LinkedList<VmStatistics>();
		long from, to;
		String period = null;
		
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		
		if(info.getQueryParameters().containsKey("period")) {
			period = info.getQueryParameters().getFirst("period");
		}
		
		try {
			server = serverSupport.getVirtualMachine(providerServerId);
		} catch (InternalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CloudException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(info.getQueryParameters().containsKey("from")) {
			from = Long.valueOf(info.getQueryParameters().getFirst("from"));
		} else {
//			from = server.getCreationTimestamp();
			from = System.currentTimeMillis() - 60000L;
		}
		
		if(info.getQueryParameters().containsKey("to")) {
			to = Long.valueOf(info.getQueryParameters().getFirst("to"));
		} else {
			to = System.currentTimeMillis();
		}
		
		try {
			if(period != null && period.equals("yes")) {
				vmStatistics = (List<VmStatistics>) serverSupport.getVMStatisticsForPeriod(providerServerId, from, to);
			} else {
				VmStatistics tempvmStatistic = serverSupport.getVMStatistics(providerServerId, from, to);
				vmStatistics.add(tempvmStatistic);
			}
			
			if (vmStatistics == null) {
				builder.type(MediaType.TEXT_PLAIN);
				builder.entity("The requested resource is not found!");
				builder.status(Response.Status.NOT_FOUND);
				Response response = builder.build();
				throw new WebApplicationException(response);
			}
			
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serverVO = convertServer(server);
		serverVO.setVmMetrics(vmStatistics);
		serverVOList.add(serverVO);
		
		cloudServerVO.setProviderServerId(providerServerId);
		cloudServerVO.setServerMethod("getServerMetrics");
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);
		
		builder.status(Response.Status.OK);
		builder.entity(cloudServerVO);
		Response response = builder.build();
		return response;
		
	}
	
	@LinkResource(value = CloudServerVO.class, rel = "update")
	@PUT
	@Path("{providerServerId}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void putMachineImage(@PathParam("providerServerId") String providerServerId, CloudServerVO putServer) {
	
		CloudServerVO cloudServerVO = new CloudServerVO();
		ServerVO serverVO = null;
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		VirtualMachine virtualMachine = null;

		String serverId = providerServerId;
		String serverMethod = putServer.getServerMethod();
		
		if(serverMethod.equals("enableServerMetrics")) {
			enableServerMetrics(serverId);
		}
		else if(serverMethod.equals("disableServerMetrics")) {
			disableServerMetrics(serverId);
		}
		else if(serverMethod.equals("startServer")) {
			startServer(serverId);
		}
		else if(serverMethod.equals("stopServer")) {
			stopServer(serverId);
		}
		else if(serverMethod.equals("rebootServer")) {
			reboodServer(serverId);
		} else {
			logger.debug("Un-Supported ServerResource PUT Method!");
		}
		
		try {
			virtualMachine = serverSupport.getVirtualMachine(serverId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverVO = convertServer(virtualMachine);
		serverVOList.add(serverVO);
		
		cloudServerVO.setProviderServerId(providerServerId);
		cloudServerVO.setServerMethod(serverMethod);
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);
		
	}
	
	private void enableServerMetrics(String serverId) {
		
		try {
			serverSupport.enableAnalytics(serverId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void disableServerMetrics(String serverId) {
		
		try {
			serverSupport.disableAnalytics(serverId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startServer(String serverId) {
		
		try {
			serverSupport.boot(serverId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void stopServer(String serverId) {
		
		try {
			serverSupport.pause(serverId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void reboodServer(String serverId) {
		
		try {
			serverSupport.reboot(serverId);
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	@LinkResource(value = CloudServerVO.class, rel = "add")
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createServer(CloudServerVO createServerVO) {
		
		CloudServerVO cloudServerVO = new CloudServerVO();
		ServerVO serverVO = new ServerVO();
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		VirtualMachine server = null;
		
		String imageId = createServerVO.getServerVOList().get(0).getProviderMachineImageId();
		VirtualMachineProduct product = createServerVO.getServerVOList().get(0).getProduct();
		String inZoneId = createServerVO.getServerVOList().get(0).getProviderZoneId();
		String name = createServerVO.getServerVOList().get(0).getName();
		String description = createServerVO.getServerVOList().get(0).getDescription();
		String keyPair = createServerVO.getServerVOList().get(0).getKeyPairName();
		
		/** TODO: tbd */
		String inVlanId = null;
		boolean asImageSandbox = false;
		boolean withMonitoring = true;
		if(createServerVO.getServerVOList().get(0).getMonitoring().equals("enabled")) {
			withMonitoring = true;
		}
		else if(createServerVO.getServerVOList().get(0).getMonitoring().equals("disabled")) {
			withMonitoring = false;
		}
		
		String fireWall = createServerVO.getServerVOList().get(0).getSecurityGroup();
//		String tags = createServerVO.getServerVOList().get(0).getTags();
		
		try {
			server = serverSupport.launch(imageId, product, inZoneId, name, description, keyPair, inVlanId, withMonitoring, asImageSandbox, fireWall);
			serverVO = convertServer(server); 
			serverVO.setKeyPairName(keyPair);
			serverVOList.add(serverVO);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cloudServerVO.setProviderServerId(server.getProviderVirtualMachineId());
		cloudServerVO.setServerMethod("createServer");
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);
		
		return Response.created(URI.create("/server/" + serverVO.getProviderServerId())).build();
		
	}
	
	@LinkResource(value = CloudServerVO.class, rel = "remove")
	@DELETE
	@Path("{providerServerId}")
	public void removeServer(@PathParam("providerServerId") String providerServerId) {
		
		try {
			serverSupport.terminate(providerServerId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ServerVO convertServer(VirtualMachine server) {
		
		ServerVO serverVO = new ServerVO();

		serverVO.setServerId(null);
		serverVO.setImagable(server.isImagable());
		serverVO.setPersistent(server.isPersistent());
		serverVO.setCurrentState(server.getCurrentState());
		serverVO.setBudget(0);
		
		serverVO.setProviderServerId(server.getProviderVirtualMachineId());
		serverVO.setName(server.getName());
		serverVO.setDescription(server.getDescription());
		serverVO.setProviderOwnerId(server.getProviderOwnerId());
		serverVO.setArchitecture(server.getArchitecture());
		serverVO.setPlatform(server.getPlatform());
		serverVO.setProduct(server.getProduct());
		serverVO.setMonitoring(server.getAnalytics());
		serverVO.setCreationTimestamp(server.getCreationTimestamp());
		serverVO.setLastBootTimestamp(server.getLastBootTimestamp());
		serverVO.setLastPauseTimestamp(server.getLastPauseTimestamp());
		serverVO.setTerminationTimestamp(server.getTerminationTimestamp());
		serverVO.setClonable(server.isClonable());
		serverVO.setPausable(server.isPausable());
		serverVO.setRebootable(server.isRebootable());
		serverVO.setRemovable(!(server.isPersistent()));
		serverVO.setProviderMachineImageId(server.getProviderMachineImageId());
		serverVO.setPublicDnsAddress(server.getPublicDnsAddress());
		
		if(server.getPublicIpAddresses() != null) {
			serverVO.setPublicIpAddresses(server.getPublicIpAddresses()[0]);
		} else {
			serverVO.setPublicIpAddresses(null);
		}
		
		serverVO.setPrivateDnsAddress(server.getPrivateDnsAddress());
		if(server.getPrivateIpAddresses() != null) {
			serverVO.setPrivateIpAddresses(server.getPrivateIpAddresses()[0]);
		} else {
			serverVO.setPrivateIpAddresses(null);
		}
		
		serverVO.setProviderAssignedIpAddressId(server.getProviderAssignedIpAddressId());
		serverVO.setProviderVlanId(server.getProviderVlanId());
		serverVO.setRootPassword(server.getRootPassword());
		serverVO.setRootUser(server.getRootUser());
		serverVO.setTags(server.getTags().toString());
		serverVO.setVmMetrics(server.getVmStatistics());
		
		String securityGroup = null;
		try {
			securityGroup = serverSupport.listFirewalls(server.getProviderVirtualMachineId()).toString();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverVO.setSecurityGroup(securityGroup);
		
		serverVO.setProviderZoneId(server.getProviderDataCenterId());
		serverVO.setKeyPairName(server.getKeyPairName());
		
		return serverVO;
		
	}

}
