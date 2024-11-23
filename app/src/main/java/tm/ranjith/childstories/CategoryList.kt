package tm.ranjith.childstories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CategoryList(categories: List<Category>, onCategoryClick: (Category) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // Display 2 items per row
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {

        items(categories) { category ->
            CategoryItem(category = category, onClick = { onCategoryClick(category) })
        }
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display the category image
        if (category.thumbnailUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(category.thumbnailUrl),
                contentDescription = category.name,
                modifier = Modifier.size(100.dp)
            )
        } else {
            // Placeholder Image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Image",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Display the category title
        Text(
            text = category.name,
      //      style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
}
