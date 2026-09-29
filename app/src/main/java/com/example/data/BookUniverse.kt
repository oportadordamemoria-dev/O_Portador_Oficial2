package com.example.data

import com.example.R

data class BookAct(
    val number: Int,
    val romanNumber: String,
    val title: String,
    val chaptersSpan: String,
    val description: String
)

data class BookChapter(
    val number: Int,
    val title: String,
    val actNumber: Int,
    val actName: String,
    val synopsis: String,
    val locations: List<String>,
    val characters: List<String>,
    val keywords: List<String>
)

data class BookCharacter(
    val id: String,
    val name: String,
    val title: String,
    val subpageTitle: String = "",
    val archetype: String,
    val shortDescription: String = "",
    val description: String,
    val narrativeRecord: String = "",
    val revelation: String = "",
    val imageRes: Int?,
    val secondaryImageRes: Int? = null,
    val tertiaryImageRes: Int? = null,
    val literaryQuote: String = "",
    val keyTraits: List<String>,
    val apparentAge: String = "",
    val origin: String = "",
    val mission: String = "",
    val symbol: String = "",
    val dominantEmotion: String = "",
    val personalItems: List<String> = emptyList(),
    val strikingDetails: List<String> = emptyList(),
    val colorPalette: List<String> = emptyList()
)

data class BookLocation(
    val id: String,
    val name: String,
    val realmType: String,
    val subpageSubtitle: String = "",
    val description: String,
    val imageRes: Int?,
    val keyFeatures: List<String>,
    val mapCoordinatesX: Float, // 0.0f to 1.0f for interactive map
    val mapCoordinatesY: Float
)

data class JourneyStep(
    val step: Int,
    val title: String,
    val location: String,
    val summary: String,
    val narrativeInsight: String = ""
)

data class AuthorArticle(
    val id: String,
    val title: String,
    val subtitle: String,
    val excerpt: String,
    val content: String,
    val date: String,
    val tags: List<String>
)

object BookUniverse {
    const val BOOK_TITLE = "O Portador da Memória"
    const val BOOK_SUBTITLE = "Entre a Luz e a Escuridão"
    const val AUTHOR_NAME = "Júlio César Rodrigues"
    const val AUTHOR_LOCATION = "Blumenau, Santa Catarina"
    const val AUTHOR_INSTAGRAM = "@juliocesarrodrigues195"
    const val BOOK_STORE_URL = "https://loja.uiclap.com/titulo/ua188578"
    const val CENTRAL_THEME_QUOTE =
        "Aquilo que se esquece não desaparece. Apenas espera por alguém que ouse lembrar. Lembrar é o mais antigo ato de amor e a última forma de resistência."

    val acts = listOf(
        BookAct(
            number = 1,
            romanNumber = "Ato I",
            title = "O Chamado",
            chaptersSpan = "Capítulos 1 a 6",
            description = "Após uma tempestade que altera o fluxo do rio em Blumenau, Jônatas é conduzido a um limiar onde a memória e o esquecimento começam a moldar a realidade visível. As primeiras fendas do mundo se abrem."
        ),
        BookAct(
            number = 2,
            romanNumber = "Ato II",
            title = "O Resgate",
            chaptersSpan = "Capítulos 7 a 12",
            description = "Jônatas desperta uma sensibilidade oculta e compreende que sua jornada exige encontrar e resgatar vidas que as sombras tentam apagar da memória do mundo."
        ),
        BookAct(
            number = 3,
            romanNumber = "Ato III",
            title = "O Coração das Trevas",
            chaptersSpan = "Capítulos 13 a 18",
            description = "A travessia adentra os recantos mais profundos da Floresta Oblivionis. Entre o Trono sob as Raízes e o Livro dos Nomes Partidos, revelam-se os segredos ancestrais que mantêm a noite."
        ),
        BookAct(
            number = 4,
            romanNumber = "Ato IV",
            title = "A Guerra da Memória",
            chaptersSpan = "Capítulos 19 a 26",
            description = "A tensão milenar entre o refúgio do Jardim das Crianças e as raízes da Floresta culmina em um confronto inevitável. O valor da lembrança torna-se a última trincheira contra o silêncio."
        ),
        BookAct(
            number = 5,
            romanNumber = "Ato V",
            title = "A Travessia",
            chaptersSpan = "Capítulos 27 a 32",
            description = "Sobre as marcas da batalha e as despedidas necessárias, descortina-se a reconstrução. Jônatas compreende que ser o Portador da Memória é um compromisso eterno com a esperança."
        )
    )

    val chapters = listOf(
        // ATO I — O CHAMADO (1 a 6)
        BookChapter(
            number = 1,
            title = "A Floresta do Esquecimento",
            actNumber = 1,
            actName = "Ato I — O Chamado",
            synopsis = "Em uma noite chuvosa em Blumenau, inquieto em meio ao cotidiano da cidade, Jônatas ouve o choro distante de uma criança. Ao seguir aquele som por uma passagem estreita entre os prédios, ele ultrapassa os limites do mundo conhecido e adentra a névoa fria da Floresta Oblivionis, deparando-se com os primeiros sinais de um domínio governado pelo esquecimento.",
            locations = listOf("Blumenau", "Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Blumenau", "Choro", "Floresta Oblivionis", "Esquecimento")
        ),
        BookChapter(
            number = 2,
            title = "Os que Não São Lembrados",
            actNumber = 1,
            actName = "Ato I — O Chamado",
            synopsis = "Avançando pela névoa densa da Floresta Oblivionis, Jônatas tem seus primeiros encontros com seres e almas que foram esquecidos pelo mundo dos vivos. Aos poucos, ele compreende que naquele território o esquecimento não é apenas uma ausência, mas uma força real e corrosiva que apaga identidades e aprisiona vidas inteiras.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Esquecimento", "Abandono", "Identidade", "Memória")
        ),
        BookChapter(
            number = 3,
            title = "Luz entre as Árvores",
            actNumber = 1,
            actName = "Ato I — O Chamado",
            synopsis = "Enquanto a opressão da Floresta parece sufocar seus passos, um clarão de luz suave surge por entre as copas escuras das árvores, quebrando a escuridão persistente. Esse vislumbre revela a Jônatas que a desesperança não é absoluta naquele domínio e que existe um caminho de proteção e esperança a ser alcançado.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Luz", "Esperança", "Proteção", "Floresta Oblivionis")
        ),
        BookChapter(
            number = 4,
            title = "Corrida pela Esperança",
            actNumber = 1,
            actName = "Ato I — O Chamado",
            synopsis = "Confrontado pelo perigo iminente e pelas sombras que rondam a Floresta, Jônatas precisa correr em uma luta desesperada contra o tempo para alcançar refúgio. Em meio ao cansaço e à perseguição das forças do esquecimento, sua determinação em encontrar um resgate torna-se o único impulso para não sucumbir.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Perseguição", "Resgate", "Determinação", "Esperança")
        ),
        BookChapter(
            number = 5,
            title = "O Jardim das Crianças",
            actNumber = 1,
            actName = "Ato I — O Chamado",
            synopsis = "Cruzando a fronteira de luz, Jônatas alcança o Jardim das Crianças, um santuário dourado em flagrante contraste com a escuridão da Floresta. Ali ele conhece Miriam e o menino Daniel, descobrindo um espaço sagrado de acolhimento e afeto dedicado a abrigar e proteger aqueles que o mundo esqueceu.",
            locations = listOf("Jardim das Crianças"),
            characters = listOf("Jônatas", "Miriam", "Daniel"),
            keywords = listOf("Jardim das Crianças", "Acolhimento", "Luz", "Proteção", "Memória")
        ),
        BookChapter(
            number = 6,
            title = "A Tempestade sobre o Jardim",
            actNumber = 1,
            actName = "Ato I — O Chamado",
            synopsis = "As forças sombrias da Floresta Oblivionis reagem e uma tempestade impetuosa avança contra as fronteiras do Jardim das Crianças. Conduzidos pelo abandono e pela indiferença que alimentam os Devoradores, os ataques testam a resistência da muralha de luz enquanto Miriam, Rafael e Jônatas se unem para proteger Daniel e as crianças.",
            locations = listOf("Jardim das Crianças"),
            characters = listOf("Jônatas", "Miriam", "Rafael", "Daniel"),
            keywords = listOf("Tempestade", "Devoradores", "Muralha de Luz", "Proteção", "Resistência")
        ),

        // ATO II — O RESGATE (7 a 12)
        BookChapter(
            number = 7,
            title = "O Eco dos Nomes",
            actNumber = 2,
            actName = "Ato II — O Resgate",
            synopsis = "No Jardim das Crianças, diante da majestosa Árvore dos Nomes, Jônatas compreende o valor sagrado que cada nome carrega na preservação da alma e da memória. O ato de lembrar e pronunciar uma identidade com ternura revela-se a principal defesa contra o poder apagador do esquecimento.",
            locations = listOf("Jardim das Crianças", "Árvore dos Nomes"),
            characters = listOf("Jônatas", "Miriam"),
            keywords = listOf("Nomes", "Memória", "Identidade", "Reconhecimento", "Esquecimento")
        ),
        BookChapter(
            number = 8,
            title = "O Retorno à Floresta",
            actNumber = 2,
            actName = "Ato II — O Resgate",
            synopsis = "Consciente de que não pode permanecer em segurança enquanto outras vidas continuam esquecidas nas trevas, Jônatas decide deixar a proteção do Jardim e retornar à Floresta Oblivionis. Guiado pelo compromisso de resgate, ele adentra novamente a névoa hostil, enfrentando perigos que desafiam sua própria integridade.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Retorno", "Missão", "Resgate", "Coragem", "Floresta Oblivionis")
        ),
        BookChapter(
            number = 9,
            title = "Os que Esperam nas Raízes",
            actNumber = 2,
            actName = "Ato II — O Resgate",
            synopsis = "Nas profundezas da Floresta, Jônatas testemunha a condição dolorosa daqueles cujas vidas foram enredadas pelas raízes retorcidas do esquecimento. Imóveis na escuridão, essas almas abandonadas aguardam em silêncio que alguém se recorde de quem foram antes de serem inteiramente tragadas pela terra.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Espera", "Abandono", "Raízes", "Esquecimento", "Memória")
        ),
        BookChapter(
            number = 10,
            title = "A Árvore dos que Nunca Foram Chamados",
            actNumber = 2,
            actName = "Ato II — O Resgate",
            synopsis = "Em uma clareira desolada da Floresta ergue-se a colossal Árvore dos que Nunca Foram Chamados, com seu tronco escuro e desprovido de marcas, guardando a ausência daqueles que partiram sem que ninguém pronunciasse seus nomes. Ali, Jônatas encontra a Menina da Canção e ouve seu canto nostálgico, que resiste à ausência absoluta.",
            locations = listOf("Floresta Oblivionis", "Árvore dos que Nunca Foram Chamados"),
            characters = listOf("Jônatas", "A Menina da Canção"),
            keywords = listOf("Árvore Sombria", "Ausência", "Identidade", "A Menina da Canção", "Esquecimento")
        ),
        BookChapter(
            number = 11,
            title = "O Vale dos Silenciados",
            actNumber = 2,
            actName = "Ato II — O Resgate",
            synopsis = "Jônatas desce pelas vertentes desoladas do Vale dos Silenciados, um território profundo da Floresta coberto por névoa cinzenta e árvores pálidas. O silêncio opressivo do vale abriga os murmúrios de vozes apagadas, onde os ecos daqueles que perderam partes de sua identidade tentam se fazer ouvir.",
            locations = listOf("Floresta Oblivionis", "Vale dos Silenciados"),
            characters = listOf("Jônatas"),
            keywords = listOf("Vale dos Silenciados", "Silêncio", "Vozes Esquecidas", "Abandono")
        ),
        BookChapter(
            number = 12,
            title = "Quando Daniel Retorna",
            actNumber = 2,
            actName = "Ato II — O Resgate",
            synopsis = "Daniel se torna a figura central deste momento crucial da narrativa, quando seu retorno marca a superação de perigos iminentes e reafirma o poder dos laços afetivos. Esse regresso traz alívio ao Jardim e reforça a convicção de Jônatas sobre a importância de resgatar cada alma perdida.",
            locations = listOf("Jardim das Crianças"),
            characters = listOf("Daniel", "Jônatas", "Miriam"),
            keywords = listOf("Retorno", "Resgate", "Afeto", "Esperança", "Proteção")
        ),

        // ATO III — O CORAÇÃO DAS TREVAS (13 a 18)
        BookChapter(
            number = 13,
            title = "O Trono sob as Raízes",
            actNumber = 3,
            actName = "Ato III — O Coração das Trevas",
            synopsis = "Nas profundezas mais recônditas da Floresta Oblivionis, Jônatas alcança a imensa câmara subterrânea onde se ergue o Trono Sob as Raízes. Tecido de madeira endurecida e veios negros, o trono carrega os rostos de vidas aprisionadas e abriga a presença imponente do Rei do Esquecimento, centro de todo o domínio sobre as memórias perdidas.",
            locations = listOf("Floresta Oblivionis", "Trono Sob as Raízes"),
            characters = listOf("Jônatas", "Rei do Esquecimento"),
            keywords = listOf("Trono Sob as Raízes", "Rei do Esquecimento", "Raízes", "Domínio", "Escuridão")
        ),
        BookChapter(
            number = 14,
            title = "A Voz que Reivindica",
            actNumber = 3,
            actName = "Ato III — O Coração das Trevas",
            synopsis = "Diante do abismo do esquecimento, ergue-se uma voz firme que reivindica os direitos fundamentais da memória e a dignidade das existências silenciadas. O confronto dialético e espiritual expõe a recusa em aceitar que as pessoas sejam transformadas em sombras sem nome.",
            locations = listOf("Floresta Oblivionis", "Trono Sob as Raízes"),
            characters = listOf("Jônatas", "Rei do Esquecimento"),
            keywords = listOf("Reivindicação", "Voz", "Confronto", "Memória", "Identidade")
        ),
        BookChapter(
            number = 15,
            title = "As Mães que Choram no Vento",
            actNumber = 3,
            actName = "Ato III — O Coração das Trevas",
            synopsis = "Ecos de lamentos ancestrais atravessam a escuridão da Floresta, carregando a dor das mães que choraram no vento por filhos que nunca puderam voltar. Jônatas entra em contato com o peso profundo dessa perda humana, cuja saudade nunca saciada ecoa contra o esquecimento forçado.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Dor", "Lamento", "Mães", "Perda", "Saudade")
        ),
        BookChapter(
            number = 16,
            title = "O Livro dos Nomes Partidos",
            actNumber = 3,
            actName = "Ato III — O Coração das Trevas",
            synopsis = "Jônatas tem acesso ao Livro dos Nomes Partidos, um registro sagrado e doloroso onde se encontram grafadas as identidades parcialmente esquecidas pelo mundo. Cada fragmento de nome inscrito em suas páginas atesta existências que ainda resistem à extinção total da memória.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Livro dos Nomes Partidos", "Nomes", "Identidade", "Resistência", "Memória")
        ),
        BookChapter(
            number = 17,
            title = "A Segunda Tempestade",
            actNumber = 3,
            actName = "Ato III — O Coração das Trevas",
            synopsis = "Uma nova e devastadora tormenta se forma nas entranhas da Floresta Oblivionis e avança com furor renovado em direção ao Jardim. A tensão entre os dois domínios atinge um limiar crítico, quando as sombras se organizam em um ataque sem precedentes que põe em xeque a paz do refúgio.",
            locations = listOf("Floresta Oblivionis", "Jardim das Crianças"),
            characters = listOf("Jônatas", "Miriam", "Rafael"),
            keywords = listOf("Tormenta", "Conflito", "Ameaça", "Escuridão", "Jardim")
        ),
        BookChapter(
            number = 18,
            title = "Quando a Muralha Cede",
            actNumber = 3,
            actName = "Ato III — O Coração das Trevas",
            synopsis = "A intensidade do ataque dos Devoradores rompe a muralha de luz que protegia o Jardim das Crianças, mergulhando o santuário em um perigo mortal. Em meio ao caos da brecha, Jônatas descobre sua própria força interior e se coloca na linha de frente ao lado de Miriam e Rafael para conter as sombras invasoras.",
            locations = listOf("Jardim das Crianças"),
            characters = listOf("Jônatas", "Miriam", "Rafael"),
            keywords = listOf("Muralha de Luz", "Ruptura", "Devoradores", "Defesa", "Coragem")
        ),

        // ATO IV — A GUERRA DA MEMÓRIA (19 a 26)
        BookChapter(
            number = 19,
            title = "Os Guardiões da Luz",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "Diante da crise iminente, a verdadeira vocação dos Guardiões da Luz se manifesta plenamente na defesa do santuário. A revelação do papel e da natureza protetora de Rafael e o auxílio de Miguel fortalecem a resistência do Jardim, unindo corações para a batalha decisiva pela preservação da memória.",
            locations = listOf("Jardim das Crianças"),
            characters = listOf("Jônatas", "Rafael", "Miguel"),
            keywords = listOf("Guardiões da Luz", "Rafael", "Miguel", "Batalha", "Memória")
        ),
        BookChapter(
            number = 20,
            title = "O Nome de Jônatas",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "Ao se aproximar da Árvore dos Nomes, Jônatas e Miguel deparam-se com uma revelação transformadora: o próprio nome de Jônatas resplandece entre as folhas douradas. Essa descoberta revela sua conexão profunda com aquele universo e seu papel como ponte viva entre o esquecimento da Floresta e a permanência da luz.",
            locations = listOf("Jardim das Crianças", "Árvore dos Nomes"),
            characters = listOf("Jônatas", "Miguel"),
            keywords = listOf("O Nome de Jônatas", "Árvore dos Nomes", "Identidade", "Revelação", "Memória")
        ),
        BookChapter(
            number = 21,
            title = "A Floresta contra o Jardim",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "O embate direto entre as forças da Floresta Oblivionis e os defensores do Jardim das Crianças atinge seu ápice em um confronto de proporções decisivas. Com os Devoradores avançando sobre as fronteiras, Jônatas e os Guardiões lutam incansavelmente para impedir que o esquecimento destrua tudo o que foi acolhido pela memória.",
            locations = listOf("Jardim das Crianças", "Floresta Oblivionis"),
            characters = listOf("Jônatas", "Rafael", "Miriam"),
            keywords = listOf("Confronto", "Devoradores", "Guardiões", "Esquecimento", "Memória")
        ),
        BookChapter(
            number = 22,
            title = "Quando o Rio se Levanta",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "Em um momento extremo da batalha, o Rio Cristalino ergue suas águas em uma manifestação extraordinária de defesa e proteção ao santuário. Em profunda comunhão com Miriam e sob o testemunho de Jônatas, as correntes luminosas repelem as sombras com um poder de renovação e sacrifício que muda o rumo do confronto.",
            locations = listOf("Jardim das Crianças", "Rio Cristalino"),
            characters = listOf("Miriam", "Jônatas"),
            keywords = listOf("Rio Cristalino", "Águas Luminosas", "Proteção", "Defesa", "Sacrifício")
        ),
        BookChapter(
            number = 23,
            title = "O Trono Vacila",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "As certezas e o poder absoluto do Trono Sob as Raízes começam a estremecer perante a determinação luminosa da memória. Diante de Jônatas, a estrutura milenar de raízes negras demonstra sinais de desgaste e vulnerabilidade, iniciando o colapso da autoridade do Rei do Esquecimento.",
            locations = listOf("Floresta Oblivionis", "Trono Sob as Raízes"),
            characters = listOf("Jônatas", "Rei do Esquecimento"),
            keywords = listOf("Trono Sob as Raízes", "Rei do Esquecimento", "Vacilo", "Transformação", "Memória")
        ),
        BookChapter(
            number = 24,
            title = "O que Ainda Pode Ser Curado",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "Diante de seu adversário enfraquecido, Jônatas rejeita o caminho da destruição e escolhe a via da misericórdia e da cura. Reconhecendo as feridas profundas que originaram tanta escuridão, ele compreende que a vitória da memória reside em resgatar e restaurar, e não em aniquilar.",
            locations = listOf("Floresta Oblivionis", "Trono Sob as Raízes"),
            characters = listOf("Jônatas", "Rei do Esquecimento"),
            keywords = listOf("Cura", "Misericórdia", "Escolha", "Compaixão", "Restauração")
        ),
        BookChapter(
            number = 25,
            title = "Quando o Rei Chora",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "A couraça impenetrável de amargura do Rei do Esquecimento se desfaz, e lágrimas rompem o manto milenar de silêncio que o envolvia. Esse choro comovente sela o fim de sua tirania e abre caminho para uma profunda transformação interior, desvelando a dor humana oculta sob séculos de frieza.",
            locations = listOf("Floresta Oblivionis", "Trono Sob as Raízes"),
            characters = listOf("Jônatas", "Rei do Esquecimento"),
            keywords = listOf("Lágrimas", "Queda", "Transformação", "Redenção", "Rei do Esquecimento")
        ),
        BookChapter(
            number = 26,
            title = "O que É Devolvido à Luz",
            actNumber = 4,
            actName = "Ato IV — A Guerra da Memória",
            synopsis = "Com a dissolução do esquecimento que o aprisionava, a verdadeira identidade de Malach é enfim revelada e devolvida à luz. Nomes e histórias sufocados por eras retornam ao reconhecimento e à dignidade, confirmando o triunfo da memória sobre as trevas.",
            locations = listOf("Floresta Oblivionis", "Trono Sob as Raízes"),
            characters = listOf("Jônatas", "Malach"),
            keywords = listOf("Malach", "Identidade", "Luz", "Reconhecimento", "Memória")
        ),

        // ATO V — A TRAVESSIA (27 a 32)
        BookChapter(
            number = 27,
            title = "Quando a Floresta se Parte",
            actNumber = 5,
            actName = "Ato V — A Travessia",
            synopsis = "As densas e opressivas raízes da Floresta Oblivionis se partem, desmantelando a barreira que retinha tantas vidas no esquecimento. Uma torrente de nomes e memórias libertadas eleva-se pelo ar, transformando a atmosfera fúnebre em um horizonte aberto de reconciliação e renovação.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas"),
            keywords = listOf("Ruptura", "Libertação", "Nomes", "Memórias", "Renovação")
        ),
        BookChapter(
            number = 28,
            title = "O que Resta entre as Ruínas",
            actNumber = 5,
            actName = "Ato V — A Travessia",
            synopsis = "Entre os vestígios do antigo domínio sombrio, Jônatas e Malach contemplam o silêncio que sucede a batalha e aquilo que permanece de pé. Nas cinzas do esquecimento superado, vislumbra-se a oportunidade dolorosa, porém serena, de iniciar uma reconstrução fundamentada na verdade e na memória.",
            locations = listOf("Floresta Oblivionis"),
            characters = listOf("Jônatas", "Malach"),
            keywords = listOf("Ruínas", "Reconstrução", "Memória", "Paz", "Verdade")
        ),
        BookChapter(
            number = 29,
            title = "A Semente sob as Raízes",
            actNumber = 5,
            actName = "Ato V — A Travessia",
            synopsis = "Em um ato simples e repleto de significado, o menino Daniel planta uma semente sob o solo antes endurecido pelas raízes do esquecimento. O gesto simboliza o nascimento de um novo tempo, no qual a vida e a esperança voltam a brotar onde antes reinava apenas o abandono.",
            locations = listOf("Floresta Oblivionis", "Jardim das Crianças"),
            characters = listOf("Daniel", "Jônatas"),
            keywords = listOf("Semente", "Esperança", "Reconstrução", "Futuro", "Daniel")
        ),
        BookChapter(
            number = 30,
            title = "Quando o Mundo Chama",
            actNumber = 5,
            actName = "Ato V — A Travessia",
            synopsis = "Os sons, as luzes e o ritmo do mundo cotidiano de Blumenau voltam a ecoar pela fronteira entre as realidades, chamando Jônatas de volta à sua vida original. Diante da fissura que une os dois mundos, ele percebe que a missão do Portador não termina ao cruzar de volta para sua cidade.",
            locations = listOf("Jardim das Crianças", "Blumenau"),
            characters = listOf("Jônatas", "Miriam"),
            keywords = listOf("Blumenau", "Chamado", "Fronteira", "Retorno", "Mundo Cotidiano")
        ),
        BookChapter(
            number = 31,
            title = "A Chuva sobre o Jardim",
            actNumber = 5,
            actName = "Ato V — A Travessia",
            synopsis = "A chuva mansa e conhecida de Blumenau transpõe a fronteira entre os mundos e cai suavemente sobre o Jardim das Crianças, unindo as duas realidades em um mesmo abraço de paz. Esse encontro comovente simboliza que as águas que antes pareciam frias e desoladoras agora nutrem a memória e renovam a esperança.",
            locations = listOf("Jardim das Crianças", "Blumenau"),
            characters = listOf("Jônatas", "Miriam", "Daniel"),
            keywords = listOf("Chuva", "Blumenau", "Jardim das Crianças", "União", "Esperança")
        ),
        BookChapter(
            number = 32,
            title = "A Travessia",
            actNumber = 5,
            actName = "Ato V — A Travessia",
            synopsis = "Ao cruzar definitivamente o limiar entre os mundos e retornar às ruas de Blumenau, Jônatas conclui sua travessia como um homem profundamente transformado. A verdadeira travessia, ele descobre, é o compromisso inegociável de reconhecer a dignidade única de cada pessoa e jamais permitir que nenhuma vida seja abandonada ao silêncio do esquecimento.",
            locations = listOf("Blumenau", "Jardim das Crianças"),
            characters = listOf("Jônatas"),
            keywords = listOf("A Travessia", "Reconhecimento", "Memória", "Dignidade", "Esperança")
        )
    )

    val characters = listOf(
        BookCharacter(
            id = "jonatas",
            name = "Jônatas",
            title = "O Portador da Memória",
            subpageTitle = "O Portador da Memória",
            archetype = "O Portador da Esperança",
            shortDescription = "Jovem de 23 anos que, em uma noite de chuva em Blumenau, segue o choro de uma criança e cruza a passagem para a Floresta Oblivionis, tornando-se o Portador da Memória e da Esperança.",
            description = "Em uma noite de chuva em Blumenau, Jônatas ouve o choro de uma criança e segue aquele chamado por uma estreita passagem que o conduz à Floresta Oblivionis. Portando a Folha da Árvore dos Nomes e movido por uma profunda empatia e determinação, sua missão é proteger a memória, os nomes e o equilíbrio entre a Luz e a Escuridão.",
            narrativeRecord = "Jônatas começa sua jornada no mundo cotidiano de Blumenau. Ao entrar na Floresta Oblivionis, encontra aqueles que o mundo esqueceu e descobre que suas memórias e identidades estão ameaçadas pelo esquecimento. Carregando sua bolsa de viagem rústica, seu caderno de anotações e o pingente luminoso da Árvore dos Nomes, ele se recusa a abandonar qualquer vida ao silêncio. No confronto final, ele acolhe o Rei do Esquecimento com a verdade e a misericórdia, devolvendo Malach à luz.",
            revelation = "Ao retornar pelas ruas de Blumenau na chuva mansa, Jônatas sela sua transformação definitiva como o guardião que mantém viva a dignidade de cada ser humano: enquanto houver um nome lembrado, a escuridão não terá a última palavra.",
            imageRes = R.drawable.char_jonatas,
            secondaryImageRes = R.drawable.jonatas_journey,
            literaryQuote = "Enquanto houver um nome lembrado, a escuridão não terá a última palavra.",
            keyTraits = listOf("Empático", "Leal", "Determinado", "Corajoso", "Observador", "Humilde"),
            apparentAge = "23 anos",
            origin = "Mundo Real (Blumenau) / Portador da Memória no Jardim",
            mission = "Proteger a memória, os nomes e o equilíbrio entre a Luz e a Escuridão",
            symbol = "Folha da Árvore dos Nomes",
            dominantEmotion = "Esperança",
            personalItems = listOf(
                "Bolsa de viagem em couro",
                "Caderno de anotações encadernado em couro",
                "Pingente luminoso da Folha da Árvore dos Nomes"
            ),
            strikingDetails = listOf(
                "Os olhos refletem discretamente a luz ao seu redor, mesmo nas trevas",
                "Postura humilde e atenta, observa antes de agir",
                "Sua presença transmite confiança, acolhimento e paz",
                "Carrega consigo a esperança, mesmo quando tudo parece perdido"
            ),
            colorPalette = listOf(
                "Azul Profundo",
                "Cinza Pedreira",
                "Marrom Couro",
                "Cru / Linho",
                "Dourado Suave"
            )
        ),
        BookCharacter(
            id = "rafael",
            name = "Rafael",
            title = "O Guardião da Luz",
            subpageTitle = "O Guardião da Luz (Estado Comum & Glorioso)",
            archetype = "O Guardião Discreto e Mensageiro da Luz",
            shortDescription = "Guardião sereno de 45 a 50 anos que caminha entre os mundos como viajante discreto, orientando Jônatas com sabedoria e compaixão sem violar seu livre-arbítrio.",
            description = "Rafael é o mensageiro e protetor que caminha entre os mundos sem revelar de imediato seu esplendor celestial. No estado comum, apresenta-se com cabelos grisalhos, barba branca bem cuidada e vestes escuras de viajante com a Pena Dourada oculta; no estado glorioso, manifesta suas majestosas asas celestiais de luz e empunha a espada resplandecente em defesa do Jardim.",
            narrativeRecord = "Desde o primeiro encontro na Floresta Oblivionis, onde sua presença traz alívio às dores do esquecimento, Rafael caminha ao lado de Jônatas como guia paciente e compassivo. Por onde passa, o ambiente se torna mais calmo, como se o tempo desacelerasse e o coração encontrasse descanso. Quando a batalha alcança o Jardim das Crianças, seu estado glorioso é desvelado: asas resplandecentes revelam sua verdadeira natureza angelical e sua espada de luz pura ergue-se para proteger os esquecidos.",
            revelation = "Sua verdadeira natureza é a de um Guardião e Mensageiro da Luz: suas asas e esplendor são revelados apenas quando o invisível se aproxima do visível. A luz não vem dele próprio, mas o acompanha e revela o caminho.",
            imageRes = R.drawable.char_rafael,
            secondaryImageRes = R.drawable.rafael_glorious,
            literaryQuote = "Vejo além do que os olhos alcançam, mas é o coração que revela o caminho.",
            keyTraits = listOf("Paciente", "Sábio", "Compassivo", "Humilde", "Atento", "Protetor", "Sereno", "Observador"),
            apparentAge = "Entre 45 e 50 anos",
            origin = "Guardião do Jardim, servo da Luz. Caminha entre os mundos como viajante.",
            mission = "Acompanhar e orientar Jônatas sem interferir em seu livre-arbítrio, até que a verdade possa ser revelada.",
            symbol = "Pena Dourada (oculta)",
            dominantEmotion = "Serenidade",
            personalItems = listOf(
                "Bolsa de Viagem",
                "Pergaminhos Antigos",
                "Pena Dourada (oculta como sinal de missão)"
            ),
            strikingDetails = listOf(
                "Olhos profundos, que carregam sabedoria e compaixão",
                "Cabelos grisalhos levemente ondulados, com fios prateados e barba bem cuidada",
                "Expressão serena, presença que transmite segurança e paz",
                "Trajes funcionais no estado comum, e radiantes com asas e espada no estado glorioso",
                "Por onde passa, o ambiente se torna mais calmo, como se o tempo desacelerasse"
            ),
            colorPalette = listOf(
                "Branco Luminoso",
                "Dourado Celestial",
                "Azul Celeste",
                "Bege Areia",
                "Marrom Antigo",
                "Vermelho Suave",
                "Cinza Pedregulho",
                "Cinza Claro"
            )
        ),
        BookCharacter(
            id = "miriam",
            name = "Miriam",
            title = "A Mãe dos Esquecidos",
            subpageTitle = "A Mãe dos Esquecidos — Guardiã do Jardim",
            archetype = "A Guardiã do Jardim / A Mãe dos Esquecidos",
            shortDescription = "Mulher acolhedora entre 48 e 55 anos, Guardiã do Jardim e Mãe dos Esquecidos, que alimenta, consola e chama cada criança pelo nome, devolvendo-lhes identidade e vida.",
            description = "Miriam é a presença materna viva no Jardim das Crianças. Nascida no próprio refúgio, foi chamada para cuidar daquilo que o mundo esqueceu: as crianças sem nome. Com seu sorriso sereno, olhos castanho-mel cheios de compaixão e trajes em verde sálvia e creme, ela nunca está de mãos vazias e se abaixa para escutar e cuidar de cada pessoa como única.",
            narrativeRecord = "Quando Jônatas chega ao Jardim, Miriam o recebe com acolhimento incondicional e apresenta o refúgio onde as memórias feridas encontram cura. Ela alimenta, ensina e devolve os nomes àqueles que a floresta quase apagou. Por onde passa, o ambiente se acalma, o medo arrefece e as flores parecem florescer mais. Durante o cerco das sombras, sua fé inabalável sustenta o coração das crianças.",
            revelation = "Miriam é a mãe espiritual de todos os esquecidos. Seu símbolo — uma flor branca simples e discreta — representa a verdade de que o amor que floresce nos gestos mais humildes tem poder para vencer o maior dos esquecimentos.",
            imageRes = R.drawable.char_miriam,
            secondaryImageRes = R.drawable.miriam_garden,
            literaryQuote = "Toda vida floresce quando alguém a chama pelo nome com amor.",
            keyTraits = listOf("Mãe dos Esquecidos", "Guardiã do Jardim", "Acolhedora", "Maternal", "Compassiva", "Paciente", "Amorosa", "Protetora"),
            apparentAge = "Entre 48 e 55 anos",
            origin = "Nascida no próprio Jardim. Chamada para cuidar daquilo que o mundo esqueceu: as crianças sem nome.",
            mission = "Acolher os esquecidos, alimentar, ensinar, consolar e chamar cada um pelo nome, devolvendo identidade e vida.",
            symbol = "Pequena flor branca (o amor nos gestos humildes)",
            dominantEmotion = "Acolhimento e Amor Maternal",
            personalItems = listOf(
                "Cesto trançado com flores, sementes e ervas medicinais",
                "Pingente de flor branca em cordão de fibra natural",
                "Pano bordado e jarro de cerâmica",
                "Livros infantis e regador do jardim"
            ),
            strikingDetails = listOf(
                "Cabelos castanho-escuros com fios grisalhos, com pequenas flores presas de forma simples",
                "Olhos castanho-mel, profundos e acolhedores, com marcas gentis do tempo",
                "Sorriso sereno e olhar materno que transmite sensação imediata de lar e segurança",
                "Mãos calejadas e carinhosas, marcadas pelo cuidado diário com a terra e com as crianças",
                "Quando chega, o medo diminui, o ambiente se acalma e as flores parecem florescer mais",
                "Sempre se abaixa para conversar na altura dos olhos das crianças e escuta com atenção plena"
            ),
            colorPalette = listOf(
                "Verde Sálvia",
                "Creme",
                "Marfim",
                "Terracota",
                "Lavanda",
                "Marrom Terra",
                "Dourado Suave"
            )
        ),
        BookCharacter(
            id = "daniel",
            name = "Daniel",
            title = "O Guardião da Esperança Infantil",
            subpageTitle = "O Guardião da Esperança Infantil",
            archetype = "A Criança que Ainda Acredita",
            shortDescription = "Menino entre 10 e 11 anos, Guardião da Esperança Infantil, que enxerga beleza onde outros não percebem e lembra a todos que o Jardim vale a pena ser defendido.",
            description = "Daniel representa a alma viva da infância no Jardim das Crianças: a criança que ainda acredita. Mesmo cercado pela ameaça da escuridão, ele continua esperando que cada criança seja chamada pelo nome. Com cabelos escuros ondulados, olhos atentos e curiosos, e roupas rústicas em verde musgo e tons de terra com as mangas sempre dobradas, Daniel coleciona pequenos tesouros naturais — folhas, penas, pedras, gravetos e sua inseparável bolota — e inspira proteção, coragem e ternura por onde passa.",
            narrativeRecord = "No universo de O Portador da Memória, Daniel é uma das crianças acolhidas no Jardim e protegidas pelo cuidado materno de Miriam. Ele caminha com desenvoltura pelas trilhas, clareiras e canteiros de flores, tornando-se amigo próximo e guia leal de Jônatas. Ao se recusar a esquecer o nome das flores e das vidas ao seu redor, sua coragem infantil lembra a todos os guardiões o real motivo pelo qual o Jardim deve ser defendido a todo custo.",
            revelation = "Seu símbolo pessoal é uma bolota, representando o potencial de tudo aquilo que ainda pode crescer. A fé inabalável de Daniel ensina que a esperança guardada no coração de uma criança é a semente mais poderosa contra o esquecimento.",
            imageRes = R.drawable.char_daniel,
            secondaryImageRes = R.drawable.daniel_scene,
            literaryQuote = "Enquanto eu ainda conseguir lembrar uma única flor pelo nome, o Jardim não estará perdido.",
            keyTraits = listOf("Guardião da Esperança", "A Criança que Acredita", "Sensível", "Observador", "Corajoso", "Leal", "Ligado à Natureza"),
            apparentAge = "Entre 10 e 11 anos",
            origin = "Uma das crianças do Jardim, marcada pelo Esquecimento, mas protegida pelo amor e pelo cuidado de Miriam.",
            mission = "Guardar a esperança das crianças e lembrar que o Jardim vale a pena ser defendido.",
            symbol = "Uma bolota (representa potencial, aquilo que ainda crescerá)",
            dominantEmotion = "Esperança, Ternura e Curiosidade",
            personalItems = listOf(
                "Bolota (símbolo pessoal do potencial de crescimento)",
                "Folhas, penas e gravetos coletados nas trilhas",
                "Pedra lisa de rio guardada no bolso",
                "Colar de fio rústico com pequeno pingente"
            ),
            strikingDetails = listOf(
                "Cabelos pretos/castanho-escuros volumosos e ondulados, levemente desalinhados pelo vento",
                "Olhar expressivo, sensível e atento, repleto de curiosidade e determinação",
                "Mangas da camisa de linho areia sempre dobradas até os antebraços",
                "Colete rústico verde musgo aberto e calça marrom terra amarrada na cintura",
                "Sempre com um toque de terra nas mãos ou joelhos pelo contato vivo com o solo",
                "Quando aparece, o Jardim se lembra de que ainda existe esperança e ele enxerga beleza onde outros não percebem"
            ),
            colorPalette = listOf(
                "Verde Musgo",
                "Folha Seca",
                "Bege Areia",
                "Marrom Terra",
                "Azul Acinzentado",
                "Cinza Pedra"
            )
        ),
        BookCharacter(
            id = "elias",
            name = "Elias",
            title = "O Guardião do Silêncio",
            subpageTitle = "O Guardião do Silêncio",
            archetype = "O Menino que Reencontrou o Caminho",
            shortDescription = "Menino entre 11 e 12 anos, Guardião do Silêncio, que sobreviveu ao esquecimento profundo e descobriu que a verdadeira força nasce da quietude.",
            description = "Elias é a personificação da estabilidade, da prudência e da redenção silenciosa no Jardim das Crianças. Sobrevivente do esquecimento mais denso, ele reencontrou sua própria identidade e descobriu que a verdadeira bravura não precisa de ruído. Menino de traços nobres e serenos, cabelos pretos crespos curtos e olhar calmo e profundo, veste camisa em tom areia com mangas dobradas, colete verde-musgo escuro e calça marrom resistente com cinto e botas de couro gasto. Suas palavras são poucas, mas carregadas de sentido; antes de avançar pelas trilhas, costuma apoiar a mão sobre o tronco das árvores antigas, ouvindo a vida que pulsa na madeira e vigiando com ternura as crianças menores.",
            narrativeRecord = "Resgatado da Floresta Oblivionis e acolhido pelo amor maternal de Miriam, Elias transformou o peso do passado em vigilância serena e zelo protetor. Ao lado de Daniel e Jônatas, ele atua como guardião dos menores e observador atento das fronteiras do Jardim. Sua presença transmite paz imediata, agindo com gestos firmes, medidos e precisos em defesa da esperança que renasceu em seu peito.",
            revelation = "Seu símbolo sagrado é uma folha parcialmente restaurada e, como amuleto pessoal, carrega no peito um pingente de madeira esculpida com uma semente brotando — lembrança perene de sua transformação e do caminho luminoso que escolheu trilhar.",
            imageRes = R.drawable.char_elias,
            secondaryImageRes = R.drawable.elias_scene,
            literaryQuote = "Há caminhos que só podem ser encontrados depois que voltamos a lembrar quem somos.",
            keyTraits = listOf("Guardião do Silêncio", "Observador", "Protetor dos Menores", "Sereno", "Prudente", "Resiliente", "Fiel"),
            apparentAge = "Entre 11 e 12 anos",
            origin = "Sobreviveu ao esquecimento profundo da Floresta, resgatado e acolhido no Jardim sob a proteção e o cuidado de Miriam.",
            mission = "Proteger os menores, observar, servir e ajudar a manter a esperança viva.",
            symbol = "Uma folha parcialmente restaurada (representa sua história e sua transformação)",
            dominantEmotion = "Serenidade, Prudência e Confiança Silenciosa",
            personalItems = listOf(
                "Pingente de semente esculpida em madeira (símbolo pessoal)",
                "Folha parcialmente restaurada preservada",
                "Pequena corda prática",
                "Saquinho de sementes para plantio",
                "Cantil de cerâmica e couro"
            ),
            strikingDetails = listOf(
                "Costuma apoiar a mão sobre o tronco das árvores antes de seguir em frente",
                "Gestos calmos, observadores e movimentos precisos",
                "Cabelos pretos crespos/cacheados curtos bem definidos e olhar sereno",
                "Camisa de linho areia com mangas dobradas, colete verde-musgo escuro e calça marrom",
                "Quando Elias está por perto, o ambiente se torna mais calmo; ele observa antes de agir e suas palavras são poucas, mas sempre cheias de sentido"
            ),
            colorPalette = listOf(
                "Verde Musgo",
                "Marrom Terra",
                "Areia",
                "Cinza Suave",
                "Azul Acinzentado",
                "Verde Escuro",
                "Preto Suave"
            )
        ),
        BookCharacter(
            id = "a_menina_da_cancao",
            name = "A Menina da Canção",
            title = "A Voz da Memória",
            subpageTitle = "A Voz da Memória",
            archetype = "A Voz da Memória",
            shortDescription = "Menina de presença atemporal (entre 11 e 13 anos), Voz da Memória, que canta a verdade do Jardim para que nenhum nome e nenhuma vida se perca no esquecimento.",
            description = "A Menina da Canção não pertence inteiramente ao fluxo comum do tempo. Sua existência é uma lembrança viva de que a memória floresce através da beleza, da verdade e da melodia sagrada. Menina de olhar límpido cor de mel e cabelos ondulados salpicados de pequenas flores brancas, ela caminha sempre descalça com seu vestido de linho leve na cor creme e faixa verde-sálvia na cintura. Ela quase nunca carrega objetos materiais — sua presença serena e sua voz bastam. Quando canta, o tempo desacelera, o vento se aquieta, os pássaros silenciam, as flores se inclinam e até as folhas da mítica Árvore dos Nomes estremecem suavemente em ressonância luminosa.",
            narrativeRecord = "No coração de O Portador da Memória, a Menina da Canção representa a prova viva de que a essência de uma alma jamais pode ser totalmente extinta pelo esquecimento. Mesmo quando o próprio nome terreno parece distante, sua voz desperta as memórias adormecidas nos corações de Jônatas, Miriam, Daniel e Elias. Ao entoar a canção eterna que o mundo esqueceu mas que o Jardim sempre guardou, ela se torna o elo musical que dissipa o nevoeiro da Floresta Oblivionis e prepara o caminho para a redenção final.",
            revelation = "Seu símbolo é uma pequena flor branca de cinco pétalas, representando a memória pura que brota e nunca morre. Sua voz não é uma música aprendida, mas lembrada do próprio sopro original da Criação.",
            imageRes = R.drawable.char_menina_cancao,
            secondaryImageRes = R.drawable.menina_cancao_scene,
            literaryQuote = "Enquanto houver alguém capaz de cantar a verdade, a memória jamais deixará de florescer.",
            keyTraits = listOf("A Voz da Memória", "Atemporal", "Pureza", "Canção Eterna", "Serenidade", "Esperança", "Luz Viva"),
            apparentAge = "Entre 11 e 13 anos (sua idade nunca é definida)",
            origin = "Ela não pertence completamente ao tempo. Guardiã da canção imemorial do Jardim das Crianças.",
            mission = "Cantar a memória do Jardim, para que nenhum nome e nenhuma vida se perca no esquecimento.",
            symbol = "Uma pequena flor branca de cinco pétalas (a memória que brota e permanece)",
            dominantEmotion = "Paz Profunda, Serenidade e Ternura Atemporal",
            personalItems = listOf(
                "Pequena flor branca de cinco pétalas do Jardim",
                "Folha luminosa da Árvore dos Nomes",
                "Pingente delicado de flor branca",
                "Quase nunca carrega objetos; sua presença e sua canção bastam"
            ),
            strikingDetails = listOf(
                "Quando ela canta, algumas folhas da Árvore dos Nomes se movem suavemente, mesmo sem vento",
                "Cabelos longos ondulados em tom castanho-mel com florzinhas brancas entrelaçadas",
                "Olhos cor de mel luminosos e límpidos com sardas suaves no rosto",
                "Vestido de linho leve na cor creme com bordados inspirados na flora do Jardim",
                "Faixa verde-musgo amarrada na cintura e pés sempre descalços",
                "Quando ela aparece, o tempo desacelera, o vento se aquieta, os pássaros silenciam e as flores se inclinam"
            ),
            colorPalette = listOf(
                "Creme",
                "Marfim",
                "Mel",
                "Verde Clareira",
                "Verde Sálvia",
                "Dourado Suave",
                "Branco Pérola"
            )
        ),
        BookCharacter(
            id = "miguel",
            name = "Miguel",
            title = "O Guardião da Fronteira",
            subpageTitle = "O Guardião da Fronteira",
            archetype = "O Guardião da Fronteira",
            shortDescription = "Ser celestial e guerreiro da Luz, vigilante implacável dos limites entre os mundos, empunhando a Lança de Luz para que a escuridão não avance.",
            description = "Miguel é um ser celestial, guardião supremo e guerreiro da Luz. Austero, disciplinado e profundamente leal, ele vigia incansavelmente os cumes e desfiladeiros que separam o Jardim das Crianças dos abismos sombrios da Floresta Oblivionis. Com imponentes asas de penas alvas, armadura em aço e ouro com o medalhão do leão no ombro, e olhar penetrante de quem discerne a verdade das almas, Miguel empunha a Lança de Luz. Ao lado de Jônatas, ele aprende que a verdadeira proteção vai além de destruir o inimigo: é a capacidade de permanecer de pé enquanto aquilo que foi ferido reaprende a viver.",
            narrativeRecord = "No limiar entre a luz e a escuridão de O Portador da Memória, Miguel é a fortaleza viva que impede a invasão definitiva das sombras. Quando ele se aproxima, a própria luz do ambiente se torna mais límpida e a relva se inclina em reverência. Ele reconhece a coragem de Jônatas e se inclina para apoiá-lo, guardando as passagens sagradas para que os inocentes possam retornar em paz ao Jardim.",
            revelation = "Seu símbolo sagrado é a lança e a fronteira. Seu grande ensinamento revela que algumas fronteiras não foram criadas para separar o mundo, mas sim para proteger aquilo que realmente tem valor eterno.",
            imageRes = R.drawable.char_miguel,
            secondaryImageRes = R.drawable.miguel_scene,
            literaryQuote = "Onde a luz permanece firme, a escuridão não avança.",
            keyTraits = listOf("Guardião da Fronteira", "Guerreiro da Luz", "Vigilante", "Disciplinado", "Leal", "Austero", "Protetor Firme"),
            apparentAge = "Entre 48 e 55 anos",
            origin = "Ser celestial. Guardião e guerreiro eterno da Luz.",
            mission = "Proteger o Jardim, manter os limites entre os mundos e impedir que a escuridão ultrapasse a fronteira.",
            symbol = "A lança e a fronteira",
            dominantEmotion = "Vigilância Inabalável, Firmeza e Nobreza Protetora",
            personalItems = listOf(
                "A Lança de Luz (haste celestial com ponta incandescente)",
                "Armadura de placas de aço e prata com detalhes dourados",
                "Medalhão do Leão gravado no fecho do peitoral",
                "Manto drapeado de tecido branco celestial"
            ),
            strikingDetails = listOf(
                "Quando Miguel se aproxima, a luz se torna mais nítida, o ambiente se organiza e a própria relva se inclina",
                "Asas celestiais brancas imponentes e olhar austero que discerne as intenções",
                "Cabelos castanhos ondulados com mechas prateadas e barba grisalha bem delineada",
                "Aprende que proteger nem sempre é destruir o que ameaça, mas permanecer enquanto o que foi ferido reaprende a viver",
                "Reconhece o papel de Jônatas e se inclina para apoiá-lo com firmeza e reverência"
            ),
            colorPalette = listOf(
                "Branco Puro",
                "Prata Aço",
                "Dourado Solar",
                "Ouro Nobre",
                "Cinza Rocha",
                "Azul Celeste"
            )
        ),
        BookCharacter(
            id = "rei_do_esquecimento",
            name = "Rei do Esquecimento / Malach",
            title = "O Devorador de Memórias / O Homem que Esqueceu de Ser Lembrado",
            subpageTitle = "A Mesma Essência. Duas Realidades.",
            archetype = "O Devorador de Memórias / O Redimido",
            shortDescription = "A face que devora memórias e o homem que esqueceu de ser lembrado: duas realidades de uma mesma alma que prova que até o mais profundo esquecimento pode ser curado.",
            description = "Rei do Esquecimento e Malach são duas realidades de uma mesma essência. Como Rei do Esquecimento ('A Face que Devora Memórias'), ele é a manifestação colossal da escuridão e da dor: um titã esculpido em raízes retorcidas com incontáveis rostos aprisionados em agonia e olhos vermelhos incandescentes ('Tudo o que é esquecido me pertence'). Sob essa carcaça sufocante de sombras oculta-se Malach ('O Homem que Esqueceu de Ser Lembrado'): um antigo soberano humano, idoso (entre 65 e 75 anos), com o rosto marcado por cicatrizes profundas da dor, cabelos compridos grisalhos desgrenhados e manto rústico de estopa. Ao ser chamado pelo nome verdadeiro e perdoado por Jônatas, sua carcaça de espinhos cai por terra, revelando a origem humana que ainda sente e provando que nem todo fim precisa ser o fim.",
            narrativeRecord = "No Trono Sob as Raízes, diante da forma colossal do Rei do Esquecimento, Jônatas compreende o mistério supremo da narrativa: o mal que domina a Floresta Oblivionis não é uma força abstrata, mas uma dor que esqueceu de ser amada. Ao invés de aniquilar a criatura — o que destruiria todas as memórias e vidas ali presas —, Jônatas o confronta com misericórdia e pronuncia o nome sagrado: Malach. A verdade quebra a ilusão milenar, fazendo com que as raízes de trevas se dissolvam e revelem o velho rei chorando com seu medalhão de folha no peito.",
            revelation = "Seus símbolos duais revelam a grande mensagem de O Portador da Memória: o vazio das raízes representa o fim da lembrança e o abandono, enquanto o medalhão de folha de Malach encarna a memória restaurada, a possibilidade de arrependimento e a certeza de que até aquilo que se perdeu pode ser reencontrado quando há alguém disposto a lembrar.",
            imageRes = R.drawable.char_rei_esquecimento,
            secondaryImageRes = R.drawable.char_malach,
            tertiaryImageRes = R.drawable.malach_scene,
            literaryQuote = "Fui feito para reinar, mas aprendi que o maior poder é lembrar de cada um pelo nome que Deus lhe deu.",
            keyTraits = listOf("Rei do Esquecimento", "Malach", "Duas Realidades", "Devorador de Memórias", "O Redimido", "Memória Restaurada", "Transformação"),
            apparentAge = "Colossal (Rei) / Idoso de 65 a 75 anos (Malach)",
            origin = "Antigo soberano humano que se corrompeu ao perder sua memória, propósito e identidade na dor.",
            mission = "Rei: consumir memórias e alimentar o vazio. Malach: redescobrir que o maior poder é lembrar de cada nome com amor.",
            symbol = "O vazio das raízes (Rei) / A memória restaurada e a folha (Malach)",
            dominantEmotion = "Da agonia do vazio à paz comovente do arrependimento e redenção",
            personalItems = listOf(
                "Raízes retorcidas e rostos aprisionados (forma colossal do Rei)",
                "Medalhão entalhado de folha sagrada no peito (Malach)",
                "Vestes rústicas esfarrapadas em tons de terra e estopa (Malach)",
                "Cicatrizes profundas no rosto que contam sua história"
            ),
            strikingDetails = listOf(
                "A mesma essência, duas realidades: a face que devora memórias e o homem que esqueceu de ser lembrado",
                "Como Rei: olhos vermelhos como brasas e corpo formado por raízes e rostos em agonia",
                "Como Malach: olhar profundo e cansado, mãos trêmulas segurando o medalhão de folha e lágrimas de redenção",
                "Frase do Rei: 'O esquecimento não destrói de uma vez. Ele apenas convence que nunca existiu'",
                "Frase de Malach: 'Não sei como soltar aquilo que mais amei. Talvez seja por isso que ainda dói'",
                "Provou que o esquecimento nasce da dor, e que nem todo fim precisa ser o fim"
            ),
            colorPalette = listOf(
                "Preto Abismo",
                "Cinza Raiz",
                "Vermelho Chama",
                "Marrom Terra",
                "Bege Estopa",
                "Dourado Redenção"
            )
        )
    )

    val locations = listOf(
        BookLocation(
            id = "blumenau",
            name = "Blumenau",
            realmType = "O mundo cotidiano",
            subpageSubtitle = "O mundo cotidiano",
            description = "Blumenau é o mundo cotidiano de Jônatas. É em uma noite de chuva, entre ruas molhadas e prédios da cidade, que ele ouve o choro de uma criança e encontra uma passagem estreita que o conduz à Floresta Oblivionis.",
            imageRes = R.drawable.blumenau_art,
            keyFeatures = listOf("Chuva e neblina", "Ruas molhadas", "Passagem estreita", "Cidade cotidiana"),
            mapCoordinatesX = 0.74f,
            mapCoordinatesY = 0.64f
        ),
        BookLocation(
            id = "rio_cristalino",
            name = "Rio Cristalino",
            realmType = "Rio do Jardim",
            subpageSubtitle = "O rio do Jardim das Crianças",
            description = "O Rio Cristalino atravessa o Jardim das Crianças e está profundamente ligado à vida e à proteção daquele lugar. Suas águas participam da defesa do Jardim e, durante os acontecimentos finais, assumem uma dimensão ainda mais extraordinária.",
            imageRes = R.drawable.loc_rio_cristalino,
            keyFeatures = listOf("Águas cristalinas", "Sustentação do Jardim", "Ligação com Miriam", "Águas suspensas"),
            mapCoordinatesX = 0.48f,
            mapCoordinatesY = 0.40f
        ),
        BookLocation(
            id = "jardim_criancas",
            name = "Jardim das Crianças",
            realmType = "Refúgio da Luz e da Memória",
            subpageSubtitle = "Refúgio da Luz e da Memória",
            description = "O Jardim das Crianças é o refúgio onde Miriam acolhe e protege aqueles que foram esquecidos. Cercado por uma fronteira de luz e marcado por árvores douradas, o Jardim representa proteção, acolhimento e resistência diante da Floresta Oblivionis.",
            imageRes = R.drawable.loc_jardim,
            keyFeatures = listOf("Refúgio de acolhimento", "Fronteira de luz", "Árvores douradas", "Preservação da memória"),
            mapCoordinatesX = 0.68f,
            mapCoordinatesY = 0.32f
        ),
        BookLocation(
            id = "arvore_nomes",
            name = "Árvore dos Nomes",
            realmType = "Árvore da Memória",
            subpageSubtitle = "Árvore da Memória",
            description = "No Jardim das Crianças ergue-se a Árvore dos Nomes, ligada à preservação das identidades e memórias daqueles que foram lembrados. Suas folhas e seus nomes tornam-se parte essencial da jornada de Jônatas.",
            imageRes = R.drawable.loc_arvore_nomes,
            keyFeatures = listOf("Identidades preservadas", "Folhas e nomes", "Coração do Jardim"),
            mapCoordinatesX = 0.82f,
            mapCoordinatesY = 0.24f
        ),
        BookLocation(
            id = "floresta_oblivionis",
            name = "Floresta Oblivionis",
            realmType = "A Floresta do Esquecimento",
            subpageSubtitle = "A Floresta do Esquecimento",
            description = "A Floresta Oblivionis é o território para onde vão aqueles que o mundo decidiu esquecer. É uma região de névoa, raízes retorcidas, árvores escuras e vozes de vidas esquecidas. É ali que Jônatas inicia sua jornada de resgate.",
            imageRes = R.drawable.loc_floresta,
            keyFeatures = listOf("Névoa e raízes retorcidas", "Árvores escuras", "Vozes esquecidas", "Domínio do esquecimento"),
            mapCoordinatesX = 0.22f,
            mapCoordinatesY = 0.20f
        ),
        BookLocation(
            id = "vale_silenciados",
            name = "Vale dos Silenciados",
            realmType = "Vale da Floresta",
            subpageSubtitle = "Vale da Floresta",
            description = "O Vale dos Silenciados é uma região profunda da Floresta Oblivionis, marcada pelo silêncio, pelas vozes esquecidas e pela presença daqueles que perderam parte de sua identidade.",
            imageRes = R.drawable.loc_vale_silenciados,
            keyFeatures = listOf("Árvores pálidas", "Silêncio profundo", "Ecos e vozes esquecidas"),
            mapCoordinatesX = 0.24f,
            mapCoordinatesY = 0.53f
        ),
        BookLocation(
            id = "trono_raizes",
            name = "Trono Sob as Raízes",
            realmType = "Centro do domínio do esquecimento",
            subpageSubtitle = "Centro do domínio do esquecimento",
            description = "Nas profundezas da Floresta Oblivionis existe uma enorme câmara onde se ergue o Trono Sob as Raízes. Construído de raízes endurecidas, veios negros e madeira entrelaçada, o Trono carrega em sua estrutura os rostos de vidas aprisionadas e representa o centro do domínio do esquecimento.",
            imageRes = R.drawable.loc_trono_raizes,
            keyFeatures = listOf("Câmara nas profundezas", "Raízes endurecidas e veios negros", "Rostos aprisionados", "Domínio do Rei do Esquecimento"),
            mapCoordinatesX = 0.18f,
            mapCoordinatesY = 0.72f
        ),
        BookLocation(
            id = "arvore_nunca_chamados",
            name = "Árvore dos que Nunca Foram Chamados",
            realmType = "Árvore da Ausência",
            subpageSubtitle = "Árvore da Ausência",
            description = "Nas profundezas da Floresta Oblivionis existe uma árvore gigantesca ligada à ausência daqueles que nunca chegaram a ser chamados pelo próprio nome. Seu tronco escuro e suas raízes profundas guardam uma forma extrema de esquecimento.",
            imageRes = R.drawable.loc_arvore_nunca_chamados,
            keyFeatures = listOf("Árvore colossal", "Tronco escuro sem marcas", "Raízes profundas", "Ausência extrema"),
            mapCoordinatesX = 0.84f,
            mapCoordinatesY = 0.80f
        )
    )

    val journeySteps = listOf(
        JourneyStep(
            step = 1,
            title = "O Chamado",
            location = "Blumenau",
            summary = "Em uma noite de chuva, Jônatas ouve o choro de uma criança e segue aquele chamado por uma estreita passagem que o conduz para além do mundo conhecido.",
            narrativeInsight = ""
        ),
        JourneyStep(
            step = 2,
            title = "A Entrada na Floresta Oblivionis",
            location = "Floresta Oblivionis",
            summary = "Na floresta, Jônatas descobre o domínio do esquecimento e testemunha o desaparecimento de nomes e memórias.",
            narrativeInsight = ""
        ),
        JourneyStep(
            step = 3,
            title = "O Encontro com Rafael",
            location = "Floresta Oblivionis",
            summary = "Rafael surge como guia e protetor, ajudando Jônatas a compreender o perigo que ameaça aqueles que foram esquecidos.",
            narrativeInsight = ""
        ),
        JourneyStep(
            step = 4,
            title = "O Jardim das Crianças",
            location = "Jardim das Crianças e Árvore dos Nomes",
            summary = "Jônatas encontra o Jardim das Crianças, um refúgio protegido pela luz. Ali conhece Miriam e encontra a Árvore dos Nomes, onde memórias e presenças daqueles que foram lembrados permanecem preservadas.",
            narrativeInsight = ""
        ),
        JourneyStep(
            step = 5,
            title = "A Descida às Raízes",
            location = "Floresta Oblivionis e Trono Sob as Raízes",
            summary = "Ao retornar à Floresta, Jônatas, Rafael e Daniel avançam pelas raízes até chegar ao centro do domínio do esquecimento.",
            narrativeInsight = ""
        ),
        JourneyStep(
            step = 6,
            title = "A Travessia",
            location = "Jardim das Crianças → Blumenau",
            summary = "Depois da batalha, Jônatas retorna ao mundo de onde veio, levando consigo as memórias daqueles que encontrou e uma nova responsabilidade.",
            narrativeInsight = ""
        )
    )

    val articles = listOf(
        AuthorArticle(
            id = "art-1",
            title = "Aquilo que se esquece não desaparece",
            subtitle = "Reflexões sobre a gênese de O Portador da Memória",
            excerpt = "O esquecimento não é a destruição da matéria, mas a perda do afeto que a mantinha viva na consciência humana.",
            content = """
                Quantas pessoas podem desaparecer antes que o mundo perceba que elas existiram?
                
                Esta foi a pergunta inicial que deu vida a O Portador da Memória. Quando comecei a conceber este universo, compreendi que a memória humana é a ponte mais frágil e ao mesmo tempo mais poderosa da nossa existência.
                
                Aquilo que se esquece não desaparece. Fica suspenso em algum lugar entre as sombras e a saudade, apenas aguardando por alguém que tenha a coragem e a sensibilidade de se lembrar.
                
                Lembrar, portanto, não é um mero processo biológico ou intelectual: é um compromisso ético, o mais antigo ato de amor e a derradeira forma de resistência que nos resta.
            """.trimIndent(),
            date = "Diário Literário",
            tags = listOf("Memória", "Filosofia", "Universo")
        ),
        AuthorArticle(
            id = "art-2",
            title = "Blumenau: onde o extraordinário toca o cotidiano",
            subtitle = "A escolha da cidade natal como cenário da fantasia contemporânea",
            excerpt = "Por que criar terras fictícias se as fendas do mundo já estão abertas sobre a chuva das nossas próprias ruas?",
            content = """
                Muitas obras de fantasia buscam castelos medievais ou terras de mapas imaginários. Para O Portador da Memória, eu sabia que a travessia precisava partir de um chão real, que os leitores pudessem pisar.
                
                Blumenau é uma cidade onde a água dita o compasso da vida: as cheias, a névoa matinal que sobe do rio Itajaí-Açu, a imponente torre da Catedral São Paulo Apóstolo que corta o céu cinzento.
                
                O extraordinário ganha força quando brota dentro do cotidiano reconhecível. Jônatas não começa sua jornada em um palácio lendário, mas caminhando por calçadas molhadas, sentindo o mesmo cheiro de chuva que nós sentimos ao olhar pela janela.
            """.trimIndent(),
            date = "Diário do Autor",
            tags = listOf("Blumenau", "Cenário", "Inspiração")
        ),
        AuthorArticle(
            id = "art-3",
            title = "Malach e o rosto do esquecimento",
            subtitle = "A dor que não encontrou palavras para se expressar",
            excerpt = "O antagonista de uma história de memória não poderia ser um mero vilão cruel; ele é o peso das mágoas não curadas.",
            content = """
                Construir a figura de Malach exigiu muito respeito e cuidado narrativo. Ele não é uma criatura grotesca ou vilanesca no sentido tradicional.
                
                Malach é mais velho que Rafael. Ele carrega consigo as marcas de séculos de silêncio. Ele é a própria personificação da dor que não encontrou ouvidos para ser ouvida nem palavras para ser acolhida.
                
                Quando alguém é abandonado pela lembrança daqueles que amava, o esquecimento surge não como escolha, mas como anestésico. O confronto de Jônatas com Malach é, no fundo, a tentativa de provar que mesmo a dor mais antiga merece ser compreendida.
            """.trimIndent(),
            date = "Notas dos Personagens",
            tags = listOf("Malach", "Antagonismo", "Psicologia")
        ),
        AuthorArticle(
            id = "art-4",
            title = "O Jardim das Crianças e a Árvore dos Nomes",
            subtitle = "O santuário concebido antes mesmo das primeiras frases",
            excerpt = "Antes de existirem os nomes de Jônatas ou Rafael, existia a imagem luminosa de um jardim onde nenhuma criança era esquecida.",
            content = """
                Na gênese do livro, a primeira imagem que surgiu em minha mente não foi a de uma batalha épica, mas a de um refúgio dourado: o Jardim das Crianças.
                
                Um lugar onde Miriam acolhe cada pequeno que o mundo dos homens deixou escapar entre os dedos. E, no coração deste santuário, ergue-se a Árvore dos Nomes.
                
                Uma folha dourada para cada vida que ainda permanece gravada no coração de alguém. A Árvore dos Nomes nos lembra de que enquanto alguém pronunciar o nosso nome com carinho, nossa história jamais terminará.
            """.trimIndent(),
            date = "Gênese da Obra",
            tags = listOf("Jardim", "Árvore dos Nomes", "Esperança")
        )
    )
}
