package org.example.components.kind;

import org.example.components.empolyee.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/api/v1/kinds")
public class KindController {
    private KindService kindService;
    @Autowired
    public KindController(KindService kindService) {
        this.kindService = kindService;
    }
    @GetMapping
    public String findAll(Model model){
        List<Kind> kinds = kindService.findAll();
        model.addAttribute("kinds", kinds);
        return "kind";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("kind", kindService.findById(id));
        return "kind_info";
    }
    @GetMapping("/update")
    public String updatePage(@ModelAttribute Kind kind, @RequestParam Long id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("kind", kind);
        return "kind_put";
    }

    @PostMapping
    public String save(@ModelAttribute Kind kind, Model model){
        kindService.save(kind);
        return findAll(model);
    }

    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        kindService.deleteById(id);
        return findAll(model);
    }

    @PutMapping
    public String update(@ModelAttribute Kind kind, @RequestParam Long id, Model model){
        kindService.update(kind, id);
        return findAll(model);
    }
}
