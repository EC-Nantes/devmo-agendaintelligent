package com.ecentrale.agendaintelligent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditScreen(
    onSave: (String, Int, Int) -> Unit,
    onCancel: () -> Unit
) {
    var selectedDayIndex by remember { mutableStateOf(2) }
    var selectedHour by remember { mutableStateOf(12) }

    val dayLabels = listOf("MAR\n28", "MER\n29", "JEU\n30", "VEN\n31", "SAM\n1")
    val dayNames  = listOf("MAR 28", "MER 29", "JEU 30", "VEN 31", "SAM 1")
    val dayRealIndices = listOf(1, 2, 3, 4, 5)
    val dayColors = listOf(EventOrange, EventGreen, EventGreen, EventRed, EventGreen)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Handle bar
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFD1D5DB))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancel) {
                    Text("✕", fontSize = 18.sp, color = Color.Gray)
                }
                Text(
                    "Modifier la tâche",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Nom (affiché, non éditable ici pour garder simple)
            Text("NOM DE LA TÂCHE", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color(0xFFD1D5DB), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Text("Révisions Physique", fontSize = 15.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Choisir un jour
            Text("CHOISIR UN JOUR", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                dayLabels.forEachIndexed { i, label ->
                    val selected = i == selectedDayIndex
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selected) dayColors[i] else Color(0xFFF3F4F6))
                            .border(
                                1.dp,
                                if (selected) dayColors[i] else Color.Transparent,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedDayIndex = i }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            fontSize = 11.sp,
                            color = if (selected) Color.White else Color.Black,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Légende
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("Libre" to EventGreen, "Modéré" to EventOrange, "Chargé" to EventRed).forEach { (label, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(label, fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Créneau horaire — 12h ou 16h
            Text("CRÉNEAU HORAIRE", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(12, 16).forEach { hour ->
                    val selected = hour == selectedHour
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selected) PrimaryBlue else Color.White)
                            .border(
                                width = if (selected) 2.dp else 1.dp,
                                color = if (selected) PrimaryBlue else Color(0xFFD1D5DB),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedHour = hour }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "🕐  ${hour}h00",
                            fontSize = 14.sp,
                            color = if (selected) Color.White else Color.Black,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Badge créneau disponible
            Text(
                "✓ Créneau disponible",
                fontSize = 12.sp,
                color = EventGreen,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Créneau suggéré
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDCFCE7))
                    .padding(12.dp)
            ) {
                Text(
                    "✓ Créneau suggéré : ${dayNames[selectedDayIndex]} · ${selectedHour}h-${selectedHour + 2}h (jour libre)",
                    fontSize = 12.sp,
                    color = Color(0xFF166534)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bouton Enregistrer
            Button(
                onClick = {
                    onSave(
                        dayNames[selectedDayIndex],
                        dayRealIndices[selectedDayIndex],
                        selectedHour
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    "✓  Enregistrer les modifications",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bouton Annuler
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD1D5DB))
            ) {
                Text("Annuler", fontSize = 16.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}