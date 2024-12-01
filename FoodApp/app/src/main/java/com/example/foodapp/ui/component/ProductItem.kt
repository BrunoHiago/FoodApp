package com.example.foodapp.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodapp.data.model.Product


@SuppressLint("DefaultLocale")
@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier,
) {


    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier,
        headlineContent = {
            Text(
                text = product.name,
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
        },
        supportingContent = {
            Column(
                modifier = Modifier.padding(end = 15.dp)
            ) {
                Text(
                    text = product.description,
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "R$ ${String.format("%.2f", product.price)}",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

        },
        trailingContent = {
            AsyncImage(
                model = product.photo,
                contentDescription = "Foto do produto",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    )

}