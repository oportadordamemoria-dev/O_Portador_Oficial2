package com.example.data

import android.net.Uri
import androidx.annotation.RawRes
import com.example.R

data class MusicTrack(
    val id: String,
    val title: String,
    val subtitle: String,
    val actConnection: String,
    val durationFormatted: String,
    val durationMs: Long,
    val description: String,
    val mood: String,
    val source: String,
    val rawResName: String? = null,
    val customUri: Uri? = null
)

object BookMusicLibrary {
    val tracks = listOf(
        MusicTrack(
            id = "track_1_chamado",
            title = "O Chamado do Guardião",
            subtitle = "Tema de Abertura & Despertar de Jônatas",
            actConnection = "Ato I — O Chamado",
            durationFormatted = "02:44",
            durationMs = 164100L,
            description = "Melodia solene e orquestral que acompanha o momento em que o cotidiano em Blumenau se desdobra para o limiar entre o mundo conhecido e as memórias esquecidas.",
            mood = "Solene, Épico, Despertar",
            source = "Composição original (Faixa Oficial)",
            rawResName = "music_memoria1"
        ),
        MusicTrack(
            id = "track_2_floresta",
            title = "Floresta Oblivionis",
            subtitle = "As Raízes do Esquecimento & Ecos de Malach",
            actConnection = "Ato II — A Travessia & Ato IV — O Esquecimento",
            durationFormatted = "03:14",
            durationMs = 194700L,
            description = "Atmosfera densa e misteriosa onde sussurros ancestrais e névoa cobrem as raízes gigantes que guardam as lembranças silenciadas pelo tempo.",
            mood = "Misterioso, Sombrio, Ancestral",
            source = "Composição original (Faixa Oficial)",
            rawResName = "music_memoria2"
        ),
        MusicTrack(
            id = "track_3_jardim",
            title = "O Santuário do Jardim",
            subtitle = "A Luz das Crianças & O Acolhimento de Miriam",
            actConnection = "Ato III — O Jardim",
            durationFormatted = "02:19",
            durationMs = 139000L,
            description = "Harmonias luminosas e serenas que transmitem proteção, refúgio e o brilho sagrado dos nomes gravados na Árvore dos Nomes.",
            mood = "Luminoso, Esperança, Sagrado",
            source = "Composição original (Faixa Oficial)",
            rawResName = "music_jardim"
        ),
        MusicTrack(
            id = "track_4_batalha",
            title = "A Batalha da Memória",
            subtitle = "A Resistência contra a Escuridão",
            actConnection = "Ato V — Entre a Luz e a Escuridão",
            durationFormatted = "02:51",
            durationMs = 171000L,
            description = "Tema dramático com percussão heroica e tensão crescente, retratando a coragem indispensável para que o esquecimento não apague a existência humana.",
            mood = "Dramático, Rítmico, Heroico",
            source = "Composição original (Faixa Oficial)",
            rawResName = "music_batalha"
        )
    )
}
