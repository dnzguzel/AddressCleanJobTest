package org.example;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.junit.Assert;

public class StepImplementation {

    @Step("Read from cvs file <table>")
    public void readFromCsvFile(Table table) {
        Object liste[] = new Object[3];
        for (TableRow row : table.getTableRows()) {
            String value = row.getCell("value");
            System.out.println("Adres: "+value);
            Assert.assertFalse("Address is not same with into the csv file!",
                    value.equals("Istasyon mahallesi dere sokak no:41"));
        }
    }
}
