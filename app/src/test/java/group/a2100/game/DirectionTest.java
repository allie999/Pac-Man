package group.a2100.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * @author Allie(Yihan) (u6684916)
 * @Date 22/05/2019
 */

public class DirectionTest {
    Direction[] dList = new Direction[]{Direction.UP,Direction.DOWN,Direction.UP,Direction.LEFT,Direction.LEFT,Direction.RIGHT,Direction.STOP};
    Direction[] dOpList = new Direction[]{Direction.DOWN,Direction.UP,Direction.DOWN,Direction.RIGHT,Direction.RIGHT,Direction.LEFT,Direction.STOP};
    @Test
    public void testParallelPerp() {
        for (Direction direction : dList) {
            /* same direction */
            assertEquals(direction.isParallel(direction),true);
            Direction[] preDir = direction.perp();
            /* check differnt direction but not parallel */
            for (Direction directionPre : preDir) {
                assertEquals(direction.isParallel(directionPre),false);

            }
        }
        /* opposite direction (parallel) */
        for (int i = 0; i < dList.length ; i++) {
            assertEquals(dList[i].isParallel(dOpList[i]),true);

            /* check differnt direction but not parallel */
            Direction[] preDir = dOpList[i].perp();
            for (Direction directionPre : preDir) {
                assertEquals(dList[i].isParallel(directionPre),false);

            }
        }

    }
}
