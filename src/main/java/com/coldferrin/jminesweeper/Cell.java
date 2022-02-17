package com.coldferrin.jminesweeper;

public class Cell {
    enum CellState {
        CLOSED,
        OPEN,
        FLAGGED
    }

    CellState cellState = CellState.CLOSED;
    private int surroundingBombs = 0;
    private final boolean isBomb;

    public Cell(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public int getSurroundingBombs() {
        return surroundingBombs;
    }

    public void setSurroundingBombs(int surroundingBombs) {
        this.surroundingBombs = surroundingBombs;
    }
}
