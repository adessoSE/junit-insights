package de.adesso.junitinsights.tools

import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.Report

interface IReportCreator {
    fun createReport(reportName: String, events: List<Event>): Report
}