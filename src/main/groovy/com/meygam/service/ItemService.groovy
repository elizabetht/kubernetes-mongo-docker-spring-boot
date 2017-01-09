package com.meygam.service

import com.meygam.domain.Item
import com.meygam.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {

    @Autowired
    ItemRepository itemRepository

    List<Item> getItems() {
        itemRepository.findAll()
    }

    String save(Item item) {
        itemRepository.save(item)._id
    }
}
