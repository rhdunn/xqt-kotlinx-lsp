@Suppress("KDocMissingDocumentation")
enum class BuildType(val suffix: String) {
    Release(""),
    Snapshot("-SNAPSHOT"),
}
