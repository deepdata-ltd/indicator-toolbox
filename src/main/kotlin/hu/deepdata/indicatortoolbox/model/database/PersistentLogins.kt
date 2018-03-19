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

package hu.deepdata.indicatortoolbox.model.database

import java.util.*
import javax.persistence.*

/**
 * Table representation needed to use persitent login tokens.
 *
 * @author Zsolt Jurányi
 */
@Entity
data class PersistentLogins(

		@Column(length = 64)
		var username: String? = null,

		@Id
		@Column(length = 64)
		var series: String? = null,

		@Column(length = 64)
		var token: String? = null,

		@Temporal(TemporalType.TIMESTAMP)
		var lastUsed: Date? = null
)