package ext

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Base64

@Composable
fun urlParseUI(){
    var parserString by remember { mutableStateOf("") }
    var inputString by remember { mutableStateOf("") }
    Column (Modifier.padding(8.dp)){
        Text(
            text = "URL Parse"
        )
        TextField(
            value = inputString,
            onValueChange = {
                inputString = it
                urlParseToDetail(it)?.let { result ->
                    parserString = result
                }
            }
        )
        Box (Modifier.padding(0.dp,16.dp,0.dp,0.dp)){
            Text(
                text = parserString,
            )
        }
    }
}

@Composable
fun urlEncodeAndDecodeUI(){
    var needEncodeString by remember { mutableStateOf("") }
    var encodedString by remember { mutableStateOf("") }
    var needDecodeString by remember { mutableStateOf("") }
    var decodedString by remember { mutableStateOf("") }

    Column(Modifier.padding(8.dp)) {
        Text("URL encode")
        TextField(
            value = needEncodeString,
            onValueChange = {
                needEncodeString = it
                URLEncoder.encode(it, Charsets.UTF_8)?.let {
                    encodedString = it
                }
            }
        )
        TextField(
            modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp),
            value = encodedString,
            onValueChange = {

            }
        )
        Text(
            text = "URL decode",
            modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp)
        )
        TextField(
            value = needDecodeString,
            onValueChange = {
                needDecodeString = it
                URLDecoder.decode(it, Charsets.UTF_8)?.let {
                    decodedString = it
                }
            }
        )
        TextField(
            modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp),
            value = decodedString,
            onValueChange = {

            }
        )
    }
}

@Composable
fun base64UI(){
    var needBase64String by remember { mutableStateOf("") }
    var alreadyBase64String by remember { mutableStateOf("") }
    var needUnBase64String by remember { mutableStateOf("") }
    var alreadyUnBase64String by remember { mutableStateOf("") }

    Column(Modifier.padding(8.dp)) {
        Text("Base64 Encode")
        TextField(
            value = needBase64String,
            onValueChange = {
                needBase64String = it
                alreadyBase64String = Base64.getEncoder().encodeToString(it.toByteArray())
            }
        )
        TextField(
            modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp),
            value = alreadyBase64String,
            onValueChange = {

            }
        )
        Text(
            text = "Base64 decode",
            modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp)
        )
        TextField(
            value = needUnBase64String,
            onValueChange = {
                needUnBase64String = it
                alreadyUnBase64String = String(Base64.getDecoder().decode(it))
            }
        )
        TextField(
            modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp),
            value = alreadyUnBase64String,
            onValueChange = {

            }
        )
    }
}