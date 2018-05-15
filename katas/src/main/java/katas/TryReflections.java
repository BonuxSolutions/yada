package katas;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@A
class C {
    private String a;
}

public class TryReflections {

    private Object c;

    public static void main(String[] a) {
        var cl = TryReflections.class.getClassLoader();
        var packages = Arrays
                .stream(cl.getDefinedPackages())
                .filter(p -> "katas".equals(p.getName()))
                .findFirst().get();
        try {
            var files = Files.list(Paths.get(cl.getResource(packages.getName()).toURI()))
                    .map(f -> packages.getName() + "/" + f.getFileName().toString());
            var classes = files.map(cl::getResource).map(r -> {
                try {
                    var t = r.toURI().relativize(cl.getResource(".").toURI()).toString();
                    return r.toURI().toString().substring(t.length());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
            classes.forEach(System.out::println);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
