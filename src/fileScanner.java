import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class fileScanner {
    public fileScanner() {}

    public ArrayList<School> getSchools(String file) {
        ArrayList<School> schools = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
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

        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return schools;

    }
}
