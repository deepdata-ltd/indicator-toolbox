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

package hu.deepdata.indicatortoolbox.exporter

import hu.deepdata.indicatortoolbox.model.export.*
import org.springframework.http.*
import java.io.*

/**
 * Exports a Table object into a TSV (tab separated
 * values) file. The first row will be the header
 * contaning the field names.
 *
 * @author Zsolt Jurányi
 */
class TsvExporter : AbstractExporter() {

	companion object {
		val TSV_SEPARATOR = "\t"
	}

	override fun export(file: File, table: Table) {
		BufferedWriter(FileWriter(file)).use { w ->
			val headers = table.headers()
			w.write(headers.joinToString(TSV_SEPARATOR))
			w.newLine()
			table.data.forEach { record ->
				w.write(headers.joinToString(TSV_SEPARATOR) {
					record[it]?.replace(Regex("(\r|\n|\t)+"), "  ") ?: ""
				})
				w.newLine()
			}
		}
	}

	override fun extension() = "tsv"

	override fun formatName() = "TSV"

	override fun mediaType() = MediaType.TEXT_PLAIN
}