package com.example.pm_corrutinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloqueoSinCorrutinas()
        }
    }
}

@Composable
fun BloqueoSinCorrutinas() {
    val scope = rememberCoroutineScope()
// Estado para controlar el ángulo del giro
    var angle by remember { mutableStateOf(0f) }
// Animación infinita para simular movimiento
    val infiniteTransition = rememberInfiniteTransition()
    val animatedAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    LaunchedEffect(animatedAngle) {
// Simula que el ángulo gira en función de la animación
        angle = animatedAngle
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
// Círculo giratorio
                Canvas(modifier = Modifier.size(100.dp)) {
                    drawCircle(
                        color = Color.Blue,
                        radius = size.minDimension / 2,
                        center = center,
                    )
                    drawLine(
                        color = Color.Red,
                        start = center,
                        end = center + center.copy(x = size.minDimension / 2 *
                                kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat(),
                            y = size.minDimension / 2 *
                                    kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
                        ),
                        strokeWidth = 4.dp.toPx()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch {
                        tareaPesada()
                    }
                }) {
                    Text("Ejecutar tarea pesada")
                }
            }
        }
    )
}
suspend fun tareaPesada(){
    delay(5000)
}//esta tarea no hace nada, solo simula un proceso pesado que tarda 5 segundos en ejecutarse (por ejemplo una carga grande de datos de una base de datos sqlite).