package dev.deitylamb.yarrow.common;

import java.util.Collections;

public interface Displayable {

    public static String INDENT = "  ";

    public static String indent(int depth) {
        return String.join("", Collections.nCopies(depth, INDENT));
    }

    String display(int depth);

    default String display() {
        return display(0);
    }
}
