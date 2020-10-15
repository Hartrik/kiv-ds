package cz.harag.ds.cv01.client;

import cz.harag.ds.cv01.client.sequencer.ApiClient;
import cz.harag.ds.cv01.client.sequencer.ApiException;
import cz.harag.ds.cv01.client.sequencer.Configuration;
import cz.harag.ds.cv01.client.sequencer.api.DefaultApi;
import cz.harag.ds.cv01.client.sequencer.model.Operation;
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
                e.printStackTrace();
            }
        }
    }

}
