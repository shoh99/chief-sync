package com.example.chiefSync.controller;

import com.example.chiefSync.dto.MenuItemDto;
import com.example.chiefSync.repostiroy.MenuItemRepository;
import com.example.chiefSync.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/menus")
public class MenuItemController {

    private final MenuItemService menuItemService;
    @PostMapping
    public ResponseEntity<MenuItemDto> newMenu(@RequestBody MenuItemDto menuItemDto) {
       MenuItemDto newMenu =  menuItemService.createNewMenu(menuItemDto);
       return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
    }
}
