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

package hu.deepdata.indicatortoolbox.repository

import hu.deepdata.indicatortoolbox.model.database.*
import org.springframework.data.jpa.repository.*

/**
 * Repository interface for Indicator entities.
 * Should NOT use directly for saving and deletion, use
 * IndicatorService for those operations.
 *
 * @author Zsolt Jurányi
 */
interface IndicatorRepository : JpaRepository<Indicator, Long> {

	fun findByTags_Name(tag: String): List<Indicator>

	@Query("SELECT DISTINCT developer FROM Indicator i")
	fun queryDevelopers(): Set<String>

	@Query("SELECT DISTINCT programmingLanguage FROM Indicator i")
	fun queryProgrammingLanguages(): Set<String>

	@Query("SELECT DISTINCT projectUrl FROM Indicator i")
	fun queryProjectUrls(): Set<String>

	@Query("SELECT DISTINCT sourceCodeUrl FROM Indicator i")
	fun querySourceCodeUrls(): Set<String>

}