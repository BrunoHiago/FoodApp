package com.example.foodapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodapp.data.model.Restaurant


@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    modifier: Modifier,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {


    ListItem(
        modifier = modifier,

        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = Color.Black,
            supportingColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black
        ),
        headlineContent = {
            Text(
                text = restaurant.name,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        },
        supportingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = restaurant.rating.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = " • ",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = restaurant.category,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        },
        leadingContent = {
            AsyncImage(
                model = restaurant.photo,
                contentDescription = "Foto do restaurante",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        },
        trailingContent = {
            Icon(
                if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onFavoriteClick()
                    }
            )
        })
}