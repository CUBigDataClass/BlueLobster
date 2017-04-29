package storm;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

/**
 * @author Amit Kumar
 */
public class LoggerBolt extends BaseBasicBolt{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(LoggerBolt.class);

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		LOG.info(input.getStringByField("state"));
		LOG.info(input.getIntegerByField("sentiment"));
		
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}
}