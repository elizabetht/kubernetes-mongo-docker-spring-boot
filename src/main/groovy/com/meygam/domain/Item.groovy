package com.meygam.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed


class Item {
    @Indexed(unique = true) BigInteger _id
    Number price
    String description
}
