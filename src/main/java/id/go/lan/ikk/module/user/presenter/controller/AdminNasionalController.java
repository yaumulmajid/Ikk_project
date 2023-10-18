package id.go.lan.ikk.module.user.presenter.controller;

import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.AdminNasionalAddUpdateUserRequest;
import id.go.lan.ikk.module.user.service.AdminNasionalService;
import io.undertow.util.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/adminnasional")
public class AdminNasionalController {

    @Autowired
    private AdminNasionalService adminNasionalService;

    @GetMapping("semua")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getAllUser(@RequestParam(name = "role") String role) {
        return new ResponseEntity<>(adminNasionalService.getAllUser(role), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(adminNasionalService.getUserById(userId), HttpStatus.OK);
    }

    // Koordinator Instansi
    @PostMapping("tambah/koordinatorinstansi")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> addKoordinatorInstansi(
            @RequestBody AdminNasionalAddUpdateUserRequest request) {
        adminNasionalService.addKoordinatorInstansi(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    // Admin Instansi
    @PostMapping("tambah/admininstansi")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> addAdminInstansi(
            @RequestBody AdminNasionalAddUpdateUserRequest request) {
        adminNasionalService.addAdminInstansi(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    // Enumerator
    @PostMapping("tambah/enumerator")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> addEnumerator(
            @RequestBody AdminNasionalAddUpdateUserRequest request) throws BadRequestException {
        adminNasionalService.addEnumerator(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("ubah/{userId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> updateUser(
            @RequestBody AddUpdateUserRequest request, @PathVariable Long userId) {
        adminNasionalService.update(userId, request);
        return new ResponseEntity<>("Akun berhasil diubah", HttpStatus.OK);
    }

    @DeleteMapping("hapus/{userId}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        adminNasionalService.delete(userId);
        return new ResponseEntity<>("Akun berhasil dihapus", HttpStatus.OK);
    }

}
