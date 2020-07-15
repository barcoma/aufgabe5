package de.hfu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class QueueTest {

    private int[] testQ1;
    private int[] testQ2;
    private int[] result1;
    private int[] result2;
    private int[] result3;

    private Queue testQueue;

    public QueueTest(int[] testQ1, int[] testQ2, int[] result1, int[] result2, int[] expectedResult3) {
        this.testQ1 = testQ1;
        this.testQ2 = testQ2;
        this.result1 = result1;
        this.result2 = result2;
        this.result3 = expectedResult3;
    }

    @Parameterized.Parameters
    public static Collection daten() {
        return Arrays.asList(new int[][][] {
                {{1, 2, 3}, {1, 4, 5, 4}, {1, 2, 3}, {1, 4, 4}, {1, 2, 3}}
        });
    }

    @Before
    public void createQueue() throws Exception {
        testQueue = new Queue(testQ1.length);
    }

    @Test(expected = IllegalArgumentException.class, timeout = 1000)
    public void testEmptyQueue() throws Exception {
        Queue q = new Queue(0);
    }

    @Test
    public void testQueue() throws Exception {
        Assert.assertArrayEquals(new int[testQ1.length], testQueue.queue);
    }

    @Test
    public void testEnqueue() throws Exception {
        for (int v : testQ1) {
            testQueue.enqueue(v);
        }
        Assert.assertArrayEquals(result1, testQueue.queue);
    }

    @Test
    public void testEnqueueFullQueue() throws Exception {
        Queue q = new Queue(testQ2.length - 1);
        for (int v : testQ2) {
            q.enqueue(v);
        }
        Assert.assertArrayEquals(result2, q.queue);
    }

    @Test(expected = IllegalStateException.class, timeout = 1000)
    public void testDequeueInEmptyQueue() throws Exception {
        Queue q = new Queue(1);
        q.dequeue();
    }

    @Test
    public void testDequeueReturnValue() throws Exception {
        for (int v : testQ1) {
            testQueue.enqueue(v);
        }
        Assert.assertEquals(result3[0], testQueue.dequeue());
    }

} 
