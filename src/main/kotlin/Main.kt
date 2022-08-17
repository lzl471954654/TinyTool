// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ext.base64UI
import ext.urlEncodeAndDecodeUI
import ext.urlParseUI
import java.awt.image.BufferedImage
import java.net.URI

var imageSize = 400

@Composable
@Preview
fun App() {
    MaterialTheme {
        Row {
            Box (Modifier.wrapContentSize().weight(3f,true)){
                qrLayout()
            }
            Box (Modifier.wrapContentSize().weight(2f,true)){
                urlParseUI()
            }
            Box (Modifier.wrapContentSize().weight(2f, true)){
                urlEncodeAndDecodeUI()
            }
            Box (Modifier.wrapContentSize().weight(2f, true)){
                base64UI()
            }

        }

    }
}

fun main() = application {
    var visible by remember { mutableStateOf(true) }
    Window(
        onCloseRequest = { visible = false },
        visible = visible,
        resizable = true,
    ) {
        App()
    }
    imageSize = with(LocalDensity.current) {
        500.dp.toPx()
    }.toInt()
    val trayState = rememberTrayState()
    Tray(
        state = trayState,
        menu = {
            Item(
                "open tool",
                onClick = {
                    visible = true
                }
            )
            Item(
                "exit",
                onClick = ::exitApplication
            )
        },
        icon = painterResource("icon.png"),
        onAction = {
            visible = true
        }
    )
}
