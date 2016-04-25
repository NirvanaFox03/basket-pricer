package rbc_interview.basket_pricer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, FruitList.class })
public class FruitListTest {

	private static final Logger logger = spy(LoggerFactory.getLogger(FruitList.class));

	@Before
	public void setUp() {
		reset(logger);
		mockStatic(LoggerFactory.class);
		when(LoggerFactory.getLogger(FruitList.class)).thenReturn(logger);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void valid_list_with_repeat_items() {
		FruitList fruitList = new FruitList("banana 1.0;apple 2.0;peach 3.0;banana 4.0");

		assertThat(fruitList.lookup("banana").get().price(), equalTo(4.0));
		assertThat(fruitList.lookup("apple").get().price(), equalTo(2.0));
		assertThat(fruitList.lookup("peach").get().price(), equalTo(3.0));
		verify(logger).warn(contains("banana"));
	}

	@Test
	public void invalid_list_with_negative_price() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("-3");

		new FruitList("banana -3");
	}

	@Test
	public void invalid_list_without_parsable_quantity() {
		thrown.expect(NumberFormatException.class);
		thrown.expectMessage("invalid");

		new BasketBuilder("banana invalid");
	}

}
