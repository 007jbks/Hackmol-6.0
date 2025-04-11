import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pets.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

// Model class for profile data
data class Profile(
    val id: Int,
    val name: String,
    val age: Int,
    val bio: String,
    val imageResId: Int
)
@Preview
@Composable
fun TinderApp() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                TinderSwipeScreen(navController)
            }
            composable("disliked") {
                DislikedScreen(navController)
            }
            composable("superlike") {
                SuperLikeScreen(navController)
            }
        }
    }
}

@Composable
fun TinderSwipeScreen(navController: NavController) {
    // Sample profile data
    val profiles = remember {
        listOf(
            Profile(1, "Alex", 28, "Love hiking and photography", R.drawable.crying_doggy),
            Profile(2, "Taylor", 24, "Coffee enthusiast and bookworm", R.drawable.crying_doggy),
            Profile(3, "Jordan", 30, "Travel addict, been to 23 countries", R.drawable.crying_doggy),
            Profile(4, "Casey", 26, "Musician and foodie", R.drawable.crying_doggy),
            Profile(5, "Morgan", 29, "Tech geek and fitness lover", R.drawable.crying_doggy)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Text(
            text = "TinderClone",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF6B6B)
        )

        // Stack of cards
        CardStack(
            profiles = profiles,
            navController = navController,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        )

        // Bottom controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = {
                    // Navigate to disliked screen
                    navController.navigate("disliked")
                },
                containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dislike",
                    tint = Color.Red
                )
            }

            FloatingActionButton(
                onClick = {
                    // Navigate to superlike screen
                    navController.navigate("superlike")
                },
                containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Super Like",
                    tint = Color(0xFF00BCD4)
                )
            }

            FloatingActionButton(
                onClick = { /* Like action handled in CardStack */ },
                containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
fun DislikedScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        // Back button
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFF6B6B)
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color(0xFFFF6B6B)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Disliked Profiles",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6B6B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "This is where your disliked profiles would appear. You can manage your preferences here.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B6B)
                )
            ) {
                Text("Back to Swiping")
            }
        }
    }
}

@Composable
fun SuperLikeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        // Back button
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF00BCD4)
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color(0xFF00BCD4)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Super Likes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00BCD4)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You've super-liked this profile! This is where you can see profiles you've super-liked and their responses.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                )
            ) {
                Text("Back to Swiping")
            }
        }
    }
}

@Composable
fun CardStack(
    profiles: List<Profile>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // State to track visible profiles
    var visibleProfiles by remember { mutableStateOf(profiles.take(2)) }
    var currentIndex by remember { mutableStateOf(0) }

    // State to track swipe actions
    var swipingInProgress by remember { mutableStateOf(false) }

    // Update visible profiles when current index changes
    LaunchedEffect(currentIndex) {
        visibleProfiles = profiles.drop(currentIndex).take(2)
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Show "No more profiles" when we've gone through all profiles
        if (visibleProfiles.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(500.dp)
                    .align(Alignment.Center),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No more profiles!", fontSize = 20.sp)
                }
            }
        } else {
            // Display the next card underneath (if available)
            if (visibleProfiles.size > 1) {
                ProfileCard(
                    profile = visibleProfiles[1],
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(500.dp)
                        .align(Alignment.Center)
                )
            }

            // Display the top card with swipe functionality
            if (visibleProfiles.isNotEmpty() && !swipingInProgress) {
                SwipeableCard(
                    profile = visibleProfiles[0],
                    onSwiped = { direction ->
                        swipingInProgress = true
                        scope.launch {
                            // Handle swipe action based on direction
                            when (direction) {
                                SwipeDirection.LEFT -> {
                                    // Navigate to disliked screen when swiped left
                                    delay(300) // Wait for animation to complete
                                    navController.navigate("disliked")
                                }
                                SwipeDirection.UP -> {
                                    // Navigate to superlike screen when swiped up
                                    delay(300) // Wait for animation to complete
                                    navController.navigate("superlike")
                                }
                                SwipeDirection.RIGHT -> {
                                    // Handle right swipe (like)
                                    delay(300) // Simulate animation completion
                                    // Move to next profile
                                    currentIndex++
                                    swipingInProgress = false
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(500.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

enum class SwipeDirection {
    LEFT, RIGHT, UP
}

@Composable
fun SwipeableCard(
    profile: Profile,
    onSwiped: (SwipeDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // Animation values
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    // Swipe threshold and animation specs
    val swipeThreshold = 300f
    val swipeAnimationSpec: AnimationSpec<Float> = spring(
        dampingRatio = 0.8f,
        stiffness = 100f
    )

    Card(
        modifier = modifier
            .graphicsLayer(
                translationX = offsetX.value,
                translationY = offsetY.value,
                rotationZ = rotation.value
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            // Check if we're past the threshold for X or Y
                            val isPastHorizontalThreshold = abs(offsetX.value) > swipeThreshold
                            val isPastVerticalThreshold = offsetY.value < -swipeThreshold

                            if (isPastHorizontalThreshold || isPastVerticalThreshold) {
                                // Determine swipe direction and target animation values
                                when {
                                    // Swipe up
                                    isPastVerticalThreshold -> {
                                        offsetY.animateTo(-1500f, swipeAnimationSpec)
                                        onSwiped(SwipeDirection.UP)
                                    }
                                    // Swipe right
                                    offsetX.value > 0 -> {
                                        offsetX.animateTo(1500f, swipeAnimationSpec)
                                        onSwiped(SwipeDirection.RIGHT)
                                    }
                                    // Swipe left
                                    else -> {
                                        offsetX.animateTo(-1500f, swipeAnimationSpec)
                                        onSwiped(SwipeDirection.LEFT)
                                    }
                                }
                            } else {
                                // If not past threshold, animate back to center
                                offsetX.animateTo(0f, swipeAnimationSpec)
                                offsetY.animateTo(0f, swipeAnimationSpec)
                                rotation.animateTo(0f, swipeAnimationSpec)
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            // Update offsets with drag distance
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y)

                            // Add some rotation based on the horizontal offset
                            rotation.snapTo(offsetX.value * 0.03f)
                        }
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        ProfileCardContent(
            profile = profile,
            offsetX = offsetX.value,
            offsetY = offsetY.value,
            swipeThreshold = swipeThreshold
        )
    }
}

@Composable
fun ProfileCard(
    profile: Profile,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ProfileCardContent(
            profile = profile,
            offsetX = 0f,
            offsetY = 0f,
            swipeThreshold = 300f
        )
    }
}

@Composable
fun ProfileCardContent(
    profile: Profile,
    offsetX: Float,
    offsetY: Float,
    swipeThreshold: Float
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Profile image
        Image(
            painter = painterResource(id = profile.imageResId),
            contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Swipe indicators
        val horizontalSwipeProgress = (offsetX / swipeThreshold).coerceIn(-1f, 1f)
        val verticalSwipeProgress = (offsetY / -swipeThreshold).coerceIn(-1f, 1f)

        // Like indicator (right swipe)
        AnimatedVisibility(
            visible = horizontalSwipeProgress > 0.2f,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.TopStart),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x884CAF50))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .rotate(-30f)
            ) {
                Text(
                    "LIKE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }

        // Dislike indicator (left swipe)
        AnimatedVisibility(
            visible = horizontalSwipeProgress < -0.2f,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.TopEnd),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x88F44336))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .rotate(30f)
            ) {
                Text(
                    "NOPE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }

        // Super Like indicator (up swipe)
        AnimatedVisibility(
            visible = verticalSwipeProgress > 0.2f,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.TopCenter),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x8800BCD4))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    "SUPER LIKE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }

        // Profile info
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xCC000000)
                        ),
                        startY = 300f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "${profile.name}, ${profile.age}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = profile.bio,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}