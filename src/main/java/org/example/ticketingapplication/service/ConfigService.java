package org.example.ticketingapplication.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ticketingapplication.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigService {

    private final AppConfig appConfig;
    private String FilePath = "src/main/resources/";

    @Autowired
    public ConfigService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setDefaultFilePath(String defaultFilePath) {
        this.FilePath = defaultFilePath;
    }

    public void loadConfigFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper(); // Converts JSON data into a Java Object

        try {
            // Parse JSON
            JsonNode rootNode = objectMapper.readTree(new File(FilePath + fileName));

            // Access elements and assign it to the configuration class
            appConfig.setTotalTickets(rootNode.has("totalTickets") ?rootNode.get("totalTickets").asInt() : 0);
            appConfig.setTicketReleaseRate(rootNode.has("ticketReleaseRate") ? rootNode.get("ticketReleaseRate").asInt() : 1);
            appConfig.setCustomerRetrievalRate(rootNode.has("customerRetrievalRate") ? rootNode.get("customerRetrievalRate").asInt() : 1);
            appConfig.setMaxTicketsPoolCapacity(rootNode.has("maxTicketPoolCapacity") ? rootNode.get("maxTicketPoolCapacity").asInt() : 0);

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    public void loadConfigFile() {
        ObjectMapper objectMapper = new ObjectMapper(); // Converts JSON data into a Java Object

        try {
            // Parse JSON
            JsonNode rootNode = objectMapper.readTree(new File(FilePath + "Configuration.json"));

            // Access elements and assign it to the configuration class
            appConfig.setTotalTickets(rootNode.has("totalTickets") ? rootNode.get("totalTickets").asInt() : 0);
            appConfig.setTicketReleaseRate(rootNode.has("ticketReleaseRate") ? rootNode.get("ticketReleaseRate").asInt() : 1);
            appConfig.setCustomerRetrievalRate(rootNode.has("customerRetrievalRate") ? rootNode.get("customerRetrievalRate").asInt() : 1);
            appConfig.setMaxTicketsPoolCapacity(rootNode.has("maxTicketPoolCapacity") ? rootNode.get("maxTicketPoolCapacity").asInt() : 0);

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }
}
