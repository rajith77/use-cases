package com.customer.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

public class DEIMServiceTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/camel-blueprint.xml,OSGI-INF/blueprint/test-property-placeholder.xml";
	}

	@Test
	public void testAddPerson() {
		this.log.warn("testAddPerson ---> begining");
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:9191/cxf/deim/add");

		StringEntity entity;
		try {
			this.log.warn("testAddPerson ---> before loading payload");
			entity = new StringEntity(loadPayload(), ContentType.create("application/xml",Consts.UTF_8));
			
			this.log.warn("testAddPerson ---> after loading payload, but before posting");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK_200){
				this.log.warn("Invocation succesfful");
			} else {
			   this.fail("add person failed with code : " + response.getStatusLine().getStatusCode());	
			}
		} catch (Exception e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

	String loadPayload() {
		StringBuilder xml = new StringBuilder();
		try {
			String file = getClass().getResource("/PatientDemographics.xml").getFile();
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