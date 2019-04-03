package asus.example.com.exercise4;

import android.content.Context;
import android.widget.Toast;

class HelpToast {
    static void showToast(int textId, Context context){
        Toast.makeText(context, context.getResources().getText(textId), Toast.LENGTH_SHORT).show();

    }
}
