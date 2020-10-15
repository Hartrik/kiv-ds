package cz.harag.ds.cv01.server.rest.shuffler;

import cz.harag.ds.cv01.server.model.Response;
import cz.harag.ds.cv01.server.model.ServerRequest;

import java.util.PriorityQueue;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/server")
public class ServerEndpoint {

	private static final PriorityQueue<ServerRequest> requests = new PriorityQueue<>();
	private static long lastId = -1;
	private static long balance = 0;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response process(ServerRequest request) {
		synchronized (ServerRequest.class) {
			System.out.println("Received " + request);

			requests.add(request);

			while (!requests.isEmpty() && lastId + 1 == requests.peek().getId()) {
				ServerRequest r = requests.poll();
				balance += r.getValue();
				lastId = r.getId();
				System.out.println(" | Executed " + r + " // " + balance);
			}
		}
		return new Response("OK");
	}

}
