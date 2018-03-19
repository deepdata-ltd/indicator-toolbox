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

package hu.deepdata.indicatortoolbox.importer

import hu.deepdata.indicatortoolbox.model.database.*
import org.apache.poi.ss.usermodel.*

/**
 * Row parser utility for `XlsxImporter`. Initialize
 * it with a header column map (key: field name,
 * value: zero based column index).
 *
 * @author Zsolt Jurányi
 */
class RowParser(private val headerCols: MutableMap<String, Int>) {

	companion object {
		val STR_FIELDS = Indicator::class.java.declaredFields
				.filter { it.type == String::class.java }
				.onEach { it.isAccessible = true }
	}

	private fun cell(row: Row, field: String) = cell(row, headerCols[field])

	private fun cell(row: Row, col: Int?): CellConverter = try {
		CellConverter(row.getCell(col!!).toString().trim().replace(Regex("\\.0$"), ""))
	} catch (e: Exception) {
		CellConverter(null)
	}

	/**
	 * Parses the `Row` into an `Indicator` object based on the map passed on initialization.
	 */
	fun parse(r: Row) = Indicator(
			id = cell(r, "id").asLong(),
			autonomy = cell(r, "autonomy").asIndicatorAutonomy(),
			// TODO developmentDate?
			processPhase = cell(r, "processPhase").asIndicatorProcessPhase(),
			state = cell(r, "state").asIndicatorState(),
			tags = cell(r, "tags").asTagSet(),
			validation = cell(r, "validation").asIndicatorValidation()
	).apply {
		// set String fields automatically
		STR_FIELDS.forEach { it.set(this, cell(r, it.name).asString()) }

		if (1L != cell(r, "import").asLong()) {
			id = -1
		}
	}

}