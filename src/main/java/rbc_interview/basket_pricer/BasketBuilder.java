package rbc_interview.basket_pricer;

import java.util.Optional;

/**
 * This represents a list of items (specified by the name and the quantity) to
 * be added to a {@link Basket}.<br>
 * <br>
 * COMMENTS FOR INTERVIEW: It's not a builder actually build a basket in one go,
 * instead it adds items to a pre-existing basket. Therefore it's useful when
 * the amount of items are huge or not all the items of the basket can be
 * decided at once. It also reduces the memory footprint (the raw data of one
 * batch can be discarded once all the items are added to the basket).
 */
class BasketBuilder extends NameListWithInfo<Integer> {

	public BasketBuilder(String list) {
		super(list, BasketBuilder::supply);
	}

	private static Integer supply(String name, String newQuantity, Optional<Integer> oldValue) {
		return (oldValue.isPresent() ? oldValue.get() : 0) + Integer.parseUnsignedInt(newQuantity);
	}

	public void addToBasket(Basket basket) {
		objects().forEach((item, quantity) -> basket.add(item, quantity));
	}

}
