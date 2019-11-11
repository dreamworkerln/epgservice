package epg.server.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringConfiguration {

    //public static final String MAIN_ENTITIES_PATH = "smarttv.entities";

    @Bean
    @Scope("singleton")
    //ObjectMapper is threadsafe
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());  // allow convertation to/from Instant

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);


        //mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);


        //mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);


        //mapper.configure(JsonWriteFeature.WRITE_NAN_AS_STRINGS)

        //mapper.enable(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS);
        


        //mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
        //ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

        //mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,true);
        //mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,true);

        //mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    @Bean
    @Scope("singleton")
    // ModelMapper is threadsafe, and, finally, by loading all Mappers beans
    // we will be available to convert whole graphs of different types (supported by Mappers classes)
    public ModelMapper modelMapper() {

        ModelMapper result = new ModelMapper();


        // https://github.com/modelmapper/modelmapper/issues/212#issuecomment-293493836
        //
        // It enables field matching, set the access level as private,
        // and the source naming convention as Mutator to avoid multiple matches with getters.
//        result.getConfiguration().setFieldMatchingEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
//                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);

        result.getConfiguration().setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PROTECTED);

        return result;
    }


    // ----------------------------------------------------------

    public static class Controller {

        public static class Handlers {

            public static class Epg {

                public static final String PROGRAM_GUIDE = "epgservice.epg.full";
            }

        }
    }

}


