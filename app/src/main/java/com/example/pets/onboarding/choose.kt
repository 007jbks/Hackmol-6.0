package com.example.pets.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.pets.R
import com.example.pets.navigation.Screens


@Composable
fun PawControlScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4EC)) // light pink
            .padding(start = 24.dp, end = 24.dp,top = 32.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What brings you to Paw\n Control today?",
                color = Color(0xFF3366CC),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 40.dp,bottom = 27.dp)
            )

            PetAppScreen(navController)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(R.drawable.dogy), // replace with your drawable
                contentDescription = "Dog Peeking",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(start=15.dp)
            )
        }
    }
}

@Composable
fun PawButton(text: String, icon: ImageVector) {
    Button(
        onClick = { /* Handle click */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)), // orange
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(10.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun PetAppScreen(navController: NavController) {
    Box(
        modifier = Modifier


    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PetButton(
                text = "I WANT TO REHOME MY PET",
                leftIcon = R.drawable.group_2198383,
                showLeftIcon = true,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            PetButton(
                text = "I AM LOOKING FOR A PET",
                rightIcon = R.drawable.vector_10,
                showRightIcon = true,
                        onClick = {navController.navigate(Screens.urpic.route)}
            )

            Spacer(modifier = Modifier.height(32.dp))

            PetButton(
                text = "FOUND ABANDONED PET",
                leftIcon = R.drawable.vector_11,
                rightIcon = R.drawable.group_2198383,
                showLeftIcon = true,
                showRightIcon = true,
                onClick = {navController.navigate(Screens.petrej1.route)}
            )
        }
    }
}

@Composable
fun PetButton(
    text: String,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    showLeftIcon: Boolean = false,
    showRightIcon: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier=Modifier.fillMaxWidth(0.7f).height(50.dp)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Left icon (outside the button) - placed first so it's behind the button
        if (showLeftIcon && leftIcon != null) {
            Image(
                painter = painterResource(id = leftIcon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(70.dp)
                    .zIndex(1f) // Ensure it's above the button
                    .offset(x = (-40).dp,-35.dp))
        }

        // The actual button
        NeobrutalistButton(
            text = text,
            onClick = onClick
        )

        // Right icon (outside the button) - placed last so it's above the button
        if (showRightIcon && rightIcon != null) {
            Image(
                painter = painterResource(id = rightIcon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(70.dp)
                    .zIndex(1f) // Ensure it's above the button
                    .offset(x = 30.dp,25.dp)
            )
        }
    }
}



@Composable
fun NeobrutalistButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animate the button position when pressed
    val offsetX by animateDpAsState(
        targetValue = if (isPressed) 0.dp else (-2).dp,
        label = "offsetX"
    )
    val offsetY by animateDpAsState(
        targetValue = if (isPressed) 0.dp else (-2).dp,
        label = "offsetY"
    )

    // Animate the shadow offset when pressed
    val shadowOffsetX by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp,
        label = "shadowOffsetX"
    )
    val shadowOffsetY by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp,
        label = "shadowOffsetY"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .offset(x = offsetX, y = offsetY)
            .zIndex(0f)
            .shadow(
                elevation = 0.dp,
                shape = RoundedCornerShape(15.dp),
                clip = false
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            )
            .then(
                Modifier.drawBehind {
                    translate(shadowOffsetX.toPx(), shadowOffsetY.toPx()) {
                        drawRoundRect(
                            color = Color.Black,
                            size = size,
                            cornerRadius = CornerRadius(15.dp.toPx(), 15.dp.toPx())
                        )
                    }
                }
            ),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE78B3F),
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(16.dp),
        interactionSource = interactionSource
    ) {
        TextWithStroke(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textColor = Color.White,
            outlineColor = Color.Black.copy(alpha = 0.7f),
            outlineWidth = 1.5.dp
        )
    }
}

@Composable
fun TextWithStroke(
    text: String,
    fontSize: TextUnit = 13.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    textColor: Color = Color.White,
    outlineColor: Color = Color.Black,
    outlineWidth: Dp = 1.dp
) {
    Box {
        // Create an effect where we draw the text multiple times slightly offset
        // This creates a more consistent stroke effect than using Paint.Style.STROKE
        val offset = with(LocalDensity.current) { outlineWidth.toPx() }

        // Draw the outline by placing the text multiple times with small offsets
        for (dx in listOf(-offset, offset)) {
            for (dy in listOf(-offset, offset)) {
                Text(
                    text = text,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    color = outlineColor,
                    modifier = Modifier.offset(x = with(LocalDensity.current) { dx.toDp() },
                        y = with(LocalDensity.current) { dy.toDp() })
                )
            }
        }

        // Additional diagonal positions for smoother outline
        for (d in listOf(-offset, offset)) {
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = outlineColor,
                modifier = Modifier.offset(x = with(LocalDensity.current) { d.toDp() },
                    y = 0.dp)
            )
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = outlineColor,
                modifier = Modifier.offset(x = 0.dp,
                    y = with(LocalDensity.current) { d.toDp() })
            )
        }

        // Original text on top
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = textColor
        )
    }
}

