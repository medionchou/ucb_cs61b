package db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by medionchou on 2017/7/27.
 */
public class Condition {

    public enum ComparisonOperator {
        EQ,
        NEQ,
        LT,
        GT,
        LET,
        GET
    }

    private String op1;
    private String op2;
    private ComparisonOperator co;

    public Condition(String cond) {
        if (cond == null) throw new NullPointerException("Arguments cannot be null");

        if (cond.indexOf("==") > 0) co = ComparisonOperator.EQ;
        else if  (cond.indexOf("!==") > 0) co = ComparisonOperator.NEQ;
        else if  (cond.indexOf("<") > 0) co = ComparisonOperator.LT;
        else if  (cond.indexOf(">") > 0) co = ComparisonOperator.GT;
        else if  (cond.indexOf("<=") > 0) co = ComparisonOperator.LET;
        else if  (cond.indexOf(">=") > 0) co = ComparisonOperator.GET;
        else throw new IllegalArgumentException("Unexpected argument \"" + cond + "\".");

        parseCond(cond);
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public ComparisonOperator getCO() {
        return co;
    }

    private void parseCond(String cond) {
        Pattern p = Pattern.compile("(\\S+)\\s+(?:==|!==|<|>|<=|>=)\\s+(\\S+)");
        Matcher m = p.matcher(cond);

        if (!m.matches()) throw new IllegalArgumentException("Invalid argument: " + cond + ".");

        op1 = m.group(1);
        op2 = m.group(2);
    }

    public static void main(String[] args) {
        Condition c = new Condition("a !== b");

        System.out.println(c.getCO());
    }
}
