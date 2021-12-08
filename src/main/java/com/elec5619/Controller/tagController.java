package com.elec5619.Controller;

import com.elec5619.Entity.Tag;
import com.elec5619.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
public class tagController {

    @Autowired
    TagRepository tagRepository;

    // get all tag
    @GetMapping("/getAllTag")
    public List<Tag> getAllTagName(){
        return tagRepository.findAll();
    }

}
