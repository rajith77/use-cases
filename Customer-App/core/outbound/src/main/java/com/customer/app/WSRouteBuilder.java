package com.customer.app;


import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

public class WSRouteBuilder extends RouteBuilder {

	JaxbDataFormat format = new JaxbDataFormat("com.sun.mdm.index.webservice");
	
	@Override
	public void configure() throws Exception {
		format.setPartClass("com.sun.mdm.index.webservice.ExecuteMatchUpdate");
		format.setPrettyPrint(true);

		from("{{ws-amq-inbound}}")
		.log("Got from Queue ${body}")
		.unmarshal(format)
		.log("After unmarshalling ${body}")
		.to("bean:routeSupportBean?method=createSoapRequest")
		.setHeader(CxfConstants.OPERATION_NAME, constant("executeMatchUpdate"))
		.log("Soap request to be sent ${body}")
	    .to(ExchangePattern.OutOnly,"{{ws-outbound}}");
	    //.marshal(format)
	    //.log("Received response from web service ${body}");
	}
}
