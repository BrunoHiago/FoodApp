package com.example.foodapp.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAlert(onDismissRequest: () -> Unit) {

    val context = LocalContext.current

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Sobre o app",
                        style = MaterialTheme.typography.headlineMedium
                            .copy(color = Color(0xFF8d5b2d))
                            .copy(fontWeight = MaterialTheme.typography.headlineMedium.fontWeight)
                            .copy(fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                            .copy(letterSpacing = MaterialTheme.typography.headlineMedium.letterSpacing)
                            .copy(lineHeight = MaterialTheme.typography.headlineMedium.lineHeight)
                    )

                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable { onDismissRequest() },
                        tint = Color.DarkGray
                    )
                }


                Text(
                    text = "Esse aplicativo oferece uma experiência intuitiva para encontrar restaurantes de diversas categorias, desde lanches até pratos sofisticados.",
                    style = MaterialTheme.typography.bodyMedium
                    .copy(color = Color.Black)
                    .copy(fontWeight = MaterialTheme.typography.bodyMedium.fontWeight)
                    .copy(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
                    .copy(letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/BrunoHiago"))
                                context.startActivity(intent)
                            }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.linkedin),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/bruno-hiago/"))
                                context.startActivity(intent)
                            },
                        tint = Color.Unspecified
                    )
                }
            }

        }


    }
}