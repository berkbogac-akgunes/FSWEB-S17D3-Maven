package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> findAll() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala find(@PathVariable Integer id) {
        if(id <= 0) {
            throw new ZooException("Id cannot be lower than 0" + id, HttpStatus.BAD_REQUEST);
        } else if(!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id does not exist" + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala addKoala(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable Integer id, @RequestBody Koala koala) {
        if(id <= 0) {
            throw new ZooException("Id cannot be lower than 0" + id, HttpStatus.BAD_REQUEST);
        } else if(!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id does not exist" + id, HttpStatus.NOT_FOUND);
        }
        koalas.replace(id, koala);

        return koalas.get(koala.getId());
    }

    @DeleteMapping("/{id}")
    public Koala deleteKoala(@PathVariable Integer id) {
        if(id <= 0) {
            throw new ZooException("Id cannot be lower than 0" + id, HttpStatus.BAD_REQUEST);
        } else if(!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id does not exist" + id, HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}
