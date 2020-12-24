package space.pal.sig.model.entity

enum class NewsSource(val text: String) {
    HUBBLE("Hubble"),
    JAMES_WEBB_SPACE_TELESCOPE("James Webb"),
    EUROPEAN_SPACE_AGENCY("European Space Agency"),
    SPACE_TELESCOPE_LIVE("Space Telescope");

    companion object {
        fun fromText(text: String): NewsSource {
            for (newsSource in values()) {
                if (newsSource.text == text) return newsSource
            }
            return HUBBLE
        }
    }
}
