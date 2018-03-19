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

import hu.deepdata.indicatortoolbox.service.*
import mu.*
import org.apache.poi.xssf.usermodel.*
import java.io.*

/**
 * Imports an XLSX file into the database.
 *
 * @author Zsolt Jurányi
 */
class XlsxImporter(private val indicatorService: IndicatorService) {

	// TODO validate imported data!
	// https://stackoverflow.com/questions/19190592/manually-call-spring-annotation-validation

	companion object : KLogging()

	/**
	 * Imports an XLSX file into the database.
	 *
	 * @param file Input XLSX file.
	 * @param headerRowIndex Row index of header row where field names are.
	 */
	fun import(file: File, headerRowIndex: Int) {
		logger.info("Importing $file")
		val workbook = XSSFWorkbook(file)
		val sheet = workbook.getSheetAt(0)
		val headerCols = mutableMapOf<String, Int>()
		val headerRow = sheet.getRow(headerRowIndex)
		headerRow.forEachIndexed { i, c -> headerCols.put(c.stringCellValue.trim(), i) }
		if (!headerCols.keys.contains("id")) {
			logger.error("Could not find 'id' in header row, aborting import")
			return
		}
		val rowParser = RowParser(headerCols)
		(headerRowIndex + 1..sheet.lastRowNum)
				.map(sheet::getRow)
				.map(rowParser::parse)
				.filter { null != it.name }
				.filter { -1L != it.id } // import=0 -> id=-1 -> DO NOT IMPORT
				.onEach { println("${it.id} ${it.validation}") }
				.onEach(indicatorService::save)
	}

}