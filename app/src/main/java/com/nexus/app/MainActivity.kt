package com.nexus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg      = Color(0xFF0A0A0F)
private val Bg2     = Color(0xFF12121A)
private val Bg3     = Color(0xFF1A1A24)
private val Border  = Color(0xFF26262F)
private val Text    = Color(0xFFE8E8F0)
private val TextDim = Color(0xFF8A8A96)
private val Accent  = Color(0xFF00D4FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NexusApp() }
    }
}

@Composable
fun NexusApp() {
    var tab by remember { mutableStateOf("devices") }

    Surface(color = Bg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Bg2)
                    .padding(16.dp)
            ) {
                Text("NEXUS", color = Accent, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Box(Modifier.weight(1f)) {
                when (tab) {
                    "devices"  -> DevicesScreen()
                    "commands" -> CommandsScreen()
                    "rules"    -> RulesScreen()
                    "ai"       -> AIScreen()
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Bg2)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NavBtn("Devices",  "devices",  tab) { tab = it }
                NavBtn("Commands", "commands", tab) { tab = it }
                NavBtn("Rules",    "rules",    tab) { tab = it }
                NavBtn("AI",       "ai",       tab) { tab = it }
            }
        }
    }
}

@Composable
fun NavBtn(label: String, id: String, current: String, onClick: (String) -> Unit) {
    Column(
        Modifier.clickable { onClick(id) }.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            label,
            color = if (current == id) Accent else TextDim,
            fontSize = 12.sp,
            fontWeight = if (current == id) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun DevicesScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Devices", color = Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxSize()
                .background(Bg2, RoundedCornerShape(12.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No device connected", color = Text, fontSize = 16.sp)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Pairing code se apna phone connect karo",
                    color = TextDim, fontSize = 12.sp, textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CommandsScreen() {
    val cmds = listOf(
        "Info", "Battery", "Location",
        "Screenshot", "Notify", "Shell",
        "Files", "Clipboard", "Ring",
        "Lock", "Vibrate", "Toast"
    )
    var output by remember { mutableStateOf("Waiting for command...") }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Commands", color = Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(300.dp)
        ) {
            items(cmds) { name ->
                Box(
                    Modifier
                        .background(Bg2, RoundedCornerShape(10.dp))
                        .clickable { output = "> $name executed\n(agent pending)" }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(name, color = Text, fontSize = 11.sp)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .background(Bg2, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(output, color = Accent, fontSize = 12.sp)
        }
    }
}

@Composable
fun RulesScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Rules", color = Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .background(Bg2, RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Text("Koi rule nahi. Naya rule banao.", color = TextDim, fontSize = 13.sp)
        }
    }
}

@Composable
fun AIScreen() {
    var input by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf("Salam! Kya karna hai bolo.") }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("AI Agent", color = Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Bg2, RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages.size) { i ->
                val isUser = i % 2 == 1
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        messages[i],
                        color = if (isUser) Color.Black else Text,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .background(if (isUser) Accent else Bg3, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Kya karna hai?", color = TextDim) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Accent,
                    unfocusedBorderColor = Border,
                    focusedTextColor = Text,
                    unfocusedTextColor = Text
                )
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    if (input.isNotBlank()) {
                        messages.add(input)
                        messages.add("Soch raha hoon...")
                        input = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Accent)
            ) {
                Text("Send", color = Color.Black)
            }
        }
    }
}
