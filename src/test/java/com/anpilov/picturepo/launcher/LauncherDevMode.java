package com.anpilov.picturepo.launcher;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LauncherDevMode {

	public static void main(String[] args) throws Exception	{
		Server server = new Server(8080);

        runGulpWatch();

		WebAppContext webapp = new WebAppContext();
		webapp.setDescriptor(webapp + "src/webapp/WEB-INF/web.xml");
		webapp.setResourceBase("src/main/webapp");
		webapp.setContextPath("/");
		webapp.setParentLoaderPriority(true);

		server.setHandler(webapp);
		server.start();
		server.join();
	}

    private static void runGulpWatch() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runGulp("watch");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void runGulp(String tasks) throws Exception {
        File websrc = new File("src/main/websrc").getAbsoluteFile();
        List<String> commands = new ArrayList<>();
        commands.add(nodePath());
        commands.add("gulprun.js");
        commands.addAll(Lists.newArrayList(Splitter.on(" ").split(tasks)));
        Process process = new ProcessBuilder().directory(websrc).command(commands).inheritIO().start();
        process.waitFor();
    }

    // TODO fix this :/
    private static String nodePath() {
        return System.getProperty("os.name").toLowerCase().contains("windows")
                ? "node.exe"
                : "/usr/local/bin/node";
    }




}
