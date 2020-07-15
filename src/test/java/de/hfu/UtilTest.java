package de.hfu;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class UtilTest {
    private int erstesHJ, zweitesHJ, ungueltig;

    public UtilTest(int erstesHJ, int zweitesHJ, int ungueltig) {
        this.erstesHJ = erstesHJ;
        this.zweitesHJ = zweitesHJ;
        this.ungueltig = ungueltig;
    }

    @Parameterized.Parameters
    public static Collection<Integer[]> daten() {
        return Arrays.asList(new Integer[][]{
                {1, 11, 0}, {4, 9, 14}, {7, 12, -1}
        });
    }

    @Test
    public void testErstesHalbjahrMitErstem() {
        assertTrue(Util.istErstesHalbjahr(erstesHJ));
    }

    @Test
    public void testErsteHalbjahrMitZweitem(){
        assertFalse(Util.istErstesHalbjahr(zweitesHJ));
    }

    @Test(expected=IllegalArgumentException.class, timeout=1000)
    public void testFalscherMonat() {
        Util.istErstesHalbjahr(ungueltig);
    }
}