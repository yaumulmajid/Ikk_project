package id.go.lan.ikk.module.policy.presenter.controller;

import id.go.lan.ikk.module.policy.model.ImplementasiKebijakanDto;
import id.go.lan.ikk.module.policy.service.ImplementasiKebijakanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("kebijakan/enumerator")
public class ImplementasiKebijakanController {

    @Autowired
    private ImplementasiKebijakanService implementasiKebijakanService;

    @GetMapping("/implementasikebijakan/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getImplementasiKebijakanByPolicyId(@PathVariable Long idKebijakan) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.getImplementasiKebijakanByPolicyId(idKebijakan);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c1a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC1A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC1A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c1b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC1B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC1B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c1c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC1C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC1C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c1d")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC1D(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC1D(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c2a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC2A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC2A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c2b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC2B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC2B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c2c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC2C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC2C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c3a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC3A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC3A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c3b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC3B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC3B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/c3c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitC3C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitC3C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/implementasikebijakan/{idKebijakan}/informasic4")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitInformasiC4(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer) {
        ImplementasiKebijakanDto responseBody = implementasiKebijakanService.submitInformasiC4(idKebijakan, answer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
