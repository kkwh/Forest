/**
 * 좋아요 싫어요
 */

 document.getElementById('like-button').addEventListener('click', function() {
	 var postId = this.getAttribute('data-post-id');
	 
  axios.post('/api/post/like', { postId: postId }) // 좋아요 API
    .then(function(response) {
      // 서버 응답에서 좋아요 개수를 받아 업데이트
      document.getElementById('like-count').textContent = response.data.likeCount;
    })
    .catch(function(error) {
      console.error('좋아요 업데이트 중 오류 발생:', error);
    });
});

document.getElementById('dislike-button').addEventListener('click', function() {
	var postId = this.getAttribute('data-post-id');
	
  axios.post('/api/post/dislike', { postId: postId }) // 싫어요 API
    .then(function(response) { 
      // 서버 응답에서 싫어요 개수를 받아 업데이트
      document.getElementById('dislike-count').textContent = response.data.dislikeCount;
    })
    .catch(function(error) {
      console.error('싫어요 업데이트 중 오류 발생:', error);
    });
});






