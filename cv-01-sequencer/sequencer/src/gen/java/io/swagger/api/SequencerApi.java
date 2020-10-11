package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.SequencerApiService;
import io.swagger.api.factories.SequencerApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Operation;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/sequencer")


@io.swagger.annotations.Api(description = "the sequencer API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-10-11T19:12:44.517Z")
public class SequencerApi  {
   private final SequencerApiService delegate;

   public SequencerApi(@Context ServletConfig servletContext) {
      SequencerApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("SequencerApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (SequencerApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = SequencerApiServiceFactory.getSequencerApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Accepts financial operation", notes = "", response = Void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Accepted", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Invalid input, object invalid", response = Void.class) })
    public Response sequencerPost(@ApiParam(value = "Financial operation to perform" ) Operation operation
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.sequencerPost(operation,securityContext);
    }
}
