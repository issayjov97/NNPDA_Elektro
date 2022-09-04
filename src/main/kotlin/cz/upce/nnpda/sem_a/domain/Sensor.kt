package cz.upce.nnpda.sem_a.domain

abstract class Sensor {
    abstract var type: SensorType
    abstract var measurementUnit: MeasurementUnitType
    var measuredValues: List<Double> = emptyList()

    override fun toString(): String {
        return "Sensor(type=$type, measurementUnit=$measurementUnit, measuredValues=$measuredValues)"
    }
}

class Barometer : Sensor() {
    override var type: SensorType = SensorType.BAROMETER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.ATM
    init {
        measuredValues = listOf(27.2, 28.39, 29.0)
    }
}

class Voltmeter : Sensor() {
    override var type: SensorType = SensorType.VOLTMETER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.V
    init {
        measuredValues = listOf(3.0 ,3.3,9.3)
    }
}

class Ammeter : Sensor() {
    override var type: SensorType = SensorType.AMMETER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.A
    init {
        measuredValues = listOf(2.0 ,10.2,8.9)
    }

}

class Hydrometer : Sensor() {
    override var type: SensorType = SensorType.HYDROMETER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.GM3
}

class ElectricityMeter : Sensor() {
    override var type: SensorType = SensorType.ELECTRICITY_METER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.KWH
    init {
        measuredValues = listOf(10.0 ,2.2,4.9)
    }
}

class Thermometer : Sensor() {
    override var type: SensorType = SensorType.THERMOMETER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.CELSIUS
    init {
        measuredValues = listOf(27.0 ,22.0,18.0)
    }
}

class Ohmmeter : Sensor() {
    override var type: SensorType = SensorType.OHMMETER
    override var measurementUnit: MeasurementUnitType = MeasurementUnitType.OHMS
    init {
        measuredValues = listOf(30.0 ,100.0,82.0)
    }
}

enum class SensorType {
    THERMOMETER,
    BAROMETER,
    ELECTRICITY_METER,
    HYDROMETER,
    AMMETER,
    VOLTMETER,
    OHMMETER
}

enum class MeasurementUnitType {
    CELSIUS,
    ATM,
    KWH,
    GM3,
    A,
    V,
    OHMS
}