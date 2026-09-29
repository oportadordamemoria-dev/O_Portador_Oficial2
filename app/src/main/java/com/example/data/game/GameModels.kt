package com.example.data.game

import com.example.R

enum class GameMode {
    PAIRS,               // Nível 1: Pares visuais simples
    RELATIONS,           // Nível 2: Relações canônicas
    SEQUENCE,            // Nível 3: Sequência memorizada
    PROGRESSIVE_CHAIN,   // Nível 4: Mais uma memória acrescentada...
    MISSING_CARD,        // Nível 5 e Modo Infantil: Qual memória desapareceu?
    INFINITE             // Modo Infinito: Rodadas consecutivas
}

data class GameLevelConfig(
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val rankTitle: String,
    val description: String,
    val environmentId: String,
    val environmentName: String,
    val environmentImageRes: Int,
    val gameMode: GameMode,
    val previewSeconds: Int = 4,
    val targetRounds: Int = 3
)

data class EnvironmentJourneyItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val imageRes: Int,
    val unlockedAtLevel: Int,
    val description: String
)

object GameContentData {

    val environments = listOf(
        EnvironmentJourneyItem(
            id = "blumenau",
            name = "Blumenau",
            subtitle = "O Ponto de Partida",
            imageRes = R.drawable.blumenau_art,
            unlockedAtLevel = 1,
            description = "Onde a chuva e o chamado inicial revelam que as memórias mais preciosas merecem ser guardadas."
        ),
        EnvironmentJourneyItem(
            id = "floresta",
            name = "Floresta Oblivionis",
            subtitle = "O Limiar da Névoa",
            imageRes = R.drawable.loc_floresta,
            unlockedAtLevel = 2,
            description = "O território onde as raízes retorcidas tentam sufocar os nomes que o mundo esqueceu."
        ),
        EnvironmentJourneyItem(
            id = "jardim",
            name = "Jardim das Crianças",
            subtitle = "O Santuário da Luz",
            imageRes = R.drawable.loc_jardim,
            unlockedAtLevel = 3,
            description = "Refúgio sagrado onde Miriam e as crianças mantêm acesa a chama do acolhimento e da vida."
        ),
        EnvironmentJourneyItem(
            id = "arvore_nomes",
            name = "Árvore dos Nomes",
            subtitle = "O Coração da Identidade",
            imageRes = R.drawable.loc_arvore_nomes,
            unlockedAtLevel = 4,
            description = "Onde cada folha dourada preserva um nome sagrado que nunca será apagado."
        ),
        EnvironmentJourneyItem(
            id = "vale_silenciados",
            name = "Vale dos Silenciados",
            subtitle = "Os Ecos da Espera",
            imageRes = R.drawable.loc_vale_silenciados,
            unlockedAtLevel = 5,
            description = "Vozes adormecidas que aguardam pacientemente por alguém que ouse recordar."
        ),
        EnvironmentJourneyItem(
            id = "trono_raizes",
            name = "Trono sob as Raízes",
            subtitle = "A Redenção Final",
            imageRes = R.drawable.loc_trono_raizes,
            unlockedAtLevel = 5,
            description = "Onde o perdão e a verdade transformam a escuridão do vazio em reconciliação definitiva."
        )
    )

    val levels = listOf(
        GameLevelConfig(
            levelNumber = 1,
            title = "O Primeiro Lembrar",
            subtitle = "Dificuldade Fácil • Memória Visual Simples",
            rankTitle = "Lembrança",
            description = "Observe as cartas abertas durante alguns segundos. Depois que virarem, encontre os pares de memórias idênticas. Adequado para todas as idades.",
            environmentId = "blumenau",
            environmentName = "Blumenau",
            environmentImageRes = R.drawable.blumenau_art,
            gameMode = GameMode.PAIRS,
            previewSeconds = 4,
            targetRounds = 3 // 6 cartas (3 pares) -> 8 cartas (4 pares) -> 10 cartas (5 pares)
        ),
        GameLevelConfig(
            levelNumber = 2,
            title = "As Memórias se Misturam",
            subtitle = "Dificuldade Média • Relações e Associações",
            rankTitle = "Guardião",
            description = "Agora as memórias se entrelaçam. Observe as relações canônicas exibidas antes da rodada e una os pares correspondentes (ex: Miriam e o Jardim, Jônatas e a Árvore dos Nomes).",
            environmentId = "floresta",
            environmentName = "Floresta Oblivionis",
            environmentImageRes = R.drawable.loc_floresta,
            gameMode = GameMode.RELATIONS,
            previewSeconds = 5,
            targetRounds = 3
        ),
        GameLevelConfig(
            levelNumber = 3,
            title = "O Caminho da Memória",
            subtitle = "Dificuldade Média / Alta • Sequência Visual",
            rankTitle = "Protetor",
            description = "Memorize a trilha de memórias apresentada na ordem exata. Depois que as cartas forem ocultadas e embaralhadas, reconstrua o caminho tocando na sequência correta.",
            environmentId = "jardim",
            environmentName = "Jardim das Crianças",
            environmentImageRes = R.drawable.loc_jardim,
            gameMode = GameMode.SEQUENCE,
            previewSeconds = 4,
            targetRounds = 3
        ),
        GameLevelConfig(
            levelNumber = 4,
            title = "Antes que Seja Esquecido",
            subtitle = "Dificuldade Alta • Cadeia Progressiva",
            rankTitle = "Guardião da Memória",
            description = "A cada acerto, uma nova memória é acrescentada ao encadeamento: 3, 4, 5, 6 cartas... Quanto você consegue lembrar antes que tudo seja esquecido?",
            environmentId = "arvore_nomes",
            environmentName = "Árvore dos Nomes",
            environmentImageRes = R.drawable.loc_arvore_nomes,
            gameMode = GameMode.PROGRESSIVE_CHAIN,
            previewSeconds = 4,
            targetRounds = 4
        ),
        GameLevelConfig(
            levelNumber = 5,
            title = "O Rei do Esquecimento",
            subtitle = "Desafio Mestre • Atenção e Reconhecimento",
            rankTitle = "Portador da Memória",
            description = "O domínio das sombras tenta apagar as lembranças. Memorize o quadro completo. Após a névoa, descubra: o que foi esquecido? A luz retornará a cada memória preservada.",
            environmentId = "trono_raizes",
            environmentName = "Trono sob as Raízes",
            environmentImageRes = R.drawable.loc_trono_raizes,
            gameMode = GameMode.MISSING_CARD,
            previewSeconds = 5,
            targetRounds = 3
        )
    )

    fun getLevel(levelNumber: Int): GameLevelConfig {
        return levels.firstOrNull { it.levelNumber == levelNumber } ?: levels.first()
    }
}
