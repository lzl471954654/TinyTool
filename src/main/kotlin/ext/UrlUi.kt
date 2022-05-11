package ext

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun urlParseUI(){
    var parserString by remember { mutableStateOf("") }
    var inputString by remember { mutableStateOf("") }
    Column (Modifier.padding(8.dp)){
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