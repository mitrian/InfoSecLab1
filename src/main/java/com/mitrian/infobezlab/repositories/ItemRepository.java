package com.mitrian.infobezlab.repositories;

import com.mitrian.infobezlab.data.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
