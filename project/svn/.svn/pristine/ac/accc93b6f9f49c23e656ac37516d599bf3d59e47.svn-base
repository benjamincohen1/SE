/**
    @author Jack Lawrence
*/

/** Encapsulates a row and a column. */
public class CoordinatePair {
    public int row;
    public int column;
    
    public CoordinatePair(int aRow, int aColumn) {
        row = aRow;
        column = aColumn;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other.getClass().isInstance(this)) {
            CoordinatePair pairObject = (CoordinatePair)other;
            return (pairObject.row == row && pairObject.column == column);
        }
        return false;
    }
    
    @Override
     public int hashCode() {
        return row*1000+column; // shift row over a bunch to separate it from column.
     }
}
