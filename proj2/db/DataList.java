package db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Created by medionchou on 2017/5/27.
 */
public abstract class DataList implements Iterable<Value> {

    protected List<Value> values;

    public DataList() {
        values = new ArrayList<>();
    }

    public DataList(int size) {
        values = new ArrayList<>(size);
    }

    public int size() {
        return values.size();
    }

    public Value getItem(int index) {
        return values.get(index);
    }

    public void addItems(Value... val) {
        for (Value v : val)
            values.add(v);
    }

    public Iterator<Value> iterator() {
        return values.iterator();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i));
            if (i < values.size() - 1) sb.append(",");
        }

        return sb.toString();
    }
}
