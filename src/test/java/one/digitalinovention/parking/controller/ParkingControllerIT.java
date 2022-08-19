package one.digitalinovention.parking.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import one.digitalinovention.parking.controllers.dto.ParkingCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerIT extends AbstractContainerBase{

	@LocalServerPort
	private int randomPort;
	
	@BeforeEach
	public void setUpTest() {
		RestAssured.port = randomPort;
	}
	
	@Test
	void whenfindAllThenCheckResult() {
		RestAssured.given()
		.when()
		.get("/parking")
		.then()
		.statusCode(HttpStatus.OK.value())
		.body("License[0]", Matchers.equalTo("DMS-1111"));
	}
	
	@Test
	void whenCreateThenCheckResult() {
		
		var createDTO = new ParkingCreateDTO();
		createDTO.setColor("Amarelo");
		createDTO.setLicense("555-ASDA");
		createDTO.setModel("uno");
		createDTO.setState("PR");
		
		RestAssured.given()
		.when()
		.contentType("") //APPLICATION_JSON_VALUE
		.body(createDTO)
		.post("/parking")
		.then()
		.statusCode(HttpStatus.CREATED.value())
		.body("License", Matchers.equalTo("555-ASDA"));
	}
	
}
