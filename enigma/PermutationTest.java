package enigma;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static enigma.TestUtils.*;
import static org.junit.Assert.assertEquals;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Anastasia Sukhorebraya
 */
public class PermutationTest {

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /**
     * Check that perm has an alphabet whose size is that of
     * FROMALPHA and TOALPHA and that maps each character of
     * FROMALPHA to the corresponding character of FROMALPHA, and
     * vice-versa. TESTID is used in error messages.
     */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
        System.out.println();
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
        System.out.println();
    }

    @Test
    public void permutations() {
        String cycles = "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)";
        Permutation perm2 = new Permutation(cycles, UPPER);
        String upper = UPPER_STRING;

        assertEquals(upper.charAt(10), perm2.permute(upper.charAt(1)));
        assertEquals(upper.charAt(0), perm2.permute(upper.charAt(20)));
        assertEquals(upper.charAt(18), perm2.permute(upper.charAt(18)));
        assertEquals(upper.charAt(4), perm2.permute(upper.charAt(0)));
        System.out.println();
    }

    @Test
    public void inverse() {
        String cycles = "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)";
        Permutation perm2 = new Permutation(cycles, UPPER);
        String uS = UPPER_STRING;

        assertEquals(uS.charAt(1), perm2.invert(uS.charAt(10)));
        assertEquals(uS.charAt(20), perm2.invert(uS.charAt(0)));
        assertEquals(uS.charAt(18), perm2.invert(uS.charAt(18)));
        assertEquals(uS.charAt(0), perm2.invert(uS.charAt(4)));
        System.out.println();
    }

    @Test
    public void testInvertChar() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) (JC)",
                new CharacterRange('A', 'Z'));
        assertEquals(p.invert('B'), 'A');
        assertEquals(p.invert('G'), 'G');
        assertEquals(p.invert('A'), 'W');
        assertEquals(p.invert('P'), 'H');
        System.out.println();
    }

    @Test
    public void testPermuteChar() {
        String cycles = "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)";
        Permutation perm2 = new Permutation(cycles, UPPER);
        String upper = UPPER_STRING;

        assertEquals(upper.charAt(10), perm2.permute(upper.charAt(1)));
        assertEquals(upper.charAt(0), perm2.permute(upper.charAt(20)));
        assertEquals(upper.charAt(18), perm2.permute(upper.charAt(18)));
        assertEquals(upper.charAt(4), perm2.permute(upper.charAt(0)));
        System.out.println();
    }

    @Test
    public void testDerangement() {
        String keyboard = "(QWERTYUIOP) (ASDFGHJKL) (ZXCVBNM)";
        Permutation p = new Permutation(keyboard, UPPER);
        assertEquals(true, p.derangement());

        String cycles = "(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)";
        Permutation f = new Permutation(cycles, UPPER);
        assertEquals(false, f.derangement());
        System.out.println();
    }
}
