package com.ecentrale.agendaintelligent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecentrale.agendaintelligent.AgendaViewModel
import com.ecentrale.agendaintelligent.Event
import com.ecentrale.agendaintelligent.EventColor

val PrimaryBlue = Color(0xFF3B82F6)
val EventGreen = Color(0xFF22C55E)
val EventOrange = Color(0xFFF59E0B)
val EventRed = Color(0xFFEF4444)
val LightBlue = Color(0xFFDBEAFE)
val LightYellow = Color(0xFFFEF9C3)
val LightRed = Color(0xFFFFE4E4)
val LightGreen = Color(0xFFDCFCE7)

fun eventBgColor(color: EventColor): Color = when (color) {
    EventColor.BLUE -> LightBlue
    EventColor.YELLOW -> LightYellow
    EventColor.RED -> LightRed
    EventColor.GREEN -> LightGreen
}

fun eventBorderColor(color: EventColor): Color = when (color) {
    EventColor.BLUE -> PrimaryBlue
    EventColor.YELLOW -> EventOrange
    EventColor.RED -> EventRed
    EventColor.GREEN -> EventGreen
}

val days = listOf("LUN\n27", "MAR\n28", "MER\n29", "JEU\n30", "VEN\n31")
val dayDots = listOf(EventGreen, EventOrange, EventOrange, EventRed, EventGreen)
val hours = (8..18).toList()
val HOUR_HEIGHT = 64.dp

@Composable
fun WeekScreen(
    viewModel: AgendaViewModel,
    onConflictClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val conflictResolved = uiState.conflictResolved

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {

            WeekHeader(conflictResolved = conflictResolved)

            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                    if (conflictResolved) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(EventGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("✓", color = Color.White, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Conflit résolu !",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF166534),
                                    fontSize = 14.sp
                                )
                                Text(
                                    "Révisions Physique déplacée avec succès",
                                    color = Color(0xFF166534),
                                    fontSize = 12.sp
                                )
                            }
                            TextButton(onClick = { viewModel.reset() }) {
                                Text(
                                    "↺ Reset",
                                    color = PrimaryBlue,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    CalendarGrid(events = uiState.events)
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }

            BottomNavBar()
        }

        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 72.dp, end = 16.dp),
            containerColor = PrimaryBlue
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        }

        if (!conflictResolved) {
            ConflictBanner(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = onConflictClick
            )
        }
    }
}

@Composable
fun WeekHeader(conflictResolved: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryBlue)
            .padding(top = 40.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("<", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Janvier 2026",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    if (conflictResolved) "Conflit résolu ✓" else "Semaine 5",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 13.sp
                )
            }
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Jour", "Semaine", "Mois").forEach { label ->
                val selected = label == "Semaine"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) Color.White else Color.Transparent)
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text(
                        label,
                        color = if (selected) PrimaryBlue else Color.White,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            days.forEachIndexed { index, day ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        day,
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(dayDots[index])
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CalendarGrid(events: List<Event>) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Column(modifier = Modifier.width(40.dp)) {
            hours.forEach { hour ->
                Box(
                    modifier = Modifier
                        .height(HOUR_HEIGHT)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text("${hour}h", fontSize = 11.sp, color = Color.Gray)
                }
            }
        }

        Row(modifier = Modifier.weight(1f)) {
            (0..4).forEach { dayIndex ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(HOUR_HEIGHT * hours.size)
                ) {
                    hours.forEachIndexed { i, _ ->
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = HOUR_HEIGHT * i),
                            color = Color(0xFFE5E7EB),
                            thickness = 0.5.dp
                        )
                    }

                    events.filter { it.dayIndex == dayIndex }.forEach { event ->
                        val topOffset = HOUR_HEIGHT * (event.startHour - 8)
                        val height = HOUR_HEIGHT * (event.endHour - event.startHour)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = topOffset)
                                .height(height)
                                .padding(horizontal = 2.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(eventBgColor(event.color))
                                .border(
                                    1.dp,
                                    eventBorderColor(event.color),
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Column {
                                Text(
                                    event.title,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.DarkGray,
                                    maxLines = 2
                                )
                                Text(
                                    "${event.startHour}:00",
                                    fontSize = 9.sp,
                                    color = Color.Gray
                                )
                                if (event.isDisplaced) {
                                    Text(
                                        "(déplacé)",
                                        fontSize = 9.sp,
                                        color = EventOrange,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConflictBanner(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 80.dp, bottom = 72.dp)
    ) {
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4E4)),
            border = androidx.compose.foundation.BorderStroke(1.dp, EventRed)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(EventRed),
                    contentAlignment = Alignment.Center
                ) {
                    Text("2", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        "Conflits détectés",
                        fontWeight = FontWeight.Bold,
                        color = EventRed,
                        fontSize = 14.sp
                    )
                    Text(
                        "Appuyez pour gérer les chevauchements",
                        color = Color(0xFF7F1D1D),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(">", color = EventRed, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Accueil", fontSize = 11.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Add, contentDescription = null) },
            label = { Text("Ajouter", fontSize = 11.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
            label = { Text("Alertes", fontSize = 11.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profil", fontSize = 11.sp) }
        )
    }
}