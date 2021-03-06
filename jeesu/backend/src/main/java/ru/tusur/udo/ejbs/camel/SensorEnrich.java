package ru.tusur.udo.ejbs.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.tusur.udo.ejbs.dto.EnrichedSensor;
import ru.tusur.udo.ejbs.dto.EnrichedSensorNode;
import ru.tusur.udo.ejbs.dto.Sensor;

import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@Named("sensorEnrich")
public class SensorEnrich implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Sensor sensor = exchange.getIn().getBody(Sensor.class);

        if (sensor != null) {
            EnrichedSensor enrichedSensor = new EnrichedSensor();
            enrichedSensor.setName(sensor.getName());
            enrichedSensor.setValue(sensor.getValue());

            //TODO do enrich logic here
            if (sensor.getValue() > 0) {
                enrichedSensor.setState("ON");
            } else {
                enrichedSensor.setState("OFF");
            }

            if (sensor.getName().toLowerCase().contains("digital")) {
                enrichedSensor.setType("DIGITAL");
            } else {
                enrichedSensor.setType("ANALOG");
            }
            EnrichedSensorNode enrichedSensorNode = exchange.getIn().getHeader("EnrichedSensorNode", EnrichedSensorNode.class);
            if (enrichedSensorNode != null) {
                enrichedSensorNode.getSensors().add(enrichedSensor);
            }

        }

    }
}
