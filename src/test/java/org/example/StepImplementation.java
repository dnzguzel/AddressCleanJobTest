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
        String[] liste= {"Osmanyılmaz mah.","Istasyon mahallesi dere sokak no:41ss","Fatih mah cami sok n 12 d:3"};
        int i = 0;
        for (TableRow row : table.getTableRows()) {
            String value = row.getCell("value");
            System.out.println("Adres: "+value);
            Assert.assertEquals(value,liste[i]);
            System.out.println(i+". address is matched.");
            i++;
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
