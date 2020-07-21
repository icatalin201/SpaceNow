package space.pal.sig.view.launches;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * SpaceNow
 * Created by Catalin on 7/21/2020
 **/
@Getter
@Setter
@AllArgsConstructor
public class LaunchFilter implements Parcelable {
    private int year;

    protected LaunchFilter(Parcel in) {
        year = in.readInt();
    }

    public static final Creator<LaunchFilter> CREATOR = new Creator<LaunchFilter>() {
        @Override
        public LaunchFilter createFromParcel(Parcel in) {
            return new LaunchFilter(in);
        }

        @Override
        public LaunchFilter[] newArray(int size) {
            return new LaunchFilter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
    }
}
