package main.java;

import java.io.*;
import java.util.ArrayList;

public class fileScanner {
    public fileScanner() {}

    public ArrayList<School> getSchools(String file) {
        ArrayList<School> schools = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(file)){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split(";");
                String schoolName = tokens[0];
                String schoolCity = tokens[1];
                String schoolScore = tokens[2];
                String newSchoolScore = schoolScore.replace(".", "");

                Integer score = Integer.valueOf(newSchoolScore);

                schools.add(new School(schoolName, schoolCity, score));
            }
        }


        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return schools;

    }
}
