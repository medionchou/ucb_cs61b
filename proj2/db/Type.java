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
    private static final Pattern INTEGER_MATCHER = Pattern.compile("^[-+]?\\d+?"),
            FLOAT_MATCHER = Pattern.compile("^[-+]?\\d*\\.\\d+?");


    Type(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

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

    public static Type isTypeMatched(String content, Type colType) {
        Type curType = null;
        if (content.length() == 0) return null;

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

    public static Type evaluateType(Value v1, Value v2) {
        Type newType;

        if (v1.getType() == v2.getType()) newType = v1.getType();
        else {
            if (v1.getType().equals(v2.getType())) newType = v1.getType();
            else if ((v1.getType().equals(Type.INTEGER) && v2.getType().equals(Type.FLOAT)) ||
                    (v1.getType().equals(Type.FLOAT) && v2.getType().equals(Type.INTEGER))) {
                newType = Type.FLOAT;
            }
            else throw new UnsupportedOperationException(Type.STRING +
                        " is only allowed to operate with 'string' type.");
        }

        return newType;
    }

    public static void main(String[] args) {
    }
}
