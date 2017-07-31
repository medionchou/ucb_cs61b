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
                throw new IllegalArgumentException("column type '" + type + "' is not allowed");
        }
    }

    public static Type isTypeMatched(String content, Type colType) {
        Type curType = checkType(content);

        if (content.equals("NOVALUE") || content.equals("NaN")) return colType;

        if (curType == colType) return curType;
        else return null;
    }

    public static Type checkType(String content) {
        if (content.length() == 0) throw new IllegalArgumentException("empty string '" + content + "' do not have type");

        if (content.charAt(0) == '\'') return Type.STRING;
        else {
            if (INTEGER_MATCHER.matcher(content).matches()) return Type.INTEGER;
            else if (FLOAT_MATCHER.matcher(content).matches()) return Type.FLOAT;
        }

        return null;
    }

    public static Type evaluateType(Type v1, Type v2) {
        Type newType;

        if (v1 == v2) newType = v1;
        else if ((v1.equals(Type.INTEGER) && v2.equals(Type.FLOAT)) ||
                    (v1.equals(Type.FLOAT) && v2.equals(Type.INTEGER))) {
            newType = Type.FLOAT;
        }
        else throw new UnsupportedOperationException(Type.STRING +
                        " is only allowed to operate with 'string' type.");

        return newType;
    }

    public static void main(String[] args) {
    }
}
