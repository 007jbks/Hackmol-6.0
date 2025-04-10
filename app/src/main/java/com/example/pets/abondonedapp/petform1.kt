package com.example.pets.abondonedapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pets.R
import com.example.pets.onboarding.PetButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetFormScreen() {
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var colorMarkings by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var distinctiveFeatures by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBD6E3)) // Light pink background
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Heading text
            Text(
                text = "Let's write this paw's happy\n chapter today",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2980B9), // Blue color from image
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Progress indicators
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF2980B9))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFC4D3E0))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFC4D3E0))
                )
            }

            // Sad dog image
            Image(
                painter = painterResource(id = R.drawable.crying_doggy),
                contentDescription = "Sad dog illustration",
                modifier = Modifier
                    .size(150.dp)
                    .padding(end = 15.dp)
            )

            // Custom "BASIC INFORMATION" button
            PetButton(
                text = "BASIC INFORMATION",
                rightIcon = R.drawable.vector_10,
                showRightIcon = true,
                onClick = {},
                modifier = Modifier.scale(0.75f).fillMaxWidth(0.7f).height(50.dp)
            )

            // Form fields in a row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ShadowedTextField(label = "SPECIE", value = species, onValueChange = { species = it }, modifier = Modifier.weight(1f))
                ShadowedTextField(label = "BREED", value = breed, onValueChange = { breed = it }, modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ShadowedTextField(label = "GENDER", value = gender, onValueChange = { gender = it }, modifier = Modifier.weight(1f))
                ShadowedTextField(label = "COLOR/ MARKINGS", value = colorMarkings, onValueChange = { colorMarkings = it }, modifier = Modifier.weight(1f))
            }
            Row{
            // Approx. Age field
            ShadowedTextField(
                label = "APPROX. AGE",
                value = age,
                onValueChange = { age = it },
                modifier = Modifier.width(175.dp).padding(top = 16.dp)
            )
            Spacer(Modifier.fillMaxWidth(1f))}

            // Distinctive features - multi-line text field


            ShadowedTextField(
                label = "ANY DISTINCTIVE FEATURES",
                value = distinctiveFeatures,
                onValueChange = { distinctiveFeatures = it },

            )

            Text(
                text = "*Write unknown if you're not sure",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF2980B9),
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp, bottom = 16.dp, top = 4.dp)
            )

            // Next button
            Button(
                onClick = { /* Handle next button click */ },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2980B9) // Blue color
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "NEXT",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PetFormPreview() {
    PetFormScreen2()
}

@Composable
fun ShadowedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        // Label with bold blue text and subtle shadow
        Text(
            text = label,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.2f),
                    offset = Offset(2f, 2f),
                    blurRadius = 2f
                )
            ),
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        // Box with shadow, border, and background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Color.Black.copy(alpha = 0.2f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color(0xFFFBD6E3), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 9.sp,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
