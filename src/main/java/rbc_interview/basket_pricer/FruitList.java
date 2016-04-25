package rbc_interview.basket_pricer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * This represents all the available/valid fruits in the system.<br>
 * <br>
 * COMMENTS FOR INTERVIEW: just like {@link Fruit}, it can represent the list of
 * any products - only renaming is needed.
 */
class FruitList extends NameListWithInfo<Fruit> {

	private static final Logger logger = LoggerFactory.getLogger(FruitList.class);

	public FruitList(String list) {
		super(list, FruitList::supply);
	}

	private static Fruit supply(String name, String newPrice, Optional<Fruit> oldValue) {
		double price = Double.parseDouble(newPrice);
		checkArgument(price >= 0, "price cannot be negative: " + newPrice);

		oldValue.ifPresent(
				fruit -> logger.warn(fruit.name() + " already exists, the existing price will be overwritten"));

		return new Fruit(name, price);
	}

	public Optional<Fruit> lookup(String name) {
		return Optional.ofNullable(objects().get(name));
	}

}
