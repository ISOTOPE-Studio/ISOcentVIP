package cc.isotopestudio.ISOcentVIP.type;

import cc.isotopestudio.ISOcentVIP.Util.S;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public enum VIPType {
    mVIP(S.toBoldDarkGreen("ÔÂ¸¶VIP")), yVIP(S.toBoldGold("Äê¸¶VIP")), NONE(S.toGray("ÎÞVIP"));
    final String name;

    VIPType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
