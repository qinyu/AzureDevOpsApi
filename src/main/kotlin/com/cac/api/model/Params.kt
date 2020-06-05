package com.cac.api.model


data class Params(var organization: String = "cac-technical",
                  var project: String = "TLTraining",
                  var version: String = "6.0-preview.6",
                  var repositoryId: String?,
                  var repositoryType: String?,
                  var branchName: String? = "refs/heads/master",
                  var minTime: String?,
                  var maxTime: String?)