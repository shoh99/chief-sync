package com.example.chiefSync.service;


import com.example.chiefSync.dto.MenuItemDto;
import com.example.chiefSync.model.MenuItem;
import com.example.chiefSync.repostiroy.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemDto createNewMenu(MenuItemDto menuItemDto) {
        menuItemRepository.findMenuItemByName(menuItemDto.getName())
                .ifPresent(menuItem -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Menu item with name " + menuItem.getName() + " already exists");
                });
        MenuItem newMenu = new MenuItem();
        newMenu.setName(menuItemDto.getName());
        newMenu.setDescription(menuItemDto.getDescription());
        newMenu.setPrice(menuItemDto.getPrice());

        MenuItem savedMenu = menuItemRepository.save(newMenu);

        MenuItemDto responseDto = new MenuItemDto();
        responseDto.setName(savedMenu.getName());
        responseDto.setDescription(savedMenu.getDescription());
        responseDto.setPrice(savedMenu.getPrice());
        responseDto.setId(savedMenu.getId());

        return responseDto;
    }
}
