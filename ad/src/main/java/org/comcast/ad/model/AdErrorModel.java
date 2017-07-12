package org.comcast.ad.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdErrorModel {

	private String ErrorMessage;
	private int errorCode;
	private String documentation;
	
	AdErrorModel() {
		;
	}
	
	public AdErrorModel(String errorMessage, int errorCode, String documentation) {
		super();
		ErrorMessage = errorMessage;
		this.errorCode = errorCode;
		this.documentation = documentation;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
}
