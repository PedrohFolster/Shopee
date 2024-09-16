package com.projeto.shopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.StatusDTO;
import com.projeto.shopee.service.StatusService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public List<StatusDTO> getAllStatus() {
        return statusService.getAllStatus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> getStatusById(@PathVariable Long id) {
        StatusDTO status = statusService.getStatusById(id);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) {
        StatusDTO novoStatus = statusService.createStatus(statusDTO);
        return ResponseEntity.ok(novoStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusDTO> updateStatus(@PathVariable Long id, @RequestBody StatusDTO statusDTO) {
        StatusDTO statusAtualizado = statusService.updateStatus(id, statusDTO);
        return ResponseEntity.ok(statusAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        statusService.deleteStatus(id);
        return ResponseEntity.noContent().build();
    }
}


