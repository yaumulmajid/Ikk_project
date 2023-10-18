package id.go.lan.ikk.seeder;

import id.go.lan.ikk.module.user.entity.PermissionEntity;
import id.go.lan.ikk.module.user.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@Slf4j
public class PermissionSeeder implements CommandLineRunner {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        try {
            seed();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private void seed() {
        if (permissionRepository.count() == 0) {
            add("role_admin_nasional");
            add("role_koordinator_utama");
            add("role_koordinator_instansi");
            add("role_admin_instansi");
            add("role_enumerator");
        }
    }

    private void add(String permissionName) {
        PermissionEntity permission = new PermissionEntity();
        permission.setName(permissionName);
        permission.setCreatedBy(0L);

        permissionRepository.save(permission);
    }
}
