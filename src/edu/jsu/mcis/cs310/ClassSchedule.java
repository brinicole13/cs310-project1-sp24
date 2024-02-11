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
import java.util.LinkedHashMap;

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
       
        JsonObject scheduletype = new JsonObject();
        JsonObject subject = new JsonObject();
        JsonObject course = new JsonObject();
        JsonArray section = new JsonArray();
        
        Iterator <String[]> iterator = csv.iterator();
        
        String [] headers = iterator.next();
      
            HashMap <String, Integer> headerRow = new HashMap<>();
            for (int i = 0; i < headers.length; ++i) {
                headerRow.put(headers[i], i);
            }
            
            while(iterator.hasNext()) {
                String[] row = iterator.next();
            
            String num = row [headerMap.get(NUM_COL_HEADER)];
            String[] splitnum = num.split("");
            String SubjectID = splitnum[0];
            subjectMap.put(SubjectID);
            
            //HASHMAP SCHEDULE
            String Namecourse[] = headerRow.get(NUM_COL_HEADER).toString().split("");
            subjectMap.put(Namecourse[0] = headerRow(SUBJECT_COL_HEADER));
            
            
                @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            LinkedHashMap courseMap = new LinkedHashMap<>();
            courseMap.put(SUBJECTID_COL_HEADER, Namecourse[0]);
            courseMap.put(NUM_COL_HEADER, Namecourse[1]);
            courseMap.put(DESCRIPTION_COL_HEADER, headerRow.get(DESCRIPTION_COL_HEADER));
            
            int credits = Integer.parseInt(headerRow.get(CREDITS_COL_HEADER).toString());
            courseMap.put(CREDITS_COL_HEADER, credits);
            
            
            //HASHMAP SECTION
            LinkedHashMap sectionMap = new LinkedHashMap<>();
            int crn1 = Integer.parseInt(headerRow.get(CRN_COL_HEADER).toString());
            
            sectionMap.put(CRN_COL_HEADER, crn1);
            sectionMap.put(SUBJECTID_COL_HEADER, Namecourse[0]);
            sectionMap.put(NUM_COL_HEADER, Namecourse[1]);
            sectionMap.put(SECTION_COL_HEADER, headerRow.get(SECTION_COL_HEADER));
            sectionMap.put(TYPE_COL_HEADER, headerRow.get(TYPE_COL_HEADER));  
            sectionMap.put(START_COL_HEADER, headerRow.get(START_COL_HEADER));
            sectionMap.put(END_COL_HEADER, headerRow.get(END_COL_HEADER));
            sectionMap.put(DAYS_COL_HEADER, headerRow.get(DAYS_COL_HEADER));
            sectionMap.put(WHERE_COL_HEADER, headerRow.get(WHERE_COL_HEADER));
            
            String Names = headerRow.get(INSTRUCTOR_COL_HEADER).toString();
            String [] instructorArray = Names.split("0,0");
            sectionMap.put(INSTRUCTOR_COL_HEADER, instructorArray);
            section.add(sectionMap);
        }
            //JSONOBJECT
            JsonObject object = new JsonObject();
            object.put("scheduletype", scheduletype);
            object.put("subject", subject);
            object.put("course", course);
            object.put("section", section);
          
            //
            String objectString = Jsoner.serialize(object);
            return objectString;
    }
       
    
    public String convertJsonToCsvString(JsonObject json) {
        String csvString;
        
        StringWriter sWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(sWriter, '\t', '"', '\\', "\n");
        
        String[] headers = {CRN_COL_HEADER, SUBJECT_COL_HEADER, NUM_COL_HEADER,
            DESCRIPTION_COL_HEADER, SECTION_COL_HEADER, TYPE_COL_HEADER,
            CREDITS_COL_HEADER, START_COL_HEADER, END_COL_HEADER, DAYS_COL_HEADER,
            WHERE_COL_HEADER, SCHEDULE_COL_HEADER, INSTRUCTOR_COL_HEADER};
        
        csvWriter.writeNext(headers);
        
        JsonObject scheduletype = (JsonObject) json.get("schedule");
        JsonObject subjects = (JsonObject) json.get("subject");
        JsonObject courses = (JsonObject) json.get("course");
        JsonArray sections = (JsonArray) json.get("section");
        
        String crn, subject, num, description, subjectID, type, credits, start, end, days, where, schedule, instructor;
        for (Object sectionObject : sections) {
            JsonObject courseSection = (JsonObject) sectionObject;
            
            crn = String.valueOf(courseSection.get(CRN_COL_HEADER));
            subject = (String) subjects.get((String) courseSection.get(SUBJECTID_COL_HEADER));
            num = ((String) courseSection.get(SUBJECTID_COL_HEADER)) + " " + ((String) courseSection.get(NUM_COL_HEADER));
            
            JsonObject courseCourse = (JsonObject) courses.get(num);
            
            description = (String) courseCourse.get(DESCRIPTION_COL_HEADER);
            subjectID = (String) courseSection.get(SECTION_COL_HEADER);
            credits = String.valueOf(courseCourse.get(CREDITS_COL_HEADER));
            type = (String) courseSection.get(TYPE_COL_HEADER);
            start = (String) courseSection.get(START_COL_HEADER);
            end = (String) courseSection.get(END_COL_HEADER);
            days = (String) courseSection.get(DAYS_COL_HEADER);
            where = (String) courseSection.get(WHERE_COL_HEADER);
            schedule = (String) scheduletype.get(type);
            
            List <String> instructors = (List <String> ) courseSection.get(INSTRUCTOR_COL_HEADER);
            instructor = String.join(", ", instructors);
            
            csvWriter.writeNext(new String [] {crn, subject, num, description, subjectID, type, credits, start, end, days, where, schedule, instructor});
            
            csvString = sWriter.toString();
            
            return csvString;
        }
        return null;
        
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
        catch (Exception e) { e.printStackTrace(); }
        
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
        catch (Exception e) { e.printStackTrace(); }
        
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
        catch (Exception e) { e.printStackTrace(); }
        
        return buffer.toString();
        
    }

    private String headerRow(String SUBJECT_COL_HEADER) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class subjectMap {

        private static void put(String SubjectID) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public subjectMap() {
        }
    }

    private static class headerMap {

        private static int get(String NUM_COL_HEADER) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public headerMap() {
        }
    }
}


    