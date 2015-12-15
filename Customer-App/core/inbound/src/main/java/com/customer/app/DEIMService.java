package com.customer.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/deim/")
public interface DEIMService {
	
	@POST
	@Path("/add")
	@Produces({"application/xml","application/json"})
	@Consumes({"application/xml","application/json"})
	public Response addPerson(Person p);
}
