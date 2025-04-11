package com.example.pets

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetJournalScreen() {
    val context = LocalContext.current
    var journalText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDDDE6)) // Light pink background
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Title
            Text(
                text = "Your Pet’s Daily Journal",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEE7B17), // Orange
                modifier = Modifier.padding(top = 16.dp)
            )

            // Pet Image
            Box(){
            Image(
                painter = painterResource(R.drawable.dog), // Replace with your image
                contentDescription = "Pet Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
                    .padding(top = 16.dp)
                    .offset(y=-5.dp)
            )}

            // Text Field Card
            Card(
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, Color(0xFFFFA500)), // Orange dashed border
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp)
            ) {
                TextField(
                    value = journalText,
                    onValueChange = { journalText = it },
                    placeholder = { Text("Write about your pet's day...") },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }

            // Submit Button
            Button(
                onClick = {
                    Toast.makeText(context, "Journal Submitted!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D6CDF)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "Submit",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
