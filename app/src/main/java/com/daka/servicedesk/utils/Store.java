package com.daka.servicedesk.utils;

import com.daka.sdk.models.User;
import com.orhanobut.hawk.Hawk;

/**
 * Created by Dana on 22-Sep-17.
 */

public class Store {
    public static final String KEY_USER = "key_user";

    public static void user (User user) {
        Hawk.put(KEY_USER, user);
    }

    public static User user() {
        return Hawk.get(KEY_USER, null);
    }

    public static void removeUser() {
        Hawk.remove(KEY_USER);
    }

    public static boolean hasUser() {
        return Hawk.contains(KEY_USER);
    }
}
