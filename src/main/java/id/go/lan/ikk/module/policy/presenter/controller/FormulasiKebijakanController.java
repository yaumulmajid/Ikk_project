package id.go.lan.ikk.module.policy.presenter.controller;

import id.go.lan.ikk.module.policy.model.FormulasiKebijakanDto;
import id.go.lan.ikk.module.policy.service.FormulasiKebijakanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("kebijakan/enumerator")
public class FormulasiKebijakanController {

    @Autowired
    private FormulasiKebijakanService formulasiKebijakanService;

    @GetMapping("/formulasikebijakan/{idKebijakan}")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> getFormulasiKebijakanByPolicyId(@PathVariable Long idKebijakan) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.getFormulasiKebijakanByPolicyId(idKebijakan);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b1a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB1A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB1A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b1b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB1B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB1B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b2a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB2A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB2A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b2b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB2B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB2B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b3a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB3A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB3A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b3b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB3B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB3B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b3c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB3C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB3C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b4a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB4A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB4A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b4b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB4B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB4B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b4c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB4C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB4C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b5a")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB5A(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB5A(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b5b")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB5B(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB5B(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/b5c")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitB5C(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitB5C(idKebijakan, answer, file);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/formulasikebijakan/{idKebijakan}/informasib6")
    @PreAuthorize("hasAnyAuthority('role_enumerator')")
    public ResponseEntity<Object> submitInformasiB6(
            @PathVariable("idKebijakan") Long idKebijakan,
            @RequestParam("answer") String answer) {
        FormulasiKebijakanDto responseBody = formulasiKebijakanService.submitInformasiB6(idKebijakan, answer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
