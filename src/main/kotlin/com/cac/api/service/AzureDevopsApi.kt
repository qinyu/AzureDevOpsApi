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
    @RequestMapping("/build")
    fun getBuild(param: Params): String {
        var duration = getBuildDuration(param)
        var time = getBuildTime(param)
        var rate = getBuildRate(param)
        var mttr = getMTTR(param)
        var result = "组织：${param.organization}" + "\n" +
                "项目：${param.project}  " + "\n" +
                "仓库：${param.repositoryId}  " + "\n" +
                "分支：${param.branchName} " + "\n" +
                "构建频率：$time 次 " + "\n" +
                "平均构建时长：$duration 分钟 " + "\n" +
                "构建成功率：$rate  " + "\n" +
                "平均失败恢复时长：$mttr 分钟 " + "\n"
        return result
    }

    private fun getDataModel(param: Params): Result {
        var result = api?.getAllBuilds(param.organization, param.project, param.version, param.branchName, param.minTime, param.maxTime)
        var response = result?.execute()

        if (response?.body() != null) {
            return response.body()!!
        }
        return Result(0, mutableListOf())
    }


}
