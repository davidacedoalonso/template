package demo.cucumber;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.web.server.LocalServerPort;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class StepsDefs extends FeatureConfiguration {
    private List<Object> request = new ArrayList<>();
    private List<Response> response = new ArrayList<>();
    private String jsonString;
    private Headers headers;
    @LocalServerPort
    private String port;

    @Given("I have a {string} json:")
    public void i_have_a_Successful_json(String escenario, DataTable dataTable) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        String file = "templatejson/" + escenario + ".json";
        for (int i = 1; i < dataTable.width(); i++) {
            DocumentContext json = JsonPath.parse(JSONUtils.getFixtureFromFile(file));
            for (List<String> row : dataTable.rows(1).asLists()) {
                String key = row.get(0);
                String value = "$." + row.get(0);
                json.set(key, value);
            }
            jsonString = json.jsonString().replaceAll("\"null\"", "null");
            request.add(jsonString);
            headers = new Headers(new Header(CONTENT_TYPE, APPLICATION_JSON_VALUE),
                    new Header(ACCEPT, APPLICATION_JSON_VALUE)
            );
        }
    }

    @When("I make a post call")
    public void i_make_a_post_call() {
        response.add(WebRequestUtils.postCall(headers, "http://localhost:" + port + "/some/some", jsonString));
    }

    @Then("The response status should be {int}")
    public void the_response_status_should_be(int status) {
        // Write code here that turns the phrase above into concrete actions
        for (Response response : response) {
            assertEquals(status, response.getStatusCode());
        }
    }

    @Then("Check response:")
    public void check_json(DataTable dataTable) throws Exception {
        List<Map<String, String>> expectResult = dataTable.asMaps();
        for (int i = 0; i < response.size(); i++) {
            for (Map<String, String> row : expectResult) {
                String valueExpected = processResponseExpected(i, row.get("Value"));
                String currentValue = Objects.toString(response.get(i).body().jsonPath().get(row.get("Field")), "null");
                assertEquals(valueExpected, currentValue);
            }
        }
    }

    private String processResponseExpected(int i, String value) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (value.startsWith("request(")) {
            String requestKey = value.substring(value.indexOf("(") + 1, value.indexOf(")"));
            return (String) PropertyUtils.getNestedProperty(request.get(i), requestKey);
        } else {
            return value;
        }
    }
}
