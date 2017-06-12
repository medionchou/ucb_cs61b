package db;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import db.Expression.Operation;

/**
 * Created by medionchou on 2017/5/27.
 */
public class Value {

    private String content;
    private Type type;

    public Value(String content, Type type) {
        if (Type.isTypeMatched(content, type) == null)
            throw new IllegalArgumentException("content \"" + content + "\" does not match the " + type + " type.");
        this.content = content;
        this.type = type;
    }

    public Value evaluate(Value o, Operation op) {
        String result = "";
        Type newType = Type.evaluateType(this, o);

        if (!op.equals(Operation.ADD) && newType.equals(Type.STRING))
            throw new UnsupportedOperationException(Type.STRING + " only supports concatenation.");

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

    public int intVal() {
        return Integer.parseInt(content);
    }

    public float floatVal() {
        return Float.parseFloat(content);
    }

    public String strVal() {
        return getContent();
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
        return content + "(" + type + ")";
    }

    public static void main(String[] args) {
        Number o = 11.1;

        System.out.println(" " + o);

    }
}
