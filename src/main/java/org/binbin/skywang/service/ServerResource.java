package org.binbin.skywang.service;


import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import org.binbin.skywang.domain.CloudMachineImageVO;
import org.binbin.skywang.domain.CloudServerVO;
import org.binbin.skywang.domain.MachineImageVO;
import org.binbin.skywang.domain.ServerVO;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.VirtualMachine;
import org.dasein.cloud.compute.VirtualMachineSupport;

@Path("/server")
public class ServerResource extends BaseCloudService {
	
	private Logger logger = Logger.getLogger(ServerResource.class);
	
	private static VirtualMachineSupport serverSupport = null;
	
	public ServerResource(String cloudId) {
		super(cloudId);
		serverSupport = provider.getComputeServices().getVirtualMachineSupport();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudServerVO listServer() {
		
		CloudServerVO cloudServerVO = new CloudServerVO();
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		Iterable<VirtualMachine> server = null;
		
		try {
			server = serverSupport.listVirtualMachines();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(VirtualMachine tmpServer : server) {
			serverVOList.add(convertServer(tmpServer));
		}
		
		cloudServerVO.setServerMethod("listServer");
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);

		return cloudServerVO;
	}
	
	@GET
	@Path("{providerServerId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CloudServerVO getServer(@PathParam("providerServerId") String providerServerId) {
		
		CloudServerVO cloudServerVO = new CloudServerVO();
		List<ServerVO> serverVOList = new LinkedList<ServerVO>();
		
		VirtualMachine server = null;
		
		try {
			server = serverSupport.getVirtualMachine(providerServerId);
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serverVOList.add(convertServer(server));
		
		cloudServerVO.setServerMethod("getServer");
		cloudServerVO.setCloudProvider(provider.getProviderName());
		cloudServerVO.setCloudName(provider.getCloudName());
		cloudServerVO.setCloudAccountNumber(provider.getContext().getAccountNumber());
		cloudServerVO.setCloudRegionId(provider.getContext().getRegionId());
		cloudServerVO.setServerVOList(serverVOList);
		
		return cloudServerVO;
		
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
		
		return serverVO;
		
	}

}
