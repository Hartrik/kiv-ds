package cz.harag.ds.cv02.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

//	public static List<String> SERVER_BASE_PATHS = Arrays.asList("http://localhost:8082");

	public static int SERVICE_REST_API_PORT;

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			printHelpAndExit();
		} else {

			try {
				SERVICE_REST_API_PORT = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				printHelpAndExit();
			}


//			SERVER_BASE_PATHS = new ArrayList<>();
//			SERVER_BASE_PATHS.addAll(Arrays.asList(args).subList(2, args.length));

			System.out.println("Starting service REST server at port " + SERVICE_REST_API_PORT);
			Server server = configureServer(SERVICE_REST_API_PORT);
			server.start();
			server.join();
		}
	}

	private static Server configureServer(int serverPort) {
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages("cz.harag.ds.cv02.server.rest");
		resourceConfig.register(JacksonFeature.class);
		ServletContainer servletContainer = new ServletContainer(resourceConfig);
		ServletHolder sh = new ServletHolder(servletContainer);
		Server server = new Server(serverPort);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.addServlet(sh, "/*");
		server.setHandler(context);
		return server;
	}

	private static void printHelpAndExit() {
		System.out.println("Usage: <SERVICE_REST_API_PORT>");
		System.exit(1);
	}

}
