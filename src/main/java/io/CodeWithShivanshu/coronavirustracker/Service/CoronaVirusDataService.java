package io.CodeWithShivanshu.coronavirustracker.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.CodeWithShivanshu.coronavirustracker.models.LocationStats;

@Service
public class CoronaVirusDataService {
	
	public static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	public List<LocationStats> allStats=new ArrayList<>();
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchCoronaData() throws IOException, InterruptedException
	{
		List<LocationStats> newStats=new ArrayList<>();
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		HttpResponse<String> httpResponse=client.send(request,HttpResponse.BodyHandlers.ofString());	
		
		String initialString = httpResponse.body();
	    Reader in = new StringReader(initialString);
	    
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationStats locationStats=new LocationStats();
			locationStats.setState(record.get("Province/State"));
			locationStats.setCountry(record.get("Country/Region"));
			
			int firstDayToPresentDayCount=record.size();
			long sumOfCasesFromFirstDayToCurrentDay=0;
			for(int currentDay=4;currentDay<firstDayToPresentDayCount;currentDay++)
			{
				sumOfCasesFromFirstDayToCurrentDay+=Long.parseLong(record.get(currentDay));
			}
			
		    locationStats.setLatestTotalCases(sumOfCasesFromFirstDayToCurrentDay);
		    locationStats.setNewCases(Long.parseLong(record.get(firstDayToPresentDayCount-1)));
		    
//		    System.out.println(locationStats.toString());
		    newStats.add(locationStats);
		}
		this.allStats=newStats;
		in.close();
	}
}
