package com.maxim_ilinov_gmail.feedsin.view;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class HandlerViewFocusChange {

    public View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean isFocused) {
            //Hide Keyboard

            if (view !=null) {

                if(!isFocused)
                {
                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }





        }
    };
}
