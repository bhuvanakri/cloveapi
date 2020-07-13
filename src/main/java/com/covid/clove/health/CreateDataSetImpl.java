package com.covid.clove.health;
// [START healthcare_create_dataset]
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
import com.google.api.services.healthcare.v1beta1.model.Operation;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class CreateDataSetImpl {
        private static final String DATASET_NAME = "projects/%s/locations/%s/datasets/%s";
        private static final JsonFactory JSON_FACTORY = new JacksonFactory();
        private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();

        public static Map<String, Object> datasetCreate(String projectId, String regionId, String datasetId)
                throws IOException {
            // String projectId = "your-project-id";
            // String regionId = "us-central1";
            // String datasetId = "your-dataset-id";

            // Initialize the client, which will be used to interact with the service.
            CloudHealthcare client = createClient();

            // Configure the dataset to be created.
            Dataset dataset = new Dataset();
            dataset.setTimeZone("America/Chicago");

            // Create request and configure any parameters.
            String parentName = String.format("projects/%s/locations/%s", projectId, regionId);
            Datasets.Create request = client.projects().locations().datasets().create(parentName, dataset);
            request.setDatasetId(datasetId);

            // Execute the request, wait for the operation to complete, and process the results.
            try {
                Operation operation = request.execute();
                System.out.println(operation.toPrettyString());
                while (operation.getDone() == null || !operation.getDone()) {
                    // Update the status of the operation with another request.
                    Thread.sleep(500); // Pause for 500ms between requests.
                    operation =
                            client
                                    .projects()
                                    .locations()
                                    .datasets()
                                    .operations()
                                    .get(operation.getName())
                                    .execute();
                }

                System.out.println("Dataset created. Response content: " + operation.getResponse());
                return operation.getResponse();
            } catch (Exception ex) {
                System.out.printf("Error during request execution: %s\n", ex.toString());
                ex.printStackTrace(System.out);
            }
            return null;
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
// [END healthcare_create_dataset]

