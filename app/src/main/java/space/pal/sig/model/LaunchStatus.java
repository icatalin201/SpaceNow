package space.pal.sig.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@Getter
@RequiredArgsConstructor
public enum LaunchStatus {
    GREEN(1, "Available for launch"),
    RED(2, "Not available for launch"),
    SUCCESS(3, "Launched successfully"),
    FAILED(4, "Launch failed");

    private final int value;
    private final String name;

    public static LaunchStatus fromValue(int value) {
        for (LaunchStatus launchStatus : LaunchStatus.values()) {
            if (launchStatus.value == value) return launchStatus;
        }
        return LaunchStatus.GREEN;
    }
}
