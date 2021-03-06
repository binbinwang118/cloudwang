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

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.binbin.skywang.exceptions.CloudWangExceptionMapper;

public class CloudWangApplication extends Application {
	
	   private Set<Object> singletons = new HashSet<Object>();
	   private Set<Class<?>> empty = new HashSet<Class<?>>();

	   public CloudWangApplication()
	   {
	      singletons.add(new VolumeResource("AWSCloud"));
	      singletons.add(new SnapshotResource("AWSCloud"));
	      singletons.add(new MachineImageResource("AWSCloud"));
	      singletons.add(new ServerResource("AWSCloud"));
	      singletons.add(new FirewallResource("AWSCloud"));
	      singletons.add(new CloudWangExceptionMapper());
	   }

	   @Override
	   public Set<Class<?>> getClasses()
	   {
	      return empty;
	   }

	   @Override
	   public Set<Object> getSingletons()
	   {
	      return singletons;
	   }

}
