package com.game.jxj.config.enums;

/**
 * @author jxj
 * @date 2018/6/28
 */
public enum MusicType {
    //1:纯音乐2:电音3:搞笑4:经典5:流行6:欧美7:舞蹈8:新歌9:原创 ,
    TYPE_CHUN_YINYUE("纯音乐", 1),
    TYPE_DIANYIN("电音", 2),
    TYPE_GAOXIAO("搞笑", 3),
    TYPE_JINGDIAN("经典", 4),
    TYPE_LIUXING("流行", 5),
    TYPE_OUMEI("欧美", 6),
    TYPE_WUDAO("舞蹈", 7),
    TYPE_XINGE("新歌", 8),
    TYPE_YUANCHUANG("原创", 9);

    private String name;
    private int index;

    MusicType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static MusicType getNameByIndex(int index) {
        for (MusicType type : MusicType.values()) {
            if (type.index == index) {
                return type;
            }
        }
        return null;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
