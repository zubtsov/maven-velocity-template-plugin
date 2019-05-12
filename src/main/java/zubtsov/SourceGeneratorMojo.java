package zubtsov;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import zubtsov.maven.plugins.velocity.parsers.ConfigParser;
import zubtsov.maven.plugins.velocity.parsers.JsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//todo: use IoC + DI?
@Mojo(name = "generate-one-template-many-configs", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class SourceGeneratorMojo extends AbstractMojo {

    @Parameter(defaultValue = "([\\w\\W]*)\\.json")
    private String configFileNamesRegex;
    @Parameter(defaultValue = "$1.generated")
    private String generatedFileNamesRegex;
    @Parameter(defaultValue = "template.vm")
    private String templateFileName;
    @Parameter(required = true)
    private String rootConfigsFolder;
    @Parameter(required = true)
    private String rootGeneratedSourcesFolder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        VelocityMerger vm = new VelocityMerger();
        ConfigParser jsonParser = new JsonParser();

        try {
            Files.walk(Paths.get(rootConfigsFolder))
                    .filter(Files::isRegularFile)
                    .filter(path -> path
                            .toFile()
                            .getName()
                            .matches(configFileNamesRegex)
                    )
                    .forEach(path -> {
                        File configFile = path.toFile();

                        String generatedFileName = configFile.getName()
                                .replaceAll(configFileNamesRegex,
                                        generatedFileNamesRegex);

                        File outputFile = Paths.get(rootGeneratedSourcesFolder, generatedFileName).toFile();
                        if (outputFile.exists()) {
                            outputFile.delete();
                        }

                        File outputDir = outputFile.getParentFile();
                        if (!outputDir.exists()) {
                            outputFile.mkdirs(); //todo: check output and throw exception
                        }

                        try (FileWriter writer = new FileWriter(outputFile)) {
                            vm.merge(templateFileName,
                                    jsonParser.parse(configFile),
                                    writer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace(); //todo: use logging
        }
    }
}
