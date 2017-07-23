package test;


import db.Column;
import db.Table;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by medionchou on 2017/5/27.
 */
public class TestTable {

    @Test
    public void testGetColumn() throws Exception {
        Table table = new Table("examples/t1.tbl");

        Column col = table.getCol(0);
        Column col1 = table.getCol("x");
        Column col2 = table.getCol("y");


        assertEquals(col1, col);
        assertEquals("5,3,7", col2.toString());
    }

    @Test
    public void testAddRow_string() throws Exception {
        Table table = new Table("examples/fans.tbl");
        int size = table.rowCount();
        String testStr = "'BUG','GUH','DIE'";
        StringBuilder sb = new StringBuilder(table.toString());

        sb.append(testStr + "\n");
        table.addRow(testStr);

        assertEquals(size + 1, table.rowCount());
        assertEquals(sb.toString(), table.toString());

    }

    @Test
    public void testAddRow_int() throws Exception {
        Table table = new Table("examples/t1.tbl");
        int size = table.rowCount();
        String testStr = "1,3";
        StringBuilder sb = new StringBuilder(table.toString());

        sb.append(testStr + "\n");
        table.addRow(testStr);

        assertEquals(size + 1, table.rowCount());
        assertEquals(sb.toString(), table.toString());
    }

    @Test
    public void testAddRow_float() throws Exception {
        Table table = new Table("examples/t3.tbl");
        int size = table.rowCount();
        String testStr = "+8.7,-3.06";
        StringBuilder sb = new StringBuilder(table.toString());

        sb.append(testStr + "\n");

        table.addRow(testStr);

        assertEquals(size + 1, table.rowCount());
        assertEquals(sb.toString(), table.toString());
    }
}
