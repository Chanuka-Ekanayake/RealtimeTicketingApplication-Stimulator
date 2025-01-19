package org.example.ticketingapplication.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ticketingapplication.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {

    private final AppConfig appConfig;
    private static String FilePath = "../../resources";

    @Autowired
    public ConfigService(AppConfig appConfig) {
        this.appConfig = appConfig;
        try {
            // Use absolute path to resources directory
            File resourceDirectory = ResourceUtils.getFile("classpath:");
            this.FilePath = resourceDirectory.getAbsolutePath() + File.separator;

            System.out.println("Config file path: " + this.FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setDefaultFilePath(String defaultFilePath) {
        this.FilePath = defaultFilePath;
    }

    public void loadConfigFile(String filePathAndName) {
        ObjectMapper objectMapper = new ObjectMapper(); // Converts JSON data into a Java Object

        try {
            // Parse JSON
            JsonNode rootNode = objectMapper.readTree(new File( filePathAndName));

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
            appConfig.setCustomerBuyingQuantity(rootNode.has("CustomerBuyingQuantity") ? rootNode.get("CustomerBuyingQuantity").asInt() : 0);
            appConfig.setVendorSellingQuantity(rootNode.has("VendorSellingQuantity") ? rootNode.get("VendorSellingQuantity").asInt() : 0);

        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    public void saveConfigFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> configMap = new HashMap<>();
        configMap.put("totalTickets", appConfig.getTotalTickets());
        configMap.put("ticketReleaseRate", appConfig.getTicketReleaseRate());
        configMap.put("customerRetrievalRate", appConfig.getCustomerRetrievalRate());
        configMap.put("maxTicketPoolCapacity", appConfig.getMaxTicketsPoolCapacity());
        configMap.put("CustomerBuyingQuantity", appConfig.getCustomerBuyingQuantity());
        configMap.put("VendorSellingQuantity", appConfig.getVendorSellingQuantity());

        // Use absolute path
        String absolutePath = FilePath + "Configuration.json";
        File configFile = new File(absolutePath);
        System.out.println("Saving config to: " + absolutePath);

        // Create parent directories
        if (!configFile.getParentFile().exists()) {
            boolean created = configFile.getParentFile().mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " + configFile.getParentFile());
            }
        }

        // Create or overwrite file
        if (!configFile.exists()) {
            boolean created = configFile.createNewFile();
            if (!created) {
                throw new IOException("Failed to create file: " + configFile);
            }
        }

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(configFile, configMap);
            System.out.println("Successfully wrote to file: " + absolutePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Add a method to validate file path
    private void ensureValidFilePath() throws IOException {
        File directory = new File(FilePath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Could not create directory: " + FilePath);
            }
        }
        if (!directory.canWrite()) {
            throw new IOException("Cannot write to directory: " + FilePath);
        }
    }

    //Update File data
    public static void updateFIle(Map<String, Integer> configMap) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Use absolute path
        String absolutePath = FilePath + "Configuration.json";
        File configFile = new File(absolutePath);
        System.out.println("Saving config to: " + absolutePath);

        // Create parent directories
        if (!configFile.getParentFile().exists()) {
            boolean created = configFile.getParentFile().mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " + configFile.getParentFile());
            }
        }

        // Create or overwrite file
        if (!configFile.exists()) {
            boolean created = configFile.createNewFile();
            if (!created) {
                throw new IOException("Failed to create file: " + configFile);
            }
        }

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(configFile, configMap);
            System.out.println("Successfully wrote to file: " + absolutePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
