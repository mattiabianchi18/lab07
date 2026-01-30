package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        Month(final int days) {
            this.days = days;
        }

        public int getDays() {
            return days;
        }

        public static Month fromString(final String s) {
            Objects.requireNonNull(s, "Month string cannot be null");
            final String normalized = s.trim().toUpperCase(Locale.ROOT);

            Month found = null;
            for (final Month m : Month.values()) {
                if (m.name().equals(normalized)) {
                    if (found != null) {
                        throw new IllegalArgumentException("Ambiguous month string: " + s);
                    }
                    found = m;
                }
            }

            if (found == null) {
                throw new IllegalArgumentException("No month matches:" + s);
            }
            return found;
        }

    }

    private static class sortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String a, final String b) {
            return Month.fromString(a).compareTo(Month.fromString(b));
        }
    }
    
    private static class sortByDate implements Comparator<String> {
        @Override
        public int compare(final String a, final String b) {
            return Integer.compare(Month.fromString(a).getDays(), Month.fromString(b).getDays());
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new sortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new sortByMonthOrder();
    }
}
