package group.a2100.game;

import java.util.ArrayList;
import java.util.List;


/**
 * @author
 *     Vikram (u6390710)
 *     Calum (u6044174)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */
public class Map {

    /*
        This class provides functions to generate maps.
        On construction, this class computes the relevant data for a map. That is:
            - The shape of the map
            - The positions of the superdots
            - The starting position of pacman
            - The starting positions of the ghosts.
        It stores these as class variables which are then read by the Game class to
        initialize a map.
     */

    public Tile[][] grid;

    List<Tile> ghostStarts;
    Tile pacStart;
    int dotCount;
    static final int stored_maps = 3;


    public Map(int level) {
        String gridStr;

        switch (level) {
            case 1:
                gridStr = "..........\n" +
                        ".G@@@@@@G.\n" +
                        ".@..@...@.\n" +
                        ".@@@!@@@@.\n" +
                        ".@......@.\n" +
                        "@@......@@\n" +
                        ".@......@.\n" +
                        ".G@@@@@@P.\n" +
                        "..........";
                break;

            case 2:
                gridStr =   ".................\n" +
                            ".G@@@@...........\n" +
                            ".@.@.@...........\n" +
                            ".@@!.@.@G@.......\n" +
                            ".@...@.@.@.......\n" +
                            "@@@@@@@@@@@@@@@@@\n" +
                            "...........@.@.@.\n" +
                            "...........P.!@G.\n" +
                            "...........@.@.@.\n" +
                            "@@@@@@@@@@@@@@@@@\n" +
                            ".@...@.@.@.......\n" +
                            ".@@!.@.@G@.......\n" +
                            ".@.@.@...........\n" +
                            ".G@@@@...........\n" +
                            ".................\n";
                break;

            default:
                gridStr = ".......................\n" +
                        ".@@@@@@@@@@.@@@@@@@@@@.\n" +
                        ".!...@....@.@....@...!.\n" +
                        ".@@@@@@@@@@@@@@@@@@@@@.\n" +
                        ".@...@..@.....@..@...@.\n" +
                        ".@@@@@..G@@.@@G..@@@@@.\n" +
                        ".....@....G.G....@.....\n" +
                        ".....@..@@G@G@@..@.....\n" +
                        "@@@@@@@@@.....@@@@@@@@@\n" +
                        ".....@..@@@@@@@..@.....\n" +
                        ".@@@@@@@@.....@@@@@@@@.\n" +
                        ".@...@..@.....@..@...@.\n" +
                        ".!@..@@@@@@@@@@@@@..@!.\n" +
                        "..@..@.@.......@.@..@..\n" +
                        ".@@@@@.@@@...@@@.@@@@@.\n" +
                        ".@.......@...@.......@.\n" +
                        ".@@@@@@@@@@P@@@@@@@@@@.\n" +
                        ".......................";
                break;
        }
        readStr(gridStr);
    }

    // Takes a map string and extracts the relevant map data into class variables
    // Takes in a square-shaped string made from '.', '@', '!', 'P', 'G', as seen above
    private void readStr(String s) {
        String[] rows = s.split("\\r?\\n");
        int xsize = rows[0].length();
        int ysize = rows.length;

        // The tiles are stored in a 2d array, but the tiles are also
        // linked to their neighbours for convenience and structure (See Game.SpritePos.Step())
        ghostStarts = new ArrayList<>();
        Tile[][] ret = new Tile[xsize][ysize];

        // Initialize tiles
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                if (rows[y].charAt(x) == '.') {
                    ret[x][y] = null;
                } else {
                    // Creates a tile at every character which isn't a '.'
                    dotCount++;
                    ret[x][y] = new Tile(x, y,false);
                    if (rows[y].charAt(x) == 'P') {
                        pacStart = ret[x][y]; // Puts the pacman starting position at the tile marked with P
                    } else if (rows[y].charAt(x) == 'G') {
                        ghostStarts.add(ret[x][y]); // Puts a ghost at every tile marked G
                    } else if (rows[y].charAt(x) == '!') {
                        ret[x][y].dot = Dot.SUPER; // Puts a super dot on every tile marked !
                    }
                }
            }
        }

        // Link every tile to its neighbours
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                // Up
                if (ret[x][y] != null) {
                    // Links to the tile above, or to the tile at the bottom of the board
                    // if we're at the very top
                    if (y - 1 < 0) {
                        ret[x][y].up = ret[x][ysize - 1];
                    } else {
                        ret[x][y].up = ret[x][y - 1];
                    }

                    // Down
                    if (y + 1 >= ysize) {
                        ret[x][y].down = ret[x][0];
                    } else {
                        ret[x][y].down = ret[x][y + 1];
                    }

                    // Left
                    if (x - 1 < 0) {
                        ret[x][y].left = ret[xsize - 1][y];
                    } else {
                        ret[x][y].left = ret[x - 1][y];
                    }

                    // Right
                    if (x + 1 >= xsize) {
                        ret[x][y].right = ret[0][y];
                    } else {
                        ret[x][y].right = ret[x + 1][y];
                    }
                }
            }
        }

        grid = ret;
    }
}
