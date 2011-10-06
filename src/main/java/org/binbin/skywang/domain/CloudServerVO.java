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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "cloudServer")
@XmlType(propOrder ={"serverMethod", "cloudProvider","cloudName","cloudAccountNumber",
		"cloudRegionId","serverVOList"})
public class CloudServerVO {
	
	private String serverMethod;
	private String cloudProvider;
	private String cloudName;
	private String cloudAccountNumber;
	private String cloudRegionId;
	private List<ServerVO> serverVOList;
	
	public CloudServerVO() {}

	public CloudServerVO(String serverMethod, String cloudProvider,
			String cloudName, String cloudAccountNumber, String cloudRegionId,
			List<ServerVO> serverVOList) {
		super();
		this.serverMethod = serverMethod;
		this.cloudProvider = cloudProvider;
		this.cloudName = cloudName;
		this.cloudAccountNumber = cloudAccountNumber;
		this.cloudRegionId = cloudRegionId;
		this.serverVOList = serverVOList;
	}

	@XmlElement
	public String getServerMethod() {
		return serverMethod;
	}

	@XmlElement
	public String getCloudProvider() {
		return cloudProvider;
	}

	@XmlElement
	public String getCloudName() {
		return cloudName;
	}

	@XmlElement
	public String getCloudAccountNumber() {
		return cloudAccountNumber;
	}

	@XmlElement
	public String getCloudRegionId() {
		return cloudRegionId;
	}

	@XmlElement(name = "server")
	public List<ServerVO> getServerVOList() {
		return serverVOList;
	}

	public void setServerMethod(String serverMethod) {
		this.serverMethod = serverMethod;
	}

	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}

	public void setCloudAccountNumber(String cloudAccountNumber) {
		this.cloudAccountNumber = cloudAccountNumber;
	}

	public void setCloudRegionId(String cloudRegionId) {
		this.cloudRegionId = cloudRegionId;
	}

	public void setServerVOList(List<ServerVO> serverVOList) {
		this.serverVOList = serverVOList;
	}

}