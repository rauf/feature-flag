package in.rauf.flagger.service.impl;

import in.rauf.flagger.model.DistributionContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistributionServiceImplTest {


    @Test
    void checkRollout() {
        var context2 = new DistributionContext(List.of("v1", "v2"), List.of(4000, 7000));

        assertTrue(DistributionServiceImpl.checkRollout(context2, 100, 0, 0));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 75, 0, 0));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 50, 0, 0));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 25, 0, 0));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 1, 0, 0));

        assertTrue(DistributionServiceImpl.checkRollout(context2, 50, 1000, 0));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 50, 1999, 0));
        assertFalse(DistributionServiceImpl.checkRollout(context2, 50, 2000, 0));
        assertFalse(DistributionServiceImpl.checkRollout(context2, 50, 3999, 0));
        assertFalse(DistributionServiceImpl.checkRollout(context2, 50, 4000, 0));

        assertTrue(DistributionServiceImpl.checkRollout(context2, 50, 4000, 1));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 50, 5499, 1));
        assertFalse(DistributionServiceImpl.checkRollout(context2, 50, 5500, 1));
        assertFalse(DistributionServiceImpl.checkRollout(context2, 50, 6999, 1));


        assertFalse(DistributionServiceImpl.checkRollout(context2, 0, 4000, 1));
        assertTrue(DistributionServiceImpl.checkRollout(context2, 1000, 4000, 1));
    }

    @Test
    void getVariantIndex() {
        var emptyContext = new DistributionContext(List.of(), List.of());
        var context1 = new DistributionContext(List.of("v1"), List.of(500));
        var context2 = new DistributionContext(List.of("v1", "v2"), List.of(500, 7000));

        assertEquals(0, DistributionServiceImpl.getVariantIndex(emptyContext, 400));
        assertEquals(0, DistributionServiceImpl.getVariantIndex(context1, 400));
        assertEquals(1, DistributionServiceImpl.getVariantIndex(context1, 700));
        assertEquals(0, DistributionServiceImpl.getVariantIndex(context2, 400));
        assertEquals(1, DistributionServiceImpl.getVariantIndex(context2, 600));
        assertEquals(2, DistributionServiceImpl.getVariantIndex(context2, 8000));
    }
}