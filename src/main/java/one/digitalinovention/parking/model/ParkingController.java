package one.digitalinovention.parking.model;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.digitalinovention.parking.controllers.dto.ParkingCreateDTO;
import one.digitalinovention.parking.controllers.dto.ParkingDTO;
import one.digitalinovention.parking.controllers.mapping.ParkingMapper;
import one.digitalinovention.parking.service.ParkingService;

@RestController
@RequestMapping("/parking")
@Api(tags = "Controlador do Estacionamento")
public class ParkingController {

	private final ParkingService parkingService;
	private final ParkingMapper parkingMapper;
	
	public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
		this.parkingService = parkingService;
		this.parkingMapper = parkingMapper;
	}
	
	@GetMapping
	@ApiOperation("Buscando Toda a Lista")
	public ResponseEntity<List<ParkingDTO>> findAll(){
		List<Parking> parkingList = parkingService.findAll();
		List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Buscando Dados Por ID")
	public ResponseEntity<ParkingDTO> finById(@PathVariable String id){
		Parking parking = (Parking) parkingService.findById(id);
		ParkingDTO result = parkingMapper.toParkingDTO(parking);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Excluindo Dados Por ID")
	public ResponseEntity delete(@PathVariable String id){
		parkingService.delete(id);	
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ApiOperation("Criando Nova Entrada")
	public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto){
		Parking parkingCreate = parkingMapper.toParkingCreate(dto);
		Parking parking = parkingService.create(parkingCreate);
		ParkingDTO result = parkingMapper.toParkingDTO(parking);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping("/{id}")
	@ApiOperation("Atualizando Entrada")
	public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO dto) {
		Parking parkingCreate = parkingMapper.toParkingCreate(dto);
		Parking parking = parkingService.update(id, parkingCreate);
		ParkingDTO result =  parkingMapper.toParkingDTO(parking);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
