package de.adesso.junitinsights.tools

import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.Report

/**
 * Creates a report from a list of Events
 * @see de.adesso.junitinsights.model.Event
 * @see de.adesso.junitinsights.model.Report
 * @see ReportCreator
 */
interface IReportCreator {
    fun createReport(events: List<Event>): Report
}