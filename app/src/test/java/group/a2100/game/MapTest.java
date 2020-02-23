package group.a2100.game;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Vikram (u6390710)
 * @Date 23/05/2019
 */

public class MapTest {

    private final static Tile[] PACMAN_POS
            = new Tile[]{ // Level 1 starting position for Pacman
            new Tile(8, 7, false),

            // Level 2 starting position for Pacman
            new Tile(11, 7, false),

            // Level 3 starting position for Pacman
            new Tile(11, 16, false)
    };

    private final static Tile[][] GHOSTS_POS
            = new Tile[][]{

            // Level 1 starting positions for ghosts
            new Tile[]{new Tile(1, 1, false),
                    new Tile(8, 1, false),
                    new Tile(1, 7, false)
            },

            // Level 2 starting positions for ghosts
            new Tile[]{new Tile(1, 1, false),
                    new Tile(8, 3, false),
                    new Tile(15, 7, false),
                    new Tile(8, 11, false),
                    new Tile(1, 13, false)
            },

            // Level 3 starting positions for ghosts
            new Tile[]{new Tile(8, 5, false),
                    new Tile(14, 5, false),
                    new Tile(10, 6, false),
                    new Tile(12, 6, false),
                    new Tile(10, 7, false),
                    new Tile(12, 7, false)
            }
    };

    /* Below are the black box tests for MapTest. */

    @Test
    public void testPacPosBlackBox() {

        /* We don't know exactly where Pacman should start, but we expect that
        Pacman at least has a starting position. */

        for (int i = 1; i <= 3; i++) {

            // Create a game for each level, and test that it contains Pacman.
            Game game = new Game(i);
            boolean test = containsPacPos(game);
            Assert.assertTrue("Expected Map to contain Pacman, but didn't",
                    test);
        }
    }

    // Determines if Pacman is in the map.

    private boolean containsPacPos(Game game) {
        Tile pac = game.m.pacStart;
        for (Tile[] tiles : game.m.grid) {
            for (Tile tile : tiles) {
                if (tile != null && pac.xc == tile.xc && pac.yc == tile.yc) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    public void testGhostPosBlackBox() {

        /* We don't know exactly where the ghosts will be, but we know how many
        are in the level, and so can test if they are all there. */

        for (int i = 1; i <= 3; i++) {

            /* Create a game for each level, and tests that it contains all of
            the ghosts. */

            Game game = new Game(i);
            boolean test = containsGhost(game);
            Assert.assertTrue("Expected Map to contain ghosts, but didn't",
                    test);
        }

    }

    // Determines if each ghost is on the map

    private boolean containsGhost(Game game) {
        List<Tile> ghosts = game.m.ghostStarts;
        for (Tile[] tiles : game.m.grid) {
            for (Tile tile : tiles) {
                if (tile != null) {
                    for (Tile ghost : ghosts) {
                        if (ghost.xc == tile.xc && ghost.yc == tile.yc) {

                            /* Sets to a superdot if ghost exists, to avoid
                            a concurrent modification exception. */

                            ghost.dot = Dot.SUPER;
                        }
                    }
                }
            }
        }
        for (Tile ghost : ghosts) {
            if (ghost.dot != Dot.SUPER) {
                return false;
            }
        }
        return true;
    }

    /* Below are the white box tests for MapTest. */

    @Test
    public void testPacPosWhiteBox() {

        /* Now that we know the exact starting position of Pacman, we test to
        see that Pacman is indeed in that position. */

        for (int i = 0; i < 3; i++) {
            Game game = new Game(i + 1);
            testPacman(game, PACMAN_POS[i]);
        }
    }

    // Determines if Pacman is in the expected starting position.

    private void testPacman(Game game, Tile exp) {
        Tile start = game.m.pacStart;
        Assert.assertTrue("Expected Pacman to start at" +
                          // expected starting position
                          "(" + exp.xc + ", " + exp.yc + ") but got" +
                          // actual starting position
                          "(" + start.xc + ", " + start.yc + ")",
                          (start.xc == exp.xc && start.yc == exp.yc));
    }

    @Test
    public void testGhostPosWhiteBox() {

        for (int i = 0; i < 3; i++) {
            Game game = new Game(i + 1);
            for (int j = 0; j < GHOSTS_POS[i].length; j ++) {
                Tile exp = GHOSTS_POS[i][j];
                Tile start = game.m.ghostStarts.get(j);
                Assert.assertTrue("Expected ghost to start at" +
                                "(" + exp.xc + ", " + exp.yc + ") but got" +
                                "(" + start.xc + ", " + start.yc + ")",
                        (start.xc == exp.xc && start.yc == exp.yc));
            }
        }

    }
}
