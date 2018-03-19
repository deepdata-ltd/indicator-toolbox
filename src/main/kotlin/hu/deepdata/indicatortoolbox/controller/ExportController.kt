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

package hu.deepdata.indicatortoolbox.controller

import hu.deepdata.indicatortoolbox.configuration.*
import hu.deepdata.indicatortoolbox.model.export.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.core.io.*
import org.springframework.http.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import java.io.*

/**
 * @author Zsolt Jurányi
 */
@Controller
@RequestMapping(Paths.EXPORT_URI)
class ExportController {

	@Autowired
	var exportService: ExportService? = null

	@GetMapping("{format}")
	fun downloadExport(@PathVariable("format") format: String): ResponseEntity<Resource> =
			downloadableFileResponse(exportService!!.lookupResourceByFormat(format))

	private fun downloadableFileResponse(resource: ExportResource): ResponseEntity<Resource> {
		return ResponseEntity.ok()
				.contentLength(resource.file.length())
				.contentType(resource.mediaType)
				.header("Content-Disposition", "attachment; filename=${resource.file.name}")
				.body(InputStreamResource(FileInputStream(resource.file)))
	}
}