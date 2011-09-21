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
package org.binbin.skywang.domain;

import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.MachineImageState;
import org.dasein.cloud.compute.MachineImageType;
import org.dasein.cloud.compute.Platform;

@XmlType(propOrder ={"providermachineImageId", "name", "description", "machineImageType", 
		"public", "architecture", "platform", "creationTimestamp", "providerOwnerId", 
		"agentName", "software"})
public class MachineImageVO {
	
	private String				machineImageId;
	private MachineImageState	status;
	private boolean				removable;
	private boolean				sharable;
	private	int					budget;
	
	private String				providermachineImageId;
	private String				name;
	private String				description;
	private MachineImageType	machineImageType;
	private boolean				isPublic;
	private Architecture		architecture;
	private Platform			platform;
	private Date				creationTimestamp;
	private	String				providerOwnerId;
	private	String				agentName;
	private String				software;
    
    public MachineImageVO() { }

	public MachineImageVO(String machineImageId, MachineImageState status,
			boolean removable, boolean sharable, int budget,
			String providermachineImageId, String name, String description,
			MachineImageType machineImageType, boolean isPublic,
			Architecture architecture, Platform platform,
			Date creationTimestamp, String providerOwnerId, String agentName,
			String software) {
		super();
		this.machineImageId = machineImageId;
		this.status = status;
		this.removable = removable;
		this.sharable = sharable;
		this.budget = budget;
		this.providermachineImageId = providermachineImageId;
		this.name = name;
		this.description = description;
		this.machineImageType = machineImageType;
		this.isPublic = isPublic;
		this.architecture = architecture;
		this.platform = platform;
		this.creationTimestamp = creationTimestamp;
		this.providerOwnerId = providerOwnerId;
		this.agentName = agentName;
		this.software = software;
	}

	@XmlAttribute
	public String getMachineImageId() {
		return machineImageId;
	}

	@XmlAttribute
	public MachineImageState getStatus() {
		return status;
	}

	@XmlAttribute
	public boolean isRemovable() {
		return removable;
	}

	@XmlAttribute
	public boolean isSharable() {
		return sharable;
	}

	@XmlAttribute
	public int getBudget() {
		return budget;
	}

	@XmlElement
	public String getProvidermachineImageId() {
		return providermachineImageId;
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
	public MachineImageType getMachineImageType() {
		return machineImageType;
	}

	@XmlElement
	public boolean isPublic() {
		return isPublic;
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
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	@XmlElement
	public String getProviderOwnerId() {
		return providerOwnerId;
	}

	@XmlElement
	public String getAgentName() {
		return agentName;
	}

	@XmlElement
	public String getSoftware() {
		return software;
	}

	public void setMachineImageId(String machineImageId) {
		this.machineImageId = machineImageId;
	}

	public void setStatus(MachineImageState status) {
		this.status = status;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setProvidermachineImageId(String providermachineImageId) {
		this.providermachineImageId = providermachineImageId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMachineImageType(MachineImageType machineImageType) {
		this.machineImageType = machineImageType;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public void setProviderOwnerId(String providerOwnerId) {
		this.providerOwnerId = providerOwnerId;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void setSoftware(String software) {
		this.software = software;
	}



    
}
