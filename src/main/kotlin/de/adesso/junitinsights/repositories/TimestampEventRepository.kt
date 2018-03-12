package de.adesso.junitinsights.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimestampEventRepository : JpaRepository<TimestampEvent, Long>