import com.thoughtworks.gauge.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Address;
import model.AddressOutput;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddressCleanTest {

    private final String TOKEN_PATH = "/auth/getToken";
       private final String ADDRESS_CLEAN_PATH = "/rest/address/clean-address";
       private static String token;
       String userName = "hbintegration";
       String password = "admin123";
       String param1;
       String param2;


    @BeforeScenario
    public void beforeScenario(){
        RestAssured.baseURI = "http://10.70.82.44";
        RestAssured.port = 8802;
        param1=System.getProperty("param1");
        param2=System.getenv("param1");
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
        createAddress(addressesList, table);

        List<AddressOutput> list;
        list = given().header("X-Auth-Token", getToken())
                .contentType("application/json")
                .body(addressesList)
                .when()
                .post(ADDRESS_CLEAN_PATH)
                .then()
                .extract().body().jsonPath().getList("data", AddressOutput.class);

        List<String> AddressText = new ArrayList();
        List<String> expectedText = new ArrayList();
        List<Long> districtId = new ArrayList();
        List<Long> districtXdockId = new ArrayList();

        for (TableRow row : table.getTableRows()) {
            AddressText.add(row.getCell("AddressText"));
            expectedText.add(row.getCell("ExpectedText"));
            districtId.add((Long.parseLong(row.getCell("DistrictId"))));
            districtXdockId.add(Long.parseLong(row.getCell("DistrictXdockId")));
        }

        int total=0;
        int textCount = 0;
        int districtCount = 0;
        int districtXdockCount = 0;

        Gauge.writeMessage("Comparison results:");
        Gauge.writeMessage("--------------------");

            for (AddressOutput addressOutput : list) {
                if (!expectedText.get(total).equals(addressOutput.getCleanText())) {
                    int row = total+1;
                    Gauge.writeMessage(row+") CleanText comparing error! CLEANTEXT: "+addressOutput.getCleanText()+" || EXPECTED CLEANTEXT: "+expectedText.get(total));
                    textCount++;
                }
                if (!districtId.get(total).equals(addressOutput.getDistrictId())) {
                    int row = total+1;
                    Gauge.writeMessage(row+") DistrictId comparing error! DISTRICTID: "+addressOutput.getDistrictId()+" || EXPECTED DISTRICTID: "+districtId.get(total));
                    districtCount++;
                }
                if (addressOutput.getDistrictXdockId()!=0){

                    if (!districtXdockId.get(total).equals(addressOutput.getDistrictXdockId())) {
                        int row = total+1;
                        Gauge.writeMessage(row+") DistrictXdockId comparing error! DISTRICTXDOCKID: "+addressOutput.getDistrictXdockId()+" || EXPECTED DISTRICTXDOCKID: "+districtXdockId.get(total));
                        districtXdockCount++;
                    }
                }else{
                    if (!districtXdockId.get(total).equals(addressOutput.getTownXdockId())) {
                        int row = total+1;
                        Gauge.writeMessage(row+") DistrictXdockId comparing error! DISTRICTXDOCKID: "+addressOutput.getTownXdockId()+" || EXPECTED DISTRICTXDOCKID: "+districtXdockId.get(total));
                        districtXdockCount++;
                    }
                }
                total++;
            }
            if(textCount>0 || districtCount>0 || districtXdockCount>0){
                Assert.assertFalse("All addresses does not match.",textCount>0 || districtCount>0 || districtXdockCount>0);
            }else{
                Gauge.writeMessage("All addresses are match.");
            }
        }
    @Step("Parametre kontrol")
    public void checkParemter(){

        //name and value of all environment variable in Java  program
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            Gauge.writeMessage("%s=%s%n", envName, env.get(envName));
        }

//        if (param2.equals("true")){
//            System.out.println("Parametre true dondu");
//        }
//        System.out.println("parametre false dondu");
    }
}
