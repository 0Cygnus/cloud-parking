package one.digitalinovention.parking.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.digitalinovention.parking.exception.ParkingNotFoundException;
import one.digitalinovention.parking.model.Parking;


@Service
public class ParkingService {

	private static Map<String, Parking> parkingMap = new HashMap();

	static {
		String id = getUUID();
		String id1 = getUUID();
		Parking parking = new Parking(id, "DMS-1111", "SP", "MUSTANG", "AZUL");
		Parking parking1 = new Parking(id1, "JKS-2222", "TO", "PONTIAC", "VERMELHO");
		parkingMap.put(id, parking);
		parkingMap.put(id1, parking1);
	}
	
	public List<Parking> findAll(){
		return parkingMap
				.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	@Transactional
	public Parking findById(String id) {
		Parking parking = parkingMap.get(id);
		if(parking == null) {
			throw new ParkingNotFoundException(id);
		}
		return parking;
	}
	
	@Transactional
	public Parking create(Parking parkingCreate) {
		String uuid = getUUID();
		parkingCreate.setId(uuid);
		parkingCreate.setEntryDate(LocalDateTime.now());
		parkingMap.put(uuid, parkingCreate);
		return parkingCreate;
	}
	
	@Transactional
	public void delete(String id) {
		findById(id);
		parkingMap.remove(id);
	}
	
	@Transactional
	public Parking update(String id, Parking parkingCreate) {
		Parking parking = findById(id);
		parking.setColor(parkingCreate.getColor());
		parkingMap.replace(id, parking);
		return parking;
	}
	
    @Transactional
    public Parking checkOut(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckout.getBill(parking));
        return null ;//parkingRepository.save(parking);
    }

}
