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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "firewall")
@XmlType(propOrder = {"providerFirewallId", "name", "description", "providerVlanId", "firewallRuleVOList"})
public class FirewallVO {

	private String					firewallId;
    private boolean 				active;
    private boolean 				available;
    private int						budget;
    
    private String  				description;
    private String 					name;
    private String  				providerFirewallId;
    private String  				providerVlanId;
    private List<FirewallRuleVO>	firewallRuleVOList;
    
    public FirewallVO() {}

	public FirewallVO(String firewallId, int budget, boolean active,
			boolean available, String description, String name,
			String providerFirewallId, String providerVlanId,
			List<FirewallRuleVO> firewallRuleVOList) {
		super();
		this.firewallId = firewallId;
		this.budget = budget;
		this.active = active;
		this.available = available;
		this.description = description;
		this.name = name;
		this.providerFirewallId = providerFirewallId;
		this.providerVlanId = providerVlanId;
		this.firewallRuleVOList = firewallRuleVOList;
	}

	@XmlAttribute
	public String getFirewallId() {
		return firewallId;
	}

	@XmlAttribute
	public boolean isActive() {
		return active;
	}

	@XmlAttribute
	public boolean isAvailable() {
		return available;
	}
	
	@XmlAttribute
	public int getBudget() {
		return budget;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	@XmlElement
	public String getProviderFirewallId() {
		return providerFirewallId;
	}

	@XmlElement
	public String getProviderVlanId() {
		return providerVlanId;
	}

	@XmlElement(name = "firewallRules")
	public List<FirewallRuleVO> getFirewallRuleVOList() {
		return firewallRuleVOList;
	}

	public void setFirewallId(String firewallId) {
		this.firewallId = firewallId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProviderFirewallId(String providerFirewallId) {
		this.providerFirewallId = providerFirewallId;
	}

	public void setProviderVlanId(String providerVlanId) {
		this.providerVlanId = providerVlanId;
	}

	public void setFirewallRuleVOList(List<FirewallRuleVO> firewallRuleVOList) {
		this.firewallRuleVOList = firewallRuleVOList;
	}
    
}
