package id.go.lan.ikk.seeder;

import id.go.lan.ikk.module.user.entity.RoleEntity;
import id.go.lan.ikk.module.user.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 2)
@Slf4j
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        try {
            seed();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void seed() {
        if (roleRepository.count() == 0) {
            add("role_admin_nasional");
            add("role_koordinator_utama");
            add("role_koordinator_instansi");
            add("role_admin_instansi");
            add("role_enumerator");
        }
    }

    private void add(String roleName) {
        RoleEntity role = new RoleEntity();
        role.setCreatedBy(0L);
        role.setName(roleName);

        roleRepository.save(role);
    }
}
