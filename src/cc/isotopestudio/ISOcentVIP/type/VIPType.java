package cc.isotopestudio.ISOcentVIP.type;

import cc.isotopestudio.ISOcentVIP.util.S;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public enum VIPType {
    mVIP("§7月费VIP"), yVIP("§7年费VIP"), NONE(S.toGray("无VIP"));
    final String name;

    VIPType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
