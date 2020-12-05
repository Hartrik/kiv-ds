package cz.harag.ds.cv02.server.rest;

import cz.harag.ds.cv02.server.Main;
import cz.harag.ds.cv02.server.Snapshot;
import cz.harag.ds.cv02.server.model.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ServiceEndpoint {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("snapshot")
	public Response snapshot() {
		int snapshot = Main.BANK.snapshot();
		return new Response("" + snapshot);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("snapshot/{marker}")
	public Response snapshotStatus(@PathParam("marker") int marker) {
		Snapshot snapshot = Main.BANK.getSnapshot(marker);
		return new Response(snapshot.toString());
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("status")
	public Response status() {
		return new Response("Balance: " + Main.BANK.getBalance());
	}

}
