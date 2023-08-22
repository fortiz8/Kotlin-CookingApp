package com.example.kotlin_cookingapp.presentation.component


import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kotlin_cookingapp.R

@Composable
fun NavBar(
    title: String,
    onBackPress: () -> Unit,
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackPress() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(text = title)
        Spacer(modifier = Modifier.requiredWidth(26.dp))
    }
}