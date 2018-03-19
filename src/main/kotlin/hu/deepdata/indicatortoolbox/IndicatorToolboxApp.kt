/*
 *    Copyright 2018 DeepData Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package hu.deepdata.indicatortoolbox

import hu.deepdata.indicatortoolbox.configuration.*
import hu.deepdata.indicatortoolbox.importer.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.event.*
import org.springframework.context.*
import org.springframework.context.event.*
import org.springframework.scheduling.annotation.*

/**
 * Main class of Indicator Toolbox containing the entry point.
 *
 * @author Zsolt Jurányi
 */
@SpringBootApplication
@EnableScheduling
open class IndicatorToolboxApp {
	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(IndicatorToolboxApp::class.java, *args)
		}
	}

	@Autowired
	var applicationContext: ApplicationContext? = null

	@Autowired
	var importConfiguration: ImportConfiguration? = null

	@Autowired
	var indicatorService: IndicatorService? = null

	@EventListener(ApplicationReadyEvent::class)
	fun import() {
		if (null != importConfiguration!!.file) {
			try {
				XlsxImporter(indicatorService!!).import(importConfiguration!!.file!!, importConfiguration!!.headerIndex)
			} catch (e: Exception) {
				e.printStackTrace()
			}
			SpringApplication.exit(applicationContext, ExitCodeGenerator { 0 })
		}
	}
}