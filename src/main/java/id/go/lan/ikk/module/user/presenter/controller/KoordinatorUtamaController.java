package id.go.lan.ikk.module.user.presenter.controller;

import id.go.lan.ikk.module.user.model.GetUserDetailDto;
import id.go.lan.ikk.module.user.presenter.model.AddUpdateUserRequest;
import id.go.lan.ikk.module.user.presenter.model.KoordinatorUtamaAddUpdateUserRequest;
import id.go.lan.ikk.module.user.service.KoordinatorUtamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class KoordinatorUtamaController {

    @Autowired
    private KoordinatorUtamaService koordinatorUtamaService;

    @GetMapping("/koordinatorutama/list/koordinatorinstansi/semua")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAllKoordinatorInstansi() {
        List<GetUserDetailDto> responseBody = koordinatorUtamaService.getAllKoordinatorInstansi();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/koordinatorutama/list/koordinatorinstansi")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getKoordinatorUtamaKoordinatorInstansi() {
        List<GetUserDetailDto> responseBody = koordinatorUtamaService.getKoordinatorUtamaKoordinatorInstansi();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/koordinatorutama/list/koordinatorinstansi/tambah/{idKoordinatorInstansi}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> addKoordinatorInstansiToListById(@PathVariable Long idKoordinatorInstansi) {
        koordinatorUtamaService.addKoordinatorInstansiToListById(idKoordinatorInstansi);
        return new ResponseEntity<>("Koordinator Instansi berhasil ditambah", HttpStatus.CREATED);
    }

    @DeleteMapping("/koordinatorutama/list/koordinatorinstansi/hapus/{idKoordinatorInstansi}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> deleteKoordinatorInstansiToListById(@PathVariable Long idKoordinatorInstansi) {
        koordinatorUtamaService.deleteKooordinatorInstansiFromListById(idKoordinatorInstansi);
        return new ResponseEntity<>("Koordinator Instansi berhasil dihapus", HttpStatus.OK);
    }

    @GetMapping("/koordinatorutama/user/admininstansi/{idAdmin}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAdminInstansiById(@PathVariable Long idAdmin) {
        GetUserDetailDto responseBody = koordinatorUtamaService.getAdminInstansi(idAdmin);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/koordinatorutama/user/admininstansi/koordinatorinstansi/{idKoordinatorInstansi}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> getAllAdminInstansiByKoordinatorInstansiId(@PathVariable Long idKoordinatorInstansi) {
        List<GetUserDetailDto> responseBody = koordinatorUtamaService.getAllAdminInstansiByKoordinatorInstansiId(idKoordinatorInstansi);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/koordinatorutama/user/admininstansi")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> addAdminInstansi(@RequestBody KoordinatorUtamaAddUpdateUserRequest requestBody) {
        koordinatorUtamaService.addAdminInstansi(requestBody);
        return new ResponseEntity<>("Admin Instansi berhasil dibuat", HttpStatus.CREATED);
    }

    @PutMapping("/koordinatorutama/user/admininstansi/{idAdmin}")
    @PreAuthorize("hasAnyAuthority('role_koordinator_utama')")
    public ResponseEntity<Object> updateAdminInstansById(
            @PathVariable Long idAdmin,
            @RequestBody AddUpdateUserRequest requestBody) {
        koordinatorUtamaService.updateAdminInstansi(idAdmin, requestBody);
        return new ResponseEntity<>("Admin Instansi berhasil diperbaharui", HttpStatus.OK);
    }

    @DeleteMapping("/koordinatorutama/user/admininstansi/{idAdmin}")
    public ResponseEntity<Object> deleteAdminInstansiById(@PathVariable Long idAdmin) {
        koordinatorUtamaService.deleteAdminInstansi(idAdmin);
        return new ResponseEntity<>("Admin Instansi berhasil dihapus", HttpStatus.OK);
    }
}
