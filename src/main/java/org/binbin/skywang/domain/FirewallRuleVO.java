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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.dasein.cloud.network.Direction;
import org.dasein.cloud.network.Permission;
import org.dasein.cloud.network.Protocol;

@XmlRootElement(name = "firewallRules")
@XmlType(propOrder = {"providerRuleId", "providerFirewallId", "direction", "protocol", "cidr", "startPort", "endPort", "permission"})
public class FirewallRuleVO {

    private String     cidr;
    private Direction  direction = Direction.INGRESS;
    private int        endPort;
    private String     providerFirewallId;
    private Permission permission = Permission.ALLOW;
    private Protocol   protocol;
    private String     providerRuleId;
    private int        startPort;
    
    public FirewallRuleVO() {}
    
	public FirewallRuleVO(String cidr, Direction direction, int endPort,
			String providerFirewallId, Permission permission, Protocol protocol,
			String providerRuleId, int startPort) {
		super();
		this.cidr = cidr;
		this.direction = direction;
		this.endPort = endPort;
		this.providerFirewallId = providerFirewallId;
		this.permission = permission;
		this.protocol = protocol;
		this.providerRuleId = providerRuleId;
		this.startPort = startPort;
	}

	@XmlElement
	public String getCidr() {
		return cidr;
	}

	@XmlElement
	public Direction getDirection() {
		return direction;
	}

	@XmlElement
	public int getEndPort() {
		return endPort;
	}

	@XmlElement
	public String getProviderFirewallId() {
		return providerFirewallId;
	}

	@XmlElement
	public Permission getPermission() {
		return permission;
	}

	@XmlElement
	public Protocol getProtocol() {
		return protocol;
	}

	@XmlElement
	public String getProviderRuleId() {
		return providerRuleId;
	}

	@XmlElement
	public int getStartPort() {
		return startPort;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setEndPort(int endPort) {
		this.endPort = endPort;
	}

	public void setProviderFirewallId(String firewallId) {
		this.providerFirewallId = firewallId;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public void setProviderRuleId(String providerRuleId) {
		this.providerRuleId = providerRuleId;
	}

	public void setStartPort(int startPort) {
		this.startPort = startPort;
	}
    
}
