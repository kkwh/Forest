/**
 * read.html
 */
document.addEventListener("DOMContentLoaded", function() {
    var links = document.querySelectorAll('.custom-link');
    var currentPostType = new URLSearchParams(window.location.search).get('postType');
    
    links.forEach(function(link) {
        if (link.href.includes("postType=" + currentPostType)) {
            link.classList.add('active-link');
        }
    });
});
