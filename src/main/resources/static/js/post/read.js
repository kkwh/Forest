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
    
    
    // 회원 클릭 시 아코디언, 정원 가기
    const authors = document.querySelectorAll('.author');
  authors.forEach((author) => {
    author.addEventListener('click', function (event) {
      const accordion = author.querySelector('.author-accordion');
      if (accordion) {
        if (accordion.style.display === 'none' || accordion.style.display === '') {
          accordion.style.display = 'block';
        } else {
          accordion.style.display = 'none';
        }
      }
    });
  });
});
