package space.pal.sig.old.model;

import lombok.RequiredArgsConstructor;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
@RequiredArgsConstructor
public enum NewsSource {
    HUBBLE("Hubble"),
    JAMES_WEBB_SPACE_TELESCOPE("James Webb"),
    EUROPEAN_SPACE_AGENCY("European Space Agency"),
    SPACE_TELESCOPE_LIVE("Space Telescope");

    private final String text;

    public String getText() {
        return text;
    }

    public static NewsSource fromText(String text) {
        for (NewsSource newsSource : NewsSource.values()) {
            if (newsSource.text.equals(text)) return newsSource;
        }
        return NewsSource.HUBBLE;
    }
}
