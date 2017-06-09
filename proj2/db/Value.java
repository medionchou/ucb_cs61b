package db;

/**
 * Created by medionchou on 2017/5/27.
 */
public class Value {

    private String content;
    private Type type;

    public Value(String content, Type type) {
        this.content = content;
        this.type = type;
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

        Value value = new Value("dog", Type.FLOAT);

        System.out.println(value);
    }
}
