package com.cac.api.service

import com.cac.api.data.AzureBuildLogic
import com.cac.api.model.Params
import com.cac.api.model.Result
import com.cac.api.source.API
import okhttp3.OkHttpClient
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Controller
class AzureDevopsApi {

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

    @ResponseBody
    @RequestMapping("/build/duration")
    fun getBuildDuration(param: Params): String {
        var azureBuildLogic = AzureBuildLogic(getDataModel(param))
        return azureBuildLogic.getDuration().toString()
    }

    @ResponseBody
    @RequestMapping("/build/time")
    fun getBuildTime(param: Params): String {
        var azureBuildLogic = AzureBuildLogic(getDataModel(param))
        return azureBuildLogic.getTimes().toString()
    }

    @ResponseBody
    @RequestMapping("/build/rate")
    fun getBuildRate(param: Params): String {
        var azureBuildLogic = AzureBuildLogic(getDataModel(param))
        return azureBuildLogic.getBuildSuccessRate().toString()
    }

    @ResponseBody
    @RequestMapping("/build/mttr")
    fun getMTTR(param: Params): String {
        var azureBuildLogic = AzureBuildLogic(getDataModel(param))
        return azureBuildLogic.getRecoveryTime().toString()
    }

    @ResponseBody
    @RequestMapping("/build/monitor")
    fun getBuild(param: Params): String {
        val totalModel = getDataModel(param)
        val builds = totalModel.value.groupBy {
            it.repository
        }
        var result = ""
        builds.forEach {
            var dataModel = Result(it.value.size, it.value)
            var azureBuildLogic = AzureBuildLogic(dataModel)
            var duration = azureBuildLogic.getDuration()
            var time = azureBuildLogic.getTimes()
            var rate = azureBuildLogic.getBuildSuccessRate()
            var mttr = azureBuildLogic.getRecoveryTime()
            result += "组织：${param.organization}" + "\n" +
                    "项目：${param.project}  " + "\n" +
                    "仓库：${dataModel.value.first().repository?.name}  " + "\n" +
                    "分支：${param.branchName} " + "\n" +
                    "当前构建状态：${dataModel.value.first().result} " + "\n" +
                    "构建频率：$time 次 " + "\n" +
                    "平均构建时长：$duration 分钟 " + "\n" +
                    "构建成功率：$rate  " + "\n" +
                    "平均失败恢复时长：$mttr 分钟 " + "\n" + "\n" + "\n"
        }

        return result
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
