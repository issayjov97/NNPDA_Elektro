package cz.upce.nnpda.sem_a.service

import cz.upce.nnpda.sem_a.generator.DataGenerator
import cz.upce.nnpda.sem_a.generator.DataType
import cz.upce.nnpda.sem_a.persistence.MeasurementEntity
import cz.upce.nnpda.sem_a.persistence.MeasurementRepository
import cz.upce.nnpda.sem_a.persistence.SensorRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private const val DATA_CREATION_PERIOD: Long = 100000

@Component
class DataCreatingService(
    private val measurementRepository: MeasurementRepository,
    private val sensorRepository: SensorRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    @Scheduled(fixedDelay = DATA_CREATION_PERIOD)
    fun enableDataGenerator() {
        sensorRepository.findAll().forEach {
            val dataGenerator = DataGenerator(MeasurementEntity::class)
            dataGenerator.setData({ measurement, s -> measurement.value = s }, DataType.MEASUREMENT)
            dataGenerator.setData({ measurement, s -> measurement.sensor = it }, DataType.ID)
            measurementRepository.saveAllAndFlush(dataGenerator.create(5))
        }
    }
}