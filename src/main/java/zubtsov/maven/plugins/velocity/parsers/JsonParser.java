package zubtsov.maven.plugins.velocity.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import zubtsov.maven.plugins.velocity.exceptions.ConfigParsingException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonParser implements ConfigParser {
    ObjectMapper mapper = new ObjectMapper();

    public Map<String, Object> parse(File file) {
        try {
            Map map = mapper.readValue(file, Map.class);
            map.put("configFileName", file.getName());
            return map;
        } catch (IOException e) {
            throw new ConfigParsingException(e);
        }
    }
}
