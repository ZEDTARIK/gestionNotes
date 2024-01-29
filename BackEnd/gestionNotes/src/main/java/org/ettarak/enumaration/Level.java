package org.ettarak.enumaration;

import lombok.Getter;
@Getter
public enum Level {
    LOW (1),
    MEDIUM (2),
    HIGH (3);

    private  final  int level;
    Level(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}
