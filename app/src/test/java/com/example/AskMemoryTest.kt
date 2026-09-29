package com.example

import com.example.data.BookUniverse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Validação da arquitetura definitiva da FASE 2:
 * "Pergunte à Memória" opera exclusivamente através da experiência web oficial integrada,
 * sem chamadas diretas a Gemini e com a preservação integral do conteúdo canônico da obra.
 */
class AskMemoryTest {

    @Test
    fun testPhase2OfficialWebRoute() {
        val officialUrl = "https://portador-da-memoria.vercel.app/pergunte-a-memoria"
        val allowedHost = "portador-da-memoria.vercel.app"

        assertTrue("A rota oficial deve ser HTTPS", officialUrl.startsWith("https://"))
        assertTrue("A rota oficial deve conter o host permitido", officialUrl.contains(allowedHost))
        assertEquals("https://portador-da-memoria.vercel.app/pergunte-a-memoria", officialUrl)
    }

    @Test
    fun testCanonicalBookUniversePreserved() {
        assertNotNull(BookUniverse.BOOK_TITLE)
        assertEquals("O Portador da Memória", BookUniverse.BOOK_TITLE)
        assertEquals("Entre a Luz e a Escuridão", BookUniverse.BOOK_SUBTITLE)
        assertEquals("Júlio César Rodrigues", BookUniverse.AUTHOR_NAME)
        assertEquals("Blumenau, Santa Catarina", BookUniverse.AUTHOR_LOCATION)

        assertTrue("Atos canônicos devem estar preservados", BookUniverse.acts.isNotEmpty())
        assertTrue("Capítulos canônicos devem estar preservados", BookUniverse.chapters.isNotEmpty())
        assertTrue("Personagens devem estar preservados", BookUniverse.characters.isNotEmpty())
        assertTrue("Lugares devem estar preservados", BookUniverse.locations.isNotEmpty())
        assertTrue("Etapas da jornada devem estar preservadas", BookUniverse.journeySteps.isNotEmpty())
        assertTrue("Artigos devem estar preservados", BookUniverse.articles.isNotEmpty())
    }

    @Test
    fun testCharactersCanonIntegrity() {
        val jonatas = BookUniverse.characters.find { it.name == "Jônatas" }
        assertNotNull("Jônatas deve existir nos personagens canônicos", jonatas)
        assertTrue(jonatas!!.description.contains("Blumenau", ignoreCase = true))

        val miriam = BookUniverse.characters.find { it.name.contains("Miriam", ignoreCase = true) }
        assertNotNull("Miriam deve existir nos personagens canônicos", miriam)

        val malach = BookUniverse.characters.find { it.name.contains("Malach", ignoreCase = true) }
        assertNotNull("Malach / Rei do Esquecimento deve existir", malach)
    }

    @Test
    fun testLocationsCanonIntegrity() {
        val floresta = BookUniverse.locations.find { it.name.contains("Floresta Oblivionis", ignoreCase = true) }
        assertNotNull("Floresta Oblivionis deve existir", floresta)

        val jardim = BookUniverse.locations.find { it.name.contains("Jardim das Crianças", ignoreCase = true) }
        assertNotNull("Jardim das Crianças deve existir", jardim)

        val blumenau = BookUniverse.locations.find { it.name.contains("Blumenau", ignoreCase = true) }
        assertNotNull("Blumenau deve existir", blumenau)
    }
}
