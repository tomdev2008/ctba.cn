/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.net9.blog.ping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

/**
 * Utility for sending a weblog update ping.
 * 
 * This implements the <code>WeblogUpdates.ping<code> XML-RPC call described at
 * <a href="http://www.xmlrpc.com/weblogsCom">www.xmlrpc.com</a>
 * as well as some variants required to interoperate with certain
 * buggy but popular ping targets.
 *
 *
 * @author <a href="mailto:anil@busybuddha.org">Anil Gangolli</a>
 * @author llavandowska (for code refactored from the now-defunct <code>RollerXmlRpcClient</code>)
 */
public class WeblogUpdatePinger {
	/**
	 * Conveys a ping result.
	 */
	public static class PingResult {
		boolean error;
		String message;

		public PingResult(Boolean error, String message) {
			this.error = error != null ? error.booleanValue() : false;
			this.message = message != null ? message : "";
		}

		public String getMessage() {
			return message;
		}

		public boolean isError() {
			return error;
		}

		public void setError(boolean error) {
			this.error = error;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String toString() {
			return "PingResult{" + "error=" + error + ", message='" + message
					+ "'" + "}";
		}
	}

	public static final Log logger = LogFactory
			.getLog(WeblogUpdatePinger.class);

	@SuppressWarnings("unchecked")
	private static PingResult parseResult(Object obj) {
		// Deal with the fact that some buggy ping targets may not respond with
		// the proper struct type.
		if (obj == null)
			return new PingResult(null, null);
		try {
			// normal case: response is a struct (represented as a Hashtable)
			// with Boolean flerror and String fields.
			Hashtable result = (Hashtable) obj;
			return new PingResult((Boolean) result.get("flerror"),
					(String) result.get("message"));
		} catch (Exception ex) {
			// exception case: The caller responded with an unexpected type,
			// though parsed at the basic XML RPC level.
			// This effectively assumes flerror = false, and sets message =
			// obj.toString();
			if (logger.isDebugEnabled())
				logger.debug("Invalid ping result of type: "
						+ obj.getClass().getName()
						+ "; proceeding with stand-in representative.");
			return new PingResult(null, obj.toString());
		}
	}

	/**
	 * Send a weblog update ping.
	 * 
	 * @param pingTarget
	 *            the target site to ping
	 * @param website
	 *            the website that changed (from which the ping originates)
	 * @return the result message string sent by the server.
	 * @throws IOException
	 * @throws XmlRpcException
	 */
	@SuppressWarnings("unchecked")
	public static PingResult sendPing(String pingTargetUrl, String blogName,
			String websiteUrl, String updatedPage, String rssUrl)
			throws IOException, XmlRpcException {

		Vector params = new Vector();
		params.addElement(blogName);
		params.addElement(websiteUrl);
		params.addElement(updatedPage);
		params.addElement(rssUrl);
		logger.debug("Executing ping to '" + pingTargetUrl + "' for website '"
				+ websiteUrl);

		// Send the ping.
		XmlRpcClient client = new XmlRpcClient(pingTargetUrl);
		PingResult pingResult = parseResult(client.execute(
				"weblogUpdates.ping", params));

		logger.info("Ping result is: " + pingResult);
		return pingResult;
	}

	/**
	 * Decide if the given exception appears to warrant later retrial attempts.
	 * 
	 * @param ex
	 *            an exception thrown by the <coce>sendPing</code> operation
	 * @return true if the error warrants retrial
	 */
	public static boolean shouldRetry(Exception ex) {
		// Determine if error appears transient (warranting retrial)
		// We give most errors the "benefit of the doubt" by considering them
		// transient
		// This picks out a few that we consider non-transient
		if (ex instanceof UnknownHostException) {
			// User probably mistyped the url in the custom target.
			return false;
		} else if (ex instanceof MalformedURLException) {
			// This should never happen due to validations but if we get here,
			// retrial won't fix it.
			return false;
		}
		return true;
	}

	// Inhibit construction
	private WeblogUpdatePinger() {
	}

}
