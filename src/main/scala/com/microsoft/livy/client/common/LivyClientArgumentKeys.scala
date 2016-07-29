/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.livy.client.common

object LivyClientArgumentKeys extends Enumeration {
  val List: String = "list"
  val Submit: String = "submit"
  val Start: String ="start"
  val Run: String = "run"
  val Monitor: String = "monitor"
  val Kill: String = "kill"
  val LivyId: String = "livyId"
  val SessionKind: String = "sessionKind"
  val ProxyUser: String = "proxyUser"
  val YarnApplicationName: String = "yarnApplicationName"
  val ApplicationJAR: String = "applicationJAR"
  val ApplicationClass: String = "applicationClass"
  val ApplicationArguments: String = "applicationArguments"
  val ClasspathJARS: String = "classpathJARS"
  val PythonpathPyFiles: String = "pythonpathPyFiles"
  val ExecutorFiles: String = "executorFiles"
  val YarnArchives: String = "yarnArchives"
  val ExecutorCount: String = "executorCount"
  val PerExecutorMemoryInGB: String = "perExecutorMemoryInGB"
  val PerExecutorCoreCount: String = "perExecutorCoreCount"
  val DriverMemoryInGB: String = "driverMemoryInGB"
  val DriverCoreCount: String = "driverCoreCount"
  val SparkConfigurations: String = "sparkConfigurations"
  val ClusterFQDN: String = "clusterFQDN"
  val ClusterUsername: String = "clusterUsername"
  val ClusterPassword: String = "clusterPassword"
  val YarnQueue: String = "yarnQueue"
  val BatchMode: String = "batchMode"
  val InteractiveMode: String = "interactiveMode"
  val TestMode: String = "testMode"
}