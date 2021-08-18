package com.senacor.tecco.MessageReadService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.tecco.MessageReadService.models.IdentifierLookup;
import com.senacor.tecco.MessageReadService.models.TrackingHistory;
import com.senacor.tecco.MessageReadService.models.TrackingHistoryShort;
import com.senacor.tecco.MessageReadService.services.TrackingService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
class MessageReadServiceApplicationTests {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	TrackingService trackingService;

	@Value("classpath:TrackingHistoryValid.json")
	File trackingHistoryValid;

	@Value("classpath:TrackingHistoryShortValid.json")
	File trackingHistoryShortValid;

	@Value("classpath:IdentifierLookupValid.json")
	File identifierLookupValid;


	@Test
	void contextLoads() {
	}

	@Test
	public void formatValidTrackingHistoryTest() throws Exception{
		TrackingHistory trackingHistory = mapper.readValue(
				Files.readString(trackingHistoryValid.toPath()), TrackingHistory.class
		);

		TrackingHistoryShort generatedTrackingHistoryShort = trackingService.createTrackingHistoryShort(trackingHistory);

		TrackingHistoryShort comparisonTrackingHistoryShort = mapper.readValue(
				Files.readString(trackingHistoryShortValid.toPath()), TrackingHistoryShort.class
		);

		System.out.println(Files.readString(trackingHistoryShortValid.toPath()));
		System.out.println(comparisonTrackingHistoryShort);

		assertThat(generatedTrackingHistoryShort).isEqualTo(comparisonTrackingHistoryShort);
	}

	@Test
	public void embeddedMongoDbIntegrationTest(@Autowired MongoTemplate mongoTemplate) throws Exception{

		TrackingHistory trackingHistory = mapper.readValue(
				Files.readString(trackingHistoryValid.toPath()), TrackingHistory.class
		);
		IdentifierLookup identifierLookup = mapper.readValue(
				Files.readString(identifierLookupValid.toPath()), IdentifierLookup.class
		);
		TrackingHistoryShort comparisonTrackingHistoryShort = mapper.readValue(
				Files.readString(trackingHistoryShortValid.toPath()), TrackingHistoryShort.class
		);

		//System.out.println(Files.readString(trackingHistoryValid.toPath()));
		mongoTemplate.save(trackingHistory);
		//System.out.println(Files.readString(identifierLookupValid.toPath()));
		mongoTemplate.save(identifierLookup);

		String trackingNumber = trackingHistory.getIdentifiers().stream().iterator().next();

		TrackingHistoryShort generatedTrackingHistoryShort = trackingService.getShortTrackingHistoryByTrackingNumber(
						trackingNumber);
		assertThat(generatedTrackingHistoryShort).isEqualTo(comparisonTrackingHistoryShort);
	}
}
