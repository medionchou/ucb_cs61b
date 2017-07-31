package db;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by medionchou on 2017/5/27.
 */
public class Table {

    private List<Row> rows;
    private Header header;
    private int colNum;
    private static class Pair {
        int i;
        int j;
        Pair (int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public Table(String tableFile) throws IOException {
        File file = new File(tableFile);
        Scanner sc;

        if (file.isFile()) sc = new Scanner(file);
        else sc = new Scanner(file);

        String buf;
        boolean firstLine = true;

        while (sc.hasNextLine()) {
            buf = sc.nextLine();

            if (firstLine) {
                parseHeader(buf);
                firstLine = false;
            } else {
                addRow(buf);
            }
        }
    }

    public Table(String[] colName, Type[] colType) {
        initTable(colName, colType);
    }


    public void addRow(String rowStr) {
        String[] result = rowStr.split(",");
        if (result.length != colNum) throw new IllegalArgumentException("Row value: '" + rowStr + "' does not match table column length " + colNum + ".");
        Value[] values = new Value[result.length];


        for (int i = 0; i < result.length; i++) {
            Type newColType = Type.isTypeMatched(result[i], header.getColumnType(i));
            if (newColType == null) throw new IllegalArgumentException("Value " + result[i] + " in column " + (i + 1) + " does not match the column type \""+ header.getColumnType(i) +"\".");

            values[i] = new Value(result[i], newColType);
        }

        addRow(values);
    }

    public void deleteRow(int index) {

    }

    private void addRow(Value[] vals) {
        Row theRow = new Row(colNum);
        theRow.addItems(vals);
        header.addColumns(vals);
        rows.add(theRow);
    }

    public void drop() {

    }

    private void initTable(String[] colName, Type[] colType) {
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

    public void store(String filename) {
        try {
            BufferedWriter bw = Files.newBufferedWriter(new File(filename).toPath(), Charset.defaultCharset());

            bw.write(this.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public int rowCount() {
        return rows.size();
    }

    public int colCount() { return header.colCount(); }

    public Table select(Table[] tables, Expression[] exprs, Condition[] conditions) {
        Table temp = join(0, tables);

        temp = select(temp, exprs);

        return null;
    }

    private Table select(Table table, Expression[] exprs) {


    }

    private Table join(int index, Table... table) {
        if (index == table.length) return this;

        HashSet<ColumnName> visited = new HashSet<>();
        ArrayList<ColumnName> commonHeader = new ArrayList<>();
        StringBuilder[] sb;

        for (ColumnName table1 : header) {
            for (ColumnName table2 : table[index].header) {
                if (table1.equals(table2)) {
                    commonHeader.add(table1);
                    visited.add(table1);
                }
            }
        }

        sb = merge(commonHeader, visited, this, table[0]);

        for (ColumnName cn : header) if (!visited.contains(cn)) commonHeader.add(cn);
        for (ColumnName cn : table[index].header) if (!visited.contains(cn)) commonHeader.add(cn);


        String[] s = new String[commonHeader.size()];
        Type[] t = new Type[commonHeader.size()];

        for (int i = 0; i < commonHeader.size(); i++) {
            ColumnName cn = commonHeader.get(i);
            s[i] = cn.getColName();
            t[i] = cn.getType();
        }
        Table newTable = new Table(s, t);

        for (StringBuilder str: sb) {
            newTable.addRow(str.toString());
        }

        return newTable.join(index + 1, table);
    }

    private StringBuilder[] merge(ArrayList<ColumnName> common, HashSet<ColumnName> visited, Table tb1, Table tb2) {
        List<Pair> pairs = getMatchedColumns(common, tb1, tb2);
        StringBuilder[] rawStr;

        if (pairs.size() > 0) {
            rawStr = new StringBuilder[pairs.size()];

            for (int i = 0; i < rawStr.length; i++) {
                rawStr[i] = new StringBuilder();
                Pair pair = pairs.get(i);
                for (ColumnName cn : common) {
                        Column col = tb1.getCol(cn.getColName());
                        rawStr[i].append(col.getItem(pair.i).getContent())
                                 .append(",");
                }
                rawStr[i].append(queryRow(pair.i, visited, tb1));
                rawStr[i].append(queryRow(pair.j, visited, tb2));
                rawStr[i].deleteCharAt(rawStr[i].length() - 1);
            }
        } else {
            rawStr = new StringBuilder[tb1.rowCount() * tb2.rowCount()];

            for (int i = 0; i < tb1.rows.size(); i++) {
                for (int j = 0; j < tb2.rows.size(); j++) {
                    rawStr[i+j] = new StringBuilder();
                    rawStr[i].append(queryRow(i, visited, tb1));
                    rawStr[i].append(queryRow(j, visited, tb2));
                    rawStr[i].deleteCharAt(rawStr[i].length() - 1);
                }
            }
        }

        return rawStr;
    }

    private String queryRow(int index, HashSet<ColumnName> visited, Table tb) {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < tb.header.colCount(); j++) {
            ColumnName tb1Cn = tb.header.getColumnName(j);
            if (!visited.contains(tb1Cn)) {
                sb.append(tb.getRow(index).getItem(j).getContent())
                  .append(",");
            }
        }

        return sb.toString();
    }

    private List<Pair> getMatchedColumns(List<ColumnName> common, Table tb1, Table tb2) {
        ArrayList<Column> tb1Col = new ArrayList<>();
        ArrayList<Column> tb2Col = new ArrayList<>();
        ArrayList<Pair> pairs = new ArrayList<>();
        int row1Len = tb1.rowCount();
        int row2Len = tb2.rowCount();

        for (ColumnName cn : common) {
            tb1Col.add(tb1.getCol(cn.getColName()));
            tb2Col.add(tb2.getCol(cn.getColName()));
        }

        for (int i = 0; i < row1Len; i++) {
            for (int j = 0; j < row2Len; j++) {
                   boolean match = true;
                for (int k = 0; k < common.size(); k++) {
                    Column c1 = tb1Col.get(k);
                    Column c2 = tb2Col.get(k);
                    Value v1 = c1.getItem(i);
                    Value v2 = c2.getItem(j);

                    if (!v1.equals(v2)) {
                        match = false;
                        break;
                    }
                }
                if (match) pairs.add(new Pair(i, j));
            }
        }

        return pairs;
    }

    private void parseHeader(String headerStr) {
        String[] result = headerStr.split(",");
        String[] colName = new String[result.length];
        Type[] colType = new Type[result.length];

        for (int i = 0; i < result.length; i++) {
            int idx = result[i].indexOf(" ");
            colName[i] = result[i].substring(0, idx);
            colType[i] = Type.conversion(result[i].substring(idx+1, result[i].length()));
        }
        initTable(colName, colType);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(header.toString() + "\n");

        for (Row row : rows) sb.append(row.toString() + "\n");

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        Table tb = new Table("t1.tbl");
        Column c = tb.getCol(0);

    }

}
