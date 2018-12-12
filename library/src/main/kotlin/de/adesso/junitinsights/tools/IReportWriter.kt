package de.adesso.junitinsights.tools

import de.adesso.junitinsights.model.Report

interface IReportWriter {
    fun writeReport(report: Report)
}
