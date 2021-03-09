package com.menu.pos;

import android.content.Context;
import android.widget.Toast;

public class Toast_show {

    private Toast toast;
    private Context context;
    Toast_show(Context context) {
        this.context = context;
//        this.toast = toast;
    }

    public Toast show(String String_message){
        if(toast == null){
            toast = Toast.makeText(context , String_message , Toast.LENGTH_LONG);
        }else{
            toast.setText(String_message);
        }
        return toast;
    }
}
