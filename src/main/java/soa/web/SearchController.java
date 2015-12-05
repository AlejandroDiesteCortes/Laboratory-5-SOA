package soa.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

	@Autowired
	private ProducerTemplate producerTemplate;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/search")
	@ResponseBody
	public Object search(@RequestParam("q") String q) {
		Map<String, Object> map = new HashMap<String, Object>();
		String kws="";
		String []parsed=q.split(" ");
		for(int i=0;i<parsed.length;i++){
			if(parsed[i].contains("max:")){
				String []max=parsed[i].split(":");
				map.put("CamelTwitterCount", max[1]);
			}
			else{
				kws+=parsed[i]+" ";
			}
		}
		map.put("CamelTwitterKeywords", kws);
		return producerTemplate.requestBodyAndHeaders("direct:search", "", map);
	}
}