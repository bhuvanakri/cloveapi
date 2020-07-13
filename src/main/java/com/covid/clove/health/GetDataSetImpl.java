package com.covid.clove.health;

    // [START healthcare_get_dataset]
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.healthcare.v1beta1.CloudHealthcare;
import com.google.api.services.healthcare.v1beta1.CloudHealthcare.Projects.Locations.Datasets;
import com.google.api.services.healthcare.v1beta1.CloudHealthcareScopes;
import com.google.api.services.healthcare.v1beta1.model.Dataset;
import java.io.IOException;
import java.util.Collections;

    public class GetDataSetImpl {
        private static final String DATASET_NAME = "projects/%s/locations/%s/datasets/%s";
        private static final JsonFactory JSON_FACTORY = new JacksonFactory();
        private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();

        public static String datasetGet(String datasetName) throws IOException {
            // String datasetName =
            //     String.format(DATASET_NAME, "your-project-id", "your-region-id", "your-dataset-id");

            // Initialize the client, which will be used to interact with the service.
            CloudHealthcare client = createClient();

            // Create request and configure any parameters.
            Datasets.Get request = client.projects().locations().datasets().get(datasetName);

            // Execute the request and process the results.
            Dataset dataset = request.execute();
            System.out.println("Dataset retrieved: \n" + dataset.toPrettyString());
            return dataset.toPrettyString();
        }

        private static CloudHealthcare createClient() throws IOException {
            // Use Application Default Credentials (ADC) to authenticate the requests
            // For more information see https://cloud.google.com/docs/authentication/production
            GoogleCredential credential =
                    GoogleCredential.getApplicationDefault(HTTP_TRANSPORT, JSON_FACTORY)
                            .createScoped(Collections.singleton(CloudHealthcareScopes.CLOUD_PLATFORM));

            // Create a HttpRequestInitializer, which will provide a baseline configuration to all requests.
            HttpRequestInitializer requestInitializer =
                    request -> {
                        credential.initialize(request);
                        request.setConnectTimeout(60000); // 1 minute connect timeout
                        request.setReadTimeout(60000); // 1 minute read timeout
                    };

            // Build the client for interacting with the service.
            return new CloudHealthcare.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
                    .setApplicationName("your-application-name")
                    .build();
        }
    }
// [END healthcare_get_dataset]

