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

package hu.deepdata.indicatortoolbox.configuration

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

/**
 * Configuration for the indicator list pagination.
 *
 * Properties are loaded from application.properties with
 * prefix "pagination". There is only 1 property:
 *
 * - itemsPerPage: number of items per page, 10 by default.
 *
 * @author Zsolt Jurányi
 */
@Component
@ConfigurationProperties(prefix = "pagination")
data class PaginationConfiguration(
		var itemsPerPage: Int = 10
)