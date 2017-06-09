package db;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by medionchou on 2017/5/27.
 */
public class Header {

    private LinkedHashMap<String, Column> headerMap;
    private Type[] type;

    public Header(String[] colName, Type[] theType) {
        type = theType;
        headerMap = new LinkedHashMap<>();

        for (String str : colName) {
            headerMap.put(str, new Column());
        }

    }


    public void addColumns(Value[] values) {
        int counter = 0;
        for (Column col : headerMap.values()) {
            col.addItems(values[counter]);
            counter += 1;
        }
    }

    public Column getColumn(String colName) {
        return headerMap.get(colName);
    }

    public Column getColumn(int index) {
        int count = 0;
        for (String s : headerMap.keySet()) {
            if (count == index) return headerMap.get(s);
            count += 1;
        }

        return null;
    }


    public Type getColumnType(int col) {
        return type[col];
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuffer sb = new StringBuffer();
        for (String str : headerMap.keySet()) {
            sb.append(str + " " + type[i]);
            if (i < type.length - 1) sb.append(",");
            i += 1;
        }

        return sb.toString();
    }

    public static void main(String[] args) {

    }

}
