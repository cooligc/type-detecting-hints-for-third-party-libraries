package com.example.library.hints;

import com.example.library.shapes.Shape;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
abstract public class ShapeCollector {

    public static Set<Class<?>> collect(List<String> packages) {


        var newPackages = new HashSet<String>();
        newPackages.addAll(packages);
        newPackages.add(Shape.class.getPackageName());

        var accumulate = new HashSet<Class<?>>();
        accumulate.add(Shape.class);
        for (var p : newPackages) {
            var shapes = gatherShapesIn(p);
            accumulate.addAll(shapes);
        }
        log.info("found " + accumulate);
        return accumulate;
    }

    private static Set<Class<?  >> gatherShapesIn(String pkg) {
        log.info("gathering in package " + pkg);
        var reflections = new Reflections(pkg);
        // here, Im using reflection to find all the types of a certain interface.
        // Another possibility would be to find the types if they have a certain annotation on them. eg:
        //
        var annotated = reflections.getTypesAnnotatedWith(com.example.library.Shape.class); // were finding types by common parent
        var subs = reflections.getSubTypesOf(Shape.class); // we're finding types by common annotation
        var hs = new HashSet<Class<? >>();
        hs.addAll(subs);
        hs.addAll(annotated) ;
        return hs;
    }
}
