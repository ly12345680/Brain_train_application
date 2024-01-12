package com.example.braintrainapp.ui


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.R
import com.example.braintrainapp.Screen
import com.example.braintrainapp.ui.data.SlideData



@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MemoryGame(
    navController: NavController
){
    val pageState = rememberPagerState(0)

    val slider = listOf(
        SlideData(R.drawable.brain3, "Color Memory", "Pick Right Color positions", Screen.ColorMemory.route),
        SlideData(R.drawable.resim, "Find New Image", "Find New Images", Screen.FindNewImage.route),
        SlideData(R.drawable.resim, "Remember Image", "Find The Disappear Image", Screen.RememberImages.route),
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memory Games",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                ) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable { /* Handle menu click */ }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Setting",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { /* Handle guidelines click */ }
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(204, 255, 255),
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .background(Color(221, 255, 204), RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)),
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
                        modifier = Modifier.clickable { /* Handle click here */ }
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
                .background(Color(0xFFFAE7F3)),
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

@Preview(showBackground = true)
@Composable
fun MemoryGame(){
    MemoryGame(
        navController = rememberNavController()
    )
}

