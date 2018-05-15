package katas;

import katas.pkg.C;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TryReflections {

    private Object c;

    @Override
    public String toString() {
        return new StringBuffer("TryReflections")
                .append("c:").append(c)
                .toString();
    }

    private static URI uri(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] a) {
        var t = new TryReflections();
        var cl = TryReflections.class.getClassLoader();
        var root = uri(cl.getResource("."));
        var packages = Arrays
                .stream(cl.getDefinedPackages())
                .filter(p -> "katas".equals(p.getName()))
                .findFirst().get();
        try {
            var files = Files.walk(Paths.get(cl.getResource(packages.getName()).toURI()))
                    .filter(f -> f.toFile().isFile())
                    .map(f -> root.relativize(f.toUri()).toString());
            var classes = files
                    .map(cl::getResource)
                    .map(r -> {
                        var name = uri(r);
                        var s = name.relativize(root).toString();
                        return name.toString().substring(s.length(), name.toString().indexOf(".class")).replace('/', '.');
                    }).map(c -> {
                        try {
                            return cl.loadClass(c);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());

            t.c = classes.stream().filter(c -> "katas.pkg.C".equals(c.getName())).findFirst().get().getConstructor().newInstance();

            assert t.c instanceof C;

            var f = t.c.getClass().getDeclaredField("str");
            f.setAccessible(true);
            f.set(t.c, "abc");

            System.out.println(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
