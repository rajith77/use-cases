package com.customer.app;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class TestXlate extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/camel-blueprint.xml,OSGI-INF/blueprint/test-property-placeholder.xml";
	}

	@Test
	public void testAddPerson() throws Exception {
		context().addRoutes(new DynamcRouteBuilder(context(),"send-to-q.empi.deim.in","direct:start", "{{xlate-amq-inbound}}"));
		ProducerTemplate prod = context().createProducerTemplate();
		prod.sendBody("direct:start", loadPayload());
		
		Thread.sleep(10000);
		
		getMockEndpoint("mock:foo").expectedMessageCount(1);
		context().addRoutes(new DynamcRouteBuilder(context(), "recv-from-q.empi.nextgate.out", "{{xlate-amq-outbound}}", "mock:foo"));
        assertMockEndpointsSatisfied();		
	}

	private static final class DynamcRouteBuilder extends RouteBuilder {
		private final String id;
		private final String from;
		private final String to;

		private DynamcRouteBuilder(CamelContext context, String id, String from, String to) {
			super(context);
			this.from = from;
			this.id = id;
			this.to = to;
		}

		@Override
		public void configure() throws Exception {
			from(from).id(id).to(to);
		}
	}

	String loadPayload() {
		StringBuilder xml = new StringBuilder();
		try {
			String file = getClass().getResource("/PatientDemographics.xml")
					.getFile();
			this.log.warn("xml-line : " + file);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				xml.append(line);
			}
			br.close();
			return xml.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
