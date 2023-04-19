package org.example.components.kind;

import org.example.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KindService {
    private final KindRepository kindRepository;
    private final MyKindRepository myKindRepository;
    private final UserService userService;
    @Autowired
    public KindService(KindRepository kindRepository, MyKindRepository myKindRepository,
                       UserService userService) {
        this.kindRepository = kindRepository;
        this.myKindRepository = myKindRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> findAll(){
        Iterable<Kind> kinds = kindRepository.findAll();
        List<KindDto> kinds2 = new ArrayList<>();
        for (Kind kind : kinds){
            kinds2.add(KindDto.fromKind(kind));
        }
        return new ResponseEntity<>(kinds2, HttpStatus.OK);
    }

    public ResponseEntity<?> findById(Long id) {
        Optional<Kind> kind = kindRepository.findById(id);
        if (!kind.isPresent()){
            return new ResponseEntity<>("No such entity", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(kind.get(), HttpStatus.OK);
    }

    public ResponseEntity<?> filter(String name, String eatingWay, String climateZone,
                                         String order){
        Iterable<Kind> kinds = myKindRepository.filter(name, eatingWay, climateZone, order);
        List<KindDto> kinds2 = new ArrayList<>();
        for (Kind kind : kinds){
            kinds2.add(KindDto.fromKind(kind));
        }
        return new ResponseEntity<>(kinds2, HttpStatus.OK);
    }

    public ResponseEntity<String> save(Kind kind, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        kindRepository.save(kind);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (!(kindRepository.findById(id)).isPresent()){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        kindRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Kind kind, Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Kind> kind1 = kindRepository.findById(id);
        if (!kind1.isPresent()){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        kind.setItems(kind1.get().getItems());
        kind.setPets(kind1.get().getPets());
        kind.setId(id);
        kindRepository.save(kind);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

}
