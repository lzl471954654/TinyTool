package ext

fun <T> List<T>.safeGet(index: Int): T? {
    if (index >= this.size) {
        return null
    }
    return this[index]
}

fun <T> Array<T>.safeGet(index: Int): T? {
    if (index >= this.size) {
        return null
    }
    return this[index]
}

