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

import hu.deepdata.indicatortoolbox.configuration.*
import hu.deepdata.indicatortoolbox.model.database.*
import hu.deepdata.indicatortoolbox.model.view.*
import hu.deepdata.indicatortoolbox.repository.*
import mu.*
import org.springframework.beans.factory.annotation.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

/**
 * Service for managing indicators. Does not provide all
 * query methods that the repository does but provides
 * save and deletion mechanisms that handle tag relations.
 *
 * @author Zsolt Jurányi
 */
@Service
class IndicatorService {

	companion object : KLogging()

	@Autowired
	protected var indicators: IndicatorRepository? = null

	@Autowired
	protected var pagination: PaginationConfiguration? = null

	@Autowired
	protected var tags: TagRepository? = null

	fun get(id: Long): Indicator? = indicators!!.findOne(id)

	/**
	 * Deletes the indicator having the specified ID.
	 * Before that, removes all tag relations to
	 * prevent cascading deletion.
	 */
	fun delete(id: Long) {
		logger.info("Deleting indicator: $id")
		indicators!!.findOne(id)?.apply {
			this.tags.clear()
			save(this)
			indicators!!.delete(id)
		}
	}

	fun isExistingId(id: Long) = null != indicators!!.findOne(id)

	/**
	 * Saves the given indicator. Updates tag relations too.
	 */
	fun save(indicator: Indicator) {
		logger.info("Saving indicator: ${indicator.id}")

		// fetch tag ids
		indicator.tags.forEach { tag ->
			tag.name = tag.name!!.toLowerCase()
			tag.id = tags!!.findOneByName(tag.name!!)?.id
		}

		// saves everything, but manages only A->B relations
		indicators!!.save(indicator)
	}

	/**
	 * Saves the indicator represented by the given
	 * form data. Updates tag relations.
	 */
	fun save(form: IndicatorEditForm): Indicator {
		var indicator =
				if (null == form.id)
					Indicator()
				else
					get(form.id!!) ?: Indicator()
		form.toIndicator(indicator)
		save(indicator)
		return indicator
	}

	/**
	 * Performs a combined search on the indicator records.
	 * Firstly it queries the table using the repository
	 * based on active, country, and language fields. Then
	 * filters the result by name and tags. Finally it
	 * returns the appropriate page.
	 */
	fun search(form: IndicatorSearchForm): IndicatorSearchResult {
		val exampleIndicator = Indicator(
				autonomy = form.autonomy,
				country = if (form.country.isNullOrBlank()) null else form.country,
				language = if (form.language.isNullOrBlank()) null else form.language,
				processPhase = form.processPhase,
				state = form.state,
				validation = form.validation
		)

		var result = indicators!!.findAll(Example.of(exampleIndicator))

		// text filter
		if (!form.search.isNullOrBlank()) {
			result = result.filter {
				val text = listOf(
						it.description,
						it.developer,
						it.name,
						it.programmingLanguage,
						it.projectUrl,
						it.sourceCodeUrl,
						if (it.validation == IndicatorValidation.VALIDATED) it.validationMethod else ""
				).joinToString("|").toLowerCase()

				form.search!!
						.toLowerCase()
						.split(" ")
						.all { text.contains(it) }
			}
		}

		// tags filter
		if (form.tags.isNotEmpty()) {
			result = result.filter { i ->
				form.tags.all { i.tags.contains(Tag(null, it)) }
			}
		}

		// sorting
		result.sortWith(compareBy(Indicator::name))

		// pagination
		val pages = Math.ceil(result.size.toDouble() / pagination!!.itemsPerPage).toInt()
		form.page = Math.max(form.page, 1)
		form.page = Math.min(form.page, pages)
		val pagedResults = result.filterIndexed { index, _ ->
			index / pagination!!.itemsPerPage == form.page - 1
		}
		return IndicatorSearchResult(
				indicators!!.count(),
				result.size,
				if (form.page < pages) form.page + 1 else null,
				0,
				if (form.page > 1) form.page - 1 else null,
				form.toString(),
				pagedResults
		)
	}
}