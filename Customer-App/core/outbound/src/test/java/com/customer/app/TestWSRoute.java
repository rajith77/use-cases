package com.customer.app;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWSRoute extends CamelBlueprintTestSupport {
	private static final Logger LOG = LoggerFactory.getLogger(TestWSRoute.class);

	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/stubbed-ws-service.xml,OSGI-INF/blueprint/camel-blueprint.xml,OSGI-INF/blueprint/test-property-placeholder.xml";
	}

	@Test
	public void testAddPerson() throws Exception {
		Thread.sleep(1000);
		getMockEndpoint("mock:foo").expectedMessageCount(1);

		context().addRoutes(
				new DynamcRouteBuilder(context(),
						"send-to-q.empi.nextgate.out", "direct:start",
						"{{ws-amq-inbound}}"));
		ProducerTemplate prod = context().createProducerTemplate();
		prod.sendBody("direct:start", loadPayload());

		Thread.sleep(1000);

		assertMockEndpointsSatisfied();
	}

	private static final class DynamcRouteBuilder extends RouteBuilder {
		private final String id;
		private final String from;
		private final String to;

		private DynamcRouteBuilder(CamelContext context, String id,
				String from, String to) {
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
			String file = getClass().getResource("/ExecuteMatchUpdate.xml")
					.getFile();
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
