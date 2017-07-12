package org.comcast.ad.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//@XmlRootElement
public class TestModel {
	//@XmlElement(name = "partner_id")
	@JsonProperty("partner_id")
	private String test1;
	private String test2;
	
	public TestModel() {
		;
	}
	
	public TestModel(String test1, String test2) {
		super();
		this.test1 = test1;
		this.test2 = test2;
	}
	public String getTest1() {
		return test1;
	}
	public void setTest1(String test1) {
		this.test1 = test1;
	}
	public String getTest2() {
		return test2;
	}
	public void setTest2(String test2) {
		this.test2 = test2;
	}
	
	
}
