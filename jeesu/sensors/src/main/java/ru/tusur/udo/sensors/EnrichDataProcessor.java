package ru.tusur.udo.sensors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tusur.udo.sensors.interfaces.Sensor;

import java.util.List;

@Component
public class EnrichDataProcessor implements Processor {
    @Value("${node.name}")
    private String nodeName;

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Sensor> sensors = exchange.getIn().getBody(List.class);

        exchange.getIn().setBody(new SensorsJsonSchema() {
            @Override
            public String getNode() {
                return nodeName;
            }

            @Override
            public List<Sensor> getSensors() {
                return sensors;
            }
        });
    }
}

