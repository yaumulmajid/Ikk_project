package id.go.lan.ikk.seeder;

import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.repository.AgencyRepository;
import id.go.lan.ikk.module.user.repository.UserRepository;
import id.go.lan.ikk.module.user.service.UserService;
import io.undertow.util.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 4)
@Slf4j
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Override
    public void run(String... args) {
        try {
            seed();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void seed() throws BadRequestException {
        if (userRepository.count() == 0) {
            AddUpdateUserRequest userRequest = new AddUpdateUserRequest();
            userRequest.setNip("admin_ikk");
            userRequest.setNik("admin_ikk");
            userRequest.setNama("Admin Nasional IKK");
            userRequest.setIdInstansi(77L);
            userRequest.setEmail("admin@ikk.lan.go.id");
            userRequest.setRole("role_admin_nasional");
            userRequest.setStatus("aktif");
            userRequest.setPassword("12345");
            userService.addAdminNasional(userRequest);
        }
    }
}
