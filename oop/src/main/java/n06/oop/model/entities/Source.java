package n06.oop.model.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Source {
	
	final static String DATE_FORMAT = "dd-MM-yyyy";
	final static DateTimeFormatter D_FORMAT = DateTimeFormatter.ofPattern(DATE_FORMAT);
	final static List<String> sourceLinks;

	static {
		List<String> links = new ArrayList<>();
		try {
			InputStream inputStream = Source.class.getResourceAsStream("/data/sources.txt");
			InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(streamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				links.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sourceLinks = links;
	}

	private String id;
	private String link;
	private LocalDate date;

	public Source() {
		int rand = ThreadLocalRandom.current().nextInt(0, sourceLinks.size());
		setLink(sourceLinks.get(rand));
		setDate(generateDate(2016,2018));
	}

	public Source(String value) {
		String[] s = value.split("|");
		LocalDate date = LocalDate.parse(s[0], D_FORMAT);
		setLink(s[1]);
		setDate(date);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return date.format(D_FORMAT) + "|" + link;
	}

	public LocalDate generateDate(int minYear, int maxYear) {
		long minDay = LocalDate.of(minYear, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(maxYear, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

		return LocalDate.ofEpochDay(randomDay);
	}
	
}
