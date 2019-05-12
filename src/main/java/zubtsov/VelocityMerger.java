package zubtsov;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.File;
import java.io.Writer;
import java.util.Map;

public class VelocityMerger {
    public void merge(String templateFilePath, Map<String, Object> contextObjects, Writer writer) {
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADERS, "file,classpath");
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER_PATHS,
                new File(templateFilePath).getParent());

        Template t = ve.getTemplate(new File(templateFilePath).getName(), "utf-8");

        VelocityContext context = new VelocityContext();
        contextObjects.forEach((str, obj) -> context.put(str, obj));

        t.merge(context, writer);
    }
}
