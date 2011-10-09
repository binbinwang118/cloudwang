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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.links.RESTServiceDiscovery;

@Mapped(namespaceMap = @XmlNsMap(jsonName = "atom", namespace = "http://www.w3.org/2005/Atom"))
@XmlRootElement(name = "cloudFirewall")
@XmlType(propOrder ={"firewallMethod", "cloudProvider","cloudName","cloudAccountNumber",
		"cloudRegionId", "rest", "links", "firewallVOList"})
public class CloudFirewallVO {

	private String					 firewallMethod;
	private String					 cloudProvider;
	private String					 cloudName;
	private String 				     cloudAccountNumber;
	private String 					 cloudRegionId;
	protected List<FirewallVO> 	 	 firewallVOList;
	private String					 providerFirewallId;
	private RESTServiceDiscovery 	 rest;
	private List<Link> 				 links;
	
	public CloudFirewallVO() {}

	public CloudFirewallVO(String firewallMethod, String cloudProvider,
			String cloudName, String cloudAccountNumber, String cloudRegionId,
			List<FirewallVO> firewallVOList, String providerFirewallId,
			RESTServiceDiscovery rest, List<Link> links) {
		super();
		this.firewallMethod = firewallMethod;
		this.cloudProvider = cloudProvider;
		this.cloudName = cloudName;
		this.cloudAccountNumber = cloudAccountNumber;
		this.cloudRegionId = cloudRegionId;
		this.firewallVOList = firewallVOList;
		this.providerFirewallId = providerFirewallId;
		this.rest = rest;
		this.links = links;
	}

	@XmlElement
	public String getFirewallMethod() {
		return firewallMethod;
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

	@XmlElement(name = "firewall")
	public List<FirewallVO> getFirewallVOList() {
		return firewallVOList;
	}
	
	@XmlID
	@XmlAttribute
	public String getProviderFirewallId() {
		return providerFirewallId;
	}

	@XmlElementRef
	public RESTServiceDiscovery getRest() {
		return rest;
	}

	@XmlElementRef
	public List<Link> getLinks() {
		return links;
	}

	public void setFirewallMethod(String firewallMethod) {
		this.firewallMethod = firewallMethod;
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

	public void setFirewallVOList(List<FirewallVO> firewallVOList) {
		this.firewallVOList = firewallVOList;
	}

	public void setProviderFirewallId(String providerFirewallId) {
		this.providerFirewallId = providerFirewallId;
	}

	public void setRest(RESTServiceDiscovery rest) {
		this.rest = rest;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
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
