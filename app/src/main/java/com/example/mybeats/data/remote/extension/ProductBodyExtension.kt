package com.example.mybeats.data.remote.extension

import com.example.mybeats.data.model.Product
import com.example.mybeats.data.remote.model.ProductBody

fun ProductBody.toModel(): Product {
    return Product(
        model = this.model,
        imageUrl = this.imageUrl,
        rating = this.rating,
        reviews = this.reviews,
        price = this.price,
        connection = this.connection,
        compatibility = this.compatibility,
        powerSupply = this.powerSupply,
        autonomy = this.autonomy,
        height = this.height,
        capture = this.capture
    )
}
