package com.nick.cancan.util;

public class AllowableHosts {

    private static String[] hosts = new String[]{"one", "two", "three"};

    public static String[] getHosts() {
        return hosts;
    }

    public static void setHosts(String[] hosts) {
        AllowableHosts.hosts = hosts;
    }
}
