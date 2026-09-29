package com.example.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.BookUniverse
import com.example.ui.theme.AmberTertiary
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.CanvasSurfaceVariant
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

const val OFFICIAL_ASK_MEMORY_URL = "https://portadordamemoria.ai.studio/pergunte-a-memoria"
const val ALLOWED_HOST = "portadordamemoria.ai.studio"
private const val TAG = "AskMemoryPortal"

/**
 * Tela do Portal "Pergunte à Memória" com experiência nativa robusta, à prova de travamentos no emulador,
 * e integração oficial com a plataforma na web (Ai Studio).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskMemoryScreen(
    onBack: () -> Unit,
    onNavigateBlumenau: () -> Unit = {}
) {
    val context = LocalContext.current
    var showEmbeddedWebView by remember { mutableStateOf(false) }

    // O botão voltar físico do sistema sempre respeita a navegação de forma segura
    BackHandler(enabled = true) {
        if (showEmbeddedWebView) {
            showEmbeddedWebView = false
        } else {
            onBack()
        }
    }

    val openInBrowser: () -> Unit = {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(OFFICIAL_ASK_MEMORY_URL)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao abrir navegador externo: ${e.message}")
        }
    }

    Scaffold(
        containerColor = CanvasDeep,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Pergunte à Memória",
                            color = GoldLight,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (showEmbeddedWebView) "Visualizador Integrado" else "Guia Canônico Oficial",
                            color = TextMuted,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (showEmbeddedWebView) {
                                showEmbeddedWebView = false
                            } else {
                                onBack()
                            }
                        },
                        modifier = Modifier.testTag("ask_memory_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = GoldLight
                        )
                    }
                },
                actions = {
                    if (showEmbeddedWebView) {
                        IconButton(
                            onClick = { showEmbeddedWebView = false },
                            modifier = Modifier.testTag("ask_memory_close_webview_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar visualizador",
                                tint = GoldLight
                            )
                        }
                    }

                    IconButton(
                        onClick = openInBrowser,
                        modifier = Modifier.testTag("ask_memory_open_browser_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = "Abrir no navegador",
                            tint = GoldLight
                        )
                    }

                    IconButton(
                        onClick = onNavigateBlumenau,
                        modifier = Modifier.testTag("ask_memory_goto_blumenau_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationCity,
                            contentDescription = "Ir para Blumenau",
                            tint = GoldLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CanvasSurface
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CanvasDeep)
        ) {
            if (showEmbeddedWebView) {
                SafeWebViewContainer(
                    url = OFFICIAL_ASK_MEMORY_URL,
                    onClose = { showEmbeddedWebView = false },
                    onOpenBrowser = openInBrowser
                )
            } else {
                NativePortalHub(
                    onOpenBrowser = openInBrowser,
                    onOpenEmbedded = { showEmbeddedWebView = true },
                    onBack = onBack,
                    onNavigateBlumenau = onNavigateBlumenau
                )
            }
        }
    }
}

/**
 * Hub nativo do "Pergunte à Memória": fluido, belo, acessível e sem dependência do motor Chromium no render principal.
 */
@Composable
private fun NativePortalHub(
    onOpenBrowser: () -> Unit,
    onOpenEmbedded: () -> Unit,
    onBack: () -> Unit,
    onNavigateBlumenau: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Card
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            GoldPrimary.copy(alpha = 0.3f),
                            CanvasSurfaceVariant
                        )
                    )
                )
                .border(1.5.dp, GoldPrimary.copy(alpha = 0.7f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = GoldLight,
                modifier = Modifier.size(38.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = BookUniverse.BOOK_TITLE,
            color = GoldLight,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Text(
            text = BookUniverse.BOOK_SUBTITLE,
            color = TextMuted,
            fontSize = 13.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Badge de Status do Servidor
        Surface(
            color = CanvasSurfaceVariant,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Servidor Oficial Online — Ai Studio",
                    color = TextParchment,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Card Explicativo
        Card(
            colors = CardDefaults.cardColors(containerColor = CanvasCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Oráculo do Guardião da Memória",
                        color = GoldLight,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Consulte o conhecimento canônico do universo criado por Júlio César Rodrigues. Obtenha respostas fundamentadas na obra sobre a jornada de Jônatas, o enigma de Miriam, o Rei do Esquecimento e as conexões místicas com Blumenau.",
                    color = TextParchment,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tópicos sugeridos
        Text(
            text = "TEMAS SUGERIDOS PARA PERGUNTAR",
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, bottom = 8.dp)
        )

        SuggestedTopicItem(
            title = "A Jornada de Jônatas",
            subtitle = "Sua missão, origens em Blumenau e o despertar da memória",
            onClick = onOpenBrowser
        )
        SuggestedTopicItem(
            title = "O Mistério de Miriam",
            subtitle = "A Guardiã, o laço eterno e os segredos da Luz",
            onClick = onOpenBrowser
        )
        SuggestedTopicItem(
            title = "O Rei do Esquecimento (Malach)",
            subtitle = "A névoa, a corrupção do tempo e a escuridão",
            onClick = onOpenBrowser
        )
        SuggestedTopicItem(
            title = "Floresta Oblivionis e o Jardim",
            subtitle = "Os reinos de transição e o poder das lembranças puras",
            onClick = onOpenBrowser
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão Primário: Acessar no Navegador
        Button(
            onClick = onOpenBrowser,
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldPrimary,
                contentColor = CanvasDeep
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.OpenInBrowser,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "ABRIR ORÁCULO NO NAVEGADOR",
                fontWeight = FontWeight.Bold,
                fontSize = 13.5.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botão Secundário: Visualizar dentro do App
        OutlinedButton(
            onClick = onOpenEmbedded,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = GoldLight
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "VISUALIZAR NESTE APLICATIVO",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botão Voltar ao Menu
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = CanvasSurfaceVariant,
                contentColor = TextParchment
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "VOLTAR AO MENU PRINCIPAL",
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SuggestedTopicItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CanvasSurfaceVariant.copy(alpha = 0.6f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = GoldLight,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = TextMuted,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Visualizador embutido protegido contra travamento de GPU no emulador, com barra de progresso e botões de saída sempre acessíveis.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun SafeWebViewContainer(
    url: String,
    onClose: () -> Unit,
    onOpenBrowser: () -> Unit
) {
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var loadProgress by remember { mutableIntStateOf(0) }
    var hasError by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            webViewInstance?.apply {
                stopLoading()
                onPause()
                removeAllViews()
                destroy()
            }
            webViewInstance = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CanvasDeep)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .testTag("ask_memory_webview"),
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    // Proteção essencial para emuladores Android na nuvem: renderização em software sem travar GPU
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                    setBackgroundColor(0xFF050811.toInt())

                    try {
                        val cookieManager = CookieManager.getInstance()
                        cookieManager.setAcceptCookie(true)
                        cookieManager.setAcceptThirdPartyCookies(this, true)
                    } catch (e: Exception) {
                        Log.w(TAG, "Aviso CookieManager: ${e.message}")
                    }

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadsImagesAutomatically = true
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                        cacheMode = WebSettings.LOAD_DEFAULT

                        allowFileAccess = false
                        allowContentAccess = false
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            if (request?.isForMainFrame == true) {
                                hasError = true
                                isLoading = false
                            }
                        }

                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?
                        ) {
                            handler?.cancel()
                            hasError = true
                            isLoading = false
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val reqUrl = request?.url ?: return false
                            val host = reqUrl.host?.lowercase() ?: ""
                            val scheme = reqUrl.scheme?.lowercase() ?: ""

                            if (scheme == "https" && (host == ALLOWED_HOST || host.endsWith(".$ALLOWED_HOST"))) {
                                return false
                            }

                            if (scheme == "http" || scheme == "https") {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, reqUrl).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    view?.context?.startActivity(intent)
                                } catch (e: Exception) {
                                    Log.e(TAG, "Erro: ${e.message}")
                                }
                                return true
                            }
                            return true
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            loadProgress = newProgress
                            if (newProgress >= 50) {
                                isLoading = false
                            }
                        }
                    }
                }.also { wv ->
                    webViewInstance = wv
                    wv.loadUrl(url)
                }
            }
        )

        // Barra de progresso não-bloqueante
        if (isLoading && !hasError && loadProgress > 0 && loadProgress < 100) {
            LinearProgressIndicator(
                progress = { loadProgress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .align(Alignment.TopCenter),
                color = GoldPrimary,
                trackColor = CanvasSurfaceVariant
            )
        }

        // Barra inferior de controle rápido
        Surface(
            color = CanvasSurface.copy(alpha = 0.95f),
            border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onClose,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Fechar e Voltar ao App", fontSize = 12.sp)
                }

                IconButton(
                    onClick = {
                        hasError = false
                        isLoading = true
                        loadProgress = 0
                        webViewInstance?.reload()
                    },
                    modifier = Modifier.testTag("ask_memory_refresh_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Recarregar",
                        tint = GoldLight
                    )
                }

                IconButton(
                    onClick = onOpenBrowser,
                    modifier = Modifier.testTag("ask_memory_browser_action")
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInBrowser,
                        contentDescription = "Abrir no Chrome",
                        tint = GoldLight
                    )
                }
            }
        }

        // Mensagem de Erro com Saída Segura
        if (hasError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CanvasDeep)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = CanvasCard,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudOff,
                            contentDescription = null,
                            tint = AmberTertiary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Falha ao carregar no emulador",
                            color = GoldLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "A conexão no emulador oscilou ou foi bloqueada. Você pode abrir diretamente no seu navegador ou retornar ao app.",
                            color = TextParchment,
                            fontSize = 12.5.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onOpenBrowser,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = CanvasDeep
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("ABRIR NO NAVEGADOR", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = onClose,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("VOLTAR AO APP", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
