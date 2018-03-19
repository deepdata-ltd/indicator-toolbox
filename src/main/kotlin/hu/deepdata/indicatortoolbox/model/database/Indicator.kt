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

import com.fasterxml.jackson.annotation.*
import java.util.*
import javax.persistence.*

/**
 * Represents a red flags indicator. Country, language an indicator
 * name columns have indices on them. Except description, all string
 * fields have the standard 255 character limit.
 *
 * Indicators have tags on them and indicators can be connected to
 * each other.
 *
 * @author Zsolt Jurányi
 */
@Entity
@Table(indexes = arrayOf(
		Index(name = "idx_indicator_country", columnList = "country"),
		Index(name = "idx_indicator_language", columnList = "language"),
		Index(name = "idx_indicator_name", columnList = "name")
))
data class Indicator(

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		var id: Long? = null,

		@Enumerated(EnumType.STRING)
		var autonomy: IndicatorAutonomy? = null,

		@Column(length = 2)
		var country: String? = null,

		@Lob
		var dataSource: String? = null,

		@Lob
		var description: String? = null,

		var developer: String? = null,

		@Temporal(TemporalType.DATE)
		var developmentDate: Date? = null,

		@Column(length = 2)
		var language: String? = null,

		var name: String? = null,

		@Enumerated(EnumType.STRING)
		var processPhase: IndicatorProcessPhase? = null,

		var programmingLanguage: String? = null,

		var projectUrl: String? = null,

		var sourceCodeUrl: String? = null,

		@Enumerated(EnumType.STRING)
		var state: IndicatorState? = null,

		@ManyToMany(cascade = arrayOf(CascadeType.ALL))
		@JoinTable(
				name = "indicator_tag",
				joinColumns = arrayOf(JoinColumn(name = "indicator_id", referencedColumnName = "id")),
				inverseJoinColumns = arrayOf(JoinColumn(name = "tag_id", referencedColumnName = "id"))
		)
		@JsonIgnore
		var tags: MutableSet<Tag> = mutableSetOf(),

		@Enumerated(EnumType.STRING)
		var validation: IndicatorValidation? = null,

		@Lob
		var validationMethod: String? = null

) {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Indicator) return false

		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		return id?.hashCode() ?: 0
	}

	override fun toString(): String {
		return "Indicator(id=$id, country=$country, language=$language, name=$name, tags=${tags.map(Tag::name)})"
	}
}