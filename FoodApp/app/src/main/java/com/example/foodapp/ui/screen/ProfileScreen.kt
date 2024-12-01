package com.example.foodapp.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodapp.ui.component.AboutAlert
import com.example.foodapp.viewmodel.UserViewModel


@Composable
fun ProfileScreen(userViewModel: UserViewModel) {

    val user = userViewModel.userState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userViewModel.getUser()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFefdcb3)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            BoxWithConstraints {
                val diameter = maxWidth

                Canvas(
                    modifier = Modifier
                        .width(diameter * 1.5f)
                        .height(diameter)
                        .offset(y = -(diameter / 2))
                ) {
                    drawOval(
                        color = Color(0x4AC0B190),
                        topLeft = Offset(10f, 10f), size = Size(size.width - 20f, size.height - 20f)
                    )

                    drawCircle(
                        color = Color(0xFFd9b88a),
                        radius = (size.width / 2) * 0.9f
                    )

                    drawCircle(
                        color = Color(0x7E8D5B2D),
                        radius = (size.width / 2) * 0.8f
                    )

                }
            }


        }

        Column(
            modifier = Modifier
                .offset(y = 50.dp)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AsyncImage(
                modifier = Modifier
                    .size(200.dp)
                    .border(6.dp, Color(0xFF8d5b2d), CircleShape)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                model = user.value?.photo,
                contentDescription = "Foto de perfil"
            )


            Text(
                text = user.value?.name ?: "",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 40.sp
            )

            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = Color(0xFF8d5b2d),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = user.value?.email ?: "",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }



            Column(
                modifier = Modifier.fillMaxSize(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(150.dp)
                        .height(40.dp),
                    onClick = { showDialog = true },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8d5b2d),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.large,
                    elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 20.dp
                    )
                ) {
                    Text(text = "Sobre")
                }
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(150.dp)
                        .height(40.dp),
                    onClick = {
                        userViewModel.logout(context)
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8d5b2d),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.large,
                    elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 20.dp
                    )
                ) {
                    Text(text = "Sair")
                }

            }
        }

    }

    if (showDialog) {
        AboutAlert(onDismissRequest = { showDialog = false })

    }


}

