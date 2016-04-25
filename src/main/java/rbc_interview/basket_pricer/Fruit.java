package rbc_interview.basket_pricer;

/**
 * This represents a fruit and its corresponding price. the equality & hash code
 * is solely decided by the name (case sensitive).<br>
 * <br>
 * COMMENTS FOR INTERVIEW: The requirements only mention fruits, but it can
 * actually represent any products and renaming the class is the only thing
 * needed for generalisation.
 */
class Fruit {

	private final String name;

	private final double price;

	public Fruit(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String name() {
		return name;
	}

	public double price() {
		return price;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Fruit ? name().equals(((Fruit) obj).name()) : false;
	};

	@Override
	public int hashCode() {
		return name().hashCode();
	};

}