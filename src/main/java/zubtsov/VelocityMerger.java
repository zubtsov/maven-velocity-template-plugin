package zubtsov;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.Writer;
import java.util.Map;

public class VelocityMerger {
    public void merge(String templateFileName, Map<String, Object> contextObjects, Writer writer) {
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        Template t = ve.getTemplate(templateFileName);

        VelocityContext context = new VelocityContext();
        contextObjects.forEach((str, obj) -> context.put(str, obj));

        t.merge(context, writer);
    }
}
