package io.swagger.api.impl;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.SequencerApiService;
import io.swagger.model.Operation;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public class SequencerApiServiceImpl extends SequencerApiService {

    private AtomicLong id = new AtomicLong();

    @Override
    public Response sequencerPost(Operation operation, SecurityContext securityContext) {
        long currentId = id.getAndIncrement();
        System.out.println("Accepted op: " + currentId + " val: " + operation.getValue());
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "Accepted")).build();
    }
}
