package com.customer.app;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.camel.Body;

import com.sun.mdm.index.webservice.ExecuteMatchUpdate;

public class RouteSupportBean {

	public List<Object> createSoapRequest(@Body ExecuteMatchUpdate body) {
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(body.getCallerInfo());
		paramList.add(body.getSysObjBean());

		return paramList;
	}
}
