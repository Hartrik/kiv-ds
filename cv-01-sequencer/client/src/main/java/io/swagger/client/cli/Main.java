package io.swagger.client.cli;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Operation;
import java.util.Random;

/**
 * @author Patrik Harag
 * @version 2020-10-11
 */
public class Main {

    private static final String BASE_PATH = "http://localhost:8080";

    private static final int DEFAULT_N = 100;
    private static final int MIN_VALUE = 10000;
    private static final int MAX_VALUE = 50000;

    public static void main(String[] args) {
        if (args.length == 0) {
            generate(DEFAULT_N);
        } else {
            generate(Integer.parseInt(args[0]));
        }
    }

    private static void generate(int n) {
        System.out.println("Generating " + n + " entries...");

        ApiClient configuration = Configuration.getDefaultApiClient();
        configuration.setBasePath(BASE_PATH);
        DefaultApi apiInstance = new DefaultApi(configuration);

        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int val = random.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE;
            if (random.nextBoolean()) {
                // CREDIT / DEBIT
                val = -val;
            }

            System.out.println(val);

            Operation operation = new Operation();
            operation.setValue(val);
            try {
                apiInstance.sequencerPost(operation);
            } catch (ApiException e) {
                System.err.println("Exception when calling DefaultApi#sequencerPost");
                e.printStackTrace();
            }
        }
    }

}
