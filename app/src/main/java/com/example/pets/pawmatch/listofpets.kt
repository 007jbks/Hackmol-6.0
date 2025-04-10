package com.example.pets.pawmatch



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// 🌟 Your data class
data class PawMatch(
    val name: String,
    val description: String,
    val imageUrl: String,
    val isTopMatch: Boolean = false
)

@Composable
fun PawMatchItem(pawMatch: PawMatch) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = pawMatch.imageUrl,
                contentDescription = "Dog Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = pawMatch.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF01579B)
                    )
                )
                Text(
                    text = pawMatch.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (pawMatch.isTopMatch) {
                Text(
                    text = "TOP MATCH",
                    color = Color(0xFF0288D1),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .border(1.dp, Color(0xFF0288D1), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PawMatchItemPreview() {
    PawMatchItem(
        pawMatch = PawMatch(
            name = "Buddy",
            description = "I am a Friendly pup",
            imageUrl = "https://images.unsplash.com/photo-1558788353-f76d92427f16",
            isTopMatch = true
        )
    )
}