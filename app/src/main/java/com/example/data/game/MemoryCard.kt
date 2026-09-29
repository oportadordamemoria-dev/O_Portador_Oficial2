package com.example.data.game

import com.example.R

enum class CardType {
    CHARACTER,
    LOCATION,
    VALUE,
    SYMBOL
}

enum class CardCategory(val label: String) {
    PERSONAGEM("Personagem"),
    LUGAR("Lugar"),
    VALOR("Valor"),
    MEMORIA("Memória")
}

data class MemoryCard(
    val id: String,
    val name: String,
    val type: CardType,
    val category: CardCategory,
    val imageRes: Int? = null,
    val symbolIcon: String = "✦",
    val shortDescription: String = "",
    val relationKey: String = "",
    val relationHint: String = ""
)

object MemoryCardRepository {

    val allCards: List<MemoryCard> = listOf(
        // PERSONAGENS CANÔNICOS
        MemoryCard(
            id = "char_jonatas",
            name = "Jônatas",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_jonatas,
            symbolIcon = "🍃",
            shortDescription = "O Portador da Memória e da Esperança",
            relationKey = "rel_jonatas_folha",
            relationHint = "Porta a Folha da Árvore dos Nomes"
        ),
        MemoryCard(
            id = "char_rafael",
            name = "Rafael",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_rafael,
            symbolIcon = "🪶",
            shortDescription = "O Guardião da Luz e Mensageiro",
            relationKey = "rel_rafael_pena",
            relationHint = "Guarda a Pena Dourada e a espada de luz"
        ),
        MemoryCard(
            id = "char_miriam",
            name = "Miriam",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_miriam,
            symbolIcon = "🌸",
            shortDescription = "A Guardiã do Jardim e Mãe dos Esquecidos",
            relationKey = "rel_miriam_jardim",
            relationHint = "Acolhe e protege no Jardim das Crianças"
        ),
        MemoryCard(
            id = "char_daniel",
            name = "Daniel",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_daniel,
            symbolIcon = "🌰",
            shortDescription = "O Guardião da Esperança Infantil",
            relationKey = "rel_daniel_bolota",
            relationHint = "Carrega a bolota que simboliza a semente viva"
        ),
        MemoryCard(
            id = "char_menina_cancao",
            name = "A Menina da Canção",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_menina_cancao,
            symbolIcon = "🎶",
            shortDescription = "A Voz da Memória",
            relationKey = "rel_menina_cancao",
            relationHint = "Entoa a canção eterna que nunca se apaga"
        ),
        MemoryCard(
            id = "char_elias",
            name = "Elias",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_elias,
            symbolIcon = "🌿",
            shortDescription = "O Guardião do Silêncio",
            relationKey = "rel_elias_folha",
            relationHint = "Guarda a folha parcialmente restaurada"
        ),
        MemoryCard(
            id = "char_miguel",
            name = "Miguel",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_miguel,
            symbolIcon = "🛡️",
            shortDescription = "O Guardião da Fronteira",
            relationKey = "rel_miguel_lanca",
            relationHint = "Empunha a Lança de Luz no limiar dos mundos"
        ),
        MemoryCard(
            id = "char_rei_esquecimento",
            name = "Rei do Esquecimento",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_rei_esquecimento,
            symbolIcon = "🕸️",
            shortDescription = "A face que devora as memórias",
            relationKey = "rel_rei_raizes",
            relationHint = "Reina sobre as raízes nas profundezas"
        ),
        MemoryCard(
            id = "char_malach",
            name = "Malach",
            type = CardType.CHARACTER,
            category = CardCategory.PERSONAGEM,
            imageRes = R.drawable.char_malach,
            symbolIcon = "🍂",
            shortDescription = "O Homem que Esqueceu de Ser Lembrado",
            relationKey = "rel_malach_perdao",
            relationHint = "A essência humana redimida pela memória"
        ),

        // LUGARES CANÔNICOS
        MemoryCard(
            id = "loc_blumenau",
            name = "Blumenau",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_blumenau,
            symbolIcon = "🌧️",
            shortDescription = "O mundo cotidiano de chuva e neblina",
            relationKey = "rel_blumenau_chuva",
            relationHint = "A cidade real onde a travessia começa"
        ),
        MemoryCard(
            id = "loc_floresta",
            name = "Floresta Oblivionis",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_floresta,
            symbolIcon = "🌲",
            shortDescription = "O território do esquecimento",
            relationKey = "rel_floresta_nevoa",
            relationHint = "Raízes retorcidas e névoa profunda"
        ),
        MemoryCard(
            id = "loc_jardim",
            name = "Jardim das Crianças",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_jardim,
            symbolIcon = "✨",
            shortDescription = "Refúgio da Luz e da Memória",
            relationKey = "rel_miriam_jardim",
            relationHint = "Santuário cercado por fronteiras de luz"
        ),
        MemoryCard(
            id = "loc_arvore_nomes",
            name = "Árvore dos Nomes",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_arvore_nomes,
            symbolIcon = "🌳",
            shortDescription = "Onde cada nome é eternamente preservado",
            relationKey = "rel_jonatas_folha",
            relationHint = "Guarda a identidade de cada alma lembrada"
        ),
        MemoryCard(
            id = "loc_arvore_nunca_chamados",
            name = "Árvore dos Nunca Chamados",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_arvore_nunca_chamados,
            symbolIcon = "🌑",
            shortDescription = "A ausência extrema na floresta",
            relationKey = "rel_nunca_chamados",
            relationHint = "Tronco escuro sem marcas nas profundezas"
        ),
        MemoryCard(
            id = "loc_vale_silenciados",
            name = "Vale dos Silenciados",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_vale_silenciados,
            symbolIcon = "🌫️",
            shortDescription = "Onde ecos e vozes esquecidas aguardam",
            relationKey = "rel_silencio",
            relationHint = "Árvores pálidas e silêncio profundo"
        ),
        MemoryCard(
            id = "loc_trono_raizes",
            name = "Trono sob as Raízes",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_trono_raizes,
            symbolIcon = "👑",
            shortDescription = "Centro do domínio do esquecimento",
            relationKey = "rel_rei_raizes",
            relationHint = "Câmara profunda construída de raízes e sombras"
        ),
        MemoryCard(
            id = "loc_rio_cristalino",
            name = "Rio Cristalino",
            type = CardType.LOCATION,
            category = CardCategory.LUGAR,
            imageRes = R.drawable.loc_rio_cristalino,
            symbolIcon = "💧",
            shortDescription = "As águas de cura e sustentação do Jardim",
            relationKey = "rel_aguas_vivas",
            relationHint = "Atravessa o Jardim com águas de proteção"
        ),

        // VALORES E CONCEITOS DO UNIVERSO
        MemoryCard(
            id = "val_memoria",
            name = "Memória",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.app_icon_symbol,
            symbolIcon = "✦",
            shortDescription = "O mais antigo ato de amor e resistência",
            relationKey = "rel_jonatas_folha",
            relationHint = "Preserva a existência contra o vazio"
        ),
        MemoryCard(
            id = "val_esperanca",
            name = "Esperança",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.daniel_scene,
            symbolIcon = "🌱",
            shortDescription = "A certeza de que a luz vencerá a névoa",
            relationKey = "rel_daniel_bolota",
            relationHint = "Guardada no coração de quem acredita"
        ),
        MemoryCard(
            id = "val_compaixao",
            name = "Compaixão",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.miriam_garden,
            symbolIcon = "🕊️",
            shortDescription = "Olhar para o esquecido e acolhê-lo",
            relationKey = "rel_miriam_jardim",
            relationHint = "O amor que se abaixa para cuidar"
        ),
        MemoryCard(
            id = "val_coragem",
            name = "Coragem",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.jonatas_journey,
            symbolIcon = "⚡",
            shortDescription = "Entrar na névoa para resgatar o próximo",
            relationKey = "rel_coragem_jonatas",
            relationHint = "Avançar mesmo quando tudo parece incerto"
        ),
        MemoryCard(
            id = "val_identidade",
            name = "Identidade",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.loc_arvore_nomes,
            symbolIcon = "📜",
            shortDescription = "Cada vida possui um nome sagrado",
            relationKey = "rel_arvore_nomes",
            relationHint = "Lembrar um nome é devolver a dignidade"
        ),
        MemoryCard(
            id = "val_acolhimento",
            name = "Acolhimento",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.loc_jardim,
            symbolIcon = "🏡",
            shortDescription = "O refúgio de paz onde o medo diminui",
            relationKey = "rel_miriam_jardim",
            relationHint = "Um espaço seguro para sarar as feridas"
        ),
        MemoryCard(
            id = "val_protecao",
            name = "Proteção",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.rafael_glorious,
            symbolIcon = "🛡️",
            shortDescription = "Manter as fronteiras firmes pela luz",
            relationKey = "rel_rafael_pena",
            relationHint = "Permanecer enquanto a ferida se cura"
        ),
        MemoryCard(
            id = "val_escuta",
            name = "Escuta",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.menina_cancao_scene,
            symbolIcon = "👂",
            shortDescription = "Ouvir o sussurro da canção que resiste",
            relationKey = "rel_menina_cancao",
            relationHint = "Atenção sincera às vozes que o mundo calou"
        ),
        MemoryCard(
            id = "val_reconciliacao",
            name = "Reconciliação",
            type = CardType.VALUE,
            category = CardCategory.VALOR,
            imageRes = R.drawable.malach_scene,
            symbolIcon = "🤝",
            shortDescription = "Nem todo fim precisa ser o fim",
            relationKey = "rel_malach_perdao",
            relationHint = "O perdão que quebra as raízes da dor"
        )
    )

    // Pares conceituais canônicos para o Nível 2 (Relações)
    val relationPairs: List<Pair<MemoryCard, MemoryCard>> by lazy {
        listOf(
            // Miriam + Jardim das Crianças
            getCard("char_miriam") to getCard("loc_jardim"),
            // Jônatas + Árvore dos Nomes
            getCard("char_jonatas") to getCard("loc_arvore_nomes"),
            // Rafael + Proteção (ou Asas da Luz)
            getCard("char_rafael") to getCard("val_protecao"),
            // Daniel + Esperança
            getCard("char_daniel") to getCard("val_esperanca"),
            // A Menina da Canção + Escuta
            getCard("char_menina_cancao") to getCard("val_escuta"),
            // Blumenau + Rio Cristalino (ou Memória)
            getCard("loc_blumenau") to getCard("val_memoria"),
            // Rei do Esquecimento + Trono sob as Raízes
            getCard("char_rei_esquecimento") to getCard("loc_trono_raizes"),
            // Malach + Reconciliação
            getCard("char_malach") to getCard("val_reconciliacao")
        )
    }

    fun getCard(id: String): MemoryCard {
        return allCards.firstOrNull { it.id == id } ?: allCards.first()
    }
}
