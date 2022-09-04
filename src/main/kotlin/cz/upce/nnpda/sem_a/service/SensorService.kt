package cz.upce.nnpda.sem_a.service

import cz.upce.nnpda.sem_a.api.SensorApi
import cz.upce.nnpda.sem_a.domain.*
import cz.upce.nnpda.sem_a.error.NNPDAException
import cz.upce.nnpda.sem_a.persistence.SensorEntity
import cz.upce.nnpda.sem_a.persistence.SensorRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class SensorService(
    private val sensores: MutableList<Sensor> = mutableListOf(),
    private val sensorRepository: SensorRepository
) {
    @PostConstruct
    fun init() {
        sensores.add(Barometer())
        sensores.add(Ohmmeter())
        sensores.add(Ammeter())
        sensores.add(Voltmeter())
    }

    fun getSensors() = sensorRepository.findAll().map {
        SensorApi(
            id = it.id!!,
            measurementUnit = it.measurementUnit,
            type = it.name
        )
    }

    fun deleteSensor(id: Long) = sensorRepository.deleteById(id)

    fun addSensor(sensor: SensorApi) = sensorRepository.saveAndFlush(
        SensorEntity(
            name = sensor.type,
            measurementUnit = sensor.measurementUnit,
            measurements = mutableListOf()
        )
    )

    fun getBarometerMeasurements() = sensores.find { it.type == SensorType.BAROMETER }
        ?: throw NNPDAException("Measurements not found", HttpStatus.NOT_FOUND)

    fun getAmmeterMeasurements() = sensores.find { it.type == SensorType.AMMETER }
        ?: throw NNPDAException("Measurements not found", HttpStatus.NOT_FOUND)

    fun getVoltmeterMeasurements() = sensores.find { it.type == SensorType.VOLTMETER }
        ?: throw NNPDAException("Measurements not found", HttpStatus.NOT_FOUND)
}
