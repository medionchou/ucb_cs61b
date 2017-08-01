package db;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by medionchou on 2017/5/27.
 *
 *
 */
public class Column extends DataList {

    public void removeItem(int index) {
        values.remove(index);
    }

    public Column operate(Column col, Expression.Operation op) {
        Column newColumn = new Column();
        for (int i = 0; i < col.size(); i++) {
            Value tmp = this.getItem(i).evaluate(col.getItem(i), op);
            newColumn.addItems(tmp);
        }
        return newColumn;
    }

    public Column operate(Value val, Expression.Operation op) {
        Column newColumn = new Column();

        for (Value v : this) {
            Value tmp = v.evaluate(val, op);
            newColumn.addItems(tmp);
        }

        return newColumn;
    }

    public ArrayList<Integer> comparison(Column col, Condition.ComparisonOperator op) {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < size(); i++) {
            Value value = getItem(i);
            Value o = col.getItem(i);
            switch (op) {
                case EQ:
                    if (value.compareTo(o) != 0) indices.add(i);
                    break;
                case NEQ:
                    if (value.compareTo(o) == 0) indices.add(i);
                    break;
                case GT:
                    if (value.compareTo(o) < 0 || value.compareTo(o) == 0) indices.add(i);
                    break;
                case LT:
                    if (value.compareTo(o) > 0 || value.compareTo(o) == 0) indices.add(i);
                    break;
                case GET:
                    if (value.compareTo(o) < 0) indices.add(i);
                    break;
                case LET:
                    if (value.compareTo(o) > 0) indices.add(i);
                    break;
            }
        }

        return indices;
    }

    public ArrayList<Integer> comparison(Value o, Condition.ComparisonOperator op) {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < size(); i++) {
            Value value = getItem(i);
            switch (op) {
                case EQ:
                    if (value.compareTo(o) != 0) indices.add(i);
                    break;
                case NEQ:
                    if (value.compareTo(o) == 0) indices.add(i);
                    break;
                case GT:
                    if (value.compareTo(o) < 0 || value.compareTo(o) == 0) indices.add(i);
                    break;
                case LT:
                    if (value.compareTo(o) > 0 || value.compareTo(o) == 0) indices.add(i);
                    break;
                case GET:
                    if (value.compareTo(o) < 0) indices.add(i);
                    break;
                case LET:
                    if (value.compareTo(o) > 0) indices.add(i);
                    break;
            }
        }

        return indices;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            sb.append(values)
        }
    }
}
