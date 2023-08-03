/**
 * 좋아요 싫어요
 */

 document.getElementById('like-button').addEventListener('click', function() {
	 var postId = this.getAttribute('data-post-id');
	 var userId = this.getAttribute('data-user-id');
	 console.log(postId);
	 console.log(userId);
	 // likeOrDislike(postId, userId, 1);
	 
	 const data = { userId, postId };
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
	var userId = this.getAttribute('data-user-id');
	console.log(postId);
	console.log(userId);
	// likeOrDislike(postId, userId, 0);
	
    const data = { userId, postId };
	
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


// 하루에 좋아요 싫어요 1회 처리
function likeOrDislike(postId, userId, likeDislike) {
    
    var request = { userId: userId, postId: postId, likeDislike: likeDislike };
    console.log(request);

    fetch("/api/post/likeLimit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(request)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert("성공!");
            // 성공적인 액션 처리
        } else {
            alert("하루에 한 번만 가능합니다.");
            // 버튼 비활성화 등
        }
    });
}

// 
function checkLikeDislike(postId, userId) {
    fetch(`/checkLikes/${postId}/${userId}`)
    .then(response => response.json())
    .then(data => {
        if (data.exists) {
            alert("해당 계정은 이 게시물에서 더 이상 좋아요/싫어요를 할 수 없습니다.");
        } else {
            // 좋아요/싫어요 처리 로직
        }
    });
}




