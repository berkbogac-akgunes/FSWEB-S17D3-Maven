package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> findAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo find(@PathVariable Integer id) {
        if(id <= 0) {
            throw new ZooException("Id cannot be lower than 0" + id, HttpStatus.BAD_REQUEST);
        } else if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist" + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo) {
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable Integer id, @RequestBody Kangaroo kangaroo) {
        if(id <= 0) {
            throw new ZooException("Id cannot be lower than 0" + id, HttpStatus.BAD_REQUEST);
        } else if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist" + id, HttpStatus.NOT_FOUND);
        }
        kangaroos.replace(id, kangaroo);

        return kangaroos.get(kangaroo.getId());
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable Integer id) {
        if(id <= 0) {
            throw new ZooException("Id cannot be lower than 0" + id, HttpStatus.BAD_REQUEST);
        } else if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id does not exist" + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}
