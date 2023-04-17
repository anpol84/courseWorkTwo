package org.example.components.kind;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/kinds")
public class KindController {
    private KindService kindService;
    @Autowired
    public KindController(KindService kindService) {
        this.kindService = kindService;
    }
    @GetMapping
    public ResponseEntity<String> findAll(@RequestParam(name = "name", required = false) String name,
                                          @RequestParam(name = "eatingWay", required = false) String eatingWay,
                                          @RequestParam(name = "climateZone", required = false) String climateZone,
                                          @RequestParam(name = "order", required = false) String order){
        if (name != null || eatingWay != null || climateZone != null || order != null){
            return kindService.filter(name, eatingWay, climateZone, order);
        }
        return kindService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id){
        return kindService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Kind kind, @NonNull HttpServletRequest request){
        return kindService.save(kind, request);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return kindService.deleteById(id, request);
    }
}
