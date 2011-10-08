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

import org.dasein.cloud.compute.SnapshotState;

@XmlRootElement(name = "snapshot")
@XmlType(propOrder ={"providerSnapshotId", "name", "description", "sizeInGb", "snapshotTimestamp",
		"currentState", "progress", "sharable", "public", "shareProviderAccounts", "providerOwnerId", 
		"volumeId"})
public class SnapshotVO {
    
	private String				snapshotId;
	private boolean	     		encrypted;
	private boolean				removable;
	private	int					budget;
	
	private String       		providerSnapshotId;
	private String       	    name;
	private String      	    description;
	private int           		sizeInGb;
	private long        		snapshotTimestamp;
	private SnapshotState		currentState;
	private String       		progress;
	private boolean		 		sharable;
	private boolean			  	isPublic;
	private String				shareProviderAccounts;
	private String 				providerOwnerId;
	private String      		volumeId;
	
	/** todo, volume metrics */
//	private List <SnapshotMetrics>	  snapshotMetrics;
	
    public SnapshotVO() { }

	public SnapshotVO(String snapshotId, boolean encrypted, boolean removable,
			int budget, String providerSnapshotId, String name,
			String description, int sizeInGb, long snapshotTimestamp,
			SnapshotState currentState, String progress, boolean sharable,
			boolean isPublic, String shareProviderAccounts, String providerOwnerId,
			String volumeId) {
		super();
		this.snapshotId = snapshotId;
		this.encrypted = encrypted;
		this.removable = removable;
		this.budget = budget;
		this.providerSnapshotId = providerSnapshotId;
		this.name = name;
		this.description = description;
		this.sizeInGb = sizeInGb;
		this.snapshotTimestamp = snapshotTimestamp;
		this.currentState = currentState;
		this.progress = progress;
		this.sharable = sharable;
		this.isPublic = isPublic;
		this.shareProviderAccounts = shareProviderAccounts;
		this.providerOwnerId = providerOwnerId;
		this.volumeId = volumeId;
	}

	@XmlAttribute
	public String getSnapshotId() {
		return snapshotId;
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
	public String getProviderSnapshotId() {
		return providerSnapshotId;
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
	public int getSizeInGb() {
		return sizeInGb;
	}

	@XmlElement
	public long getSnapshotTimestamp() {
		return snapshotTimestamp;
	}

	@XmlElement
	public SnapshotState getCurrentState() {
		return currentState;
	}

	@XmlElement
	public String getProgress() {
		return progress;
	}

	@XmlElement
	public boolean isSharable() {
		return sharable;
	}

	@XmlElement
	public boolean isPublic() {
		return isPublic;
	}

	@XmlElement
	public String getShareProviderAccounts() {
		return shareProviderAccounts;
	}

	@XmlElement
	public String getProviderOwnerId() {
		return providerOwnerId;
	}

	@XmlElement
	public String getVolumeId() {
		return volumeId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
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

	public void setProviderSnapshotId(String providerSnapshotId) {
		this.providerSnapshotId = providerSnapshotId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSizeInGb(int sizeInGb) {
		this.sizeInGb = sizeInGb;
	}

	public void setSnapshotTimestamp(long snapshotTimestamp) {
		this.snapshotTimestamp = snapshotTimestamp;
	}

	public void setCurrentState(SnapshotState currentState) {
		this.currentState = currentState;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setShareProviderAccounts(String shareProviderAccounts) {
		this.shareProviderAccounts = shareProviderAccounts;
	}

	public void setProviderOwnerId(String providerOwnerId) {
		this.providerOwnerId = providerOwnerId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
    

}
