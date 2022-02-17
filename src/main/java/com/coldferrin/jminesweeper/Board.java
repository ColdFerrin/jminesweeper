package com.coldferrin.jminesweeper;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

public class Board {
    enum BoardState {
        PLAYING,
        WON,
        LOST
    }

    record BombPosition(int xPos, int yPos){}

    private BoardState boardState = BoardState.PLAYING;
    private Cell[][] board;
    private final int xSize;
    private final int ySize;
    private final int numBombs;

    private final Comparator<BombPosition> bombPositionComparator = (o1, o2) -> {
        if (o1.xPos > o2.xPos){
            return 1;
        } else if (o1.xPos < o2.xPos){
            return -1;
        } else {
            return Integer.compare(o1.yPos, o2.yPos);
        }
    };

    TreeSet<BombPosition> bombPositions = new TreeSet<>(bombPositionComparator);
    TreeSet<BombPosition> foundBombs = new TreeSet<>(bombPositionComparator);

    public Board(int xSize, int ySize, int numBombs) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.numBombs = numBombs;
    }

    BoardState doAction(int xPos, int yPos, Cell.CellState newCellState){
        if(board == null){
            initializeBoard(xPos, yPos);
        }

        return BoardState.PLAYING;
    }

    private void initializeBoard(int xPos, int yPos){
        board = new Cell[xSize][ySize];

        Random random = new Random();

        while (bombPositions.size() < numBombs){
            int x = random.nextInt(0,xSize);
            int y = random.nextInt(0,ySize);

            if (x != xPos || y != yPos){
                bombPositions.add(new BombPosition(x,y));
                board[x][y] = new Cell(true);
            }
        }

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (board[x][y] == null){
                    board[x][y] = new Cell(false);
                }
            }
        }

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                int count = 0;
                if (x > 0 && y > 0 && x < xSize - 1 && y < ySize - 1){
                    count += (board[x-1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x-1][y].isBomb()) ? 1 : 0;
                    count += (board[x-1][y+1].isBomb()) ? 1 : 0;
                    count += (board[x][y-1].isBomb()) ? 1 : 0;
                    count += (board[x][y+1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y].isBomb()) ? 1 : 0;
                    count += (board[x+1][y+1].isBomb()) ? 1 : 0;
                } else if (x == 0 && y == 0) {
                    count += (board[x][y+1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y].isBomb()) ? 1 : 0;
                    count += (board[x+1][y+1].isBomb()) ? 1 : 0;
                } else if (x == 0 && y < ySize - 1) {
                    count += (board[x][y-1].isBomb()) ? 1 : 0;
                    count += (board[x][y+1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y].isBomb()) ? 1 : 0;
                    count += (board[x+1][y+1].isBomb()) ? 1 : 0;
                } else if (x == 0){
                    count += (board[x][y-1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y].isBomb()) ? 1 : 0;
                } else if (x < xSize - 1 && y == 0) {
                    count += (board[x-1][y].isBomb()) ? 1 : 0;
                    count += (board[x-1][y+1].isBomb()) ? 1 : 0;
                    count += (board[x][y+1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y].isBomb()) ? 1 : 0;
                    count += (board[x+1][y+1].isBomb()) ? 1 : 0;
                } else if (x < xSize - 1 && y == ySize - 1){
                    count += (board[x-1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x-1][y].isBomb()) ? 1 : 0;
                    count += (board[x][y-1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x+1][y].isBomb()) ? 1 : 0;
                } else if (x == xSize - 1 && y == 0) {
                    count += (board[x-1][y].isBomb()) ? 1 : 0;
                    count += (board[x-1][y+1].isBomb()) ? 1 : 0;
                    count += (board[x][y+1].isBomb()) ? 1 : 0;
                } else if (x == xSize - 1 && y < ySize - 1){
                    count += (board[x-1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x-1][y].isBomb()) ? 1 : 0;
                    count += (board[x-1][y+1].isBomb()) ? 1 : 0;
                    count += (board[x][y-1].isBomb()) ? 1 : 0;
                    count += (board[x][y+1].isBomb()) ? 1 : 0;
                } else if (x == xSize - 1 && y == ySize - 1) {
                    count += (board[x-1][y-1].isBomb()) ? 1 : 0;
                    count += (board[x-1][y].isBomb()) ? 1 : 0;
                    count += (board[x][y-1].isBomb()) ? 1 : 0;
                }
                board[x][y].setSurroundingBombs(count);
            }
        }
    }
}
