package com.meygam.repository

import com.meygam.domain.Item
import org.springframework.data.mongodb.repository.MongoRepository


interface ItemRepository extends MongoRepository<Item, String> {
}