package org.refactoringminer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.refactoringminer.api.RefactoringType;

public class RefFinderResultReader {

    private static Map<String, Function<List<String>, RefactoringRelationship>> mappers = initMappings();
    
    public static RefactoringSet read(String project, String revision, String folderPath) {
        try {
            RefactoringSet result = new RefactoringSet(project, revision);
            for (RefactoringRelationship r : readFolder(folderPath)) {
                result.add(r);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<RefactoringRelationship> readFolder(String path) throws Exception {
        List<RefactoringRelationship> result = new ArrayList<>();
        File folder = new File(path);
        for (File f : folder.listFiles()) {
            if (f.isFile()) {
                readXml(f.getPath(), result);
            }
        }
        return result;
    }

    public static void readXml(String path, List<RefactoringRelationship> result) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    RefactoringRelationship r = parse(line);
                    if (r != null) {
                        result.add(r);
                    }
                }
            }
        }
    }

    private static RefactoringRelationship parse(String line) {
        int openPar = line.indexOf('(');
        String type = line.substring(0, openPar);
        String args = line.substring(openPar + 2, line.length() - 2);
        Function<List<String>, RefactoringRelationship> mapper = mappers.get(type);
        //rename_method("org.springframework.boot.bind%.RelaxedDataBinder#setIgnoreNestedProperties()","org.springframework.boot.bind%.RelaxedDataBinder#setIgnoreNestedPropertiesRenamed()","org.springframework.boot.bind%.RelaxedDataBinder")
        if (mapper != null) {
            List<String> argList = Arrays.asList(args.split("\",\""));
            return mapper.apply(argList);
        }
        return null;
    }

    private static Map<String, Function<List<String>, RefactoringRelationship>> initMappings() {
        Map<String, Function<List<String>, RefactoringRelationship>> mappers = new HashMap<>();
		RefactoringType type1 = RefactoringType.EXTRACT_SUPERCLASS;
		EntityParser parserBefore = type(1);
		EntityParser parserAfter = type(2);
        mappers.put("extract_superclass", args -> new RefactoringRelationship(type1, parserBefore.parse(args), parserAfter.parse(args)));
		RefactoringType type2 = RefactoringType.EXTRACT_INTERFACE;
		EntityParser parserBefore1 = type(2);
		EntityParser parserAfter1 = type(1);
        mappers.put("extract_interface", args -> new RefactoringRelationship(type2, parserBefore1.parse(args), parserAfter1.parse(args)));
		RefactoringType type3 = RefactoringType.RENAME_METHOD;
		EntityParser parserBefore2 = member(1);
		EntityParser parserAfter2 = member(2);
        mappers.put("rename_method", args -> new RefactoringRelationship(type3, parserBefore2.parse(args), parserAfter2.parse(args)));
		RefactoringType type4 = RefactoringType.MOVE_OPERATION;
		EntityParser parserBefore3 = member(2, 1);
		EntityParser parserAfter3 = member(3, 1);
        mappers.put("move_method", args -> new RefactoringRelationship(type4, parserBefore3.parse(args), parserAfter3.parse(args)));
		RefactoringType type5 = RefactoringType.PUSH_DOWN_OPERATION;
		EntityParser parserBefore4 = member(2, 1);
		EntityParser parserAfter4 = member(3, 1);
        mappers.put("push_down_method", args -> new RefactoringRelationship(type5, parserBefore4.parse(args), parserAfter4.parse(args)));
		RefactoringType type6 = RefactoringType.PULL_UP_OPERATION;
		EntityParser parserBefore5 = member(2, 1);
		EntityParser parserAfter5 = member(3, 1);
        mappers.put("pull_up_method", args -> new RefactoringRelationship(type6, parserBefore5.parse(args), parserAfter5.parse(args)));
		RefactoringType type7 = RefactoringType.EXTRACT_OPERATION;
		EntityParser parserBefore6 = member(1);
		EntityParser parserAfter6 = member(2);
        mappers.put("extract_method", args -> new RefactoringRelationship(type7, parserBefore6.parse(args), parserAfter6.parse(args)));
		RefactoringType type8 = RefactoringType.INLINE_OPERATION;
		EntityParser parserBefore7 = member(2);
		EntityParser parserAfter7 = member(1);
        mappers.put("inline_method", args -> new RefactoringRelationship(type8, parserBefore7.parse(args), parserAfter7.parse(args)));
		RefactoringType type9 = RefactoringType.MOVE_ATTRIBUTE;
		EntityParser parserBefore8 = member(2, 1);
		EntityParser parserAfter8 = member(3, 1);
        mappers.put("move_field", args -> new RefactoringRelationship(type9, parserBefore8.parse(args), parserAfter8.parse(args)));
		RefactoringType type10 = RefactoringType.PUSH_DOWN_ATTRIBUTE;
		EntityParser parserBefore9 = member(2, 1);
		EntityParser parserAfter9 = member(3, 1);
        mappers.put("push_down_field", args -> new RefactoringRelationship(type10, parserBefore9.parse(args), parserAfter9.parse(args)));
		RefactoringType type11 = RefactoringType.PULL_UP_ATTRIBUTE;
		EntityParser parserBefore10 = member(2, 1);
		EntityParser parserAfter10 = member(3, 1);
        mappers.put("pull_up_field", args -> new RefactoringRelationship(type11, parserBefore10.parse(args), parserAfter10.parse(args)));
        
        return mappers;
    }
    
    private static EntityParser type(final int i) {
        return new EntityParser() {
            @Override
            String parse(List<String> args) {
                return normalize(args.get(i - 1));
            }
        };
    }
    
    private static EntityParser member(final int i, final int j) {
        return new EntityParser() {
            @Override
            String parse(List<String> args) {
                return normalize(args.get(i - 1)) + "#" + normalize(args.get(j - 1));
            }
        };
    }
    
    private static EntityParser member(final int i) {
        return new EntityParser() {
            @Override
            String parse(List<String> args) {
                return normalize(args.get(i - 1));
            }
        };
    }

    private static String normalize(String entity) {
        return entity.replace("%.", ".").replace('#', '.');
    }
}

abstract class EntityParser {
    abstract String parse(List<String> args);
}










