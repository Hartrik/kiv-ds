package cz.harag.ds.cv02.server.rest;

import cz.harag.ds.cv02.server.model.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ServiceEndpoint {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("snapshot")
	public Response snapshot() {
		return new Response("OK");
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("status")
	public Response status() {
		return new Response("OK");
	}

}
