package rbc_interview.basket_pricer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This represents a list contains {name:info} pairs.<br>
 * It is constructed from a string representation of the list and also build the
 * info objects for the specific type (using the specific supplier).<br>
 * <br>
 * COMMENTS FOR INTERVIEW: A "home-made" parser using regex is usually a good
 * start point for a prototype - quick & simple, but not enough for the real
 * production. For example, the names here can only be word characters
 * ([a-zA-Z_0-9]), which is quite limited. For more flexibility, JSON message is
 * recommended.<br>
 * The map is constructed completely inside the constructor and each add task is
 * not heavy-load, so there is no need to use the concurrent map.
 *
 * @param <T>
 *            the type for the info object
 */
class NameListWithInfo<T> {

	@FunctionalInterface
	public interface InfoSupplier<T> {
		public T supply(String name, String newValue, Optional<T> oldValue);
	}

	private static final Pattern OBJECT_REGEX = Pattern.compile("(?<name>\\w+) (?<info>.+)");

	private HashMap<String, T> objects = new HashMap<>();
	private final InfoSupplier<T> infoSupplier;

	public NameListWithInfo(String list, InfoSupplier<T> infoSupplier) {
		this.infoSupplier = infoSupplier;
		Arrays.stream(list.split(";")).forEach(object -> add(object));
	}

	private void add(String description) {
		Matcher matcher = OBJECT_REGEX.matcher(description);
		if (!matcher.matches()) {
			throw new RuntimeException("invalid description: " + description);
		}

		String name = matcher.group("name");
		String info = matcher.group("info");
		objects.put(name, infoSupplier.supply(name, info, Optional.ofNullable(objects.get(name))));
	}

	Map<String, T> objects() {
		return Collections.unmodifiableMap(objects);
	}

}
