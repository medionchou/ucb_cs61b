package db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by medionchou on 2017/6/10.
 */
public class Expression {

    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV,
        WILDCARD,
        COLUMN_NAME
    }

    private String op1;
    private String op2;
    private String alias;
    private Operation type;


    public Expression(String expr) throws Exception {
        if (expr.length() == 1) {
            type = Operation.WILDCARD;
        } else {
            if (expr.indexOf("+") > 0)  type = Operation.ADD;
            else if (expr.indexOf("-") > 0)  type = Operation.SUB;
            else if (expr.indexOf("*") > 0)  type = Operation.MUL;
            else if (expr.indexOf("/") > 0)  type = Operation.DIV;
            else {
                type = Operation.COLUMN_NAME;
                op1 = expr;
                return;
            }
            parseEq(expr);
        }
    }

    private void parseEq(String expr) {
        Pattern p = Pattern.compile("(\\S+)\\s*[+\\-*/]\\s*(.+?)(?:\\s+as\\s+(\\S*))");
        Matcher m = p.matcher(expr);

        if (!m.matches()) throw new IllegalArgumentException("Expression \"" + expr + "\" is invalid.");

        op1 = m.group(1);
        op2 = m.group(2);
        alias = m.group(3);
    }

    public Operation getOpType() {
        return type;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getAlias() {
        return alias;
    }

    public static void main(String[] args) throws Exception {

        Expression exp = new Expression("*");

        System.out.println(exp.getOpType());
        System.out.println(exp.op2);
        System.out.println(exp.alias);
    }
}
