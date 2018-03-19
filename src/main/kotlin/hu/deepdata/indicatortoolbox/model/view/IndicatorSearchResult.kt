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

package hu.deepdata.indicatortoolbox.model.view

import hu.deepdata.indicatortoolbox.model.database.*

/**
 * Model for the indicator service in which it can return
 * query results. Besides the indicator list it contains
 * fields useful for pagination.
 *
 * @author Zsolt Jurányi
 */
data class IndicatorSearchResult(
		var allCount: Long = 0L,
		var count: Int = 0,
		var nextPage: Int? = null,
		var pages: Int = 0,
		var prevPage: Int? = null,
		var queryString: String = "",
		var indicators: List<Indicator> = listOf()
)