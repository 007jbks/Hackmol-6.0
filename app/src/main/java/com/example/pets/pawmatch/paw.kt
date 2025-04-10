package com.example.pets.pawmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pets.R

// Custom shape class for the concave bottom edge
class ConcaveBottomShape(private val curveHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                // Start from the top left
                moveTo(0f, 0f)
                // Line to the top right
                lineTo(size.width, 0f)
                // Line to right side just above where curve starts
                lineTo(size.width, size.height - curveHeight)
                // Draw curve from right to left
                cubicTo(
                    size.width, size.height - curveHeight,
                    size.width / 2, size.height + curveHeight,
                    0f, size.height - curveHeight
                )
                // Line back to start
                lineTo(0f, 0f)
                close()
            }
        )
    }
}

// Custom shape class for the convex top edge (for pink section)
class ConvexTopShape(private val curveHeight: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                // Start from the bottom left
                moveTo(0f, curveHeight)
                // Draw curve from left to right
                cubicTo(
                    0f, curveHeight,
                    size.width / 2, -curveHeight,
                    size.width, curveHeight
                )
                // Line to bottom right
                lineTo(size.width, size.height)
                // Line to bottom left
                lineTo(0f, size.height)
                // Close the path
                close()
            }
        )
    }
}

@Preview
@Composable
fun PetProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Pet image taking full width with concave bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .clip(ConcaveBottomShape(30f))
        ) {
            Image(
                painter = painterResource(R.drawable.crying_doggy), // Replace with your image resource
                contentDescription = "Golden Retriever puppy",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Back button with heart
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(
                onClick = { /* Handle back button click */ },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFF9E45), // Orange background
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.crying_doggy), // Add back arrow icon
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // Heart icon

        }

        // Pink bottom section with convex top that matches the image bottom curve
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(Alignment.BottomCenter)
                .clip(ConvexTopShape(30f))
                .background(Color(0xFFFFD6E4)) // Light pink background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Info box with name, age and breed
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF3178C6)) // Blue background for info box
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "BUDDY",
                            style = TextStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "2 months",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Golden Retriever",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                // Pet details in rows
                Spacer(modifier = Modifier.height(24.dp))

                DetailRow(label = "Animal type:", value = "Dog", valueColor = Color(0xFF3178C6))
                DetailRow(label = "Gender:", value = "Male", valueColor = Color(0xFF3178C6))
                DetailRow(label = "Color:", value = "Golden", valueColor = Color(0xFF3178C6))
                DetailRow(label = "Weight:", value = "5.2", valueColor = Color(0xFF3178C6))
                DetailRow(label = "Paw Personality:", value = "I am very friendly", valueColor = Color(0xFF3178C6))
                DetailRow(label = "Health History:", value = "I am very friendly", valueColor = Color(0xFF3178C6))

                // Adopt button
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle adopt button click */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9E45) // Orange button
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "I am ready to adopt!",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.width(160.dp)
        )

        Text(
            text = value,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = valueColor
            )
        )
    }
}