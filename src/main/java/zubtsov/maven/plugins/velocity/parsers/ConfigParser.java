package zubtsov.maven.plugins.velocity.parsers;

import java.io.File;
import java.util.Map;

public interface ConfigParser {
    public Map<String, Object> parse(File file);
}
