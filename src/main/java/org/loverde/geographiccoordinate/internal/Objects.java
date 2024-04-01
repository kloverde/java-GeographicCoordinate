package org.loverde.geographiccoordinate.internal;

import java.util.function.Supplier;


public class Objects {

    public static void failIf(boolean condition, Supplier<String> iaeMessage) {
        if (!condition) {
            throw new IllegalArgumentException(iaeMessage.get());
        }
    }
}
