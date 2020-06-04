package com.cac.api.service;

import com.cac.api.data.AzureBuildLogic
import com.cac.api.model.Monitor
import com.cac.api.model.Params
import com.cac.api.model.Result
import com.cac.api.source.API
import okhttp3.OkHttpClient
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Controller
class MonitorController {

    private var api: API? = null
    private val BASE_URL = "https://dev.azure.com/"

    init {
        var retrofit: Retrofit? = null
        //初始化对象
        val client = OkHttpClient.Builder()
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) //配置baseUrl
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //配置使用Gson解析响应数据
                .build()

        this.api = retrofit?.create(API::class.java)
    }


    @RequestMapping("/monitor")
    fun index(model: Model, param: Params): String {
        val totalModel = getDataModel(param)
        val builds = totalModel.value.groupBy {
            it.repository
        }
        var monitors = mutableListOf<Monitor>()
        builds.forEach {
            var dataModel = Result(it.value.size, it.value)
            var azureBuildLogic = AzureBuildLogic(dataModel)
            var duration = azureBuildLogic.getDuration()
            var time = azureBuildLogic.getTimes()
            var rate = azureBuildLogic.getBuildSuccessRate()
            var mttr = azureBuildLogic.getRecoveryTime()
            if (dataModel.value.isNotEmpty()) {
                var build = dataModel.value.first()
                var monitor = Monitor(param.organization, param.project, build.repository?.name, param.branchName, build.result, duration, time, rate, mttr)
                monitors.add(monitor)
            }
        }
        model.addAttribute("monitors", monitors)
        return "monitor"
    }


    private fun getDataModel(param: Params): Result {
        var result = api?.getAllBuilds(param.organization, param.project, param.version, param.branchName, param.repositoryId, param.repositoryType, param.minTime, param.maxTime)
        var response = result?.execute()

        if (response?.body() != null) {
            return response.body()!!
        }
        return Result(0, mutableListOf())
    }

}
