package esprit.collaborative_space.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.core.io.FileSystemResource;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ImageDetectionService {

    @Value("${flask.api.url}") // Flask API URL, e.g., http://localhost:5001/detect
    private String flaskApiUrl;

    public String detectImage(String imagePath) {
        try {
            // Prepare the file to be sent in the request
            FileSystemResource fileResource = new FileSystemResource(imagePath);

            // Prepare the request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Call the Flask API
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(flaskApiUrl, HttpMethod.POST, requestEntity, String.class);

            return response.getBody();  // Returning the detection result
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to process the image.";
        }
    }
}
