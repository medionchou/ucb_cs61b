package test;

import db.Expression;
import db.Type;
import db.Value;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by medionchou on 2017/6/11.
 */
public class TestValue {

    @Test
    public void testAddition_int() {
        Value v1 = new Value("35", Type.INTEGER);
        Value v2 = new Value("67", Type.INTEGER);


        Value v3 = v1.evaluate(v2, Expression.Operation.ADD);
        Value v4 = v1.evaluate(v2, Expression.Operation.SUB);
        Value v5 = v1.evaluate(v2, Expression.Operation.MUL);
        Value v6 = v1.evaluate(v2, Expression.Operation.DIV);

        assertEquals(102, v3.intVal());
        assertEquals(-32, v4.intVal());
        assertEquals(2345, v5.intVal());
        assertEquals(0, v6.intVal());

        Value noval = new Value("NOVALUE", Type.INTEGER);;

        Value v7 = noval.evaluate(v1, Expression.Operation.ADD);
        Value v8 = noval.evaluate(v1, Expression.Operation.SUB);
        Value v9 = noval.evaluate(v1, Expression.Operation.MUL);
        Value v10 = noval.evaluate(v1, Expression.Operation.DIV);
        Value v11 = v10.evaluate(noval, Expression.Operation.DIV);
        Value v12 = v7.evaluate(noval, Expression.Operation.DIV);

        assertEquals(35, v7.intVal());
        assertEquals(-35, v8.intVal());
        assertEquals(0, v9.intVal());
        assertEquals(0, v10.intVal());
        assertEquals("NaN", v11.strVal());
        assertEquals("NaN", v12.strVal());

    }

    @Test
    public void testAddition_float() {
        Value v1 = new Value("35.87", Type.FLOAT);
        Value v2 = new Value("7.37", Type.FLOAT);

        Value v3 = v1.evaluate(v2, Expression.Operation.ADD);
        Value v4 = v1.evaluate(v2, Expression.Operation.SUB);
        Value v5 = v1.evaluate(v2, Expression.Operation.MUL);
        Value v6 = v1.evaluate(v2, Expression.Operation.DIV);

        assertEquals(43.24, v3.floatVal(), 0.1);
        assertEquals(28.5, v4.floatVal(), 0.1);
        assertEquals(264.36, v5.floatVal(), 0.1);
        assertEquals(4.867, v6.floatVal(), 0.1);
    }
}
