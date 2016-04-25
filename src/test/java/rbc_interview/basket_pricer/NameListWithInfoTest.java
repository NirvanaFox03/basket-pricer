package rbc_interview.basket_pricer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NameListWithInfoTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void valid_list_contains_one_object_only() {
		Map<String, String> sut = new NameListWithInfo<>("item1 desc1", this::infoSupplier).objects();

		assertThat(sut.size(), equalTo(1));
		assertThat(sut.get("item1"), equalTo("desc1"));
	}

	@Test
	public void valid_list_without_repeat_objects() {
		Map<String, String> sut = new NameListWithInfo<>("item1 desc1;item2 desc2;item3 desc3;Item1 desc1",
				this::infoSupplier).objects();

		assertThat(sut.size(), equalTo(4));
		assertThat(sut.get("item1"), equalTo("desc1"));
		assertThat(sut.get("item2"), equalTo("desc2"));
		assertThat(sut.get("item3"), equalTo("desc3"));
		assertThat(sut.get("Item1"), equalTo("desc1"));
	}

	@Test
	public void valid_list_with_repeat_objects() {
		Map<String, String> sut = new NameListWithInfo<>("item1 desc1;item2 desc2;item1 desc3", this::infoSupplier)
				.objects();

		assertThat(sut.size(), equalTo(2));
		assertThat(sut.get("item1"), equalTo("desc1 desc3"));
		assertThat(sut.get("item2"), equalTo("desc2"));
	}

	@Test
	public void invalid_desc() {
		String list = "item1desc1";
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("invalid description: " + list);

		new NameListWithInfo<>(list, this::infoSupplier);
	}

	private String infoSupplier(String name, String newValue, Optional<String> oldValue) {
		return (oldValue.isPresent() ? oldValue.get() + " " : "") + newValue;
	}
}
