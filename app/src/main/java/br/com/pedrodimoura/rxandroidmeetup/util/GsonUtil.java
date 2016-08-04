package br.com.pedrodimoura.rxandroidmeetup.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by pedrodimoura on 24/07/16.
 */
public class GsonUtil {

    public static Gson buildCustomGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

}
