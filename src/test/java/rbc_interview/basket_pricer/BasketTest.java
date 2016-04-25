package rbc_interview.basket_pricer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rbc_interview.basket_pricer.Basket.BasketSnapshot;

public class BasketTest {

	private static FruitList fruitList = new FruitList("Banana 1.0;Orange 2.0;Apple 3.0;Lemon 4.0;Peach 5.0");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void calc_cost_without_repeat_fruit() {
		Basket basket = new Basket(fruitList);
		basket.add("Banana", 3);
		basket.add("Orange", 1);
		basket.add("Apple", 2);

		assertThat(basket.getSnapshot().calcCost(), equalTo(1.0 * 3 + 2.0 * 1 + 3.0 * 2));
	}

	@Test
	public void calc_cost_with_repeat_fruit() {
		Basket basket = new Basket(fruitList);
		basket.add("Banana", 3);
		basket.add("Orange", 1);
		basket.add("Banana", 2);

		assertThat(basket.getSnapshot().calcCost(), equalTo(1.0 * 5 + 2.0 * 1));
	}

	@Test
	public void invalid_fruit() {
		String fruit = "Grape";
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(fruit);

		new Basket(fruitList).add(fruit, 1);
	}

	@Test
	public void snapshot_unchanged_when_basket_changed() {
		Basket basket = new Basket(fruitList);
		basket.add("Banana", 3);
		basket.add("Orange", 1);
		BasketSnapshot snapshot = basket.getSnapshot();
		basket.add("Apple", 2);

		assertThat(snapshot.calcCost(), equalTo(1.0 * 3 + 2.0 * 1));
	}

}
