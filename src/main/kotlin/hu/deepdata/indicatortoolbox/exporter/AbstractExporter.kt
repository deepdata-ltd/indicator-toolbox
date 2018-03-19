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
 * Interface and abstraction for exporter engines. Exporters
 * should be able to export a Table object into a File.
 *
 * @author Zsolt Jurányi
 */
abstract class AbstractExporter {

	/**
	 * It should export data in the Table object into
	 * the file described by the File argument.
	 */
	abstract fun export(file: File, table: Table)

	/**
	 * It should return the file extension, without
	 * the leading dot. This will be used to generate
	 * the final filename, e.g. "indicators.ext".
	 */
	abstract fun extension(): String

	/**
	 * Generates a filename using the extension method.
	 * The result will be like "indicators.ext".
	 */
	fun filename() = "indicators.${extension()}"

	/**
	 * It should return the name of the output format.
	 * This will be used in view layer on export buttons.
	 */
	abstract fun formatName(): String

	/**
	 * It should return the media type of the output
	 * file.
	 */
	abstract fun mediaType(): MediaType
}