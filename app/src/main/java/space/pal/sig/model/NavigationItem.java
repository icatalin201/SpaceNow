package space.pal.sig.model;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavigationItem {

    public static final int MENU_ITEM = 1;
    public static final int MENU_DIVIDER = 2;

    private int title;
    private int icon;
    private int type;
    private Intent intent;
    private Fragment fragment;

}
