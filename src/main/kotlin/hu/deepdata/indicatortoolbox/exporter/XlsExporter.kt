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
import org.apache.poi.hssf.usermodel.*
import org.springframework.http.*
import java.io.*

/**
 * Exports a Table object into a XLS (Excel
 * spreadsheet) file. The first row will be the
 * header contaning the field names.
 *
 * @author Zsolt Jurányi
 */
class XlsExporter : AbstractExporter() {

	override fun export(file: File, table: Table) {
		val wb = HSSFWorkbook()
		val sh = wb.createSheet()
		val headers = table.headers()

		var r = sh.createRow(0)
		headers.forEachIndexed { i, h ->
			r.createCell(i).setCellValue(h)
		}

		table.data.forEachIndexed { i, map ->
			r = sh.createRow(i + 1)
			headers.forEachIndexed { j, h ->
				r.createCell(j).setCellValue(map[h] ?: "")
			}
		}

		FileOutputStream(file).use {
			wb.write(it)
		}
	}

	override fun extension() = "xls"

	override fun formatName() = "XLS"

	override fun mediaType() = MediaType.APPLICATION_OCTET_STREAM
}