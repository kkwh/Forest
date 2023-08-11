/**
 * read에서 카테고리 별 분류
 */

 document.getElementById('like-button').addEventListener('click', function() {
	 var boardId = this.getAttribute('data-board-id');
	 
	 console.log(boardId);
	 
	 axios.get(`/api/post/category/${boardId}`)
    .then(function(response) {
      console.log(response)
      
    })
    .catch(function(error) {
      console.error('카테고리 분류 에러', error);
    });

});




