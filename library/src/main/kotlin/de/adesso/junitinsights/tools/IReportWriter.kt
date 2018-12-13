package de.adesso.junitinsights.tools

import de.adesso.junitinsights.model.Report

/**
 * Writes a report to disk.
 * @see ReportWriter
 */
interface IReportWriter {
    fun writeReport(report: Report)
}
