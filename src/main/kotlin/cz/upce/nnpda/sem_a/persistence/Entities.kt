package cz.upce.nnpda.sem_a.persistence

import cz.upce.nnpda.sem_a.error.NNPDAException
import org.springframework.http.HttpStatus
import java.util.*
import javax.persistence.*

private const val oneTimePasswordDuration: Long = 5 * 60 * 1000L; // 5 minutes

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 1,
    val username: String,
    var password: String,
    val enabled: Boolean = true,
    var firstname: String,
    var lastname: String,
    val email: String,
    var otp_required: Boolean = true,
    @OneToOne(cascade = [CascadeType.ALL],fetch = FetchType.LAZY,mappedBy = "userEntity")
    var oneTimePassword: OneTimePasswordEntity? = null
) {

    fun isOTPValid(value: String): Boolean {
        oneTimePassword?.let {
            when {
                it.createdAt.time + oneTimePasswordDuration < System.currentTimeMillis() -> throw NNPDAException("One time password has expired", HttpStatus.UNAUTHORIZED)
                it.value != value -> throw NNPDAException("Wrong one time password value", HttpStatus.UNAUTHORIZED)
                else -> return true
            }
        }?: run{
            throw NNPDAException("One time password not found", HttpStatus.NOT_FOUND)
        }
    }
}

@Entity
@Table(name = "authorities")
class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "authority")
    val authority: String
)

@Entity
@Table(name = "otp")
class OneTimePasswordEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 1,
    @Column(name = "value")
    val value: String,
    @Column(name = "created_at")
    val createdAt: Date,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    val userEntity: UserEntity? = null
)

@Entity
@Table(name = "sensors")
class SensorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
    @Column(name = "measurement_unit")
    val measurementUnit: String,
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sensor")
    val measurements: List<MeasurementEntity>
    )

@Entity
@Table(name = "measurements")
class MeasurementEntity(
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    @Column(name = "value")
    var value: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    lateinit var sensor: SensorEntity
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