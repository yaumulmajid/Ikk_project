package id.go.lan.ikk.seeder;

import id.go.lan.ikk.module.user.entity.CoordinatorTypeEntity;
import id.go.lan.ikk.module.user.repository.CoordinatorTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 7)
@Slf4j
public class CoordinatorTypeSeeder implements CommandLineRunner {

    @Autowired
    private CoordinatorTypeRepository coordinatorTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            seed();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void seed() {
        if (coordinatorTypeRepository.count() == 0) {
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 1"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 2"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 3"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 4"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 5"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 6"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 7"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 8"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 9"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kementerian 10"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 1"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 2"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 3"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 4"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 5"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 6"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 7"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 8"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 9"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 10"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 11"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 12"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 13"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 14"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("LPNK 15"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 1"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 2"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 3"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 4"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 5"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 6"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 7"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Jawa-Bali 8"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 1"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 2"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 3"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 4"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 5"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 6"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 7"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sulawesi-Maluku-Papua 8"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 1"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 2"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 3"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 4"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 5"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 6"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 7"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Kalimantan-NTB-NTT 8"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 1"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 2"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 3"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 4"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 5"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 6"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 7"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 8"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 9"));
            coordinatorTypeRepository.save(new CoordinatorTypeEntity("Sumatera 10"));
        }
    }
}
