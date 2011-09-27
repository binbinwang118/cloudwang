/**
 * Copyright (C) 2009-2011 enStratusNetworks LLC
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

package org.binbin.skywang.domain;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.Platform;
import org.dasein.cloud.compute.VirtualMachineProduct;
import org.dasein.cloud.compute.VmState;

@XmlType(propOrder ={"providerServerId", "name","description", "providerOwnerId", "architecture", "platform", 
		"product", "creationTimestamp", "clonable", "pausable", "rebootable", "removable", "lastBootTimestamp", 
		"lastPauseTimestamp", "terminationTimestamp",  "providerMachineImageId",  "publicDnsAddress",  
		"publicIpAddresses",  "privateDnsAddress",  "privateIpAddresses",  "providerAssignedIpAddressId",  
		"providerVlanId",  "rootPassword",  "rootUser", "tags"})
public class ServerVO {
	
	private String				  serverId;
	private boolean				  analytics;
    private boolean               imagable;
    private boolean               persistent;
    private VmState               currentState;
	private int					  budget;
	
    private String                providerServerId;
    private String                name;
    private String                description;
    private String                providerOwnerId;
    private Architecture          architecture;
    private Platform              platform;
    private VirtualMachineProduct product;
    private long                  creationTimestamp;
    private boolean               clonable;
    private boolean               pausable;   
    private boolean               rebootable;
    private boolean				  removable;
    private long                  lastBootTimestamp;
    private long                  lastPauseTimestamp;
    private long                  terminationTimestamp;
    private String                providerMachineImageId;
    private String                publicDnsAddress;
    public String[]               publicIpAddresses;
    private String                privateDnsAddress;
    private String[]              privateIpAddresses;
    private String                providerAssignedIpAddressId;
    private String                providerVlanId;
    private String                rootPassword;
    private String                rootUser;
    private Map<String,String>    tags;   
    
	public ServerVO() { }

	public ServerVO(String serverId, boolean analytics, boolean imagable,
			boolean persistent, VmState currentState, int budget,
			String providerServerId, String name, String description,
			String providerOwnerId, Architecture architecture,
			Platform platform, VirtualMachineProduct product,
			long creationTimestamp, boolean clonable, boolean pausable,
			boolean rebootable, boolean removable, long lastBootTimestamp,
			long lastPauseTimestamp, long terminationTimestamp,
			String providerMachineImageId, String publicDnsAddress,
			String[] publicIpAddresses, String privateDnsAddress,
			String[] privateIpAddresses, String providerAssignedIpAddressId,
			String providerVlanId, String rootPassword, String rootUser,
			Map<String, String> tags) {
		super();
		this.serverId = serverId;
		this.analytics = analytics;
		this.imagable = imagable;
		this.persistent = persistent;
		this.currentState = currentState;
		this.budget = budget;
		this.providerServerId = providerServerId;
		this.name = name;
		this.description = description;
		this.providerOwnerId = providerOwnerId;
		this.architecture = architecture;
		this.platform = platform;
		this.product = product;
		this.creationTimestamp = creationTimestamp;
		this.clonable = clonable;
		this.pausable = pausable;
		this.rebootable = rebootable;
		this.removable = removable;
		this.lastBootTimestamp = lastBootTimestamp;
		this.lastPauseTimestamp = lastPauseTimestamp;
		this.terminationTimestamp = terminationTimestamp;
		this.providerMachineImageId = providerMachineImageId;
		this.publicDnsAddress = publicDnsAddress;
		this.publicIpAddresses = publicIpAddresses;
		this.privateDnsAddress = privateDnsAddress;
		this.privateIpAddresses = privateIpAddresses;
		this.providerAssignedIpAddressId = providerAssignedIpAddressId;
		this.providerVlanId = providerVlanId;
		this.rootPassword = rootPassword;
		this.rootUser = rootUser;
		this.tags = tags;
	}

	@XmlAttribute
	public String getServerId() {
		return serverId;
	}

	@XmlAttribute
	public boolean isAnalytics() {
		return analytics;
	}

	@XmlAttribute
	public boolean isImagable() {
		return imagable;
	}

	@XmlAttribute
	public boolean isPersistent() {
		return persistent;
	}

	@XmlAttribute
	public VmState getCurrentState() {
		return currentState;
	}

	@XmlAttribute
	public int getBudget() {
		return budget;
	}

	@XmlElement
	public String getProviderServerId() {
		return providerServerId;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	@XmlElement
	public String getProviderOwnerId() {
		return providerOwnerId;
	}

	@XmlElement
	public Architecture getArchitecture() {
		return architecture;
	}

	@XmlElement
	public Platform getPlatform() {
		return platform;
	}

	@XmlElement
	public VirtualMachineProduct getProduct() {
		return product;
	}

	@XmlElement
	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	@XmlElement
	public boolean isClonable() {
		return clonable;
	}

	@XmlElement
	public boolean isPausable() {
		return pausable;
	}

	@XmlElement
	public boolean isRebootable() {
		return rebootable;
	}

	@XmlElement
	public boolean isRemovable() {
		return removable;
	}

	@XmlElement
	public long getLastBootTimestamp() {
		return lastBootTimestamp;
	}

	@XmlElement
	public long getLastPauseTimestamp() {
		return lastPauseTimestamp;
	}

	@XmlElement
	public long getTerminationTimestamp() {
		return terminationTimestamp;
	}

	@XmlElement
	public String getProviderMachineImageId() {
		return providerMachineImageId;
	}

	@XmlElement
	public String getPublicDnsAddress() {
		return publicDnsAddress;
	}

	@XmlElement
	public String[] getPublicIpAddresses() {
		return publicIpAddresses;
	}

	@XmlElement
	public String getPrivateDnsAddress() {
		return privateDnsAddress;
	}

	@XmlElement
	public String[] getPrivateIpAddresses() {
		return privateIpAddresses;
	}

	@XmlElement
	public String getProviderAssignedIpAddressId() {
		return providerAssignedIpAddressId;
	}

	@XmlElement
	public String getProviderVlanId() {
		return providerVlanId;
	}

	@XmlElement
	public String getRootPassword() {
		return rootPassword;
	}

	@XmlElement
	public String getRootUser() {
		return rootUser;
	}

	@XmlElement
	public Map<String, String> getTags() {
		return tags;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setAnalytics(boolean analytics) {
		this.analytics = analytics;
	}

	public void setImagable(boolean imagable) {
		this.imagable = imagable;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	public void setCurrentState(VmState currentState) {
		this.currentState = currentState;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setProviderServerId(String providerServerId) {
		this.providerServerId = providerServerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setProviderOwnerId(String providerOwnerId) {
		this.providerOwnerId = providerOwnerId;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public void setProduct(VirtualMachineProduct product) {
		this.product = product;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public void setClonable(boolean clonable) {
		this.clonable = clonable;
	}

	public void setPausable(boolean pausable) {
		this.pausable = pausable;
	}

	public void setRebootable(boolean rebootable) {
		this.rebootable = rebootable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public void setLastBootTimestamp(long lastBootTimestamp) {
		this.lastBootTimestamp = lastBootTimestamp;
	}

	public void setLastPauseTimestamp(long lastPauseTimestamp) {
		this.lastPauseTimestamp = lastPauseTimestamp;
	}

	public void setTerminationTimestamp(long terminationTimestamp) {
		this.terminationTimestamp = terminationTimestamp;
	}

	public void setProviderMachineImageId(String providerMachineImageId) {
		this.providerMachineImageId = providerMachineImageId;
	}

	public void setPublicDnsAddress(String publicDnsAddress) {
		this.publicDnsAddress = publicDnsAddress;
	}

	public void setPublicIpAddresses(String[] publicIpAddresses) {
		this.publicIpAddresses = publicIpAddresses;
	}

	public void setPrivateDnsAddress(String privateDnsAddress) {
		this.privateDnsAddress = privateDnsAddress;
	}

	public void setPrivateIpAddresses(String[] privateIpAddresses) {
		this.privateIpAddresses = privateIpAddresses;
	}

	public void setProviderAssignedIpAddressId(String providerAssignedIpAddressId) {
		this.providerAssignedIpAddressId = providerAssignedIpAddressId;
	}

	public void setProviderVlanId(String providerVlanId) {
		this.providerVlanId = providerVlanId;
	}

	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}

	public void setRootUser(String rootUser) {
		this.rootUser = rootUser;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
 









}
