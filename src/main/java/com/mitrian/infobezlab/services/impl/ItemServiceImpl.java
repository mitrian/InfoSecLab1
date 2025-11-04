package com.mitrian.infobezlab.services.impl;

import com.mitrian.infobezlab.data.entities.Item;
import com.mitrian.infobezlab.dto.ItemRequestDTO;
import com.mitrian.infobezlab.dto.ItemResponseDTO;
import com.mitrian.infobezlab.exceptions.ItemValidateException;
import com.mitrian.infobezlab.repositories.ItemRepository;
import com.mitrian.infobezlab.services.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemResponseDTO addItem(ItemRequestDTO itemRequestDTO) {
        validateItemRequest(itemRequestDTO);

        Item item = new Item();
        item.setName(HtmlUtils.htmlEscape(itemRequestDTO.name().trim()));
        item.setPrice(itemRequestDTO.price());

        Item savedItem = itemRepository.save(item);

        return mapToResponseDTO(savedItem);
    }

    @Override
    public List<ItemResponseDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private void validateItemRequest(ItemRequestDTO itemRequestDTO) {
        if (itemRequestDTO == null) {
            throw new ItemValidateException("Item request cannot be null");
        }

        if (itemRequestDTO.name() == null || itemRequestDTO.name().trim().isEmpty()) {
            throw new ItemValidateException("Item name cannot be empty");
        }

        if (itemRequestDTO.price() < 0) {
            throw new ItemValidateException("Price cannot be negative");
        }

        if (itemRequestDTO.price() > 1000000000) {
            throw new ItemValidateException("Price is too high");
        }
    }

    private ItemResponseDTO mapToResponseDTO(Item item) {
        return new ItemResponseDTO(item.getId(), item.getName(), item.getPrice());
    }
}