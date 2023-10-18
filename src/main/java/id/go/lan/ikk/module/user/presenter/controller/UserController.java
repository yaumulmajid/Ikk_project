package id.go.lan.ikk.module.user.presenter.controller;

import id.go.lan.ikk.module.user.presenter.model.UpdatePasswordUserRequest;
import id.go.lan.ikk.module.user.presenter.model.UpdatePhoneNumberRequest;
import id.go.lan.ikk.module.user.service.UserService;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import io.undertow.util.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    // Update Phone Admin Nasional
    @GetMapping("adminnasional/phone")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getPhoneNumberAdminNasional() {
        return new ResponseEntity<>(userService.getAdminNasionalPhoneNumber(), HttpStatus.OK);
    }

    @PutMapping("adminnasional/phone")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> updatePhoneNumberAdminNasional(@RequestBody UpdatePhoneNumberRequest request) {
        userService.updateAdminNasionalPhoneNumber(request);
        return new ResponseEntity<>("Berhasil update phone number admin nasional", HttpStatus.OK);
    }

    // CRUD Koordinator Utama
    @GetMapping("koordinatorutama")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getAllKoordinatorUtama() {
        return new ResponseEntity<>(userService.getAll("role_koordinator_utama"), HttpStatus.OK);
    }

    @GetMapping("koordinatorutama/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> getKoordinatorUtamaById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("tambah/koordinatorutama")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> addKoordinatorUtama(@RequestBody AddUpdateUserRequest request) throws BadRequestException {
        userService.add(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("ubah/koordinatorutama/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> updateKoordinatorUtama(
            @RequestBody AddUpdateUserRequest request,
            @PathVariable Long id) {

        userService.update(request, id);
        return new ResponseEntity<>("Berhasil edit data koordinator utama", HttpStatus.OK);
    }

    @DeleteMapping("hapus/koordinatorutama/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional')")
    public ResponseEntity<Object> deleteKoordinatorUtama(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("Berhasil hapus koordinator utama", HttpStatus.OK);
    }

    // CRUD Koordinator Intansi
    @GetMapping("koordinatorinstansi")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAllKoordinatorInstansi() {
        return new ResponseEntity<>(userService.getAll("role_koordinator_instansi"), HttpStatus.OK);
    }

    @GetMapping("koordinatorinstansi/{id}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getKoordinatorInstansiByNip(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("tambah/koordinatorinstansi")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> addKoordinatorInstansi(@RequestBody AddUpdateUserRequest request) {
        userService.addKoordinatorInstansi(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("ubah/koordinatorinstansi/{idUser}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> updateKoordinatorInstansi(
            @RequestBody AddUpdateUserRequest request,
            @PathVariable Long idUser) {

        userService.update(request, idUser);
        return new ResponseEntity<>("Berhasil edit data koordinator instansi", HttpStatus.OK);
    }

    @DeleteMapping("hapus/koordinatorinstansi/{id}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> deleteKoordinatorInstansi(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("Berhasil hapus koordinator instansi", HttpStatus.OK);
    }

    // CRUD Admin Intansi
    @GetMapping("admininstansi")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAllAdminInstansi() {
        return new ResponseEntity<>(userService.getAll("role_admin_instansi"), HttpStatus.OK);
    }

    @GetMapping("admininstansi/{id}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getAdminInstansiByNip(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("admininstansi/{id}/enumerator")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> getEnumeratorListByAdminInstansiId(@PathVariable Long id) {
        return new ResponseEntity<>(userService.kiGetEnumeratorList(id), HttpStatus.OK);
    }

    @PostMapping("tambah/admininstansi")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> addAdminInstansi(@RequestBody AddUpdateUserRequest request) {
        userService.addAdminInstansi(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("ubah/admininstansi/{idUser}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> updateAdminInstansi(
            @RequestBody AddUpdateUserRequest request,
            @PathVariable Long idUser) {

        userService.updateAdminInstansi(request, idUser);
        return new ResponseEntity<>("Berhasil edit data admin instansi", HttpStatus.OK);
    }

    @DeleteMapping("hapus/admininstansi/{id}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_instansi')")
    public ResponseEntity<Object> deleteAdminInstansi(@PathVariable Long id) {
        userService.deleteAdminInstansi(id);
        return new ResponseEntity<>("Berhasil hapus admin instansi", HttpStatus.OK);
    }

    // CRUD Enumerator
    @GetMapping("enumerator")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getAllEnumerator() {
        return new ResponseEntity<>(userService.getAll("role_enumerator"), HttpStatus.OK);
    }

    @GetMapping("enumerator/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> getEnumeratorByNip(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("tambah/enumerator")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> addEnumerator(@RequestBody AddUpdateUserRequest request) {
        userService.addEnumerator(request);
        return new ResponseEntity<>("Akun berhasil ditambahkan", HttpStatus.CREATED);
    }

    @PutMapping("ubah/enumerator/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> updateEnumerator(
            @RequestBody AddUpdateUserRequest request,
            @PathVariable Long id) {

        userService.update(request, id);
        return new ResponseEntity<>("Berhasil edit data enumerator", HttpStatus.OK);
    }

    @DeleteMapping("hapus/enumerator/{id}")
    @PreAuthorize("hasAnyAuthority('role_admin_instansi')")
    public ResponseEntity<Object> deleteEnumerator(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("Berhasil hapus enumerator", HttpStatus.OK);
    }

    // Update user password
    @PutMapping("/ubah-password")
    @PreAuthorize("hasAnyAuthority('role_admin_nasional','role_koordinator_utama','role_koordinator_instansi','role_admin_instansi','role_enumerator')")
    public ResponseEntity<Object> updateUserPassword(@RequestBody UpdatePasswordUserRequest request) {
        userService.updatePassword(request);
        return new ResponseEntity<>("Berhasil update password", HttpStatus.OK);
    }
}
