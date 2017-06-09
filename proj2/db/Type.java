package db;

import java.util.regex.Pattern;

/**
 * Created by medionchou on 2017/5/27.
 */
public enum Type {
    STRING("string"),
    INTEGER("int"),
    FLOAT("float");

    private final String name;


    Type(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
    private static final Pattern INTEGER_MATCHER = Pattern.compile("^[-+]?\\d+?"),
                                 FLOAT_MATCHER = Pattern.compile("^[-+]?\\d*\\.\\d+?");

    public static Type conversion(String type) {
        switch (type) {
            case "string":
                return Type.STRING;
            case "float":
                return Type.FLOAT;
            case "int":
                return Type.INTEGER;
            default:
                throw new IllegalArgumentException(type + " is not allowed");
        }
    }

    public static Type checkType(String content, Type colType) {
        Type curType = null;
        if (content.charAt(0) == '\'') curType = Type.STRING;
        else {
            if (content.equals("NOVALUE")) return colType;
            else if (content.equals("NaN")) return colType;
            else if (INTEGER_MATCHER.matcher(content).matches()) curType = Type.INTEGER;
            else if (FLOAT_MATCHER.matcher(content).matches()) curType = Type.FLOAT;
        }
        if (curType == colType) return curType;
        else return null;
    }

    public static void main(String[] args) {
        System.out.println(FLOAT_MATCHER.matcher("1.0").matches());
    }
}
