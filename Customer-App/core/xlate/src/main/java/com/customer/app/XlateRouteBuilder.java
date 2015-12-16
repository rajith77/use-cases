package com.customer.app;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import com.sun.mdm.index.webservice.ExecuteMatchUpdate;

public class XlateRouteBuilder extends RouteBuilder {

	JaxbDataFormat inFormat = new JaxbDataFormat("com.customer.app");
	JaxbDataFormat outFormat = new JaxbDataFormat("com.sun.mdm.index.webservice");
	
	@Override
	public void configure() throws Exception {
		inFormat.setPartClass("com.customer.app.Person");
		inFormat.setPrettyPrint(true);
		outFormat.setPartClass("com.sun.mdm.index.webservice.ExecuteMatchUpdate");
		outFormat.setPrettyPrint(true);
		getContext().getTypeConverterRegistry().addTypeConverter(ExecuteMatchUpdate.class, Person.class, new XlateConverter());
		
		from("{{xlate-amq-inbound}}")
		.log("Got from Queue ${body}")
		.unmarshal(inFormat)
		.convertBodyTo(ExecuteMatchUpdate.class)
	    //.marshal(outFormat)
	    .log("After converting and marshalling ${body}")
	    .to(ExchangePattern.InOnly,"{{xlate-amq-outbound}}");
	}

}
