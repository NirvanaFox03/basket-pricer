package rbc_interview.basket_pricer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggerFactory.class, App.class })
public class AppTest {

	/*
	 * The importance of logging is often ignored - most time are handled
	 * poorly. However, it's very helpful no matter when doing the
	 * implementation or in the future maintenance (rather than forcing people
	 * debug the code). So to make sure the error/warning messages are logged
	 * properly (developers are quite easily ignore them when re-factoring),
	 * it's important to verify the logger behaviour as well. The same policy
	 * applies to all the unit tests in this project.
	 */
	private static final Logger logger = spy(LoggerFactory.getLogger(App.class));

	@Before
	public void setUp() {
		reset(logger);
		mockStatic(LoggerFactory.class);
		when(LoggerFactory.getLogger(App.class)).thenReturn(logger);
	}

	@Test
	public void run_without_error() throws IOException {
		BufferedReader input = new BufferedReader(
				new StringReader("Banana 3;Orange 1;Apple 2" + System.lineSeparator() + "Banana 1;Orange 2;Apple 3"));
		StringWriter output = new StringWriter();

		App.calcBasketCost(input, output);
		assertThat(output.toString(),
				equalTo("total price: " + (1.0 * 4 + 2.0 * 3 + 3.0 * 5) + System.lineSeparator()));
	}

	@Test
	public void run_with_error() throws IOException {
		BufferedReader input = new BufferedReader(new StringReader("banana 3;orange 1;apple 2"));
		StringWriter output = new StringWriter();

		App.calcBasketCost(input, output);
		verify(logger).error(anyString(), any(IllegalArgumentException.class));
	}

}
