package test

import cashedFunction.LongCache
import org.testng.Assert.*
import org.testng.annotations.Test

/**
 * Created by kasper on 16/06/2017.
 */
class CacheTest {
    @Test fun nothingThere() {
        var c = LongCache()
        assertNull(c.get(10L))
    }
    @Test fun inAndOut() {
        var c = LongCache()
        c.set(10L, 200L)
        assertEquals(200L, c.get(10L))
    }
    @Test fun override() {
        var c = LongCache()
        c.set(10L, 200L)
        c.set(10L, 300L)
        assertEquals(300L, c.get(10L))
    }
    @Test fun collision() {
        var c = LongCache(1)
        c.set(10L, 200L)
        c.set(20L, 400L)
        assertNull(c.get(10L))
        assertEquals(400L, c.get(20L))
    }
}