package com.acmerobotics.dashboard

import dev.frozenmilk.sinister.loading.Preload
import dev.frozenmilk.sinister.util.log.Logger
import com.acmerobotics.dashboard.DashboardBuildMetaData

@Preload
object LogMetaData {
    init {
        Logger.d("Meta", """
name: ${DashboardBuildMetaData.name}
version: ${DashboardBuildMetaData.version}
ref: ${DashboardBuildMetaData.gitRef}
""")
    }
}
