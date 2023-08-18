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
  
  // 모든 아코디언을 숨기는 함수
  function hideAllAccordions() {
    authors.forEach((author) => {
      const accordion = author.querySelector('.author-accordion');
      if (accordion) {
        accordion.style.display = 'none';
      }
    });
  }

  authors.forEach((author) => {
    author.addEventListener('click', function (event) {
      const accordion = author.querySelector('.author-accordion');
      if (accordion) {
        // 현재 클릭된 아코디언 외에는 모두 숨김
        hideAllAccordions();

        if (accordion.style.display === 'none' || accordion.style.display === '') {
          accordion.style.display = 'block';
          document.addEventListener('click', outsideClickListener);
        } else {
          accordion.style.display = 'none';
          document.removeEventListener('click', outsideClickListener);
        }
        event.stopPropagation();
      }
    });
  });

  // 외부 클릭 감지 함수
  function outsideClickListener(event) {
    if (!event.target.closest('.author')) {
      hideAllAccordions();
      document.removeEventListener('click', outsideClickListener);
    }
  }
});
