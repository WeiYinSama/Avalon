package net.avalon.generic.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Heda
 * @Create: 2024/11/6
 */
public enum DeletedEnum {


    NOMAL((byte) 0,"正常"),
    DELETED((byte) 1,"已删除");

    private byte code;
    private String desc;

    private static final Map<Byte, String> map;

    static {
        map = new HashMap<>(DeletedEnum.values().length);
        for (DeletedEnum deletedEnum : DeletedEnum.values()) {
            map.put(deletedEnum.getCode(), deletedEnum.getDesc());
        }
    }

    //枚举的构造器总是私有的
    DeletedEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(byte code) {
        return map.get(code);
    }

    public String getDesc() {
        return desc;
    }

    public byte getCode() {
        return code;
    }
}
