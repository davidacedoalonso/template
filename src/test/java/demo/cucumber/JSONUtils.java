package demo.cucumber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class JSONUtils {
//    public static String objectToJson(Object object) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object).replaceAll("\"null\"", "null");
//        } catch (JsonProcessingException e) {
//            log.error(e);
//            return null;
//        }
//    }

    public static String getFixtureFromFile(String path) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(path).getInputStream(), Charset.defaultCharset());
    }
}
