package cc.isotopestudio.ISOcentVIP.type;

import cc.isotopestudio.ISOcentVIP.Util.S;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public enum VIPType {
    mVIP(S.toBoldDarkGreen("�¸�VIP")), yVIP(S.toBoldGold("�긶VIP")), NONE(S.toGray("��VIP"));
    final String name;

    VIPType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
