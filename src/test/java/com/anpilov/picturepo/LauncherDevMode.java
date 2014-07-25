package com.anpilov.picturepo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class LauncherDevMode {

	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);

		WebAppContext webapp = new WebAppContext();
		webapp.setDescriptor(webapp + "src/webapp/WEB-INF/web.xml");
		webapp.setResourceBase("src/main/webapp");
		webapp.setContextPath("/");
		webapp.setParentLoaderPriority(true);

		server.setHandler(webapp);
		server.start();
		server.join();
	}

}
