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

/**
 * Helper object to centralize URI and view name configuration.
 * This is used by controllers and also populated to view
 * templates for easier and consistent link generation.
 *
 * @author Zsolt Jurányi
 */
object Paths {

	const val ADMIN_MANUAL_URI = "/admin/manual/"
	const val ADMIN_URI = "/admin/"
	const val ADMIN_VIEW = "admin/index"
	const val EXPORT_URI = "/export/"
	const val INDICATOR_ADD_URI = "/admin/indicators/new/"
	const val INDICATOR_DELETE_URI = "/admin/indicator/delete/"
	const val INDICATOR_DELETE_VIEW = "admin/indicator-delete"
	const val INDICATOR_EDIT_URI = "/admin/indicator/"
	const val INDICATOR_LIST_URI = "/indicators/"
	const val INDICATOR_LIST_VIEW = "indicators"
	const val INDICATOR_URI = "/indicator/"
	const val INDICATOR_VIEW = "indicator"
	const val LOGIN_URI = "/login"
	const val LOGIN_VIEW = "login"
	const val LOGOUT_URI = "/logout"
	const val RESOURCES_URI = "/resources"
	const val RESOURCES_VIEW = "resources"
	const val TAG_CLEANUP_URI = "/admin/tags/cleanup/"
	const val TAG_CLEANUP_VIEW = "admin/tags-cleanup"
	const val TAG_DELETE_URI = "/admin/tag/delete/"
	const val TAG_DELETE_VIEW = "admin/tag-delete"
	const val TAG_LIST_URI = "/admin/tags/"
	const val TAG_LIST_VIEW = "admin/tags"
}