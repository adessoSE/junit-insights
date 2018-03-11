package de.adesso.junitinsights

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TimestampEventRepository : CrudRepository<TimestampEvent, Long>