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
    public void testArithmeticOp_int() {
        Value v1 = new Value("35", Type.INTEGER);
        Value v2 = new Value("67", Type.INTEGER);


        Value v_102 = v1.evaluate(v2, Expression.Operation.ADD);
        Value v_n32 = v1.evaluate(v2, Expression.Operation.SUB);
        Value v_2345 = v1.evaluate(v2, Expression.Operation.MUL);
        Value v_0 = v1.evaluate(v2, Expression.Operation.DIV);

        assertEquals(102, v_102.value());
        assertEquals(-32, v_n32.value());
        assertEquals(2345, v_2345.value());
        assertEquals(0, v_0.value());

        Value noval = new Value("NOVALUE", Type.INTEGER);
        Value nan = new Value("NaN", Type.INTEGER);

        Value v_35 = noval.evaluate(v1, Expression.Operation.ADD);
        Value v_n35 = noval.evaluate(v1, Expression.Operation.SUB);
        Value v_0_p1 = noval.evaluate(v1, Expression.Operation.MUL);
        Value v_0_p2 = noval.evaluate(v1, Expression.Operation.DIV);
        Value v_NaN = v_0.evaluate(noval, Expression.Operation.DIV);
        Value v_NaN_p1 = noval.evaluate(noval, Expression.Operation.DIV);
        Value v_NaN_p2 = nan.evaluate(v1, Expression.Operation.ADD);
        Value v_NaN_p3 = nan.evaluate(v1, Expression.Operation.SUB);
        Value v_NaN_p4 = nan.evaluate(v1, Expression.Operation.MUL);
        Value v_NaN_p5 = nan.evaluate(v1, Expression.Operation.DIV);

        assertEquals(35, v_35.value());
        assertEquals(-35, v_n35.value());
        assertEquals(0, v_0_p1.value());
        assertEquals(0, v_0_p2.value());
        assertEquals("NaN", v_NaN.value());
        assertEquals("NaN", v_NaN_p1.value());
        assertEquals("NaN", v_NaN_p2.value());
        assertEquals("NaN", v_NaN_p3.value());
        assertEquals("NaN", v_NaN_p4.value());
        assertEquals("NaN", v_NaN_p5.value());
    }

    @Test
    public void testArithmeticOp_float() {
        Value v1 = new Value("35.87", Type.FLOAT);
        Value v2 = new Value("7.37", Type.FLOAT);

        Value v_43d24 = v1.evaluate(v2, Expression.Operation.ADD);
        Value v_28d5 = v1.evaluate(v2, Expression.Operation.SUB);
        Value v_264d36 = v1.evaluate(v2, Expression.Operation.MUL);
        Value v_4d867 = v1.evaluate(v2, Expression.Operation.DIV);

        assertEquals(43.24f, v_43d24.floatVal(), 0.1);
        assertEquals(28.5f, v_28d5.floatVal(), 0.1);
        assertEquals(264.36f, v_264d36.floatVal(), 0.1);
        assertEquals(4.867f, v_4d867.floatVal(), 0.1);

        Value v_noval = new Value("NOVALUE", Type.FLOAT);
        Value v_35d87 = v1.evaluate(v_noval, Expression.Operation.ADD);
        Value v_n35d87 = v_noval.evaluate(v1, Expression.Operation.SUB);
        Value v_0 = v1.evaluate(v_noval, Expression.Operation.MUL);
        Value v_NaN = v1.evaluate(v_noval, Expression.Operation.DIV);

        assertEquals(35.87f, v_35d87.floatVal(), 0.1);
        assertEquals(-35.87f, v_n35d87.floatVal(), 0.1);
        assertEquals(0f, v_0.floatVal(), 0.1);
        assertEquals("NaN", v_NaN.value());

        v_NaN = new Value("NaN", Type.FLOAT);
        Value v_Nan_p1 = v_NaN.evaluate(v1, Expression.Operation.ADD);
        Value v_Nan_p2 = v_NaN.evaluate(v1, Expression.Operation.SUB);
        Value v_Nan_p3 = v_NaN.evaluate(v1, Expression.Operation.MUL);
        Value v_Nan_p4 = v_NaN.evaluate(v1, Expression.Operation.DIV);

        assertEquals("NaN", v_Nan_p1.value());
        assertEquals("NaN", v_Nan_p2.value());
        assertEquals("NaN", v_Nan_p3.value());
        assertEquals("NaN", v_Nan_p4.value());
    }

    @Test
    public void testArithmeticOp_string() {
        Value str1 = new Value("'Dog'", Type.STRING);
        Value str2 = new Value("' bites'", Type.STRING);
        Value noval = new Value("NOVALUE", Type.STRING);

        Value dog_bites = str1.evaluate(str2, Expression.Operation.ADD);
        Value dog = str1.evaluate(noval, Expression.Operation.ADD);
        Value v_NaN = str1.evaluate(new Value("NaN", Type.STRING), Expression.Operation.ADD);

        assertEquals("'Dog bites'", dog_bites.value());
        assertEquals("'Dog'", dog.value());
        assertEquals("NaN", v_NaN.value());
    }
}
