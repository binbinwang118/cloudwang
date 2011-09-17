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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "cloudSnapshots")
public class CloudSnapshotVO {
	
	protected String cloudProvider;
	protected String snapshotMethod;
	
	protected List<SnapshotVO> snapshotVOList;
	protected List<Link> links;

	public CloudSnapshotVO() {};

	public CloudSnapshotVO(String cloudProvider, String snapshotMethod,
			List<SnapshotVO> snapshotVOList, List<Link> links) {
		super();
		this.cloudProvider = cloudProvider;
		this.snapshotMethod = snapshotMethod;
		this.snapshotVOList = snapshotVOList;
		this.links = links;
	}

	@XmlElement
	public String getCloudProvider() {
		return cloudProvider;
	}

	@XmlElement
	public String getSnapshotMethod() {
		return snapshotMethod;
	}

	@XmlElement(name = "snapshot")
	public List<SnapshotVO> getSnapshotVOList() {
		return snapshotVOList;
	}

	@XmlElementRef
	public List<Link> getLinks() {
		return links;
	}

	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	public void setSnapshotMethod(String snapshotMethod) {
		this.snapshotMethod = snapshotMethod;
	}

	public void setSnapshotVOList(List<SnapshotVO> snapshotVOList) {
		this.snapshotVOList = snapshotVOList;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	};
	
	@XmlTransient
	public String getNext()
	{
		if (links == null) return null;
		for (Link link : links)
		{
			if ("next".equals(link.getRelationship())) return link.getHref();
		}
		return null;
	}

	@XmlTransient
	public String getPrevious()
	{
		if (links == null) return null;
		for (Link link : links)
		{
			if ("previous".equals(link.getRelationship())) return link.getHref();
		}
		return null;
	}
	
}
