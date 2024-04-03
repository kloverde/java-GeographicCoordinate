package org.loverde.geographiccoordinate.internal;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class EnumHelperTest {

    @Test
    void populateEnumMap_whenKeyExtractorProvidesUniqueValues_thenReturnFullyPopulatedMap() {
        final Map<Integer, GoodEnum> map = EnumHelper.populateEnumMap(GoodEnum.class, GoodEnum::getVal);

        assertEquals(GoodEnum.values().length, map.size());

        for (var pair : map.entrySet()) {
            assertEquals(pair.getKey(), map.get(pair.getKey()).getVal());
        }
    }

    @Test
    void populateEnumMap_whenKeyExtractorDoesNotProvideUniqueValues_thenThrowException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> EnumHelper.populateEnumMap(BadEnum.class, BadEnum::getVal));
        assertEquals("The specified key extractor does not provide unique values", e.getMessage());
    }

    private enum GoodEnum {
        VALUE_A(1),
        VALUE_B(2);

        private final int val;

        GoodEnum(int val) {
            this.val = val;
        }

        int getVal() {
            return val;
        }
    }

    private enum BadEnum {
        VALUE_A(1),
        VALUE_B(1);

        private final int val;

        BadEnum(int val) {
            this.val = val;
        }

        int getVal() {
            return val;
        }
    }

}
