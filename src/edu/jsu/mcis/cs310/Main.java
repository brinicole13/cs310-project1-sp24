package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        
        ClassSchedule schedule = new ClassSchedule();
        
        try {

            List<String[]> csvOriginal = schedule.getCsv();
            JsonObject jsonOriginal = schedule.getJson();
            
            // Print Total Sections Found in CSV and JSON Data (should be equal)
            
            System.out.println("Sections Found (CSV): " + (csvOriginal.size() - 1));
            
            JsonArray sections = (JsonArray)jsonOriginal.get("section");
            System.out.println("Sections Found (JSON): " + sections.size());
            
            // Test "convertCsvToJsonString()" Method
            
            String json = schedule.convertCsvToJsonString(csvOriginal);
            System.out.println(json);
            
            String csv = schedule.convertJsonToCsvString(jsonOriginal);
            System.out.println(csv);
            
        }
        catch (Exception e) { e.printStackTrace(); }
            
    }
    
}
