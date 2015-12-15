package com.customer.app;

import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class DEIMServiceImpl implements DEIMService{
	
	private CamelContext camelContext;
	private ProducerTemplate template;
	
	public Response addPerson(Person p) {
	    template = template != null ? template : camelContext.createProducerTemplate();	
	    
	    String response = (String) template.requestBody("{{inbound-rest-uri}}", p);
		return Response.ok(response).build();
	}

	public CamelContext getCamelContext() {
		return camelContext;
	}

	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}	
}
