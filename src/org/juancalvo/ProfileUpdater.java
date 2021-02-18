package org.juancalvo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ProfileUpdater {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("***************************************************************************");
            System.out.println("");
            System.out.println("*  USAGE: ");
            System.out.println("* java -jar msi-profile-template.jar pathToTemplates pathToProfiles *****");
            System.out.println("");
            System.out.println("***************************************************************************");
        }

        String templatesPath = args[0];
        String profilesPath = args[1];

        Map<String, File> templates = findTemplates(templatesPath);

        templates.forEach((name, file) ->
        {
            try {
                findAndReplaceTemplates(profilesPath, name, file);
            } catch (IOException e) {
                System.err.println("Error finding profiles: " + e.getMessage());
            }
        });
    }

    private static void findAndReplaceTemplates(String profilesPath, String prefixName, File template) throws IOException {
        Stream<Path> pathStream = Files.find(Paths.get(profilesPath), 1,
                (p, m) -> p.getFileName().toString().startsWith(prefixName));

        pathStream.forEach(path -> {
            try {
                Files.copy(template.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(String.format("File %s replaced with template %s", path.getFileName(), prefixName));
            } catch (IOException e) {
                System.err.println("Error copying profiles: " + e.getMessage());
            }
        });
    }

    private static Map<String, File> findTemplates(String templatesPath) throws IOException {
        File basePath = new File(templatesPath);
        Stream<Path> list = Files.list(basePath.toPath());

        return list
                .filter(path -> path.toString().endsWith("template"))
                .map(Path::toFile)
                .collect(toMap(file -> file
                                .getName()
                                .substring(0, file.getName()
                                        .lastIndexOf("."))
                        , Function.identity()));
    }
}
