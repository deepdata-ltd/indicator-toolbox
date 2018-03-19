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

import hu.deepdata.indicatortoolbox.model.database.*
import hu.deepdata.indicatortoolbox.repository.*
import mu.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*

/**
 * Service for tags. Provides simple query methods, and
 * mechanisms for renaming, saving and deleting tags.
 * Maintains indicator relations.
 *
 * @author Zsolt Jurányi
 */
@Service
class TagService {

	companion object : KLogging()

	@Autowired
	protected var indicators: IndicatorRepository? = null

	@Autowired
	protected var indicatorService: IndicatorService? = null

	@Autowired
	protected var tags: TagRepository? = null

	fun all() = tags!!.findAll().toSet()

	/**
	 * Saves a new tag into the database, unless it
	 * already exists.
	 */
	fun create(name: String): Tag {
		var tag = tags?.findOneByName(name)
		if (null == tag) {
			tag = Tag(name)
			save(tag)
		}
		return tag
	}

	/**
	 * Deletes the tag having the given ID.
	 */
	fun delete(id: Long) {
		val tag = tags!!.findOne(id)
		if (null != tag) deleteImpl(tag)
	}

	/**
	 * Deltes the tag having the given name.
	 */
	fun delete(name: String) {
		val tag = tags!!.findOneByName(name)
		if (null != tag) deleteImpl(tag)
	}

	/**
	 * Deletes the given tag. It removes indicator
	 * relations first to get rid of orphan records.
	 */
	protected fun deleteImpl(tag: Tag) {
		logger.info("Deleting tag: $tag")
		tag.indicators.forEach {
			it.tags.remove(tag)
			indicators!!.save(it)
		}
		tags!!.delete(tag.id)
	}

	fun get(id: Long): Tag? = tags!!.findOne(id)

	fun get(name: String): Tag? = tags!!.findOneByName(name)

	/**
	 * Sets a new name for the tag having the given ID.
	 */
	fun rename(id: Long, newName: String) {
		get(id)?.apply { renameImpl(this, newName) }
	}

	/**
	 * Renames the tag defined by the first parameter to
	 * have the name given in the second parameter.
	 */
	fun rename(oldName: String, newName: String) {
		get(oldName)?.apply { renameImpl(this, newName) }
	}

	/**
	 * Renames a tag. If a tag with the new name already
	 * exists, merges the two tags.
	 */
	protected fun renameImpl(tag: Tag, newName: String) {
		val existing = get(newName.toLowerCase())
		if (null == existing) {
			logger.info("Renaming tag ${tag.id} from ${tag.name} to $newName")
			tag.name = newName
			save(tag)
		} else {
			logger.info("Merging tag $tag to existing tag $existing")
			tag.indicators.forEach {
				it.tags.remove(tag)
				it.tags.add(existing)
				indicatorService!!.save(it)
			}
			tags!!.delete(tag)
		}
	}

	/**
	 * Saves the given tag into the database.
	 */
	fun save(tag: Tag) {
		tag.name = tag.name!!.toLowerCase()
		logger.info("Saving tag: $tag")
		tags?.save(tag)
	}
}