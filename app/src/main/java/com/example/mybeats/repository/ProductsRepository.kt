package com.example.mybeats.repository

import com.example.mybeats.R
import com.example.mybeats.data.model.Product

class ProductsRepository {

    fun getProducts() : List<Product>{
        val mockedList = listOf(
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa"),
            Product("Fone modelo 02", R.drawable.headset, 4.6, 86, "20,50", "Bluetoof", "Bluetooth 4.1", "Bateria", "16 horas", "18,4 cm", "Semi ativa")
        )
        return mockedList
    }
}