package org.example.components.kind;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class KindService {
    private final KindRepository kindRepository;

    @Autowired
    public KindService(KindRepository kindRepository) {
        this.kindRepository = kindRepository;

    }

    public List<Kind> findAll(){
        return kindRepository.findAll();
    }

    public Kind findById(Long id) {
        Optional<Kind> kind = kindRepository.findById(id);
        return kind.get();
    }



    public void save(Kind kind){
        kindRepository.save(kind);
    }

    public void deleteById(Long id){
        kindRepository.deleteById(id);
    }

    public void update(Kind kind, Long id){
        Optional<Kind> kind1 = kindRepository.findById(id);
        kind.setItems(kind1.get().getItems());
        kind.setPets(kind1.get().getPets());
        kind.setId(id);
        kindRepository.save(kind);
    }

}
