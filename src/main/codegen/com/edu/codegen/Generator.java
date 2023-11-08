package com.edu.codegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Generator {

    private static final Path JAVA_FILE_OUTPUT = Paths.get("src/main/java/com/edu/app/generated/Greeter.java");
    private static final Path JAVA_TEMPLATE_FILE = Paths.get("src/main/resources/templates/Greeter.java.tmpl");

    private static final Path TAG_JSON_FILE = Paths.get("src/main/resources/tags.json");

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] tagBytes = Files.readAllBytes(TAG_JSON_FILE);
        Tag[] tags = mapper.readValue(tagBytes, Tag[].class);
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(String.format("\t@JsonProperty(\"%s\")\n\tprivate %s %s = %s;\n\n",
                    tag.maprColumn, tag.variableType, tag.variableName, tag.variableDefaultValue));
        }
        writeJavaFile(generateClass(sb.toString()));
    }

    private static void writeJavaFile(String content) {
        try {
            Files.write(JAVA_FILE_OUTPUT, content.getBytes());
        } catch (IOException e) {
            log.error("Error writing java file", e);
            throw new RuntimeException(e);
        }
    }

    private static String generateClass(String partialCode) {
        try {
            String content = new String(Files.readAllBytes(JAVA_TEMPLATE_FILE));
            return content.replace("$attributes", partialCode);
        } catch (Exception e) {
            log.error("Error reading template", e);
            throw new RuntimeException(e);
        }
    }

    @Data
    static class Tag {
        String maprColumn;
        String variableName;
        String variableType;
        String variableDefaultValue;
    }

//    {
//        "maprColumn": "tags.xxx",
//            "variableName": "powerGenerated",
//            "variableType": "Double",
//            "variableDefaultValue": "0.0d"
//    },

}