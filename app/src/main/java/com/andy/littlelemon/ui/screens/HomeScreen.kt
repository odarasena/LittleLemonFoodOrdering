package com.andy.littlelemon.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andy.littlelemon.R
import com.andy.littlelemon.data.local.model.MenuItemEntity
import com.andy.littlelemon.ui.composables.LittleLemonHeader
import com.andy.littlelemon.ui.theme.HighlightBlack
import com.andy.littlelemon.ui.theme.PrimaryGreen
import com.andy.littlelemon.ui.theme.PrimaryYellow

@Composable
fun HomeScreen(
    categories: List<String>,
    selectedCategory: MutableState<String>,
    searchString: MutableState<String>,
    menuItemsFiltered: List<MenuItemEntity>,
    onUserAvatarClicked: () -> Unit
) {
    Column {
        LittleLemonHeader(
            showUserAvatar = true,
            onUserAvatarClicked = onUserAvatarClicked
        )
        LazyColumn {
            item {
                LittleLemonDescription(searchString = searchString)
                OrderForDelivery(selectedCategory = selectedCategory, categories = categories)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            if (menuItemsFiltered.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = "No menu items matching these criteria found!",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(menuItemsFiltered) {
                    MenuItem(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LittleLemonDescription(searchString: MutableState<String>) {
    Column(
        modifier = Modifier
            .background(PrimaryGreen)
            .padding(16.dp)
    ) {
        Text(text = "Little Lemon", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold), color = PrimaryYellow)
        Text(text = "Chicago", style = MaterialTheme.typography.headlineSmall, color = Color.White)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with  a modern twist.",
                modifier = Modifier.weight(0.6f),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Image(
                modifier = Modifier
                    .weight(0.4f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(id = R.drawable.hero_image),
                contentScale = ContentScale.Crop,
                contentDescription = "Hero Image"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            value = searchString.value,
            onValueChange = {
                searchString.value = it
            },
            placeholder = {
                Text(text = "Enter search phrase")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderForDelivery(selectedCategory: MutableState<String>, categories: List<String>) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 8.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            color = HighlightBlack,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (category in categories) {
                FilterChip(
                    selected = selectedCategory.value == category,
                    onClick = {
                        selectedCategory.value = category
                    },
                    label = {
                        Text(
                            text = category,
                            color = PrimaryGreen,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(containerColor = Color.Gray.copy(alpha = 0.2f), selectedContainerColor = PrimaryYellow)
                )
            }
        }
    }
}

@Composable
fun MenuItem(menuItem: MenuItemEntity) {
    Card(modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.7f), verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = menuItem.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = menuItem.description, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "$${menuItem.price}", fontWeight = FontWeight.Bold)
            }
            AsyncImage(
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(1f),
                model = menuItem.imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}