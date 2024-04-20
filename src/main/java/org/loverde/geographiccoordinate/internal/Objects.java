package org.loverde.geographiccoordinate.internal;

import java.util.function.Supplier;


public class Objects {

    /**
     * Shorthand for IF statements that throw IllegalArgumentException
     * @param isFailed The result of the check
     * @param iaeMessage Exception message
     */
    public static void failIf(final boolean isFailed, final Supplier<String> iaeMessage) {
        if (isFailed) {
            throw new IllegalArgumentException(iaeMessage.get());
        }
    }

    /**
     * Shorthand for IF statements that throw IllegalArgumentException
     * @param isFailed The result of the check
     * @param iaeMessage Exception message
     */
    public static void failIf(final boolean isFailed, final String iaeMessage) {
        if (isFailed) {
            throw new IllegalArgumentException(iaeMessage);
        }
    }
}
