package peaksoft.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.service.StopListService;


import java.util.List;
@RestController
@RequestMapping("/api/stoplists")
public class StopListController {

    private final StopListService stopListService;

    @Autowired
    public StopListController(StopListService stopListService) {
        this.stopListService = stopListService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/save/{menuItemId}")
    public ResponseEntity<SimpleResponse> saveStopList(@RequestBody StopListRequest stopListRequest, @PathVariable Long menuItemId) {
        SimpleResponse response = stopListService.save(stopListRequest, menuItemId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<StopListResponse>> getAllStopLists() {
        List<StopListResponse> stopLists = stopListService.getAll();
        return ResponseEntity.ok(stopLists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StopListResponse> getStopListById(@PathVariable Long id) {
        StopListResponse stopList = stopListService.getById(id);
        return ResponseEntity.ok(stopList);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponse> updateStopList(@PathVariable Long id, @RequestBody StopListRequest stopListRequest) {
        SimpleResponse response = stopListService.update(id, stopListRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteStopList(@PathVariable Long id) {
        SimpleResponse response = stopListService.delete(id);
        return ResponseEntity.ok(response);
    }
}
