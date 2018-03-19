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

package hu.deepdata.indicatortoolbox.service

import hu.deepdata.indicatortoolbox.controller.exception.*
import hu.deepdata.indicatortoolbox.exporter.*
import hu.deepdata.indicatortoolbox.model.database.*
import hu.deepdata.indicatortoolbox.model.export.*
import hu.deepdata.indicatortoolbox.repository.*
import mu.*
import org.springframework.beans.factory.*
import org.springframework.beans.factory.annotation.*
import org.springframework.scheduling.annotation.*
import org.springframework.stereotype.*
import java.io.*
import java.text.*
import javax.servlet.*

/**
 * Service which exports indicators' data into TSV format.
 * It's called on startup and every day at midnight. Provides
 * methods also to get information on exported files.
 *
 * @author Zsolt Jurányi
 */
@Service
class ExportService : InitializingBean {

	companion object : KLogging()

	@Autowired
	var context: ServletContext? = null

	var exporters = listOf(
			TsvExporter(),
			XlsExporter()
			// further exporters can be added here
	)

	@Autowired
	var indicators: IndicatorRepository? = null

	/**
	 * Calls all registered exporter. This method is
	 * scheduled to be called at midnight, every day.
	 */
	@Scheduled(cron = "0 0 0 * * *")
	fun export() {
		logger.trace("Exporter called")
		val table = generateTable()
		exporters.forEach { export(it, table) }
	}

	/**
	 * Provides a Map with exported files data to be used
	 * in view models.
	 */
	fun exportsData(): Map<String, Any> = mapOf("formats" to
			exporters.map {
				ExportedFileInfo(it.formatName(), it.extension(), fileSizeStr(it.filename()))
			})

	fun lookupResourceByFormat(format: String): ExportResource {
		val e = exporters.find { it.extension().equals(format, true) } ?: throw PageNotFoundException()
		return ExportResource(file(e.filename()), e.mediaType())
	}

	override fun afterPropertiesSet() {
		export()
	}

	private fun generateTable(): Table {
		val formatter = SimpleDateFormat("yyyy-MM-dd")
		return Table(indicators!!.findAll().map {
			with(it) {
				mapOf(
						"id" to id?.toString(),
						"name" to name,
						"autonomy" to autonomy.toString(),
						"country" to country,
						"dataSource" to dataSource,
						"description" to description,
						"developer" to developer,
						"development_date" to
								// TODO should move formatting to exp implementations
								// TODO so we should change value to Any?
								if (developmentDate != null) formatter.format(developmentDate) else null,
						"language" to language,
						"processPhase" to processPhase.toString(),
						"programming_language" to programmingLanguage,
						"project_url" to projectUrl,
						"source_code_url" to sourceCodeUrl,
						"state" to state.toString(),
						"tags" to tags.map(Tag::name).joinToString(", "),
						"validation" to validation.toString(),
						"validation_method" to validationMethod
				)
			}
		}.toList())
	}

	private fun export(exporter: AbstractExporter, table: Table) {
		val filename = exporter.filename()
		val target = file(filename)
		val temp = temporaryFile(filename)
		exporter.export(temp, table)
		if (target.exists()) target.delete()
		if (temp.exists()) temp.renameTo(target)
		if (target.exists()) {
			logger.info("Export written to: ${target.absolutePath}")
		} else {
			logger.warn("Cannot find export: ${target.absolutePath}")
		}
	}

	fun file(filename: String) =
			File(context!!.getRealPath("$filename"))

	private fun fileSizeStr(filename: String) =
			String.format("%.2f", file(filename).length().toDouble() / 1024)

	private fun temporaryFile(filename: String) =
			File(context!!.getRealPath("$filename.tmp"))

}