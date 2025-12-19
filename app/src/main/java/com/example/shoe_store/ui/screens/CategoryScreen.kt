package com.example.shoe_store.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoe_store.data.model.Product
import com.example.shoe_store.ui.theme.AppTypography
import com.example.shoe_store.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onFavoriteClick: (Product) -> Unit
) {
    var isFavoritedMap by remember { mutableStateOf<Map<String, Boolean>>(emptyMap()) }

    val categoryProducts = listOf(
        Product(
            id = "1",
            name = "Nike Air Max",
            price = "P752.00",
            originalPrice = "P850.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "2",
            name = "Nike Air Force 1",
            price = "P820.00",
            originalPrice = "P900.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "3",
            name = "Adidas Ultraboost",
            price = "P680.00",
            originalPrice = "P750.00",
            category = "NEW",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "4",
            name = "Puma RS-X",
            price = "P520.00",
            originalPrice = "P600.00",
            category = "TRENDING",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "5",
            name = "Reebok Nano",
            price = "P620.00",
            originalPrice = "P700.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "6",
            name = "New Balance 574",
            price = "P580.00",
            originalPrice = "P650.00",
            category = "NEW",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F9))
    ) {
        // Header with back button
        TopAppBar(
            title = {
                Text(
                    text = categoryName,
                    style = AppTypography.headingRegular32.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFF7F7F9),
                titleContentColor = Color.Black
            ),
            modifier = Modifier.height(60.dp)
        )

        // Products grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(categoryProducts) { product ->
                CategoryProductCard(
                    product = product,
                    isFavorited = isFavoritedMap[product.id] ?: false,
                    onProductClick = { onProductClick(product) },
                    onFavoriteClick = {
                        isFavoritedMap = isFavoritedMap.toMutableMap().apply {
                            this[product.id] = !(this[product.id] ?: false)
                        }
                        onFavoriteClick(product)
                    }
                )
            }
        }
    }
}

@Composable
private fun CategoryProductCard(
    product: Product,
    isFavorited: Boolean,
    onProductClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White)
            ) {
                // Product image
                Image(
                    painter = painterResource(id = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

                // Favorite button
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorited) R.drawable.favorite_fill else R.drawable.favorite
                        ),
                        contentDescription = "Favorite",
                        tint = if (isFavorited) Color.Red else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Add to cart button
                IconButton(
                    onClick = { /* Handle add to cart */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart_na),
                        contentDescription = "Add to cart",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Product info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = product.category,
                    style = AppTypography.bodyRegular12,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.name,
                    style = AppTypography.bodyMedium16.copy(fontWeight = FontWeight.Medium),
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.price,
                        style = AppTypography.bodyMedium16.copy(fontWeight = FontWeight.SemiBold),
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = product.originalPrice,
                        style = AppTypography.bodyRegular12.copy(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        ),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(
        categoryName = "Running",
        onBackClick = {},
        onProductClick = {},
        onFavoriteClick = {}
    )
}