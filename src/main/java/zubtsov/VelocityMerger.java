package zubtsov;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.Writer;
import java.util.Map;
import java.util.Properties;

public class VelocityMerger {
    public void merge(String templateFilePath, Map<String, Object> contextObjects, Writer writer) {
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        p.put(Velocity.FILE_RESOURCE_LOADER_PATH, "");
        ve.init(p);

        Template t = ve.getTemplate(templateFilePath, "UTF-8");

        VelocityContext context = new VelocityContext();
        contextObjects.forEach((str, obj) -> context.put(str, obj));

        t.merge(context, writer);
    }
}
