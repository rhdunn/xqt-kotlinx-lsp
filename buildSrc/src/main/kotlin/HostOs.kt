@file:Suppress("KDocMissingDocumentation")

enum class HostOs {
    Windows,
    Linux,
    MacOsX,
    Unknown
}

fun HostOs(name: String): HostOs = when {
    name.startsWith("Windows") -> HostOs.Windows
    name == "Linux" -> HostOs.Linux
    name == "Mac OS X" -> HostOs.MacOsX
    else -> HostOs.Unknown
}
