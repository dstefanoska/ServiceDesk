package com.daka.servicedesk.utils;

import com.daka.sdk.models.Task;
import com.daka.sdk.models.User;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 22-Sep-17.
 */

public class Store {
    private static final String KEY_USER = "key_user";
    private static final String KEY_OPEN_TASK = "key_open_task";
    private static final String KEY_DELAYED_TASKS = "key_delayed_tasks";
    private static final String KEY_ARCHIVED_TASKS = "key_archived_tasks";

    public static void user(User user) {
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

    public static void openTask(Task openTask) {
        Hawk.put(KEY_OPEN_TASK, openTask);
    }

    public static Task openTask() {
        return Hawk.get(KEY_OPEN_TASK, null);
    }

    public static boolean hasOpenTask() {
        return Hawk.contains(KEY_OPEN_TASK);
    }

    public static List<Task> delayedTasks() {
        return Hawk.get(KEY_DELAYED_TASKS, new ArrayList<Task>());
    }

    public static void delayedTasks(List<Task> tasks) {
        Hawk.put(KEY_DELAYED_TASKS, tasks);
    }

    public static List<Task> archivedTasks() {
        return Hawk.get(KEY_ARCHIVED_TASKS, new ArrayList<Task>());
    }

    public static void archivedTasks(List<Task> tasks) {
        Hawk.put(KEY_ARCHIVED_TASKS, tasks);
    }


}
