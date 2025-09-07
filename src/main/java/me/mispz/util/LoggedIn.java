package me.mispz.util;

import me.mispz.entities.Konobar;
import me.mispz.entities.Vlasnik;

public final class LoggedIn {
    private static Konobar konobar;
    private static Vlasnik vlasnik;
    public static void logIn(Object logged){
        if(logged instanceof Konobar){
            LoggedIn.konobar = (Konobar) logged;
            vlasnik = null;
        } else if(logged instanceof Vlasnik){
            LoggedIn.vlasnik = (Vlasnik) logged;
            konobar = null;
        }
    }
    public static Konobar getKonobar(){
        return konobar;
    }

    public static Vlasnik getVlasnik() {
        return vlasnik;
    }

    public static boolean isKonobar(){
        return konobar != null;
    }
    public static void loggout(){
        konobar = null;
        vlasnik = null;
    }
}
