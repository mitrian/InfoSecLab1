package com.mitrian.infobezlab.controllers;

import com.mitrian.infobezlab.dto.ItemRequestDTO;
import com.mitrian.infobezlab.dto.ItemResponseDTO;
import com.mitrian.infobezlab.repositories.ItemRepository;
import com.mitrian.infobezlab.services.ItemService;
import com.mitrian.infobezlab.services.impl.ItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //Spotbug P2 fix (Бины stateless, Spring инжектит зависимости и больше никто не имеет к ним доступа)
//    public ItemController(ItemRepository itemRepository) {
//        this.itemService = new ItemServiceImpl(itemRepository);
//    }

//    public ItemController(ItemService itemService) {
//        this.itemService = itemService;
//    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(String.format("Item %s was created", itemService.addItem(itemRequestDTO).name()));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        List<ItemResponseDTO> items=itemService.getAllItems();
        return ResponseEntity.ok(items);
    }
}
