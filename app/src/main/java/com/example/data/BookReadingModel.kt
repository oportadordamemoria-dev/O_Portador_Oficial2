package com.example.data

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

data class ChapterReading(
    val number: Int,
    val title: String,
    val content: String
)

data class ChapterReadingItem(
    val number: Int,
    val title: String,
    val isAvailable: Boolean
)

object BookReadingRepository {

    // Lista oficial e canônica dos 32 capítulos extraída do Sumário do livro
    val allChapters: List<ChapterReadingItem> = listOf(
        ChapterReadingItem(1, "A FLORESTA DO ESQUECIMENTO", isAvailable = true),
        ChapterReadingItem(2, "OS QUE NÃO SÃO LEMBRADOS", isAvailable = true),
        ChapterReadingItem(3, "LUZ ENTRE AS ÁRVORES", isAvailable = true),
        ChapterReadingItem(4, "CORRIDA PELA ESPERANÇA", isAvailable = false),
        ChapterReadingItem(5, "O JARDIM DAS CRIANÇAS", isAvailable = false),
        ChapterReadingItem(6, "A TEMPESTADE SOBRE O JARDIM", isAvailable = false),
        ChapterReadingItem(7, "O ECO DOS NOMES", isAvailable = false),
        ChapterReadingItem(8, "O RETORNO À FLORESTA", isAvailable = false),
        ChapterReadingItem(9, "OS QUE ESPERAM NAS RAÍZES", isAvailable = false),
        ChapterReadingItem(10, "A ÁRVORE DOS QUE NUNCA FORAM CHAMADOS", isAvailable = false),
        ChapterReadingItem(11, "O VALE DOS SILENCIADOS", isAvailable = false),
        ChapterReadingItem(12, "QUANDO DANIEL RETORNA", isAvailable = false),
        ChapterReadingItem(13, "O TRONO SOB AS RAÍZES", isAvailable = false),
        ChapterReadingItem(14, "A VOZ QUE REIVINDICA", isAvailable = false),
        ChapterReadingItem(15, "AS MÃES QUE CHORAM NO VENTO", isAvailable = false),
        ChapterReadingItem(16, "O LIVRO DOS NOMES PARTIDOS", isAvailable = false),
        ChapterReadingItem(17, "A SEGUNDA TEMPESTADE", isAvailable = false),
        ChapterReadingItem(18, "QUANDO A MURALHA CEDE", isAvailable = false),
        ChapterReadingItem(19, "OS GUARDIÕES DA LUZ", isAvailable = false),
        ChapterReadingItem(20, "O NOME DE JÔNATAS", isAvailable = false),
        ChapterReadingItem(21, "A FLORESTA CONTRA O JARDIM", isAvailable = false),
        ChapterReadingItem(22, "QUANDO O RIO SE LEVANTA", isAvailable = false),
        ChapterReadingItem(23, "O TRONO VACILA", isAvailable = false),
        ChapterReadingItem(24, "O QUE AINDA PODE SER CURADO", isAvailable = false),
        ChapterReadingItem(25, "QUANDO O REI CHORA", isAvailable = false),
        ChapterReadingItem(26, "O QUE É DEVOLVIDO À LUZ", isAvailable = false),
        ChapterReadingItem(27, "QUANDO A FLORESTA SE PARTE", isAvailable = false),
        ChapterReadingItem(28, "O QUE RESTA ENTRE AS RUÍNAS", isAvailable = false),
        ChapterReadingItem(29, "A SEMENTE SOB AS RAÍZES", isAvailable = false),
        ChapterReadingItem(30, "QUANDO O MUNDO CHAMA", isAvailable = false),
        ChapterReadingItem(31, "A CHUVA SOBRE O JARDIM", isAvailable = false),
        ChapterReadingItem(32, "A TRAVESSIA", isAvailable = false)
    )

    fun getAvailableChapters(): List<ChapterReadingItem> {
        return allChapters.filter { it.isAvailable }
    }

    fun getChapterItem(number: Int): ChapterReadingItem? {
        return allChapters.find { it.number == number }
    }

    fun loadChapter(context: Context, chapterNumber: Int): ChapterReading? {
        val item = getChapterItem(chapterNumber) ?: return null
        val fileName = String.format("chapters/chapter_%02d.txt", chapterNumber)
        return try {
            val content = context.assets.open(fileName).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    reader.readText()
                }
            }
            ChapterReading(
                number = item.number,
                title = item.title,
                content = content
            )
        } catch (e: Exception) {
            null
        }
    }
}
