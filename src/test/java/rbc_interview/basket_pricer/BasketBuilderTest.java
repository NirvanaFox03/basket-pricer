package rbc_interview.basket_pricer;

import static org.mockito.Mockito.verify;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasketBuilderTest {

	@Mock
	private Basket basket;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void valid_list_with_repeat_items() {
		new BasketBuilder("banana 3;apple 4;peach 5;banana 6").addToBasket(basket);

		verify(basket).add("banana", 9);
		verify(basket).add("apple", 4);
		verify(basket).add("peach", 5);
	}

	@Test
	public void invalid_list_with_negative_quantity() {
		thrown.expect(NumberFormatException.class);
		thrown.expectMessage("-3");

		new BasketBuilder("banana -3");
	}

	@Test
	public void invalid_list_without_parsable_quantity() {
		thrown.expect(NumberFormatException.class);
		thrown.expectMessage("invalid");

		new BasketBuilder("banana invalid");
	}

}
