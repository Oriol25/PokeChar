package org.insbaixcamp.pokechar.conf;


import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

public class Language {

    /**
     * IDIOMAS: fr /  de / en / it / es
     *
     *
     */

    private static String language;
    private static String TAG = "PokeChar/Language";

    public static String language() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            locale = Resources.getSystem().getConfiguration().locale;
        }

        language = locale.toLanguageTag().substring(0, 2);

        if (language.equals("en")) {
            return language;
        } else if (language.equals("de")) {
            return language;
        } else if (language.equals("fr")) {
            return language;
        } else if (language.equals("it")) {
            return language;
        } else if (language.equals("es")) {
            return language;
        } else {
            return "en";
        }

    }
}
