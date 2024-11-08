package classes;

public class GameTable {
    private Card[][] table;
    private int rows, columns;

    public GameTable(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
        this.table = new Card[rows][columns];
    }

    /**
     * Returns the current state of the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the current state of the table
     */
    public Card[][] getTable() {
        return table;
    }

    /**
     * Sets the state of the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param table the new state of the table
     */
    public void setTable(final Card[][] table) {
        this.table = table;
    }

    /**
     * Returns the number of rows in the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows in the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param rows the new number of rows
     */
    public void setRows(final int rows) {
        this.rows = rows;
    }

    /**
     * Returns the number of columns in the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns in the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param columns the new number of columns
     */
    public void setColumns(final int columns) {
        this.columns = columns;
    }

    public boolean isRowFull(final int playerIndex) {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (playerIndex == 0) {
                    if (table[2][j] == null || table[3][j] == null) {
                        return false;
                    }
                } else {
                    if (table[0][j] == null || table[1][j] == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Places a card on the table at the specified row.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param card the card to place
     * @param rowIndex the index of the row to place the card in
     */
    public void placeCard(final Card card, final  int rowIndex) {
        for (int i = 0; i < columns; i++) {
            if (table[rowIndex][i] == null) {
                table[rowIndex][i] = card;
                break;
            }
        }
    }

    /**
     * Returns the card at the specified position in the table.
     * Subclasses should override this method to provide additional behavior.
     *
     * @param row the row index of the card
     * @param columnIndex the column index of the card
     * @return the card at the specified position
     */
    public Card getCard(final int row, final int columnIndex) {
        return table[row][columnIndex];
    }
}
