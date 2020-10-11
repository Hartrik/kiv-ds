package io.swagger.api.factories;

import io.swagger.api.SequencerApiService;
import io.swagger.api.impl.SequencerApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-10-11T19:12:44.517Z")
public class SequencerApiServiceFactory {
    private final static SequencerApiService service = new SequencerApiServiceImpl();

    public static SequencerApiService getSequencerApi() {
        return service;
    }
}
