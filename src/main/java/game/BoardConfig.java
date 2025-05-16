package game;

import java.util.Map;

public class BoardConfig {
    private int boardSize;
    private Map<Integer, Integer> tileConfigs;

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Map<Integer, Integer> getTileConfigs() {
        return tileConfigs;
    }

    public void setTileConfigs(Map<Integer, Integer> tileConfigs) {
        this.tileConfigs = tileConfigs;
    }
}

