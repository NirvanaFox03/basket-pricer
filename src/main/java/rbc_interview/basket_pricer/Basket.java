package rbc_interview.basket_pricer;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * This represents a basket. A list of available/valid items are needed to make
 * sure all the items added to the basket are valid.<br>
 * It's safe to add items to the basket from multiple threads.<br>
 * <br>
 * COMMENTS FOR INTERVIEW: a potential requirement for this will be the
 * discounts. An extra functional interface can be added to
 * {@link BasketSnapshot#calcCost()} for applying the discount rules.
 * 
 */
class Basket {

	private final FruitList validItems;
	private final ConcurrentHashMap<Fruit, LongAdder> items = new ConcurrentHashMap<>();

	public Basket(FruitList validItems) {
		this.validItems = validItems;
	}

	/**
	 * This represents a snapshot of the basket at a specific time. It prevents
	 * the changes when calculating the cost.<br>
	 * <br>
	 * COMMENTS FOR INTERVIEW: It's also useful to use multiple price (i.e.
	 * discount rules) strategies (finding the optimal combination) without
	 * worrying the underlying basket changes.
	 */
	public static class BasketSnapshot {

		private HashMap<Fruit, Long> items = new HashMap<>();

		private BasketSnapshot(Basket basket) {
			basket.items.forEach((item, count) -> items.put(item, count.longValue()));
		}

		public double calcCost() {
			return items.entrySet().stream()
					.collect(Collectors.summarizingDouble(entry -> entry.getKey().price() * entry.getValue())).getSum();
		}
	}

	public void add(String name, long count) {
		Optional<Fruit> item = validItems.lookup(name);
		checkArgument(item.isPresent(), "invalid item: " + name);
		items.computeIfAbsent(item.get(), key -> new LongAdder()).add(count);
	}

	public BasketSnapshot getSnapshot() {
		return new BasketSnapshot(this);
	}

}
