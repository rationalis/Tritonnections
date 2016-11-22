package com.ucsdcse110.tritonnections;

import com.ucsdcse110.tritonnections.task.HTTPRequestTask;
import com.ucsdcse110.tritonnections.task.TritonlinkLoginTask;

public class TritonlinkLoginManager {
    private static TritonlinkLoginTask task;
    private static String pid;
    private static String name = "Anonymous";

    public static boolean isLoggedIn() {
        return task != null && task.isLoggedIn();
    }

    public static String pid() {
        if (isLoggedIn()) return pid;
        else throw new LoginRequiredException();
    }

    public static void setName(String theirName){
        name = theirName;
    }

    public static String getName(){
        return name;
    }

    public static TritonlinkLoginTask login(String pid, String pw) {
        if (isLoggedIn()) return task;

        TritonlinkLoginManager.pid = pid;
        task = new TritonlinkLoginTask(pid, pw);
        task.execute();

        return task;
    }

    // TODO: Actually logout instead of just clearing out cookies
    public static void logout() {
        pid = null;
        name = "Anonymous";
        task = null;
        HTTPRequestTask.cookieManager.getCookieStore().removeAll();
    }

    public static class LoginRequiredException extends RuntimeException {
        public LoginRequiredException() {
            super("Login required");
        }
    }
}
