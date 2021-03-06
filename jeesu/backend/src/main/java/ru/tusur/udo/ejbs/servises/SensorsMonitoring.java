package ru.tusur.udo.ejbs.servises;

import ru.tusur.udo.ejbs.camel.SensorsCamelContext;
import ru.tusur.udo.ejbs.camel.SensorsRoutes;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class SensorsMonitoring {

    @Inject
    private SensorsCamelContext sensorsCamelContext;
    @Inject
    private SensorsRoutes sensorsRoutes;

    @PostConstruct
    public void init() throws Exception {
        sensorsCamelContext.addRoutes(sensorsRoutes);
        sensorsCamelContext.start();
    }

    public void fireSensors(String sensorsJSON) {
        sensorsCamelContext.getApiControllerTemplate()
                .sendBody("seda:apiController", sensorsJSON);
    }

    public String retrieveSnapshot() {
        return sensorsCamelContext.getWsConsumerTemplate()
                .receiveBody("direct://wsController", 1000, String.class);
    }
}
