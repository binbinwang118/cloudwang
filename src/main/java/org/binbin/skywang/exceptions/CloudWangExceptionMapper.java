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
package org.binbin.skywang.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class CloudWangExceptionMapper implements ExceptionMapper<CloudWangException> {
    
    private Logger logger = Logger.getLogger(CloudWangExceptionMapper.class);
    
    @Override
    public Response toResponse(CloudWangException exception) {
    	
    	if(exception.hasResponseBuilder()) {
    		return exception.getResponseBuilder().build();
    	}
    	
    	logger.debug("500 - Internal Server Error. Reason :",exception);
    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    		.type("text/plain").build();
    	
    }
}
