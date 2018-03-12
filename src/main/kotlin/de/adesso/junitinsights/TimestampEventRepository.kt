package de.adesso.junitinsights

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
open interface TimestampEventRepository : JpaRepository<TimestampEvent, Long>