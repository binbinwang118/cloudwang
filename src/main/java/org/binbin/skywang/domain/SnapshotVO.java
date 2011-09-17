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
@XmlType(propOrder ={"customer", "owningAccount", "owningCloudAccountId", "owningCloudAccountNumber", 
		"owningUser", "owningGroups", "providerSnapshotId", "description", "publics", "volumeId", 
		"snapshotTimestamp", "name", "cloud", "providerId", "regionId", "progress", "label", "array"})
public class SnapshotVO {
    
	private boolean 	  available;
	private int			  budget;
	private boolean	      encrypted;
	private boolean		  sharable;
	private boolean		  removable;
	private int			  snapshotId;
	private int           sizeInGb;
    private SnapshotState currentState;
    
    private String 		  array;
    private String		  cloud;
    private String		  customer;
    private String        description;
    private SnapshotLable label;
    private String        name;
    private String        owningAccount;
    private int			  owningCloudAccountId;
    private String		  owningCloudAccountNumber;
    private String		  owningGroups;
    private String		  owningUser;
    private String 		  providerId;
    private boolean		  publics;
    private String        regionId;
    private long          snapshotTimestamp;
    private String        providerSnapshotId;
    private String        progress;
    private String        volumeId;
    
    public SnapshotVO() { }

	public SnapshotVO(boolean available, int budget, boolean encrypted,
			boolean sharable, boolean removable, int snapshotId, int sizeInGb,
			SnapshotState currentState, String array, String cloud,
			String customer, String description, SnapshotLable label,
			String name, String owningAccount, int owningCloudAccountId,
			String owningCloudAccountNumber, String owningGroups,
			String owningUser, String providerId, boolean publics,
			String regionId, long snapshotTimestamp, String providerSnapshotId,
			String progress, String volumeId) {
		super();
		this.available = available;
		this.budget = budget;
		this.encrypted = encrypted;
		this.sharable = sharable;
		this.removable = removable;
		this.snapshotId = snapshotId;
		this.sizeInGb = sizeInGb;
		this.currentState = currentState;
		this.array = array;
		this.cloud = cloud;
		this.customer = customer;
		this.description = description;
		this.label = label;
		this.name = name;
		this.owningAccount = owningAccount;
		this.owningCloudAccountId = owningCloudAccountId;
		this.owningCloudAccountNumber = owningCloudAccountNumber;
		this.owningGroups = owningGroups;
		this.owningUser = owningUser;
		this.providerId = providerId;
		this.publics = publics;
		this.regionId = regionId;
		this.snapshotTimestamp = snapshotTimestamp;
		this.providerSnapshotId = providerSnapshotId;
		this.progress = progress;
		this.volumeId = volumeId;
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
	public boolean isSharable() {
		return sharable;
	}

	@XmlAttribute
	public boolean isRemovable() {
		return removable;
	}

	@XmlAttribute
	public int getSnapshotId() {
		return snapshotId;
	}

	@XmlAttribute
	public int getSizeInGb() {
		return sizeInGb;
	}

	@XmlAttribute
	public SnapshotState getCurrentState() {
		return currentState;
	}

	@XmlElement
	public String getArray() {
		return array;
	}

	@XmlElement
	public String getCloud() {
		return cloud;
	}

	@XmlElement
	public String getCustomer() {
		return customer;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	@XmlElement
	public SnapshotLable getLabel() {
		return label;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	@XmlElement
	public String getOwningAccount() {
		return owningAccount;
	}

	@XmlElement
	public int getOwningCloudAccountId() {
		return owningCloudAccountId;
	}

	@XmlElement
	public String getOwningCloudAccountNumber() {
		return owningCloudAccountNumber;
	}

	@XmlElement
	public String getOwningGroups() {
		return owningGroups;
	}

	@XmlElement
	public String getOwningUser() {
		return owningUser;
	}

	@XmlElement
	public String getProviderId() {
		return providerId;
	}

	@XmlElement
	public boolean isPublics() {
		return publics;
	}

	@XmlElement
	public String getRegionId() {
		return regionId;
	}

	@XmlElement
	public long getSnapshotTimestamp() {
		return snapshotTimestamp;
	}

	@XmlElement
	public String getProviderSnapshotId() {
		return providerSnapshotId;
	}

	@XmlElement
	public String getProgress() {
		return progress;
	}

	@XmlElement
	public String getVolumeId() {
		return volumeId;
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

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public void setSnapshotId(int snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setSizeInGb(int sizeInGb) {
		this.sizeInGb = sizeInGb;
	}

	public void setCurrentState(SnapshotState currentState) {
		this.currentState = currentState;
	}

	public void setArray(String array) {
		this.array = array;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLabel(SnapshotLable label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwningAccount(String owningAccount) {
		this.owningAccount = owningAccount;
	}

	public void setOwningCloudAccountId(int owningCloudAccountId) {
		this.owningCloudAccountId = owningCloudAccountId;
	}

	public void setOwningCloudAccountNumber(String owningCloudAccountNumber) {
		this.owningCloudAccountNumber = owningCloudAccountNumber;
	}

	public void setOwningGroups(String owningGroups) {
		this.owningGroups = owningGroups;
	}

	public void setOwningUser(String owningUser) {
		this.owningUser = owningUser;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public void setPublics(boolean publics) {
		this.publics = publics;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public void setSnapshotTimestamp(long snapshotTimestamp) {
		this.snapshotTimestamp = snapshotTimestamp;
	}

	public void setProviderSnapshotId(String providerSnapshotId) {
		this.providerSnapshotId = providerSnapshotId;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
    
    

}
