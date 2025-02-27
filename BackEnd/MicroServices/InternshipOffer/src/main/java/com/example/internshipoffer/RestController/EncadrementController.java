package com.example.internshipoffer.RestController;

import com.example.internshipoffer.Entity.Encadrement;
import com.example.internshipoffer.Service.EncadrementService;
import com.example.internshipoffer.Service.EncadrementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/encadrements")
public class EncadrementController {

@Autowired
EncadrementServiceImpl encadrementServiceImpl;
    @PostMapping
    public Encadrement createEncadrement(@RequestBody Encadrement encadrement) {
        return encadrementServiceImpl.createEncadrement(encadrement);
    }
    @GetMapping
    public List<Encadrement> getAllEncadrements() {
        return encadrementServiceImpl.getAllEncadrements();
    }

    @PutMapping("/{id}")
    public Encadrement updateEncadrement(@PathVariable Long id, @RequestBody Encadrement encadrement) {
        return encadrementServiceImpl.updateEncadrement(id, encadrement);
    }
    @DeleteMapping("/{id}")
    public void deleteEncadrement(@PathVariable Long id) {
        encadrementServiceImpl.deleteEncadrement(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Encadrement> getEncadrementById(@PathVariable Long id) {
        Optional<Encadrement> encadrement = encadrementServiceImpl.getEncadrementById(id);
        if (encadrement.isPresent()) {
            return new ResponseEntity<>(encadrement.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
