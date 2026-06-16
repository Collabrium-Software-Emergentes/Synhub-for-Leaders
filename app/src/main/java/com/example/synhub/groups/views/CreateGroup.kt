package com.example.synhub.groups.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.synhub.groups.application.dto.GroupRequest
import com.example.synhub.groups.viewmodel.GroupViewModel
import com.example.synhub.shared.components.TopBar
import com.example.synhub.shared.icons.abcSVG
import com.example.synhub.shared.icons.keyboardSVG
import com.example.synhub.shared.icons.linkSVG

@Composable
fun CreateGroup(nav: NavHostController) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFFFFFFF),
        topBar = {
            TopBar(
                function = {
                    nav.popBackStack()
                },
                "Crear Grupo",
                Icons.AutoMirrored.Filled.ArrowBack
            )
        }
    ){
        innerPadding -> CreateGroupScreen(modifier = Modifier.padding(innerPadding), nav)
    }
}
@Composable
fun CreateGroupScreen(modifier: Modifier, nav: NavHostController) {
    var txtNameGroup by remember { mutableStateOf("") }
    var txtDescriptionGroup by remember { mutableStateOf("") }
    var txtUrlPfp by remember { mutableStateOf("") }

    val groupViewModel: GroupViewModel = viewModel()

    val haveGroup by groupViewModel.haveGroup.collectAsState()

    LaunchedEffect(Unit) {
        groupViewModel.fetchLeaderGroup()
    }

    if (haveGroup) {
        nav.navigate("Group")
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = txtNameGroup,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nombre de grupo")},
            placeholder = { Text(text = "Nombre de grupo")},
            leadingIcon = {
                Icon(
                    imageVector = abcSVG,
                    tint = Color.Gray,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF3F3F3),
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Cyan
            ),
            onValueChange = {txtNameGroup=it}
        )

        OutlinedTextField(
            value = txtDescriptionGroup,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Descripción del grupo")},
            placeholder = { Text(text = "Descripción")},
            leadingIcon = {
                Icon(
                    imageVector = keyboardSVG,
                    tint = Color.Gray,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF3F3F3),
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Cyan
            ),
            onValueChange = {txtDescriptionGroup=it}
        )

        OutlinedTextField(
            value = txtUrlPfp,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Url de la foto de perfil")},
            placeholder = { Text(text = "Url de la foto de perfil")},
            leadingIcon = {
                Icon(
                    imageVector = linkSVG,
                    tint = Color.Gray,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF3F3F3),
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Cyan
            ),
            onValueChange = {txtUrlPfp=it}
        )

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(Color(0xFF4A90E2)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                groupViewModel.createGroup(
                    groupRequest = GroupRequest(
                        name = txtNameGroup,
                        description = txtDescriptionGroup,
                        imgUrl = txtUrlPfp
                    )
                ) { success ->
                    if (success) {
                        groupViewModel.fetchLeaderGroup()
                    }
                }
            }
        ) {
            Text(
                text = "Crear Grupo", fontSize = 20.sp,
                color = Color.White, fontWeight = FontWeight.Bold
            )

        }
    }
}