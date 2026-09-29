package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BookUniverse
import com.example.data.JourneyStep
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.NextSectionButton
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

@Composable
fun JourneyScreen(
    onBack: () -> Unit,
    onNavigateCharacter: (String) -> Unit,
    onNavigateCharacters: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BookTopBar(
                title = "A Jornada de Jônatas",
                onBack = onBack,
                shareContent = "Acompanhe as 6 etapas da jornada épica de Jônatas em O Portador da Memória: Entre a Luz e a Escuridão."
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Protagonist Profile Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .testTag("protagonist_card"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, GoldDark)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.char_jonatas),
                            contentDescription = "Jônatas - Jovem Adulto Protagonista",
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, GoldPrimary, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            GoldBadge(text = "O Portador da Esperança")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Jônatas",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextParchment
                                )
                            )
                            Text(
                                text = "Jovem de 23 anos que, em uma noite de chuva em Blumenau, cruza o limiar para a Floresta Oblivionis. Portando a Folha da Árvore dos Nomes, sua missão é proteger a memória e a esperança.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMuted,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }

            // Timeline Header
            item {
                SectionHeader(
                    title = "As 6 Etapas da Jornada",
                    subtitle = "Do primeiro chamado à travessia de volta para Blumenau",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }

            // Steps Timeline
            items(BookUniverse.journeySteps.size) { index ->
                val step = BookUniverse.journeySteps[index]
                JourneyStepItem(
                    step = step,
                    isLast = index == BookUniverse.journeySteps.size - 1,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            // Atalho para a Próxima Seção
            item {
                NextSectionButton(
                    sectionTitle = "Personagens",
                    icon = Icons.Default.Person,
                    onClick = onNavigateCharacters,
                    testTag = "journey_btn_next_characters"
                )
            }
        }
    }
}

@Composable
fun JourneyStepItem(
    step: JourneyStep,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Timeline indicator column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(GoldContainer)
                    .border(1.5.dp, GoldPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${step.step}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldLight
                    )
                )
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(CanvasBorder)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content Card
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = CanvasCard),
            border = BorderStroke(1.dp, CanvasBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = step.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldLight
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = MistSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = step.location,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MistSecondary
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = step.summary,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextParchment,
                        lineHeight = 20.sp
                    )
                )
            }
        }
    }
}
