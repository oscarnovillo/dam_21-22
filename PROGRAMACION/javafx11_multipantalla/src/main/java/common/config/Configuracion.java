package common.config;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
public class Configuracion {

    private String pathDatos;
    private int numeroSuspensos;
    public Configuracion() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream("/config/config.properties"));
            this.pathDatos = p.getProperty("pathDatos");

        } catch (IOException e) {
           log.error(e.getMessage(),e);
        }
    }


}
