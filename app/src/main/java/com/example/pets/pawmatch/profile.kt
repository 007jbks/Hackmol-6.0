package com.example.pets.pawmatch

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pets.R
import com.example.pets.onboarding.PetButton

@Preview
@Composable
fun Profile() {
    ConstraintLayout(Modifier.background( Color(0xFFFFDDEA)
    )) {
        val (picture, pink,box,text ,button ) = createRefs()
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        Column(Modifier.constrainAs(picture) {
            top.linkTo(parent.top, margin = -5.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 25.dp)}
        .zIndex(-1f)){
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "profilepic",
            modifier = Modifier
                .fillMaxWidth()  // Full width
                .fillMaxHeight(0.5f)  // 50% height

        )
        Spacer(Modifier.fillMaxHeight())}

        Column (Modifier.constrainAs(pink) {
            // Place below the first image
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 10.dp)  // Optional: Stretch to bottom
        }.zIndex(0f)){
            Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.frame_202),
            contentDescription = "backgroundcanvas",
            modifier = Modifier
                .fillMaxWidth()  // Full width
                .fillMaxHeight(1f)  // 50% height
                .weight(2.5f)


        )}
        Box(Modifier.background(Color(0xFF066EBA)).constrainAs(box){
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent, bias = 0.45f)
        }.padding(20.dp).width(150.dp), contentAlignment = Alignment.Center){
            Column(){
                Row{
                    Text(text="BUDDY", color = Color.White, fontWeight = FontWeight.ExtraBold)
                    Text(text="2 months", color = Color.White,fontWeight = FontWeight.Bold)
            }
                Text(text="Golden Retriever", color = Color.White,fontWeight = FontWeight.ExtraBold)
        }}




        val backgroundColor = null
        Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .constrainAs(text){
                        centerVerticallyTo(parent, bias = .70f)
                        centerHorizontallyTo(parent)
                    }

            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Animal type:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "Dog", color = Color(0xFF066EBA),fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Location:", fontWeight = FontWeight.Bold,fontSize = 16.sp)
                        Text(text = "", color = Color(0xFF066EBA),fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row{
                    Text(text = "Gender: ", fontWeight = FontWeight.Bold,fontSize = 16.sp)
                    Text(text = "Male", color = Color(0xFF066EBA),fontSize = 16.sp)}

                    Spacer(modifier = Modifier.height(4.dp))
                    Row{
                    Text(text = "Color: ", fontWeight = FontWeight.Bold,fontSize = 16.sp)
                    Text(text = "Golden", color = Color(0xFF066EBA),fontSize = 16.sp)}

                    Spacer(modifier = Modifier.height(4.dp))
                    Row{
                    Text(text = "Weight: ", fontWeight = FontWeight.Bold,fontSize = 16.sp)
                    Text(text = "5.2", color = Color(0xFF066EBA),fontSize = 16.sp)}

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                                append("Paw Personality: ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF066EBA),fontSize = 16.sp)) {
                                append("I am very friendly")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,fontSize = 16.sp)) {
                                append("Health History: ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF066EBA),fontSize = 16.sp)) {
                                append("I am very friendly")
                            }
                        }
                    )
                }
            }
        PetButton(text="I am ready to Adopt", onClick = {}, modifier = Modifier.constrainAs(button){
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent,bias=0.85f)
        }.fillMaxWidth(0.5f).height(50.dp))
        }

    }
