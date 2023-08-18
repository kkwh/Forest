/**
 * create.html
 */

document.addEventListener('DOMContentLoaded', function() {
    // 페이지가 완전히 로드된 후에 이 코드가 실행됩니다.
    window.write_headtext = function(element, selectedValue) {
        document.getElementById('selected_value').value = selectedValue;

        var listItems = document.querySelectorAll('.subject_list li');
        listItems.forEach(function(li) {
            li.className = '';
        });
        element.className = 'sel';
    };
});









