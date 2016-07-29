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

package com.microsoft.livy.client.interactive.session

import com.microsoft.livy.client.argument.parser._
import com.microsoft.livy.client.common._

object LivyInteractiveClient {

  def list(inputOptions: LivyClientArgumentParser.ArgumentMap): Unit = {

    val testMode: Boolean = inputOptions.contains(Symbol(LivyClientArgumentKeys.TestMode))

    val listURL = "https://%s/livy/sessions".format(inputOptions(Symbol(LivyClientArgumentKeys.ClusterFQDN)))
    val livySessionList: LivySessionList = LivyInteractiveSessionAction.list(listURL,
      userCredentials(inputOptions), testMode)
    if (livySessionList != null) LivyClientUtilities.printSessionList(livySessionList)
  }

  def get(inputOptions: LivyClientArgumentParser.ArgumentMap, livySessionId: Long): Unit = {

    if (inputOptions.contains(Symbol(LivyClientArgumentKeys.Run))
      || inputOptions.contains(Symbol(LivyClientArgumentKeys.Monitor))) {

      val sessionId = if (livySessionId < 0) inputOptions(Symbol(LivyClientArgumentKeys.LivyId)).asInstanceOf[Int]
      else livySessionId

      val testMode: Boolean = inputOptions.contains(Symbol(LivyClientArgumentKeys.TestMode))

      val getURL = "https://%s/livy/sessions/%s".format(inputOptions(Symbol(LivyClientArgumentKeys.ClusterFQDN)),
        sessionId)

      var livySessionDetails: LivySessionDetails = null

      do {
        livySessionDetails = LivyInteractiveSessionAction.get(getURL, sessionId,
          userCredentials(inputOptions), testMode)
        if (livySessionDetails != null) LivyClientUtilities.printSessionDetails(livySessionDetails)
        Thread.sleep(1000)
      }
      while (!livySessionDetails.state.equalsIgnoreCase("FINISHED")
        && !livySessionDetails.state.equalsIgnoreCase("ERROR")
        && !livySessionDetails.state.equalsIgnoreCase("UNDEFINED"))
    }
  }

  def start(inputOptions: LivyClientArgumentParser.ArgumentMap): Long = {

    var livyId: Long = -1

    if (inputOptions.contains(Symbol(LivyClientArgumentKeys.Start))
      || inputOptions.contains(Symbol(LivyClientArgumentKeys.Run))) {

      val sessionRequest: LivyInteractiveSession = new LivyInteractiveSession(
        if (inputOptions.contains(Symbol(LivyClientArgumentKeys.YarnApplicationName)))
          inputOptions(Symbol(LivyClientArgumentKeys.YarnApplicationName)).toString
        else "Livy",
        inputOptions(Symbol(LivyClientArgumentKeys.SessionKind)).toString,
        if (inputOptions.contains(Symbol(LivyClientArgumentKeys.ProxyUser)))
          inputOptions(Symbol(LivyClientArgumentKeys.ProxyUser)).toString
        else null,
        if (inputOptions.contains(Symbol(LivyClientArgumentKeys.ClasspathJARS)))
          inputOptions(Symbol(LivyClientArgumentKeys.ClasspathJARS)).asInstanceOf[List[String]]
        else List[String](),
        if (inputOptions.contains(Symbol(LivyClientArgumentKeys.PythonpathPyFiles)))
          inputOptions(Symbol(LivyClientArgumentKeys.PythonpathPyFiles)).asInstanceOf[List[String]]
        else List[String](),
        if (inputOptions.contains(Symbol(LivyClientArgumentKeys.ExecutorFiles)))
          inputOptions(Symbol(LivyClientArgumentKeys.ExecutorFiles)).asInstanceOf[List[String]]
        else List[String](),
        if (inputOptions.contains(Symbol(LivyClientArgumentKeys.YarnArchives)))
          inputOptions(Symbol(LivyClientArgumentKeys.YarnArchives)).asInstanceOf[List[String]]
        else List[String](),
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.ExecutorCount)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.ExecutorCount)).asInstanceOf[Int])
        else None,
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.PerExecutorMemoryInGB)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.PerExecutorMemoryInGB)).toString + "G")
        else None,
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.PerExecutorCoreCount)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.PerExecutorCoreCount)).asInstanceOf[Int])
        else None,
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.DriverMemoryInGB)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.DriverMemoryInGB)).toString + "G")
        else None,
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.DriverCoreCount)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.DriverCoreCount)).asInstanceOf[Int])
        else None,
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.YarnQueue)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.YarnQueue)).toString)
        else None,
        if(inputOptions.contains(Symbol(LivyClientArgumentKeys.SparkConfigurations)))
          Some(inputOptions(Symbol(LivyClientArgumentKeys.SparkConfigurations)).toString.split(",").map(x =>
          { val y = x.split("=")
            (y(0), y(1))
          }).toMap)
        else None
      )

      val testMode: Boolean = inputOptions.contains(Symbol(LivyClientArgumentKeys.TestMode))

      val postURL = "https://%s/livy/sessions".format(inputOptions(Symbol(LivyClientArgumentKeys.ClusterFQDN)))

      val livySessionDetails: LivySessionDetails = LivyInteractiveSessionAction.run(postURL,
        userCredentials(inputOptions), sessionRequest, testMode)

      if (livySessionDetails != null) LivyClientUtilities.printSessionDetails(livySessionDetails)

      livyId = livySessionDetails.id
    }

    livyId
  }

  private def userCredentials(inputOptions: LivyClientArgumentParser.ArgumentMap) : LivyUserCredentials = {
    new LivyUserCredentials(inputOptions(Symbol(LivyClientArgumentKeys.ClusterUsername)).toString,
      inputOptions(Symbol(LivyClientArgumentKeys.ClusterPassword)).toString)
  }
}


