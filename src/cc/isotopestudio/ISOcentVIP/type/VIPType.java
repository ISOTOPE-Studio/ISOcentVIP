package cc.isotopestudio.ISOcentVIP.type;

import cc.isotopestudio.ISOcentVIP.util.S;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public enum VIPType {
    mVIP("��7�·�VIP"), yVIP("��7���VIP"), NONE(S.toGray("��VIP"));
    final String name;

    VIPType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
