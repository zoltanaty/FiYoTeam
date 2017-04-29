package com.fiyoteam.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fiyoteam.utils.Authentication;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public class MyRequestFilter implements ResourceFilter, ContainerRequestFilter, ContainerResponseFilter  {
    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {
    	
    	if (containerRequest.getRequestHeader("authorization") != null && containerRequest.getRequestHeader("identifier") != null) {
    		
			String token = containerRequest.getRequestHeader("authorization").get(0);
			Integer identifier = Integer.parseInt(containerRequest.getRequestHeader("identifier").get(0));

			if (Authentication.isTokenAllowed(token, identifier)) {
				return containerRequest;
			} 
		}
    	
    	ResponseBuilder builder = null;
        String response = "Access Denied";
        builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
        throw new WebApplicationException(builder.build());
    }
    
    @Override
	public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
		return containerResponse;
	}

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return this;
	}
}
