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

import javax.ws.rs.core.Response.ResponseBuilder;

public class CloudWangException extends RuntimeException {

	private ResponseBuilder responseBuilder;
	
	public CloudWangException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public CloudWangException(String message) {
		super(message);
	}
	
	/**
	 * @param cause
	 */
	public CloudWangException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CloudWangException(String message, Throwable cause) {
		super(message, cause);
	}
	

	/**
	 * @return responseBuilder
	 */
	public ResponseBuilder getResponseBuilder() {
		return responseBuilder;
	}
	
	/**
	 * @return true if has responseBuilder
	 */
	public boolean hasResponseBuilder() {
		return responseBuilder != null;
	}

	
}
