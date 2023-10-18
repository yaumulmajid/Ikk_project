package id.go.lan.ikk.seeder;

import id.go.lan.ikk.module.policy.entity.ActiveYearEntity;
import id.go.lan.ikk.module.policy.repository.ActiveYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Order(value = 6)
@Slf4j
public class ActiveYearSeeder implements CommandLineRunner {

    @Autowired
    private ActiveYearRepository activeYearRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            seed();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void seed() {
        if (activeYearRepository.count() == 0) {
            Date firstDayOfCurrentYear = new Date(2019 - 1900, 0, 1);
            Date lastDayOfNextTwoYear = new Date(2021 - 1900, 11, 31);

            ActiveYearEntity activeYearEntity = new ActiveYearEntity();
            activeYearEntity.setStartYear(firstDayOfCurrentYear);
            activeYearEntity.setEndYear(lastDayOfNextTwoYear);
            activeYearEntity.setCreatedBy(0L);
            activeYearRepository.save(activeYearEntity);
        }
    }
}
