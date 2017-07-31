package db;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import db.Expression.Operation;

/**
 * Created by medionchou on 2017/5/27.
 */
public class Value implements Comparable<Value>{

    private String content;
    private Type type;

    public Value(String content, Type type) {
        if (Type.isTypeMatched(content, type) == null)
            throw new IllegalArgumentException("content \"" + content + "\" does not match " + type + " type.");
        this.content = content;
        this.type = type;
    }

    public Value evaluate(Value o, Operation op) {
        String result = "";
        Type newType = Type.evaluateType(this.getType(), o.getType());

        if (!op.equals(Operation.ADD) && newType.equals(Type.STRING))
            throw new UnsupportedOperationException(Type.STRING + " only supports concatenation(+).");

        if (content.equals("NaN") || o.content.equals("NaN"))
            return new Value("NaN", newType);
        else if (content.equals("NOVALUE") && o.content.equals("NOVALUE"))
            return new Value("NOVALUE", newType);

        content = convert(content, newType);
        o.content = convert(o.content, newType);

        switch (newType) {
            case INTEGER:
                result = eval(o.content, op, newType);
                break;
            case FLOAT:
                result = eval(o.content, op, newType);
                break;
            case STRING:
                result = "'" + content.substring(1, content.length() - 1) + o.content.substring(1, o.content.length() - 1) + "'";
                break;
        }

        return new Value(result, newType);
    }

    private String eval(String v1, Operation op, Type t) {
        String result = "";

        if (op.equals(Operation.ADD))
            result = "" + (Float.parseFloat(content) + Float.parseFloat(v1));
        else if (op.equals(Operation.SUB))
            result = "" + (Float.parseFloat(content) - Float.parseFloat(v1));
        else if (op.equals(Operation.MUL))
            result = "" + (Float.parseFloat(content) * Float.parseFloat(v1));
        else if (op.equals(Operation.DIV)) {
            float f = (Float.parseFloat(content) / Float.parseFloat(v1));


            if (Float.isNaN(f) || Float.isInfinite(f)) return "NaN";
            else result = "" + f;
        }

        if (t.equals(Type.FLOAT)) return result;
        else return "" + (int)Float.parseFloat(result);
    }

    private String convert(String val, Type t) {
        if (val.equals("NOVALUE")) {
            switch (t) {
                case FLOAT:
                    return "0.0";
                case INTEGER:
                    return "0";
                case STRING:
                    return "''";
            }
        }

        return val;
    }

    public Integer intVal() {
        return Integer.parseInt(content);
    }

    public Float floatVal() {
        return Float.parseFloat(content);
    }

    public String strVal() {
        return content;
    }

    public Object value() {
        if (content.equals("NaN") || content.equals("NOVALUE")) return content;

        switch (type) {
            case FLOAT:
                return Float.parseFloat(content);
            case INTEGER:
                return Integer.parseInt(content);
            case STRING:
                return content;
        }
        throw new UnsupportedOperationException("Not a valid type " + type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value = (Value) o;

        if (content != null ? !content.equals(value.content) : value.content != null) return false;
        return type == value.type;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public String getContent() {
        return content;
    }

    public Type getType() {
        return type;
    }

    public boolean isTypeEqual(Type otherType) {
        return type == otherType;
    }

    public String toString() {
        return content;
    }

    public static void main(String[] args) {
        Number o = 11.1;

        System.out.println(" " + o);

    }

    @Override
    public int compareTo(Value o) {
        if (this == o) return 0;

        if (this.type != o.type) {
            if (this.type == Type.STRING || o.type == Type.STRING) throw new UnsupportedOperationException("Type 'string' can only be compared with 'string' type");
            else return floatVal().compareTo(o.floatVal());
        } else {
            if (this.type == Type.INTEGER) return intVal().compareTo(o.intVal());
            else if (this.type == Type.FLOAT) return floatVal().compareTo(o.floatVal());
            else return content.compareTo(o.content);
        }
    }
}
