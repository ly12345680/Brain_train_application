package com.example.braintrainapp.ui


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.R
import com.example.braintrainapp.Screen
import com.example.braintrainapp.ui.data.SlideData
import com.example.braintrainapp.ui.data.UserData


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(navController: NavController) {
    val Indigo = Color(0x3F, 0x51, 0xB5)
    val Violet = Color(0xEE, 0x82, 0xEE)
    var user = UserData(R.drawable.user, "John Cena")
    val pageState = rememberPagerState(0)
    val slider = listOf(
        SlideData(R.drawable.brain3, "Memory Games", "Train Your Memory Skills", Screen.MemoryGame.route),
        SlideData(R.drawable.resim, "Languages Games", "Train Your Languages Skills", Screen.LanguageGame.route),
        SlideData(R.drawable.resim, "Attention Games", "Train Your Attention Skills", Screen.AttentionGame.route),
        SlideData(R.drawable.resim, "Math Games", "Train Your Math Skills", Screen.MathGame.route),
    )
Scaffold(
    topBar = {

        userCompose(imageRes = user.imageRes, name = user.name)
    },
    bottomBar = {
        BottomAppBar(
            containerColor = Color.Transparent,
            
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        Color(221, 255, 204),
                        RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp
                        )
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier.clickable { /* Handle click here */ }
                )
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.MainMenu.route)
                    }
                )
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Setting",
                    modifier = Modifier.clickable { /* Handle click here */ }
                )
            }
        }
    }
) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(
                    colors = listOf(Color.Yellow, Color.Green, Color.Blue, Violet),
                    startY = 0.0f,
                    endY = Float.POSITIVE_INFINITY)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Games",
                fontSize = 28.sp
            )


            Spacer(modifier = Modifier.height(62.dp))
            HorizontalPager(
                pageCount = slider.size,
                state = pageState,
                contentPadding = PaddingValues(horizontal = 30.dp),
                modifier = Modifier
                    .height(350.dp),

                ) { page ->
                val (imageRes, title, description, route) = slider[page]
                val onClick = {
                    navController.navigate(route)
                }
                val scale by animateFloatAsState(
                    targetValue = if (pageState.currentPage == page) 1f else 0.85f,
                    label = "Scale animation"
                )
                val alpha by animateFloatAsState(
                    targetValue = if (pageState.currentPage == page)1f else 0.5f,
                    label = "Alpha animation"
                )
                Card(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        5.dp, Color.White
                    ),
                    modifier = Modifier
                        .clickable(onClick = onClick)
                        .scale(scale)
                        .alpha(alpha)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(imageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(0.8f)
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .weight(0.2f)
                                .padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.7f)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    description,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun userCompose(imageRes: Int, name: String){
    Row(modifier = Modifier
        .padding(16.dp)
        .size(180.dp, 50.dp)
        .background(color = Color(221, 255, 204), shape = MaterialTheme.shapes.extraLarge)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Score",
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text =  name,
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = androidx.compose.material3.contentColorFor(Color.White),
                style = TextStyle(fontFamily = FontFamily(Font(R.font.simonetta_regular)), fontWeight = FontWeight.Bold),
                fontSize = 20.sp
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MemoryAppPreview() {
    MainMenu(navController = rememberNavController())
//    userCompose(imageRes = R.drawable.user, "John Cena")
}
