package de.adesso.junitinsights

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : CrudRepository<TimestampEvent, Long> {

}