package org.example;

import com.thoughtworks.gauge.*;
import org.junit.Assert;

public class StepImplementation {
    @BeforeScenario
    public void beforeScenario(){
        System.out.println("Test is starting.");
    }

    @Step("Read from cvs file <table>")
    public void readFromCsvFile(Table table) {
        for (TableRow row : table.getTableRows()) {
            String value = row.getCell("value");
            System.out.println("Adres: "+value);
            Assert.assertFalse("Address is not same with into the csv file!",
                    value.equals("Istasyon mahallesi dere sokak no:41"));
        }
    }
    @Step("deneme adımı")
    public void deneme(){
        System.out.println("deneme adımı çalıştı");
    }
    @AfterScenario
    public void afterScenario(){
        System.out.println("Test is finished.");
    }
}
