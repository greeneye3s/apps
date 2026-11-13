package com.example.greeneye

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import greeneye.shared.generated.resources.Res
import greeneye.shared.generated.resources.greeneye
import org.jetbrains.compose.resources.painterResource

// Dữ liệu mẫu ứng dụng
data class AppProject(
    val id: String,
    val title: String,
    val category: String,
    val rating: Float,
    val downloads: String,
    val description: String,
    val iconEmoji: String
)

val sampleApps = listOf(
    AppProject(
        id = "1",
        title = "Electric Car Simulator 2D",
        category = "Trò chơi",
        rating = 4.8f,
        downloads = "10K+",
        description = "Trò chơi mô phỏng lái xe điện góc nhìn từ trên xuống, tối ưu cho màn hình dọc với hệ thống đặt vé thông minh và giải đấu trực tuyến.",
        iconEmoji = "🚗"
    ),
    AppProject(
        id = "2",
        title = "Finance Tracker & Metrics",
        category = "Năng suất",
        rating = 4.6f,
        downloads = "50K+",
        description = "Ứng dụng theo dõi chỉ số tài chính doanh nghiệp, EPS, ROE, ROIC và ghi nhớ thuật toán quản lý tài sản thông minh.",
        iconEmoji = "📊"
    ),
    AppProject(
        id = "3",
        title = "Karate Master Trainer",
        category = "Thể thao",
        rating = 4.9f,
        downloads = "5K+",
        description = "Hệ thống bài tập, câu hỏi ôn tập và hướng dẫn kỹ thuật luyện tập võ thuật chuyên nghiệp chuẩn thi đấu.",
        iconEmoji = "🥋"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GooglePlayPortfolioApp() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    var selectedApp by remember { mutableStateOf<AppProject?>(null) }

    val categories = listOf("Tất cả", "Trò chơi", "Năng suất", "Thể thao")
    val filteredApps = if (selectedCategory == "Tất cả") {
        sampleApps
    } else {
        sampleApps.filter { it.category == selectedCategory }
    }

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme(
            primary = Color(0xFF01875F),
            surface = Color(0xFF121212),
            background = Color(0xFF000000)
        ) else lightColorScheme(
            primary = Color(0xFF01875F),
            surface = Color(0xFFF8F9FA),
            background = Color(0xFFFFFFFF)
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.greeneye),
                                    contentDescription = "GreenEye",
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("GreenEye", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("Google Play Verified Developer", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
//                            Icon(
//                                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
//                                contentDescription = "Toggle Theme"
//                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Banner
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text("Chào mừng đến với không gian ứng dụng của tôi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Khám phá các sản phẩm ứng dụng di động và trò chơi chất lượng cao được phát hành chính thức trên Google Play.", fontSize = 14.sp)
                        }
                    }
                }

                // Filter Chips
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categories) { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                label = { Text(category) }
                            )
                        }
                    }
                }

                // App Grid / List
                items(filteredApps) { app ->
                    AppItemCard(app = app, onClick = { selectedApp = app })
                }
            }

            // Detail Dialog
            selectedApp?.let { app ->
                AppDetailDialog(app = app, onDismiss = { selectedApp = null })
            }
        }
    }
}

@Composable
fun AppItemCard(app: AppProject, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(app.iconEmoji, fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(app.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(app.category, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("${app.rating}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("📥 ${app.downloads}", fontSize = 12.sp, color = Color.Gray)
                }
            }
//            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun AppDetailDialog(app: AppProject, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(app.iconEmoji, fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(app.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(app.category, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Mô tả ứng dụng", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(app.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Mở link Google Play ở đây */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Xem trên Google Play")
                }
            }
        }
    }
}