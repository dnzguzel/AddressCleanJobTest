import com.thoughtworks.gauge.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Address;
import model.AddressOutput;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

public class AddressCleanTest {

    private final String TOKEN_PATH = "/auth/getToken";
       private final String ADDRESS_CLEAN_PATH = "/rest/address/clean-address";
       private static String token;
       String userName = "hbintegration";
       String password = "admin123";


    @BeforeScenario
    public void beforeScenario(){
        RestAssured.baseURI = "http://10.70.82.44";
        RestAssured.port = 8802;
    }

       private String getToken() {

           Response response = given()
                   .auth()
                   .preemptive()
                   .basic(userName, password)
                   .when()
                   .get(TOKEN_PATH)
                   .then()
                   .statusCode(200)
                   .extract()
                   .response();

           token = response.jsonPath().getString("data.token");
           return token;

       }

    public void createAddress(List<Address> addressList,Table table) {
           int i = 0;
           for (TableRow row : table.getTableRows()) {

               Address address = new Address();
               address.setAddressText(row.getCell("AddressText"));
               address.setDistrict(row.getCell("District"));
               address.setTown(row.getCell("Town"));
               address.setCity(row.getCell("City"));

               addressList.add(address);
           }
    }

    @Step("Clean address check from <table>")
    public void addressCleanTest(Table table) {

        List<Address> addressesList = new ArrayList();
        createAddress(addressesList,table);

        List<AddressOutput> list;
        list = given().header("X-Auth-Token", getToken())
                .contentType("application/json")
                .body(addressesList)
                .when()
                .post(ADDRESS_CLEAN_PATH)
                .then()
                .extract().body().jsonPath().getList("data", AddressOutput.class);

        List<String> expectedText = new ArrayList();
        for(TableRow row: table.getTableRows()) {
            expectedText.add(row.getCell("ExpectedText"));
        }
        List<String> AddressText = new ArrayList();
        for(TableRow row: table.getTableRows()) {
            AddressText.add(row.getCell("AddressText"));
        }
        List<String> cleanedAddress = new ArrayList();

        for (AddressOutput addressOutput : list) {
            cleanedAddress.add(addressOutput.getCleanText());
        }

        int i=0;
        int count=0;
        List<String> inmatchedAddress = new ArrayList();

        Gauge.writeMessage("Inmatched Addresses:");
        Gauge.writeMessage("--------------------");

        for (AddressOutput addressOutput : list) {
            if (!expectedText.get(i).equals(addressOutput.getCleanText())) {
                Gauge.writeMessage("ADDRESSTEXT: "+AddressText.get(i)+", CLEANTEXT: "+addressOutput.getCleanText()+", EXPECTEDTEXT: "+expectedText.get(i));
                inmatchedAddress.add(AddressText.get(i));
                count++;
            }
            i++;
        }
        Assert.assertFalse("All addresses aren't matched.",count>0);
    }
}
