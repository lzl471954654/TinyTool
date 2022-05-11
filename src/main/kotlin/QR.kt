import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.zxing.*
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer
import java.awt.Image
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.image.BufferedImage


@Composable
fun qrLayout() {
    Column(Modifier.wrapContentSize(), verticalArrangement = Arrangement.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 二维码识别
            Card(
                elevation = 4.dp,
                modifier = Modifier.padding(8.dp).weight(1f,true)
            ) {
                qrReaderLayout()
            }
            // 二维码生成
            Card(
                elevation = 4.dp,
                modifier = Modifier.padding(8.dp).weight(1f,true)
            ) {
                qrDecodeLayout()
            }
        }
    }
}

@Composable
fun qrDecodeLayout(){
    var imgFromText by remember { mutableStateOf(getFirstBitmap()) }
    var encodeText by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "二维码生成",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
        Row(
            Modifier.fillMaxWidth().wrapContentHeight().weight(1f,true),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                encodeText,
                modifier = Modifier.fillMaxHeight()
                    .weight(1f,true)
                    .padding(8.dp),
                onValueChange = {
                    encodeText = it
                    encodeToQRImage(it)?.let {
                        imgFromText = it
                    }
                }
            )
            Image(
                imgFromText.toComposeImageBitmap(),
                "",
                Modifier.size(500.dp).weight(1f,false).padding(16.dp)
            )
        }
    }
}

@Composable
fun qrReaderLayout(){
    var imgFromMem by remember { mutableStateOf(BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)) }
    var decodeText by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "识别二维码，点击按钮识别",
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center
        )
        Row(
            Modifier.fillMaxWidth().wrapContentHeight().weight(1f,true),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    readImgFromClipBoard()?.let {
                        imgFromMem = it
                        decode(it)?.let {
                            decodeText = it
                        }
                    }
                },
                modifier = Modifier.wrapContentSize().weight(1f,true)
            ) {
                Text("点击识别")
            }
            TextField(
                decodeText,
                onValueChange = {},
                modifier = Modifier.fillMaxHeight().weight(1f,true).padding(8.dp)
            )
        }
    }
}


fun decode(bufferedImage: BufferedImage?): String? {
    bufferedImage ?: return null
    runCatching {
        val source = BufferedImageLuminanceSource(bufferedImage)
        val barbarize = HybridBinarizer(source)
        val binaryBitmap = BinaryBitmap(barbarize)
        val decodeHints = hashMapOf(DecodeHintType.CHARACTER_SET to "UTF-8")
        val result = MultiFormatReader().decode(binaryBitmap, decodeHints)
        return result.text
    }.exceptionOrNull()?.let {
        System.err.println(it)
    }
    return null
}


fun encodeToQRImage(string: String?): BufferedImage? {
    runCatching {
        val bitMatrix = MultiFormatWriter().encode(string, BarcodeFormat.QR_CODE, imageSize, imageSize)
        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }
    return null
}

fun getFirstBitmap() : BufferedImage{
    return encodeToQRImage("0")!!
}

fun readImgFromClipBoard(): BufferedImage? {
    val systemClipboard = Toolkit.getDefaultToolkit().systemClipboard
    val contents = systemClipboard.getContents(null)
    if (contents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
        val img = contents.getTransferData(DataFlavor.imageFlavor)
        if (img is BufferedImage){
            return img as BufferedImage
        }
        if (img is Image){
            return convertToBufferedImage(img)
        }
    }
    return null
}

fun convertToBufferedImage(image: Image): BufferedImage? {
    val newImage = BufferedImage(
        image.getWidth(null), image.getHeight(null),
        BufferedImage.TYPE_INT_RGB
    )
    val g = newImage.createGraphics()
    g.drawImage(image, 0, 0, null)
    g.dispose()
    return newImage
}