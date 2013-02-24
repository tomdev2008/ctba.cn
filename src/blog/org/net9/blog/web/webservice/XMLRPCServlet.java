package org.net9.blog.web.webservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcServer;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.web.servlet.BusinessCommonServlet;

/**
 * Roller's XML RPC Servlet sets up XmlRpcHandler for Blogger & MetaWeblog API.
 * 
 */
@WebModule("xmlrpc")
public class XMLRPCServlet extends BusinessCommonServlet {

	private static Log mLogger = LogFactory.getLog(XMLRPCServlet.class);

	private transient XmlRpcServer mXmlRpcServer = new XmlRpcServer();
	private BloggerAPIHandler mBloggerHandler = null;
	private MetaWeblogAPIHandler mMetaWeblogHandler = null;
	private CtbaAPIHandler mCtbaWeblogHandler = null;

	public XMLRPCServlet() {
		try {
			mBloggerHandler = new BloggerAPIHandler();
			mXmlRpcServer.addHandler("blogger", mBloggerHandler);

			mMetaWeblogHandler = new MetaWeblogAPIHandler();
			mXmlRpcServer.addHandler("metaWeblog", mMetaWeblogHandler);

			mCtbaWeblogHandler = new CtbaAPIHandler();
			mXmlRpcServer.addHandler("ctba", mCtbaWeblogHandler);
		} catch (Exception e) {
			mLogger.error("Initialization of XML-RPC servlet failed", e);
		}
		// XmlRpc.setDebug(true);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		try {
			InputStream is = request.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			mLogger.debug(sb.toString());
			is = new java.io.ByteArrayInputStream(sb.toString().getBytes(
					"UTF-8"));

			// execute XML-RPC request
			byte[] result = mXmlRpcServer.execute(is);

			if (mLogger.isDebugEnabled()) {
				String output = new String(result);
				mLogger.debug(output);
			}

			// response.setContentType("text/xml");
			response.setContentType("text/xml;charset=UTF-8");
			response.setContentLength(result.length);
			OutputStream output = response.getOutputStream();
			output.write(result);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
	}
}
