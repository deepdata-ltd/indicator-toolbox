/*
	Copyright 2018 DeepData Ltd.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

$(function(){
	// initialize Materialize components
	initMaterialComponents();

	// focus automatically on relevant input
	$('input[type=text]:not([readonly])').first().focus();
	$('input.invalid').first().focus();
});


function importFor(field) {
	var
		formData = importValues('input.import[name=' + field + ']').array,
		autocompleteData = importValues('input.autocomplete-import.' + field).object,
		autocompleteOptions = {
			data: autocompleteData,
			limit: 5,
			minLength: 1
		};
	return { formData: formData, autocomplete: autocompleteOptions };
}

function importValues(selector) {
	var a = [], o = {};
	$(selector).each(function(i, e){
		a.push($(e).val());
		o[$(e).val()] = null;
		$(e).remove();
	});
	return { array: a, object: o };
}

function initMaterialComponents() {
	$('select').material_select();
	$('.dropdown-button').dropdown();
	$('.materialize-textarea').trigger('autoresize');
	$('.datepicker').pickadate({
		closeOnSelect: false,
		container: 'body',
		format: 'yyyy-mm-dd',
		selectMonths: true,
		selectYears: 15
	});
	$('.button-collapse').sideNav();
}

function initTagsEditor(field, model) {
	var imported = importFor(field);
	var modelFunction = model[field]
	modelFunction(imported.formData.map(function(t) { return { tag: t }; }));

	var autocompleteId = 'tags-input';
	var $tagsInput = $('#tags-input')
	$tagsInput.material_chip({
		autocompleteOptions: imported.autocomplete,
		data: modelFunction(),
		placeholder: '+Tag',
		secondaryPlaceholder: '+Tag'
	});
	$tagsInput.on('chip.add', function(e, chip){
		modelFunction.push(chip);
	});
	$tagsInput.on('chip.delete', function(e, chip){
		var tags = modelFunction();
		tags.splice(tags.indexOf(chip), 1);
		modelFunction(tags);
	});
}