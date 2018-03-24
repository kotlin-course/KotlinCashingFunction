package test

import cashedFunction.LongCache
import org.testng.Assert.*
import org.testng.annotations.Test

/**
 * Created by kasper on 16/06/2017.
 */
class CacheTest {
    @Test fun nothingThere() {
        val c = LongCache()
        assertNull(c.get(10L))
    }
    @Test fun inAndOut() {
        val c = LongCache()
        c.set(10L, 200L)
        assertEquals(200L, c.get(10L))
    }
    @Test fun override() {
        val c = LongCache()
        c.set(10L, 200L)
        c.set(10L, 300L)
        assertEquals(300L, c.get(10L))
    }
    @Test fun collision() {
        val c = LongCache(1)
        c.set(10L, 200L)
        c.set(20L, 400L)
        assertNull(c.get(10L))
        assertEquals(400L, c.get(20L))
    }
}