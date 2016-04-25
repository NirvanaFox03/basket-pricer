package rbc_interview.basket_pricer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the entry point of the program.<br>
 * <br>
 * COMMENTS FOR INTERVIEW: for demo purpose, the input is from console and the
 * output is also the console. However, the main logic is in a separate method
 * which receives a buffered reader as input and a writer as output - this will
 * cover most of the real scenarios.
 */
public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);
	static FruitList fruitList = new FruitList("Banana 1.0;Orange 2.0;Apple 3.0;Lemon 4.0;Peach 5.0");

	public static void main(String[] args) {
		try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				Writer output = new PrintWriter(System.out)) {
			calcBasketCost(input, output);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

	static void calcBasketCost(BufferedReader input, Writer output) {
		try {
			Basket basket = new Basket(fruitList);
			input.lines().forEach(line -> new BasketBuilder(line).addToBasket(basket));
			output.write("total price: " + basket.getSnapshot().calcCost() + System.lineSeparator());
		} catch (Throwable e) {
			logger.error("an error occurred when calculating the cost: " + e.getMessage(), e);
		}
	}

}
