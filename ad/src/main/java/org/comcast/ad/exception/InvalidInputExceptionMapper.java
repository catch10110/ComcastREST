package org.comcast.ad.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.comcast.ad.model.AdErrorModel;

public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

	@Override
	public Response toResponse(InvalidInputException e) {
		AdErrorModel errorMessage = new AdErrorModel(e.getMessage(), 422, "http://google.com");
		return Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
	}
	

}
