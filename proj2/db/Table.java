package db;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by medionchou on 2017/5/27.
 */
public class Table {

    private List<Row> rows;
    private Header header;
    private String tableName;
    private int colNum;

    public Table(String tableFile) throws IOException {
        File file = new File(tableFile);
        if (!file.isFile()) throw new FileNotFoundException("File " + tableFile + " not found.");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String buf;
        boolean firstLine = true;

        while ((buf = br.readLine()) != null) {

            if (firstLine) {
                parseHeader(buf, tableFile);
                firstLine = false;
            } else {
                addRow(buf);
            }
        }
    }

    public Table(String tableName, String[] colName, Type[] colType) {
        initTable(tableName, colName, colType);
    }


    public void addRow(String rowStr) {
        String[] result = rowStr.split(",");
        if (result.length != colNum) throw new IllegalArgumentException("Row value: '" + rowStr + "' does not match table column length " + colNum + ".");
        Value[] values = new Value[result.length];


        for (int i = 0; i < result.length; i++) {
            Type newColType = Type.checkType(result[i], header.getColumnType(i));
            if (newColType == null) throw new IllegalArgumentException("Value " + result[i] + " in column " + (i + 1) + " does not match the column type.");

            values[i] = new Value(result[i], newColType);
        }

        addRow(values);
    }

    private void addRow(Value[] vals) {
        Row theRow = new Row(colNum);
        theRow.addItems(vals);
        header.addColumns(vals);
        rows.add(theRow);
    }

    public void drop() {

    }

    private void initTable(String tableName, String[] colName, Type[] colType) {
        this.tableName = tableName;
        header = new Header(colName, colType);
        rows = new ArrayList<>();
        colNum = colName.length;
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

    public Column getCol(int index) {
        return header.getColumn(index);
    }

    public Column getCol(String colName) {
        return header.getColumn(colName);
    }

    public void store() {

    }

    public int size() {
        return rows.size();
    }

    public void join(Table... table) {

    }

    public String print() {
        return toString();
    }


    private void parseHeader(String headerStr, String tableFile) {
        String[] result = headerStr.split(",");
        String[] colName = new String[result.length];
        Type[] colType = new Type[result.length];

        for (int i = 0; i < result.length; i++) {
            int idx = result[i].indexOf(" ");
            colName[i] = result[i].substring(0, idx);
            colType[i] = Type.conversion(result[i].substring(idx+1, result[i].length()));
        }
        initTable(tableFile, colName, colType);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(header.toString() + "\n");

        for (Row row : rows) sb.append(row.toString() + "\n");

        return sb.toString();
    }

    public static void main(String[] args) {
        String a = "+0033";

        Pattern p = Pattern.compile("^[-+]?\\d+?");
        Pattern p2 = Pattern.compile("^[-+]?\\d*\\.\\d+?");
        Matcher m = p.matcher(a);
        Matcher m2 = p2.matcher(a);


        System.out.println(m.matches());
        System.out.println(m2.matches());
        System.out.println(Float.parseFloat(a));
    }

}
