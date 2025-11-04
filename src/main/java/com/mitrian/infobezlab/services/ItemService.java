package com.mitrian.infobezlab.services;

import com.mitrian.infobezlab.data.entities.Item;
import com.mitrian.infobezlab.dto.ItemRequestDTO;
import com.mitrian.infobezlab.dto.ItemResponseDTO;

import java.util.List;

public interface ItemService {
    public ItemResponseDTO addItem(ItemRequestDTO itemRequestDTO);
    public List<ItemResponseDTO> getAllItems();
}
