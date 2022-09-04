package cz.upce.nnpda.sem_a.persistence

import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?

    fun findByEmail(email: String): UserEntity?

}

interface OTPRepository : JpaRepository<OneTimePasswordEntity, Long>

interface SensorRepository : JpaRepository<SensorEntity, Long>

interface MeasurementRepository : JpaRepository<MeasurementEntity, Long>