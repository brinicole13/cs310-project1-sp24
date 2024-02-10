package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

public class ClassSchedule {
    
    private final String CSV_FILENAME = "jsu_sp24_v1.csv";
    private final String JSON_FILENAME = "jsu_sp24_v1.json";
    
    private final String CRN_COL_HEADER = "crn";
    private final String SUBJECT_COL_HEADER = "subject";
    private final String NUM_COL_HEADER = "num";
    private final String DESCRIPTION_COL_HEADER = "description";
    private final String SECTION_COL_HEADER = "section";
    private final String TYPE_COL_HEADER = "type";
    private final String CREDITS_COL_HEADER = "credits";
    private final String START_COL_HEADER = "start";
    private final String END_COL_HEADER = "end";
    private final String DAYS_COL_HEADER = "days";
    private final String WHERE_COL_HEADER = "where";
    private final String SCHEDULE_COL_HEADER = "schedule";
    private final String INSTRUCTOR_COL_HEADER = "instructor";
    private final String SUBJECTID_COL_HEADER = "subjectid";
    
    public String convertCsvToJsonString(List<String[]> csv) {
        
        // Create CSV Structures
        
        JsonObject jsonRoot = new JsonObject();
        
        JsonObject scheduleTypeMap = new JsonObject();
        JsonObject subjectMap = new JsonObject();
        JsonObject courseMap = new JsonObject();
        JsonArray sectionArray = new JsonArray();
        
        HashMap<String, Integer> headerMap = new HashMap<>();
        
        // Iterate CSV Data
        
        Iterator iterator = csv.iterator();
        
        if (iterator.hasNext()) {
            
            String[] headers = (String[])iterator.next();
            
            for (int i = 0; i < headers.length; ++i) {
                headerMap.put(headers[i], i);
            }
        }
        
        while (iterator.hasNext()) {
            
            // Get Next CSV Row
            
           String[] row = (String[])iterator.next();
            
           String typeField = row[headerMap.get(TYPE_COL_HEADER) ];
           String scheduleField = row[headerMap.get(SCHEDULE_COL_HEADER) ];
            
           scheduleTypeMap.put(typeField, scheduleField);
            
           String num = row[headerMap.get(NUM_COL_HEADER) ];
           String subject = row[headerMap.get(SUBJECT_COL_HEADER) ];
           String[] splitnum = num.split("");
           String Subjectid = splitnum[0];
           String Subjectcode = splitnum[1];
           subjectMap.put(Subjectid,subject);
        
           
           if (iterator.hasNext()) {
            
            String[] Coursedetails = (String[])iterator.next();
            
            for (int i = 0; i < Coursedetails.length; ++i) {
                headerMap.put(Coursedetails[i], i);
            }
        }
        
           JsonObject Coursedetails = new JsonObject();
           String description = row[headerMap.get(DESCRIPTION_COL_HEADER) ];
           String credits1 = row[headerMap.get(CREDITS_COL_HEADER) ];
           Integer credits = Integer.valueOf(credits1);
           Coursedetails.put(SUBJECTID_COL_HEADER, Subjectid);
           Coursedetails.put("num",Subjectcode);
           Coursedetails.put(DESCRIPTION_COL_HEADER,description);
           Coursedetails.put(CREDITS_COL_HEADER, credits);
           
           courseMap.put(num, Coursedetails);
           
           if (iterator.hasNext()) {
            
            String[] headers = (String[])iterator.next();
            
            for (int i = 0; i < headers.length; ++i) {
                headerMap.put(headers[i], i);
            }
        
           JsonObject Sectiondetails = new JsonObject();
           String section = row[headerMap.get(SECTION_COL_HEADER) ];
           String start = row[headerMap.get(START_COL_HEADER) ];
           String end = row[headerMap.get(END_COL_HEADER) ];
           String days = row[headerMap.get(DAYS_COL_HEADER) ];
           String where = row[headerMap.get(WHERE_COL_HEADER) ];
           String type = row [headerMap.get(TYPE_COL_HEADER)];
           String instructors = row[headerMap.get(INSTRUCTOR_COL_HEADER) ];
           String crn = row[headerMap.get(CRN_COL_HEADER) ];
           Integer crn1 = Integer.valueOf(crn);
        
           String[] Instructors = instructors.split(",");
           Sectiondetails.put(CRN_COL_HEADER,crn1);
           Sectiondetails.put(SUBJECTID_COL_HEADER, Subjectid);
           Sectiondetails.put(NUM_COL_HEADER, Subjectcode);
           Sectiondetails.put(SECTION_COL_HEADER, section);
           Sectiondetails.put(TYPE_COL_HEADER, type);
           Sectiondetails.put(START_COL_HEADER, start);
           Sectiondetails.put(END_COL_HEADER, end);
           Sectiondetails.put(DAYS_COL_HEADER, days);
           Sectiondetails.put(WHERE_COL_HEADER, where);
           Sectiondetails.put(INSTRUCTOR_COL_HEADER, Instructors);
           
           sectionArray.add(Sectiondetails);
        // Add JSON Containers to Top-Level JSON Structure
        
        jsonRoot.put("scheduletype", scheduleTypeMap);
        jsonRoot.put("subject", subjectMap);
        jsonRoot.put("course", courseMap);
        jsonRoot.put("section", sectionArray);
        
        }
        
        return Jsoner.serialize(jsonRoot);
        
    }
    
    public String convertJsonToCsvString(JsonObject json) {
       
       
    
       
        
        
    }
    
      
    
    public JsonObject getJson() {
        
        JsonObject json = getJson(getInputFileData(JSON_FILENAME));
        return json;
        
    }
    
    public JsonObject getJson(String input) {
        
        JsonObject json = null;
        
        try {
            json = (JsonObject)Jsoner.deserialize(input);
        }
        catch (Exception e) {e.printStackTrace(); }
        
        return json;
        
    }
    
    public List<String[]> getCsv() {
        
        List<String[]> csv = getCsv(getInputFileData(CSV_FILENAME));
        return csv;
        
    }
    
    public List<String[]> getCsv(String input) {
        
        List<String[]> csv = null;
        
        try {
            
            CSVReader reader = new CSVReaderBuilder(new StringReader(input)).withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
            csv = reader.readAll();
            
        }
        catch (Exception e) {e.printStackTrace(); }
        
        return csv;
        
    }
    
    public String getCsvString(List<String[]> csv) {
        
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, '\t', '"', '\\', "\n");
        
        csvWriter.writeAll(csv);
        
        return writer.toString();
        
    }
    
    private String getInputFileData(String filename) {
        
        StringBuilder buffer = new StringBuilder();
        String line;
        
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        try {
        
            BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("resources" + File.separator + filename)));

            while((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            
        }
        catch (Exception e) {e.printStackTrace(); }
        
        return buffer.toString();
        
    }
    
}