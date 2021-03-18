package org.example;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.datastore.ScenarioDataStore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class GaugeTest {

    private HashSet<Character> vowels;

    @Step("Vowels in English language are <vowelString>.")
    public void setLanguageVowels(String vowelString) {
        vowels = new HashSet<>();
        for (char ch : vowelString.toCharArray()) {
            vowels.add(ch);
        }
    }

    @Step("The word <word> has <expectedCount> vowels.")
    public void verifyVowelsCountInWord(String word, int expectedCount) {
        int actualCount = countVowels(word);
        assertThat(expectedCount).isEqualTo(actualCount);
    }

    @Step("Almost all words have vowels <wordsTable>")
    public void verifyVowelsCountInMultipleWords(Table wordsTable) {
        for (TableRow row : wordsTable.getTableRows()) {
            String word = row.getCell("Word");
            int expectedCount = Integer.parseInt(row.getCell("Vowel Count"));
            int actualCount = countVowels(word);

            assertThat(expectedCount).isEqualTo(actualCount);
        }
    }
    @Step("Read from cvs file <usersTable>")
    public void readFromCsvFile(Table usersTable) {
        Object liste[] = new Object[3];
        for (TableRow row : usersTable.getTableRows()) {
            String name = row.getCell("name");
            String surname = row.getCell("surname");
            System.out.println("Merhaba "+name+" "+surname);
        }
    }

    private int countVowels(String word) {
        int count = 0;
        for (char ch : word.toCharArray()) {
            if (vowels.contains(ch)) {
                count++;
            }
        }
        return count;
    }


    private static void parseEmployeeObject(JSONObject employee){
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        System.out.println(lastName);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);
    }
    @BeforeScenario(tags = {"test"})
    public void loginUser(ExecutionContext context) {
//        String scenarioName = context.getCurrentScenario().getName();
        System.out.println("=====BeforeScenario is running=====");
    }
    @AfterSpec(tags = {"test"})
    public void performAfterSpec(ExecutionContext context) {
//        Specification currentSpecification = context.getCurrentSpecification();
        System.out.println("=====AfterScenario is running=====");
    }
    @Step("Tablodaki verileri konsola yazdır <peoples>")
    public void tableDriven(Table peoples){
        for(TableRow row : peoples.getTableRows()){
            String name=row.getCell("name");
            String surname=row.getCell("surname");
            System.out.println("Merhaba benim adım soyadım "+name+" "+surname+" dur.");
        }
    }
    @Step("Say <greeting> to <product name>")
    public void helloWorld(String greeting, String name) {
        System.out.println(greeting+" "+name);
    }
    public enum Direction { NORTH, SOUTH, EAST, WEST; }

    @Step("Navigate towards <direction>")
    public void navigate(Direction direction) {
        switch (direction){
            case NORTH :
                System.out.println("You are in the NORTH");
                break;
            case SOUTH :
                System.out.println("You are in the SOUTH");
                break;
            case EAST :
                System.out.println("You are in the EAST");
                break;
            case WEST :
                System.out.println("You are in the WEST");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }
    @Step("dataStore deneme")
    public void dataStore(){
        ScenarioDataStore.put("Name","Deniz");
        ScenarioDataStore.put("Surname","Guzel");



        System.out.print(ScenarioDataStore.get("Name")+" "+ScenarioDataStore.get("Surname"));
    }
    @Step("Read json file")
    public void readJson(){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("Files//employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
