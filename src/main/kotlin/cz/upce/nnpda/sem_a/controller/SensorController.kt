package cz.upce.nnpda.sem_a.controller

import cz.upce.nnpda.sem_a.api.SensorApi
import cz.upce.nnpda.sem_a.service.SensorService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/sensors")
@Tag(name = "Sensor API", description = "Information from different sensors")
class SensorController(
    private val sensorService: SensorService
) {

    @GetMapping
    fun getSensors(
        authentication: Authentication
    ): List<SensorApi> = sensorService.getSensors()

    @DeleteMapping("/{id}")
    fun deleteSensors(
        @PathVariable id: Long,
        authentication: Authentication
    ) = sensorService.deleteSensor(id)

    @PostMapping()
    fun addSensor(
        @RequestBody sensor: SensorApi,
        authentication: Authentication
    ) = sensorService.addSensor(sensor)

    @GetMapping("/barometer")
    fun getBarometerMeasurements(
        authentication: Authentication
    ) = sensorService.getBarometerMeasurements()

    @GetMapping("/ammeter")
    fun getAmmeterMeasurements(
        authentication: Authentication
    ) = sensorService.getAmmeterMeasurements()

    @GetMapping("/voltmeter")
    fun getVoltmeterMeasurements(
        authentication: Authentication
    ) = sensorService.getVoltmeterMeasurements()
}