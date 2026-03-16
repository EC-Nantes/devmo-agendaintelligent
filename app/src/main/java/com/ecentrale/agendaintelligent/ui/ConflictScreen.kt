package com.ecentrale.agendaintelligent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecentrale.agendaintelligent.AgendaViewModel

@Composable
fun ConflictScreen(
    viewModel: AgendaViewModel,
    onAccept: () -> Unit,
    onEditManually: () -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Icône warning
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(Color(0xFFFFE4E4)),
            contentAlignment = Alignment.Center
        ) {
            Text("⚠️", fontSize = 28.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Conflit détecté !",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            "Deux événements se chevauchent",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Event 1 — RDV Doctolib
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFFE4E4))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(EventRed)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    "RDV Doctolib",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Text(
                    "Mer 29 · 14h30 - 15h30",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        // Séparateur conflit
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color(0xFFE5E7EB))
            Text(
                "  1 CONFLIT  ",
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Divider(modifier = Modifier.weight(1f), color = Color(0xFFE5E7EB))
        }

        // Event 2 — Révisions Physique
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFEF9C3))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(EventOrange)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    "Révisions Physique",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Text(
                    "Mer 29 · 14h00 - 16h00",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Suggestion
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFEFF6FF))
                .border(1.dp, Color(0xFF93C5FD), RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("💡", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "Suggestion : Déplacer à Mar 28 · 10h-12h ?",
                fontSize = 13.sp,
                color = Color(0xFF1D4ED8),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bouton Accepter
        Button(
            onClick = onAccept,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EventGreen)
        ) {
            Text(
                "✓  Accepter la suggestion",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bouton Modifier manuellement
        OutlinedButton(
            onClick = onEditManually,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD1D5DB))
        ) {
            Text(
                "Modifier manuellement",
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Supprimer
        TextButton(onClick = onDelete) {
            Text(
                "Supprimer un événement",
                fontSize = 15.sp,
                color = EventRed,
                fontWeight = FontWeight.Medium
            )
        }
    }
}