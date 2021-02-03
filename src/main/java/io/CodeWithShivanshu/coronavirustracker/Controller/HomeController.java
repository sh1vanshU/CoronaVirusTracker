package io.CodeWithShivanshu.coronavirustracker.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.CodeWithShivanshu.coronavirustracker.Service.CoronaVirusDataService;
import io.CodeWithShivanshu.coronavirustracker.models.LocationStats;


/*The job of @Controller is to create a Map of the model object and find a view but 
@RestController simply returns the object and object 
data is directly written into HTTP response as JSON or XML.
@Controller @ResponseBody public class MVCController { .. your logic } @RestController public class RestFulController { .... your logic }
*/
@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataService coronaVirusService;
	
	@GetMapping("/")
	public String home(Model model)
	{
		
		List<LocationStats> locations=coronaVirusService.getAllStats();
//		long totalReportedCases=0;
//		for(LocationStats location:locations)
//		{
//			totalReportedCases+=location.getLatestTotalCases();
//		}
		long totalReportedCases=locations.stream().mapToLong(location->location.getLatestTotalCases()).sum();
		model.addAttribute("locationStats", coronaVirusService.getAllStats());
		model.addAttribute("totalReportedCases",totalReportedCases);
		return "home";
	}
}
