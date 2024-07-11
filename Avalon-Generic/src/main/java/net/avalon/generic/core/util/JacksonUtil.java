//School of Informatics Xiamen University, GPL-3.0 license
package net.avalon.generic.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JacksonUtil {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private static final ObjectMapper om = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());


    public static <T> T toObj(String data, Class<T> clazz) {

        try {
            return om.readValue(data, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJson(Object data) {

        try {
            return om.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseString(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asText();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static List<String> parseStringList(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null) {
                return om.convertValue(leaf, new TypeReference<List<String>>() {
                });
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> List<T> parseObjectList(String body, String field, Class<T> clazz) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static Integer parseInteger(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asInt();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Integer> parseIntegerList(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null) {
                return om.convertValue(leaf, new TypeReference<List<Integer>>() {
                });
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static Boolean parseBoolean(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asBoolean();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static Short parseShort(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                Integer value = leaf.asInt();
                return value.shortValue();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static Byte parseByte(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                Integer value = leaf.asInt();
                return value.byteValue();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T parseObject(String body, String field, Class<T> clazz) {

        JsonNode node;
        try {
            node = om.readTree(body);
            node = node.get(field);
            return om.treeToValue(node, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static Object toNode(String json) {
        if (json == null) {
            return null;
        }

        try {

            return om.readTree(json);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public static Map<String, String> toMap(String data) {

        try {
            return om.readValue(data, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static <T> List<T> parseObjectList(String body, Class<T> clazz) {


        JsonNode node = (JsonNode) toNode(body);
        if (node != null) {
            return om.convertValue(node, new TypeReference<List<T>>() {
            });
        }
        return null;
    }

    public static List<String> parseSubnodeToStringList(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.at(field);

            if (leaf != null) {
                List<JsonNode> retObj = om.convertValue(leaf, new TypeReference<List<JsonNode>>() {
                });
                List<String> ret = new ArrayList<>(retObj.size());
                for (JsonNode item : retObj) {
                    ret.add(item.toString());
                }
                return ret;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String parseSubnodeToString(String body, String field) {

        JsonNode node;
        try {
            node = om.readTree(body);
            JsonNode leaf = node.at(field);
            if (leaf != null) {
                return leaf.toString();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}