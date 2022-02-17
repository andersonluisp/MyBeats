package com.example.mybeats.data.repository

import com.example.mybeats.data.remote.model.ProductBody
import com.example.mybeats.data.remote.model.ProductsBody

object FakeProductsRepository {

    fun getProductsBody() : ProductsBody{
        return ProductsBody(
            listOf(
                ProductBody(
                    autonomy = "16 Horas",
                    capture = "Semi ativa",
                    compatibility = "Bluetooth 4.1",
                    connection = "Bluetooth",
                    height = "18,4 cm",
                    imageUrl = "imageUrl",
                    model = "Fone modelo 02",
                    powerSupply = "Bateria",
                    price = "20,50",
                    rating = 4.6,
                    reviews = 86
                )
            )
        )
    }
}