package com.example.synhub.shared.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    function: () -> Unit,
    title: String,
    icon: ImageVector,
    actions: (@Composable () -> Unit)? = null
){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFDFDFD),
            titleContentColor = Color.Black
        ),
        title = {
            Text( text = title,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton( onClick = {
                function()
            }) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        actions = {
            actions?.invoke()
        }
    )
}