package com.example.healthai.Controllers.Helpers;

import android.content.Context;
import android.content.Intent;

public class SwitchViewHelper {

    private SwitchViewHelper() {}

    public static void switchToActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
