package com.example.data.puzzle

import com.example.R

/**
 * Níveis de Dificuldade do Puzzle da Memória.
 * Totalmente livres e desbloqueados desde o início.
 */
enum class PuzzleDifficulty(
    val gridSize: Int,
    val title: String,
    val piecesCount: Int,
    val badgeLabel: String,
    val description: String
) {
    FACIL(
        gridSize = 3,
        title = "Fácil",
        piecesCount = 9,
        badgeLabel = "3 × 3",
        description = "9 peças • Ideal para começar ou para crianças"
    ),
    MEDIO(
        gridSize = 4,
        title = "Médio",
        piecesCount = 16,
        badgeLabel = "4 × 4",
        description = "16 peças • Equilíbrio perfeito entre foco e observação"
    ),
    DIFICIL(
        gridSize = 5,
        title = "Difícil",
        piecesCount = 25,
        badgeLabel = "5 × 5",
        description = "25 peças • Desafio atento para mentes observadoras"
    ),
    MESTRE(
        gridSize = 6,
        title = "Mestre",
        piecesCount = 36,
        badgeLabel = "6 × 6",
        description = "36 peças • O teste supremo de reconstrução da memória"
    );

    val dimensionLabel: String get() = "$gridSize × $gridSize"
}

/**
 * Representação de uma imagem oficial do universo de O Portador da Memória.
 * Utiliza exclusivamente os assets oficiais existentes no projeto.
 */
data class PuzzleImage(
    val id: String,
    val title: String,
    val category: String,
    val imageRes: Int,
    val loreQuote: String
)

/**
 * Peça individual do quebra-cabeça.
 * Guarda suas coordenadas originais na imagem e sua posição atual no tabuleiro.
 */
data class PuzzlePiece(
    val id: Int,
    val originalRow: Int,
    val originalCol: Int,
    val currentRow: Int,
    val currentCol: Int
) {
    val isCorrect: Boolean get() = originalRow == currentRow && originalCol == currentCol
    val currentSlotIndex: Int get() = currentRow * 1000 + currentCol
}

/**
 * Catálogo das imagens oficiais disponíveis para o Puzzle da Memória.
 */
object PuzzleImageCatalog {
    val ALL_IMAGES: List<PuzzleImage> = listOf(
        PuzzleImage(
            id = "loc_blumenau",
            title = "Blumenau",
            category = "Cidade & Ponto de Partida",
            imageRes = R.drawable.loc_blumenau,
            loreQuote = "Onde o cotidiano de Santa Catarina e os limites do extraordinário se encontram."
        ),
        PuzzleImage(
            id = "loc_jardim",
            title = "Jardim das Crianças",
            category = "Lugar Sagrado",
            imageRes = R.drawable.loc_jardim,
            loreQuote = "Flores que nunca murcham e o eco de risos puros guardados no tempo."
        ),
        PuzzleImage(
            id = "loc_arvore_nomes",
            title = "Árvore dos Nomes",
            category = "Santuário Místico",
            imageRes = R.drawable.loc_arvore_nomes,
            loreQuote = "Em cada folha dourada, um nome que o mundo jamais deveria esquecer."
        ),
        PuzzleImage(
            id = "loc_floresta",
            title = "Floresta Oblivionis",
            category = "Reino da Névoa",
            imageRes = R.drawable.loc_floresta,
            loreQuote = "Névoas densas onde cada passo desafia a própria lembrança de quem somos."
        ),
        PuzzleImage(
            id = "loc_vale_silenciados",
            title = "Vale dos Silenciados",
            category = "Fronteira das Sombras",
            imageRes = R.drawable.loc_vale_silenciados,
            loreQuote = "Desfiladeiros de pedra onde os ecos do passado clamam por justiça e luz."
        ),
        PuzzleImage(
            id = "loc_trono_raizes",
            title = "Trono sob as Raízes",
            category = "Câmara Ancestral",
            imageRes = R.drawable.loc_trono_raizes,
            loreQuote = "A autoridade ancestral que dorme sob as raízes que sustentam a criação."
        ),
        PuzzleImage(
            id = "loc_arvore_nunca_chamados",
            title = "Árvore dos Nunca Chamados",
            category = "Monumento Misterioso",
            imageRes = R.drawable.loc_arvore_nunca_chamados,
            loreQuote = "Troncos colossais dedicados às almas cujos nomes nunca foram pronunciados."
        ),
        PuzzleImage(
            id = "loc_rio_cristalino",
            title = "Rio Cristalino",
            category = "Águas da Clareza",
            imageRes = R.drawable.loc_rio_cristalino,
            loreQuote = "Correntezas serenas que refletem a verdade da memória sem distorções."
        ),
        PuzzleImage(
            id = "miriam_garden",
            title = "Miriam no Jardim",
            category = "Cena Canônica",
            imageRes = R.drawable.miriam_garden,
            loreQuote = "A guardiã dos pequenos momentos cuidando das flores da inocência."
        ),
        PuzzleImage(
            id = "jonatas_journey",
            title = "A Jornada de Jônatas",
            category = "A Travessia",
            imageRes = R.drawable.jonatas_journey,
            loreQuote = "O jovem caminhante entre a claridade das ruas e o mistério das sombras."
        ),
        PuzzleImage(
            id = "menina_cancao_scene",
            title = "A Menina da Canção",
            category = "Presença Luminosa",
            imageRes = R.drawable.menina_cancao_scene,
            loreQuote = "Uma melodia pura que ecoa quando tudo parece perdido no esquecimento."
        ),
        PuzzleImage(
            id = "rafael_glorious",
            title = "O Guardião Rafael",
            category = "Encontro Celestial",
            imageRes = R.drawable.rafael_glorious,
            loreQuote = "A sabedoria serena e a força protetora que guiam os portadores da memória."
        ),
        PuzzleImage(
            id = "daniel_scene",
            title = "O Despertar de Daniel",
            category = "Momento Revelador",
            imageRes = R.drawable.daniel_scene,
            loreQuote = "O retorno da consciência e a lembrança do propósito esquecido."
        ),
        PuzzleImage(
            id = "blumenau_catedral",
            title = "Catedral São Paulo Apóstolo",
            category = "Marco Histórico de Blumenau",
            imageRes = R.drawable.blumenau_catedral,
            loreQuote = "A torre icônica recortando o céu catarinense com fé e história."
        ),
        PuzzleImage(
            id = "blumenau_rio",
            title = "Rio Itajaí-Açu",
            category = "Águas do Cotidiano",
            imageRes = R.drawable.blumenau_rio,
            loreQuote = "As águas que cortam a cidade onde a travessia começou."
        ),
        PuzzleImage(
            id = "blumenau_ruas",
            title = "Ruas sob a Chuva",
            category = "Cenário Urbano",
            imageRes = R.drawable.blumenau_ruas,
            loreQuote = "O reflexo dourado das lâmpadas sobre as calçadas molhadas de Blumenau."
        ),
        PuzzleImage(
            id = "hero_cover_art",
            title = "Luz e Escuridão",
            category = "Arte Conceitual",
            imageRes = R.drawable.hero_cover_art,
            loreQuote = "O eterno equilíbrio entre aquilo que lembramos e o que o esquecimento oculta."
        )
    )

    /**
     * Sorteia exatamente 3 imagens diferentes da lista oficial.
     * Nunca repete a mesma imagem entre as 3 opções.
     */
    fun getRandomTrio(excludeImageId: String? = null): List<PuzzleImage> {
        val pool = if (excludeImageId != null && ALL_IMAGES.size > 3) {
            ALL_IMAGES.filter { it.id != excludeImageId }
        } else {
            ALL_IMAGES
        }
        return pool.shuffled().take(3)
    }

    fun findById(id: String): PuzzleImage {
        return ALL_IMAGES.find { it.id == id } ?: ALL_IMAGES.first()
    }
}
