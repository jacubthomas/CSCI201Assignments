package jharring_CSCI201_Assignment3;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Stopwatch {
	public static Instant begin;
	public static Instant elapsed;
	
	// Prints pretty-formatted time in HH:MM:SS.ms
	public static void timeElapsed() {
		Instant insta = Instant.now();
		Duration time = Duration.between(begin, insta);
		
		long millis = time.toMillis();
		
		final long hours = TimeUnit.MILLISECONDS.toHours(millis);

		final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));

		final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

		final long ms = TimeUnit.MILLISECONDS.toMillis(millis)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis));
		
		System.out.print("[");
		System.out.print(String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, ms));
		System.out.print("] ");
	}
	public static void timeElapsed(PrintWriter pw) {
		Instant insta = Instant.now();
		Duration time = Duration.between(begin, insta);
		
		long millis = time.toMillis();
		
		final long hours = TimeUnit.MILLISECONDS.toHours(millis);

		final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));

		final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

		final long ms = TimeUnit.MILLISECONDS.toMillis(millis)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis));
		
		pw.print("[");
		pw.print(String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, ms));
		pw.print("] ");
		pw.flush();
	}
}
