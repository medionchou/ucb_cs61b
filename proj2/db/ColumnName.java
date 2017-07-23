package db;

/**
 * Created by medionchou on 2017/7/22.
 */
public class ColumnName {
    private String colName;
    private Type type;

    public ColumnName(String colName, Type type) {
        this.colName = colName;
        this.type = type;
    }

    public String getColName() {
        return colName;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return colName + " " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnName that = (ColumnName) o;

        if (colName != null ? !colName.equals(that.colName) : that.colName != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = colName != null ? colName.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
