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

@XmlRootElement(name = "volume")
@XmlType(propOrder = {"providerVolumeId", "volumeName", "sizeInGb", "creationTimestamp", "volumeStatus", 
		"description", "serverId", "deviceId", "zoneId", "snapshotId"})
public class VolumeVO{

	private String		volumeId;
	private boolean		encrypted;
	private boolean		removable;
	private int			budget;

	private String		providerVolumeId;
	private String      volumeName;
	private int         sizeInGb;
	private long        creationTimestamp;
	private String		volumeStatus;
	private String		description;
	private String      serverId;
	private String      deviceId;  
	private String      zoneId;
	private String      snapshotId;   
	
	/** todo, volume metrics */
//	private List <VolumeMetrics>	  volumeMetrics;
	
	public VolumeVO() {}

	public VolumeVO(String volumeId, boolean encrypted, boolean removable,
			int budget, String providerVolumeId, String volumeName,
			int sizeInGb, long creationTimestamp, String volumeStatus,
			String description, String serverId, String deviceId,
			String zoneId, String snapshotId) {
		super();
		this.volumeId = volumeId;
		this.encrypted = encrypted;
		this.removable = removable;
		this.budget = budget;
		this.providerVolumeId = providerVolumeId;
		this.volumeName = volumeName;
		this.sizeInGb = sizeInGb;
		this.creationTimestamp = creationTimestamp;
		this.volumeStatus = volumeStatus;
		this.description = description;
		this.serverId = serverId;
		this.deviceId = deviceId;
		this.zoneId = zoneId;
		this.snapshotId = snapshotId;
	}

	@XmlAttribute
	public String getVolumeId() {
		return volumeId;
	}

	@XmlAttribute
	public boolean isEncrypted() {
		return encrypted;
	}

	@XmlAttribute
	public boolean isRemovable() {
		return removable;
	}

	@XmlAttribute
	public int getBudget() {
		return budget;
	}

	@XmlElement
	public String getProviderVolumeId() {
		return providerVolumeId;
	}

	@XmlElement
	public String getVolumeName() {
		return volumeName;
	}

	@XmlElement
	public int getSizeInGb() {
		return sizeInGb;
	}

	@XmlElement
	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	@XmlElement
	public String getVolumeStatus() {
		return volumeStatus;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	@XmlElement
	public String getServerId() {
		return serverId;
	}

	@XmlElement
	public String getDeviceId() {
		return deviceId;
	}

	@XmlElement
	public String getZoneId() {
		return zoneId;
	}

	@XmlElement
	public String getSnapshotId() {
		return snapshotId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setProviderVolumeId(String providerVolumeId) {
		this.providerVolumeId = providerVolumeId;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public void setSizeInGb(int sizeInGb) {
		this.sizeInGb = sizeInGb;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public void setVolumeStatus(String volumeStatus) {
		this.volumeStatus = volumeStatus;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}
	
	
}
