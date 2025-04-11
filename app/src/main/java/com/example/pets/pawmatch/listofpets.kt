package com.example.pets.pawmatch



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

// 🌟 Your data class
data class PawMatch(
    val name: String,
    val description: String,
    val imageUrl: String,
    val isTopMatch: Boolean = false
)

@Composable
fun matchesScreen(pawMatches: List<PawMatch>,navController: NavController) {
    Box(Modifier.fillMaxSize().background(Color(0xFFFFDDEA))) {

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Search bar centered with 75% width
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(0.75f),
                onSearchTextChanged = {}
            )
        }

        Spacer(Modifier.height(16.dp))

        // Section title
        Text(
            text = "THE BEST PAW MATCHES",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF01579B)
            ),
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Grid of paw matches
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pawMatches) { pawMatch ->
                PawMatchItem(pawMatch = pawMatch, navController = navController)
            }
        }
    }
}

@Composable
fun PawMatchItem(pawMatch: PawMatch,navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth().clickable {  },
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTextChanged: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    val blueBackground = Color(0xFF4080C0)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)  // Standard Material search bar height
            .clip(RoundedCornerShape(28.dp))  // Half of height for perfect rounded corners
            .background(blueBackground),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            },
            placeholder = {
                Text(
                    text = "Search...",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchTextChanged(searchText)
                }
            )
        )
    }
}


@Composable
fun MatchesScreenPreview(navController: NavController) {
    val samplePawMatches = listOf(
        PawMatch(
            name = "Buddy",
            description = "Friendly Golden Retriever",
            imageUrl = "https://example.com/dog1.jpg",
            isTopMatch = true
        ),
        PawMatch(
            name = "Max",
            description = "Playful Labrador",
            imageUrl = "https://example.com/dog2.jpg"
        ),
        PawMatch(
            name = "Charlie",
            description = "Energetic Beagle",
            imageUrl = "https://example.com/dog3.jpg",
            isTopMatch = true
        ),
        PawMatch(
            name = "Lucy",
            description = "Gentle Bulldog",
            imageUrl = "https://example.com/dog4.jpg"
        )
    )

    MaterialTheme {
        matchesScreen(pawMatches = samplePawMatches, navController = navController)
    }
}