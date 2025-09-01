package me.mispz.util;

import me.mispz.entities.Konobar;

public final class LoggedIn {
    private static Konobar konobar;

    public static void logIn(Konobar konobar){
        LoggedIn.konobar = konobar;
    }
    public static Konobar getLogged(){
        return konobar;
    }
}
