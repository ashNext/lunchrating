package lunchrating.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static class SystemClock extends Clock {

        @Override
        public ZoneId getZone() {
            return Clock.systemUTC().getZone();
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            return Clock.fixed(Instant.now(), zoneId);
        }

        @Override
        public Instant instant() {
            return Instant.now();
        }
    }

    public static class MockClock extends Clock {

        @Override
        public ZoneId getZone() {
            return Clock.systemUTC().getZone();
        }

        @Override
        public Clock withZone(ZoneId zoneId) {
            return Clock.fixed(Instant.now(), zoneId);
        }

        private String time;

        @Override
        public Instant instant() {
            return time == null ? Instant.now() : Instant.parse(time);
        }

        public void setTime(String time) {
            this.time = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + 'T' + time + 'Z';
        }
    }
}
