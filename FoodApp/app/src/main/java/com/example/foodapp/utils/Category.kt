package com.example.foodapp.utils

import androidx.annotation.DrawableRes
import com.example.foodapp.R

data class CategoryItem(val name: String, @DrawableRes val image: Int)

enum class Category(val displayName: String, @DrawableRes val imageRes: Int) {
    LANCHES("Lanches", R.drawable.ic_lanches),
    JAPONESA("Japonesa", R.drawable.ic_japonesa),
    PIZZA("Pizza", R.drawable.ic_pizza),
    BRASILEIRA("Brasileira", R.drawable.ic_brasileira),
    ITALIANA("Italiana", R.drawable.ic_italiana),
    CHINESA("Chinesa", R.drawable.ic_chinesa),
    MEXICANA("Mexicana", R.drawable.ic_mexicana),
    VEGETARIANA("Vegetariana", R.drawable.ic_vegetariana),
    DOCES("Doces", R.drawable.ic_doces),
    BEBIDAS("Bebidas", R.drawable.ic_bebidas),
    CAFETERIA("Cafeteria", R.drawable.ic_cafeteria),
    SORVETES("Sorvetes", R.drawable.ic_sorvetes),
    SAUDAVEL("Saudável", R.drawable.ic_saudavel);

    companion object {
        fun fromDisplayName(name: String): Category? {
            return entries.find { it.displayName.equals(name, ignoreCase = true) }
        }


    }
}