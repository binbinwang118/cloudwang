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

package org.binbin.skywang.service;

import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.rackspace.RackspaceCloud;
import org.dasein.cloud.openstack.nova.os.NovaOpenStack;

import org.binbin.skywang.utils.PropertyUtil;

public class BaseCloudService{

	protected CloudProvider provider = null;
	private	static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	
	/**
	 * todo: put the cloudinfo into db, and based on the cloudId to new different cloud
	 * @param cloudId
	 */
    public BaseCloudService(String cloudId) {
    	if(cloudId.equals("AWSCloud")) {
    		this.provider = new AWSCloud();
    	}
    	else if(cloudId.equals("RackSpaceCloud")) {
    		this.provider = new RackspaceCloud();
    	}
    	else if(cloudId.equals("NovaOpenStack")) {
    		this.provider = new NovaOpenStack();
    	}
		provider.connect(BaseCloudService.getCloudContext()); 
    }
    
    static public ProviderContext getCloudContext() {

    	ProviderContext ctx = new ProviderContext();
        
    	String apiSharedKey = propertyUtil.getPropertyByKey("cloudwang.apiSharedKey");
    	String apiSecretKey = propertyUtil.getPropertyByKey("cloudwang.apiSecretKey");
    	String accountNumber = propertyUtil.getPropertyByKey("cloudwang.accountNumber");
    	String cloudName = propertyUtil.getPropertyByKey("cloudwang.cloudName");
    	String endpoint = propertyUtil.getPropertyByKey("cloudwang.endpoint");
    	String providerName = propertyUtil.getPropertyByKey("cloudwang.providerName");
        String regionId = propertyUtil.getPropertyByKey("cloudwang.regionId");
        String x509Cert = propertyUtil.getPropertyByKey("cloud.x509Cert");
        String x509Key = propertyUtil.getPropertyByKey("cloud.x509Key");
        
        if(apiSharedKey != null && apiSecretKey != null) {
        	ctx.setAccessKeys(apiSharedKey.getBytes(), apiSecretKey.getBytes());
        }
        ctx.setAccountNumber(accountNumber);
        ctx.setCloudName(cloudName);
        ctx.setEndpoint(endpoint);
        ctx.setRegionId(regionId);
        ctx.setProviderName(providerName);
        
        if(x509Key != null && x509Cert != null) {
        	ctx.setStorageKeys(x509Key.getBytes(), x509Cert.getBytes());
        }

        return ctx;
    }
}
