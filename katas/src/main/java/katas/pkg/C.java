package katas.pkg;

import katas.A;

@A
public class C {
    private String str;

    @Override
    public String toString() {
        return new StringBuffer("C")
                .append("str:").append(str)
                .toString();
    }
}
