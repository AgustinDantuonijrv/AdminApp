package com.example.adminapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.adminapp.AgregarideosFragment;
import com.example.adminapp.MensajesFragent;
import com.example.adminapp.R;
import com.example.adminapp.VerusuariosFragment;
import com.example.adminapp.Videostodelete;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3,R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                VerusuariosFragment verusuariosFragment = new VerusuariosFragment();
                return verusuariosFragment;
            case 1:
                AgregarideosFragment agregarideosFragment = new AgregarideosFragment();
                return agregarideosFragment;
            case 2:
                MensajesFragent mensajesFragent = new MensajesFragent();
                return mensajesFragent;
            case 3:
                Videostodelete videostodelete = new Videostodelete();
                return videostodelete;
            default:
                 return  null;
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}