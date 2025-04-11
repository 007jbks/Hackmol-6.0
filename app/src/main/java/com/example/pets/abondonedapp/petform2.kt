package com.example.pets.abondonedapp



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pets.R
import com.example.pets.navigation.Screens
import com.example.pets.onboarding.PetButton

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetFormScreen2(navController: NavHostController) {
    var species by remember { mutableStateOf("") }
    var illness by remember { mutableStateOf("") }
    var wearingColor by remember { mutableStateOf("") }
    var vaccinated by remember { mutableStateOf("") }
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
                        .background(Color(0xFFC4D3E0))
                )
                Spacer(modifier = Modifier.width(8.dp))
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
            }

            // Sad dog image
            Row{

            // Custom "BASIC INFORMATION" button
            PetButton(
                text = "Physical Condition",
                rightIcon = R.drawable.vector_10,
                showRightIcon = true,
                onClick = {},
                modifier = Modifier.scale(0.75f).fillMaxWidth(0.7f).height(50.dp)
            )
            Spacer(Modifier.fillMaxWidth())}
            // Form fields in a row
            Row {
            ShadowedTextField(label = "Overall Health Appearance", value = species, onValueChange = { species = it }, modifier = Modifier.width(250.dp).scale(scaleX = 1f, scaleY = 1.25f))
                Spacer(Modifier.fillMaxWidth())}


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MultiOptionToggle(
                    label = "SIGNS OF ILLNESS",
                    options = listOf("YES", "NO"),
                    selectedOption = illness,
                    onOptionSelected = { illness = it },
                    modifier = Modifier.weight(1f)
                )


                MultiOptionToggle(
                    label = "WEARING COLOR?",
                    options = listOf("YES", "NO"),
                    selectedOption = wearingColor,
                    onOptionSelected = { wearingColor = it },
                    modifier = Modifier.weight(1f)
                )

                MultiOptionToggle(
                    label = "VACCINATED?",
                    options = listOf("YES", "NO"),
                    selectedOption = vaccinated,
                    onOptionSelected = { vaccinated = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Row{
                // Approx. Age field
                MultiOptionToggle(
                    label = "VACCINATED?",
                    options = listOf("YES", "NO"),
                    selectedOption = vaccinated,
                    onOptionSelected = { vaccinated = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.weight(2f))}

            // Distinctive features - multi-line text field

            Row{
            ShadowedTextField(
                label = "ANY Other Identification",
                value = distinctiveFeatures,
                onValueChange = { distinctiveFeatures = it },
                modifier = Modifier.weight(2f).scale(scaleY = 1.25f, scaleX = 1f)
                )
                Spacer(Modifier.weight(1f))}


            Row{
            PetButton(
                text = "BEHAIVIOR",
                rightIcon = R.drawable.vector_10,
                showRightIcon = true,
                onClick = {},
                modifier = Modifier.scale(0.75f).fillMaxWidth(0.7f).height(50.dp)
            )
            Spacer(Modifier.fillMaxWidth())}
            Row{

            MultiOptionToggle(
                label = "WEARING COLOR?",
                options = listOf("Friendly", "Scared ","Agrressive","Lethergic"),
                selectedOption = wearingColor,
                onOptionSelected = { wearingColor = it },

            )
            Spacer(Modifier.fillMaxWidth())}

                Spacer(Modifier.height(10.dp))
            Row{
            MultiOptionToggle(
                label = "WEARING COLOR?",
                options = listOf("Easily", "Cautiously ","Avoiding"),
                selectedOption = wearingColor,
                onOptionSelected = { wearingColor = it },
            )
                Spacer(Modifier.fillMaxWidth())}
            Spacer(Modifier.height(10.dp))
            Row{
            MultiOptionToggle(
                label = "Making any concerning sound",
                options = listOf("Yes","No"),
                selectedOption = wearingColor,
                onOptionSelected = { wearingColor = it },
            )
                Spacer(Modifier.fillMaxWidth())}



            Spacer(Modifier.height(20.dp))

            // Next button
            Button(
                onClick = { navController.navigate(Screens.petrej2) },
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

@Composable
fun MultiOptionToggle(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp, // Smaller font
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(4.dp)) // Reduced spacing

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) Color.White else Color(0xFFFFE6F0))
                        .border(
                            width = if (isSelected) 1.5.dp else 0.75.dp, // Thinner borders
                            color = if (isSelected) Color(0xFF070101) else Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .shadow(
                            elevation = 3.dp, // Smaller shadow
                            shape = RoundedCornerShape(10.dp),
                            ambientColor = Color.Gray.copy(alpha = 0.2f),
                            spotColor = Color.Gray.copy(alpha = 0.1f)
                        )
                        .clickable { onOptionSelected(option) }
                        .padding(horizontal = 12.dp, vertical = 6.dp), // Smaller padding
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        fontSize = 12.sp, // Smaller text
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PetIllnessSection() {
    var selectedOption by remember { mutableStateOf("NO") }

    MultiOptionToggle(
        label = "SIGNS OF ILLNESS",
        options = listOf("YES", "NO"),
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )
}
