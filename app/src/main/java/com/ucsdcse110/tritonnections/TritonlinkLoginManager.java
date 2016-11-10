package com.ucsdcse110.tritonnections;

import com.ucsdcse110.tritonnections.task.TritonlinkLoginTask;

public class TritonlinkLoginManager {
    private static boolean loggedIn = false;
    private static String pid;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String pid() {
        if (loggedIn) return pid;
        else throw new LoginRequiredException();
    }

    public static void login(String pid, String pw) {
        if (loggedIn) return;

        TritonlinkLoginManager.pid = pid;
        TritonlinkLoginTask task = new TritonlinkLoginTask(pid, pw);
        task.execute();

        try {
            task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (task.isLoggedIn()) loggedIn = true;
    }

    public static class LoginRequiredException extends RuntimeException {
        public LoginRequiredException() {
            super("Login required");
        }
    }
}
