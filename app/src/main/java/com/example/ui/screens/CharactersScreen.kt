package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookCharacter
import com.example.data.BookUniverse
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.LiteraryQuoteCard
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
fun CharactersScreen(
    onBack: () -> Unit,
    onSelectCharacter: (String) -> Unit,
    onNavigateLocations: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BookTopBar(
                title = "Personagens",
                onBack = onBack,
                shareContent = "Conheça os personagens canônicos de O Portador da Memória: Entre a Luz e a Escuridão."
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
            item {
                SectionHeader(
                    title = "Figuras do Volume 1",
                    subtitle = "Vozes, guardiões e almas que atravessam a fronteira entre a memória e o esquecimento",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }

            items(BookUniverse.characters.size) { index ->
                val char = BookUniverse.characters[index]
                CharacterItemCard(
                    character = char,
                    onClick = { onSelectCharacter(char.id) },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Atalho para a Próxima Seção
            item {
                NextSectionButton(
                    sectionTitle = "Lugares do Universo",
                    icon = Icons.Default.Landscape,
                    onClick = onNavigateLocations,
                    testTag = "characters_btn_next_locations"
                )
            }
        }
    }
}

@Composable
fun CharacterItemCard(
    character: BookCharacter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayTitle = if (character.subpageTitle.isNotEmpty()) character.subpageTitle else character.title

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("character_card_${character.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            if (character.imageRes != null) {
                Image(
                    painter = painterResource(id = character.imageRes),
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, GoldDark, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldContainer)
                        .border(1.dp, GoldDark, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        fontSize = 19.sp
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = displayTitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MistSecondary,
                        fontSize = 14.sp
                    )
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ver detalhes",
                tint = GoldDark,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterDetailScreen(
    characterId: String,
    onBack: () -> Unit
) {
    val character = BookUniverse.characters.find {
        it.id == characterId || (characterId == "malach" && it.id == "rei_do_esquecimento")
    } ?: BookUniverse.characters.first()

    val displayTitle = if (character.subpageTitle.isNotEmpty()) character.subpageTitle else character.title
    var selectedDualForm by remember { mutableStateOf(if (characterId == "malach") 1 else 0) }

    val activeHeaderImage = if (character.id == "rei_do_esquecimento") {
        if (selectedDualForm == 0) character.imageRes else character.secondaryImageRes
    } else {
        character.imageRes
    }

    Scaffold(
        topBar = {
            BookTopBar(
                title = character.name,
                onBack = onBack,
                shareContent = if (character.literaryQuote.isNotEmpty()) {
                    "${character.name} — $displayTitle\n\n${character.description}\n\n“${character.literaryQuote}”"
                } else {
                    "${character.name} — $displayTitle\n\n${character.description}"
                }
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
            // Header Image / Portrait
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                        .background(CanvasDeep),
                    contentAlignment = Alignment.Center
                ) {
                    if (activeHeaderImage != null) {
                        Image(
                            painter = painterResource(id = activeHeaderImage),
                            contentDescription = character.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(GoldContainer)
                                .border(2.dp, GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }

            // Info Details
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    if (character.id == "rei_do_esquecimento") {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
                            colors = CardDefaults.cardColors(containerColor = CanvasCard),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "A MESMA ESSÊNCIA. DUAS REALIDADES.",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = GoldLight,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    FilterChip(
                                        selected = selectedDualForm == 0,
                                        onClick = { selectedDualForm = 0 },
                                        label = {
                                            Text(
                                                text = "🌑 Rei do Esquecimento",
                                                fontSize = 11.sp,
                                                fontWeight = if (selectedDualForm == 0) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = GoldContainer,
                                            selectedLabelColor = GoldLight
                                        )
                                    )
                                    FilterChip(
                                        selected = selectedDualForm == 1,
                                        onClick = { selectedDualForm = 1 },
                                        label = {
                                            Text(
                                                text = "☀️ Malach (O Redimido)",
                                                fontSize = 11.sp,
                                                fontWeight = if (selectedDualForm == 1) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = GoldContainer,
                                            selectedLabelColor = GoldLight
                                        )
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    val dynamicArchetype = if (character.id == "rei_do_esquecimento") {
                        if (selectedDualForm == 0) "O Devorador de Memórias (Forma Sombria)" else "O Redimido (A Origem que Ainda Sente)"
                    } else character.archetype

                    val dynamicSubtitle = if (character.id == "rei_do_esquecimento") {
                        if (selectedDualForm == 0) "A Face que Devora Memórias" else "O Homem que Esqueceu de Ser Lembrado"
                    } else displayTitle

                    GoldBadge(text = dynamicArchetype)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Text(
                        text = dynamicSubtitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MistSecondary
                        )
                    )

                    val effectiveQuote = if (character.id == "rei_do_esquecimento") {
                        if (selectedDualForm == 0) {
                            "Tudo o que é esquecido me pertence. O esquecimento não destrói de uma vez. Ele apenas convence que nunca existiu."
                        } else {
                            "Fui feito para reinar, mas aprendi que o maior poder é lembrar de cada um pelo nome que Deus lhe deu."
                        }
                    } else character.literaryQuote

                    val effectiveAuthor = if (character.id == "rei_do_esquecimento") {
                        if (selectedDualForm == 0) "Rei do Esquecimento" else "Malach"
                    } else character.name

                    if (effectiveQuote.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        LiteraryQuoteCard(
                            quote = effectiveQuote,
                            author = effectiveAuthor
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = character.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextParchment,
                            lineHeight = 22.sp
                        )
                    )

                    if (character.narrativeRecord.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        SectionHeader(
                            title = "Registro da Narrativa",
                            subtitle = "Natureza, essência e papel no universo de O Portador da Memória"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = character.narrativeRecord,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextParchment,
                                lineHeight = 22.sp
                            )
                        )
                    }

                    if (character.revelation.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        SectionHeader(
                            title = "Revelação",
                            subtitle = "A verdade desvelada ao final da história"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = CanvasCard),
                            border = BorderStroke(1.dp, CanvasBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = character.revelation,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextParchment,
                                    lineHeight = 22.sp
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Ficha de Referência Canônica Permanente
                    if (character.apparentAge.isNotEmpty() || character.mission.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(
                            title = "Ficha Canônica Permanente",
                            subtitle = "Diretrizes e identidade oficial do personagem no universo"
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = CanvasCard),
                            border = BorderStroke(1.dp, GoldDark.copy(alpha = 0.6f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                if (character.apparentAge.isNotEmpty()) {
                                    CanonicalDetailRow(label = "Idade Aparente", value = character.apparentAge)
                                }
                                if (character.origin.isNotEmpty()) {
                                    CanonicalDetailRow(label = "Origem", value = character.origin)
                                }
                                if (character.mission.isNotEmpty()) {
                                    CanonicalDetailRow(label = "Missão Canônica", value = character.mission)
                                }
                                if (character.symbol.isNotEmpty()) {
                                    CanonicalDetailRow(label = "Símbolo Sagrado", value = character.symbol)
                                }
                                if (character.dominantEmotion.isNotEmpty()) {
                                    CanonicalDetailRow(label = "Emoção Dominante", value = character.dominantEmotion)
                                }

                                if (character.personalItems.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Objetos Pessoais:",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = GoldLight,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    character.personalItems.forEach { item ->
                                        Row(
                                            modifier = Modifier.padding(vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("✦", color = GoldPrimary, fontSize = 11.sp)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = item,
                                                style = MaterialTheme.typography.bodySmall.copy(color = TextParchment)
                                            )
                                        }
                                    }
                                }

                                if (character.strikingDetails.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Detalhes Marcantes:",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = GoldLight,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    character.strikingDetails.forEach { detail ->
                                        Row(
                                            modifier = Modifier.padding(vertical = 2.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text("•", color = GoldPrimary, fontSize = 14.sp)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = detail,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = TextMuted,
                                                    lineHeight = 18.sp
                                                )
                                            )
                                        }
                                    }
                                }

                                if (character.colorPalette.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Paleta de Cores Canônica:",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = GoldLight,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        character.colorPalette.forEach { colorName ->
                                            GoldBadge(text = colorName, isSecondary = true)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Galeria / Ilustração Secundária
                    if (character.secondaryImageRes != null) {
                        val galleryTitle = when (character.id) {
                            "rafael" -> "O Estado Glorioso Revelado"
                            "miriam" -> "O Acolhimento no Jardim"
                            "daniel" -> "A Esperança nas Pequenas Coisas"
                            "elias" -> "A Quietude das Árvores Antigas"
                            "a_menina_da_cancao" -> "A Canção que o Tempo Não Apaga"
                            "miguel" -> "A Vigília na Fronteira dos Mundos"
                            "rei_do_esquecimento" -> "A Origem que Ainda Sente (Malach)"
                            else -> "A Jornada em Cena"
                        }
                        val gallerySubtitle = when (character.id) {
                            "rafael" -> "A verdadeira natureza angelical e resplandecente do Guardião da Luz"
                            "miriam" -> "A Mãe dos Esquecidos nutrindo a vida e a esperança no Jardim das Crianças"
                            "daniel" -> "Daniel descobrindo a beleza e a vida nas trilhas do Jardim"
                            "elias" -> "Elias em comunhão com o silêncio e as raízes protetoras do Jardim"
                            "a_menina_da_cancao" -> "A Menina da Canção caminhando descalça entre as clareiras e a Árvore dos Nomes"
                            "miguel" -> "Miguel vigiando a fronteira contra o avanço das sombras da Floresta Oblivionis"
                            "rei_do_esquecimento" -> "O homem real que se corrompeu na dor e reencontrou seu nome"
                            else -> "O Portador da Memória na travessia entre os mundos"
                        }
                        val galleryCaption = when (character.id) {
                            "rafael" -> "Rafael manifesta suas majestosas asas celestiais e empunha a espada de luz viva para proteger o Jardim das Crianças e seus acolhidos."
                            "miriam" -> "Miriam no solo fértil do Jardim das Crianças, cuidando com ternura das flores e ensinando que toda vida floresce quando é chamada pelo próprio nome com amor."
                            "daniel" -> "Daniel contempla os tesouros da terra — a bolota que carrega o potencial de uma floresta inteira —, lembrando que enquanto uma flor tiver seu nome lembrado, o Jardim nunca estará perdido."
                            "elias" -> "Elias apoia suavemente a mão sobre o tronco rugoso das árvores antigas antes de prosseguir, ouvindo a vida que renasceu em seu interior e vigiando com prudência e coragem silenciosa as crianças menores."
                            "a_menina_da_cancao" -> "Ao entoar a canção eterna do Jardim, o tempo desacelera, o vento se aquieta e as folhas douradas da Árvore dos Nomes estremecem suavemente, lembrando a cada coração que a memória da verdade jamais deixará de florescer."
                            "miguel" -> "Empunhando a majestosa Lança de Luz no cume dos desfiladeiros celestes, Miguel mantém os limites sagrados entre os mundos, assegurando que onde a luz permanece firme, a escuridão não pode avançar."
                            "rei_do_esquecimento" -> "Ao ser chamado por Jônatas pelo nome que Deus lhe deu, o Rei do Esquecimento deixa ruir sua carcaça colossal de raízes e sombras para revelar Malach: o homem real marcado pelas cicatrizes da dor, cujo coração volta a sentir quando a memória é restaurada."
                            else -> "Jônatas na travessia entre Blumenau e a Floresta Oblivionis, carregando a esperança e os nomes dos que não podem ser esquecidos."
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(
                            title = galleryTitle,
                            subtitle = gallerySubtitle
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, CanvasBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = character.secondaryImageRes),
                                    contentDescription = "${character.name} em cena",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(if (character.id == "rei_do_esquecimento") 360.dp else 210.dp),
                                    contentScale = ContentScale.Crop,
                                    alignment = if (character.id == "rei_do_esquecimento") Alignment.TopCenter else Alignment.Center
                                )
                                Text(
                                    text = galleryCaption,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextMuted,
                                        fontSize = 12.sp
                                    ),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    if (character.tertiaryImageRes != null) {
                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader(
                            title = "Entre a Luz e a Escuridão: A Redenção",
                            subtitle = "Nem todo fim precisa ser o fim"
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, CanvasBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = character.tertiaryImageRes),
                                    contentDescription = "Cena de Redenção",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(210.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = "As muralhas de trevas da Floresta Oblivionis se rompem diante da luz do Jardim. Malach acolhe o perdão e a redenção com lágrimas de alívio nos olhos e seu medalhão de folha no peito, provando que até o esquecimento mais profundo pode ser curado.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextMuted,
                                        fontSize = 12.sp
                                    ),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Traços Fundamentais",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        character.keyTraits.forEach { trait ->
                            GoldBadge(text = trait, isSecondary = true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CanonicalDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodySmall.copy(
                color = GoldLight,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextParchment,
                fontSize = 13.5.sp,
                lineHeight = 18.sp
            )
        )
    }
}
