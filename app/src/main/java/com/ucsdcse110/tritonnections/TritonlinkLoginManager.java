package com.ucsdcse110.tritonnections;

import com.ucsdcse110.tritonnections.task.TritonlinkLoginTask;

public class TritonlinkLoginManager {
    private static TritonlinkLoginTask task;
    private static String pid;

    public static boolean isLoggedIn() {
        return task != null && task.isLoggedIn();
    }

    public static String pid() {
        if (isLoggedIn()) return pid;
        else throw new LoginRequiredException();
    }

    public static TritonlinkLoginTask login(String pid, String pw) {
        if (isLoggedIn()) return task;

        TritonlinkLoginManager.pid = pid;
        task = new TritonlinkLoginTask(pid, pw);
        task.execute();

        return task;
    }

    public static class LoginRequiredException extends RuntimeException {
        public LoginRequiredException() {
            super("Login required");
        }
    }
}
