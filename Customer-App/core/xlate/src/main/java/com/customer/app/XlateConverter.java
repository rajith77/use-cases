package com.customer.app;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

import com.sun.mdm.index.webservice.CallerInfo;
import com.sun.mdm.index.webservice.ExecuteMatchUpdate;
import com.sun.mdm.index.webservice.SystemPerson;

@Converter
public class XlateConverter extends TypeConverterSupport {

	@Override
	public <T> T convertTo(Class<T> type, Exchange msg, Object value)
			throws TypeConversionException {
		ExecuteMatchUpdate update = new ExecuteMatchUpdate();
		Person person = (Person)value;
		update.setCallerInfo(getCallerInfo(person));
		update.setSysObjBean(getSysObjBean(person));
		
		return (T) update;
	}

	private SystemPerson getSysObjBean(Person person) {
		SystemPerson sysPerson = new SystemPerson();
		sysPerson.setUpdateUser(person.getLegalname().getFamily());		
		return sysPerson;
	}

	private CallerInfo getCallerInfo(Person person) {
		CallerInfo info = new CallerInfo();
		info.setApplication("foo");
		info.setApplicationFunction("bar");
		info.setAuthPassword("admin");
		info.setAuthPassword("admin");
		info.setExecutionCycleId("1");
		info.setSystem("abcdefg");
		info.setUser(person.getLegalname().getFamily());
		return info;
	}

}
