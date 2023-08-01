/**
 * 좋아요 싫어요
 */

 document.getElementById('like-button').addEventListener('click', function() {
	 var postId = this.getAttribute('data-post-id');
	 console.log(postId);
	 const data = { postId };
  axios.post('/api/post/like', data) // 좋아요 API
    .then(function(response) {
      // 서버 응답에서 좋아요 개수를 받아 업데이트
      console.log('좋아요 버튼 인식 성공');
      document.getElementById('like-count').textContent = response.data;
    })
    .catch(function(error) {
      console.error('좋아요 업데이트 중 오류 발생:', error);
    });
});

document.getElementById('dislike-button').addEventListener('click', function() {
	var postId = this.getAttribute('data-post-id');
	console.log(postId);
    const data = { postId };
	
  axios.post('/api/post/dislike', data) // 싫어요 API
    .then(function(response) { 
      // 서버 응답에서 싫어요 개수를 받아 업데이트
      console.log('싫어요 버튼 인식 성공');
      document.getElementById('dislike-count').textContent = response.data;
    })
    .catch(function(error) {
      console.error('싫어요 업데이트 중 오류 발생:', error);
    });
});






