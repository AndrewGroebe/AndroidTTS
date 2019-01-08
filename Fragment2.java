package com.example.groebe.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The second fragment, or page, of the application's GUI. In the context of the application's functionality, this
 * page represents the "Common Words & Phrases (Continued)" portion.
 *
 * @author Andrew Groebe.
 */
public final class Fragment2 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        if (container == null) {
            return null;
        }
        return inflater.inflate(R.layout.fragment2_layout, container, false);
    }
    
}
