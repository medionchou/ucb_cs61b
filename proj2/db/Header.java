package db;

import java.util.*;


/**
 * Created by medionchou on 2017/5/27.
 */
public class Header implements Iterable<ColumnName> {

    private LinkedHashMap<String, Column> headerMap;
    private Type[] type;
    private ArrayList<ColumnName> colNameList;


    @Override
    public Iterator<ColumnName> iterator() {
        return colNameList.iterator();
    }

    public Header(String[] colName, Type[] theType) {
        type = theType;
        colNameList = new ArrayList<>();
        headerMap = new LinkedHashMap<>();

        for (int i = 0; i < colName.length; i++) {
            headerMap.put(colName[i], new Column());
            colNameList.add(new ColumnName(colName[i], theType[i]));
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
        ColumnName cn = colNameList.get(index);
        return headerMap.get(cn.getColName());
    }

    public ColumnName getColumnName(int index) {
        return colNameList.get(index);
    }

    public int colCount() {return headerMap.size();}

    public Type getColumnType(int col) {
        return type[col];
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuffer sb = new StringBuffer();

        for (ColumnName cn : colNameList) {
            sb.append(cn.getColName() + " " + cn.getType());
            if (i < colNameList.size() - 1) sb.append(",");
            i += 1;
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }

}
