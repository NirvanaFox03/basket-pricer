package rbc_interview.basket_pricer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FruitTest {

	@Test
	public void fruits_with_same_names_are_equal() {
		Fruit sut1 = new Fruit("Apple", 1.0);
		Fruit sut2 = new Fruit("Apple", 2.0);

		assertThat(sut1, equalTo(sut2));
		assertThat(sut1.hashCode(), equalTo(sut2.hashCode()));
	}

	@Test
	public void fruits_with_different_names_are_not_equal() {
		Fruit sut1 = new Fruit("Apple", 1.0);
		Fruit sut2 = new Fruit("apple", 1.0);

		assertThat(sut1, not(equalTo(sut2)));
		assertThat(sut1.hashCode(), not(equalTo(sut2.hashCode())));
	}

	@Test
	public void fruit_is_not_comparable_with_other_object() {
		assertFalse(new Fruit("Peach", 1.0).equals("Peach"));
	}

	@Test
	public void fruit_is_not_comparable_with_null() {
		assertFalse(new Fruit("Banana", 1.0).equals(null));
	}

}
