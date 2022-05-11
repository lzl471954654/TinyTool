package ext

import java.net.URI
import java.net.URL

fun urlParseToDict(url: String?): String? {
    url ?: return null
    URI.create(url)?.query?.let { query ->
        val builder = StringBuilder()
        for (entry in query.split('&')) {
            builder.append(entry)
                .append("\n")
        }
        return builder.toString()
    }
    return null
}

fun urlParseToDetail(url: String?) : String? {
    url ?: return null
    val uri = URI.create(url)
    val port = uri?.port
    val scheme = uri?.scheme
    val host = uri?.host
    val path = uri?.path
    val fragment = uri?.fragment
    val queryString = uri?.query
    val queryMap = getQueryMapByString(queryString)

    return "Raw url :\n\n" +
            "$url\n\n" +
            "scheme :$scheme\n\n" +
            "host :$host\n\n" +
            "path :$path\n\n" +
            "fragment :$fragment\n\n" +
            "query :-------------\n\n" +
            buildString {
                queryMap.forEach {
                    this.append("\n${it.key}=${it.value}")
                }
            }
}

fun getQueryMapByString(queryString : String?) : Map<String?,String?>{
    queryString ?: return hashMapOf()
    val query = hashMapOf<String?, String?>()
    queryString.split('&').forEach { entryString ->
        val keyValueArray = entryString.split('=')
        query[keyValueArray.safeGet(0)] = keyValueArray.safeGet(1)
    }
    return query
}
