package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cheques")
public class ChequeController {

    private final ChequeService chequeService;

    @Autowired
    public ChequeController(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PreAuthorize("hasAnyAuthority('WAITER','ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<SimpleResponse> saveCheque(@RequestBody ChequeRequest chequeRequest) {
        SimpleResponse response = chequeService.save(chequeRequest);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ChequeResponse>> getAllCheques() {
        List<ChequeResponse> cheques = chequeService.getAll();
        return ResponseEntity.ok(cheques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChequeResponse> getChequeById(@PathVariable Long id) {
        ChequeResponse cheque = chequeService.getById(id);
        return ResponseEntity.ok(cheque);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ChequeResponse> updateCheque(@PathVariable Long id, @RequestBody ChequeRequest chequeRequest) {
        ChequeResponse response = chequeService.update(id, chequeRequest);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteCheque(@PathVariable Long id) {
        SimpleResponse response = chequeService.delete(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/sum/{id}/{date}")
    public Double getCheckTodaySum(@PathVariable LocalDate date, @PathVariable Long id){
        return chequeService.getChequeAverageSum(id,date);
    }
}

