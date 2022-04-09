package dev.dizel.discordintegration.vm

import androidx.lifecycle.ViewModel
import dev.dizel.discordintegration.domain.model.Report
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _report: MutableStateFlow<Report> = MutableStateFlow(Report.Empty)
    val report: StateFlow<Report> = _report.asStateFlow()

    fun setReport(report: Report) {
        _report.value = report
    }
}