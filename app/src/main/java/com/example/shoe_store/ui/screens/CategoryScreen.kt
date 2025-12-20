package com.example.shoe_store.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    onFavoriteClick: (Product) -> Unit,
    onAllClick: () -> Unit
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
<<<<<<< HEAD
            name = "Nike Air Max 2",
=======
            name = "Nike Air Force 1",
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
            price = "P820.00",
            originalPrice = "P900.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "3",
<<<<<<< HEAD
            name = "Nike Air Max 3",
=======
            name = "Adidas Ultraboost",
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
            price = "P680.00",
            originalPrice = "P750.00",
            category = "NEW",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "4",
<<<<<<< HEAD
            name = "Nike Air Max 4",
=======
            name = "Puma RS-X",
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
            price = "P520.00",
            originalPrice = "P600.00",
            category = "TRENDING",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "5",
<<<<<<< HEAD
            name = "Nike Air Max 5",
=======
            name = "Reebok Nano",
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
            price = "P620.00",
            originalPrice = "P700.00",
            category = "BEST SELLER",
            imageUrl = "",
            imageResId = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3
        ),
        Product(
            id = "6",
<<<<<<< HEAD
            name = "Nike Air Max 6",
=======
            name = "New Balance 574",
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
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

<<<<<<< HEAD
        // Фильтр категорий (если нужно)
        if (categoryName != "All") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Кнопка "All" - кастомная реализация
                CategoryFilterButton(
                    text = "All",
                    selected = false,
                    onClick = onAllClick
                )

                // Кнопка текущей категории
                CategoryFilterButton(
                    text = categoryName,
                    selected = true,
                    onClick = { }
                )
            }
        }

        // Сетка товаров
=======
        // Products grid
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = if (categoryName == "All") 8.dp else 0.dp),
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

// Простая кастомная кнопка для фильтров
@Composable
fun CategoryFilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = if (selected) MaterialTheme.colorScheme.primary else Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = if (selected) Color.Transparent else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = AppTypography.bodyMedium16.copy(
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                color = if (selected) Color.White else Color.Black
            )
        )
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
<<<<<<< HEAD
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.White)
                .clip(RoundedCornerShape(12.dp))
        ) {
            // Изображение товара
            Image(
                painter = painterResource(id = R.drawable.nike_zoom_winflo_3_831561_001_mens_running_shoes_11550187236tiyyje6l87_prev_ui_3),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Бейдж категории
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = product.category,
                    style = AppTypography.bodyRegular12.copy(
                        fontSize = 10.sp,
                        color = Color.White
                    )
                )
            }

            // Кнопка сердца (favorited)
            IconButton(
                onClick = onFavoriteClick,
=======
        Column {
            Box(
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
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
<<<<<<< HEAD

        // Информация о товаре
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = product.name,
                style = AppTypography.bodyMedium16.copy(
                    fontWeight = FontWeight.Medium
                ),
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = product.price,
                        style = AppTypography.bodyMedium16.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    if (product.originalPrice.isNotEmpty() && product.originalPrice != product.price) {
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

                // Рейтинг
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_open),
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "4.5",
                        style = AppTypography.bodyRegular12,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
=======
    }
}

@Preview
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(
        categoryName = "Running",
        onBackClick = {},
        onProductClick = {},
<<<<<<< HEAD
        onFavoriteClick = {},
        onAllClick = {}
=======
        onFavoriteClick = {}
>>>>>>> bd2c49be81facd76bc3c6e4b0b07cce13f79b8bd
    )
}