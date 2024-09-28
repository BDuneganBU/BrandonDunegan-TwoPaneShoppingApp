package com.example.multi_paneshoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multi_paneshoppingapp.ui.theme.MultiPaneShoppingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiPaneShoppingAppTheme {
                ShoppingApp()
            }
        }
    }
}

//Pane design is largely based on the 'Content Panes 1' demo from Lecture 4
@Composable
fun ShoppingApp() {
    val windowInfo = calculateCurrentWindowInfo()
    val products = listOf(
        Product(1, "Product A", "a cool product",5.99),
        Product(2, "Product B", "a fun product",15.99),
        Product(3, "Product C", "a mystery product",19.99),
        Product(4, "Product D", "a funky product", 9.99),
        Product(5, "Product E", "a funny product", 18.99),
        Product(6, "Product F", "a wild product", 27.99),
        Product(7, "Product G", "a wacky product",5.99),
        Product(8, "Product H", "a precious product",15.99),
        Product(9, "Product I", "a valuable product",109.99),
        Product(10, "Product J", "an interesting product", 9.99),
        Product(11, "Product K", "a clever product", 18.99),
        Product(12, "Product L", "a lame product", 5.99),
    )
    var selectedItem by remember { mutableStateOf<Product?>(null) }

    if (windowInfo.isWideScreen) {
        // Two-pane layout for wide screens, one for the task list
        // the other for the task details
        Row(modifier = Modifier.fillMaxSize()) {
            TaskList(items = products, onItemSelected = { selectedItem = it }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            TaskDetailPane(task = selectedItem, modifier = Modifier.weight(1f))
        }
    } else {
        // Single-pane layout for narrow screens
        if (selectedItem == null) {
            TaskList(items = products, onItemSelected = { selectedItem = it }, modifier = Modifier.fillMaxSize())
        } else {
            TaskDetailPane(task = selectedItem, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TaskList(items: List<Product>, onItemSelected: (Product) -> Unit, modifier: Modifier = Modifier) {
    //Lazy Column for feature 1
    LazyColumn (
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        item {
            // List Title
            Text(
                text = "Tasks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // List Items
            items.forEach { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemSelected(item) }
                        .padding(8.dp),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun TaskDetailPane(task: Product?, modifier: Modifier = Modifier) {
    // Task details pane used when the user selects a particular task
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (task != null) {
            // Task Detail
            val name = task.name
            val desc = task.description
            val price = task.price
            Text(
                text = "Details for $name",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$name is $desc and costs: $price",
                fontSize = 16.sp
            )
        } else {
            // No task selected
            Text(
                text = "Select a product to view details.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun calculateCurrentWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    // Set a breakpoint for wide vs narrow screens (600dp is commonly used)
    val isWideScreen = screenWidth >= 600

    return WindowInfo(
        isWideScreen = isWideScreen
    )
}

data class WindowInfo(
    val isWideScreen: Boolean
)

data class Product(val id: Int,
                   val name: String,
                   val description: String,
                   val price: Double)