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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.dasein.cloud.compute.VolumeState;

@XmlRootElement(name = "volume")
@XmlType(propOrder = {"customer", "owningAccount", "owningUser", "owningGroups", "volumeName", "description", "sizeInGb", "volumeStatus", 
		"creationTimestamp", "snapshotId", "serverId", "deviceId", "lable",  "cloud", "providerRegionId", "dataCenterId", "array"})
public class VolumeVO{

	private int			volumeId;
	private boolean 	available;
	private int			budget;		// tbd. did not use
	private boolean		encrypted;	// did not use
	private boolean		removable;
    private int         sizeInGb;
	private VolumeState volumeStatus;
	private String		array;		// tbd. did not use
    private long        creationTimestamp;
    private String		description;
    private String      deviceId;
    private VolumeLable	lable;		// tbd. did not use
    private String      volumeName;
	private String		cloud;
    private String      dataCenterId;
    private String      providerRegionId;
    private String      serverId;
    private String      snapshotId;   
    private int			customer;		// tbd. did not use
    private int			owningAccount;	// tbd. did not use
    private int			owningGroups;	// tbd. did not use
    private String		owningUser;		// tbd. did not use
	
	public VolumeVO() {}
	
    public VolumeVO(int volumeId, boolean available, int budget,
			boolean encrypted, boolean removable, int sizeInGb,
			VolumeState volumeStatus, String array, long creationTimestamp,
			String description, String deviceId, VolumeLable lable,
			String volumeName, String cloud, String dataCenterId,
			String providerRegionId, String serverId, String snapshotId,
			int customer, int owningAccount, int owningGroups, String owningUser) {
		super();
		this.volumeId = volumeId;
		this.available = available;
		this.budget = budget;
		this.encrypted = encrypted;
		this.removable = removable;
		this.sizeInGb = sizeInGb;
		this.volumeStatus = volumeStatus;
		this.array = array;
		this.creationTimestamp = creationTimestamp;
		this.description = description;
		this.deviceId = deviceId;
		this.lable = lable;
		this.volumeName = volumeName;
		this.cloud = cloud;
		this.dataCenterId = dataCenterId;
		this.providerRegionId = providerRegionId;
		this.serverId = serverId;
		this.snapshotId = snapshotId;
		this.customer = customer;
		this.owningAccount = owningAccount;
		this.owningGroups = owningGroups;
		this.owningUser = owningUser;
	}
	
	@XmlAttribute
	public int getVolumeId() {
		return volumeId;
	}

	@XmlAttribute
	public boolean isAvailable() {
		return available;
	}

	@XmlAttribute
	public int getBudget() {
		return budget;
	}

	@XmlAttribute
	public boolean isEncrypted() {
		return encrypted;
	}

	@XmlAttribute
	public boolean isRemovable() {
		return removable;
	}

	@XmlElement
	public int getSizeInGb() {
		return sizeInGb;
	}

	@XmlElement
	public VolumeState getVolumeStatus() {
		return volumeStatus;
	}

	@XmlElement
	public String getArray() {
		return array;
	}

	@XmlElement
	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	@XmlElement
	public String getDeviceId() {
		return deviceId;
	}

	@XmlElement
	public VolumeLable getLable() {
		return lable;
	}

	@XmlElement
	public String getVolumeName() {
		return volumeName;
	}

	@XmlElement
	public String getCloud() {
		return cloud;
	}

	@XmlElement
	public String getDataCenterId() {
		return dataCenterId;
	}

	@XmlElement
	public String getProviderRegionId() {
		return providerRegionId;
	}

	@XmlElement
	public String getServerId() {
		return serverId;
	}

	@XmlElement
	public String getSnapshotId() {
		return snapshotId;
	}

	@XmlElement
	public int getCustomer() {
		return customer;
	}

	@XmlElement
	public int getOwningAccount() {
		return owningAccount;
	}

	@XmlElement
	public int getOwningGroups() {
		return owningGroups;
	}

	@XmlElement
	public String getOwningUser() {
		return owningUser;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public void setSizeInGb(int sizeInGb) {
		this.sizeInGb = sizeInGb;
	}

	public void setVolumeId(int volumeId) {
		this.volumeId = volumeId;
	}

	public void setVolumeStatus(VolumeState volumeStatus) {
		this.volumeStatus = volumeStatus;
	}

	public void setArray(String array) {
		this.array = array;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setLable(VolumeLable lable) {
		this.lable = lable;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public void setProviderRegionId(String providerRegionId) {
		this.providerRegionId = providerRegionId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setCustomer(int customer) {
		this.customer = customer;
	}

	public void setOwningAccount(int owningAccount) {
		this.owningAccount = owningAccount;
	}

	public void setOwningGroups(int owningGroups) {
		this.owningGroups = owningGroups;
	}

	public void setOwningUser(String owningUser) {
		this.owningUser = owningUser;
	}
	
}
