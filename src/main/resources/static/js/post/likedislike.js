/**
 * 좋아요 싫어요
 */

 document.getElementById('like-button').addEventListener('click', function() {
	 var postId = this.getAttribute('data-post-id');
	 var userId = this.getAttribute('data-user-id');
	 console.log(postId);
	 console.log(userId);
	 // likeOrDislike(postId, userId, 1);
	 
	 if (userId == 0 || userId === null || userId === 'null' || userId === '') {
         alert('로그인 후 이용 가능한 기능입니다.');
         return;
       }

	 
	 checkAndExecuteLikeDislike(postId, userId, true);
});

document.getElementById('dislike-button').addEventListener('click', function() {
	var postId = this.getAttribute('data-post-id');
	var userId = this.getAttribute('data-user-id');
	console.log(postId);
	console.log(userId);
	// likeOrDislike(postId, userId, 0);
	
	if (userId == 0 || userId === null || userId === 'null' || userId === '') {
         alert('로그인 후 이용 가능한 기능입니다.');
         return;
       }
	
    checkAndExecuteLikeDislike(postId, userId, false);
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

// postId, userId가 이미 해당 게시물에 좋아요/싫어요를 누른 적이 있는지 체크
function checkAndExecuteLikeDislike(postId, userId, isLike) {
  axios.get(`/api/post/checkLikes/${postId}/${userId}`)
    .then(function(response) {
      if (response.data.exists) {
        alert("해당 계정은 이 게시물에서 더 이상 좋아요/싫어요를 할 수 없습니다.");
      } else {
        executeLikeDislike(postId, userId, isLike);
      }
    })
    .catch(function(error) {
      console.error('좋아요/싫어요 체크 중 오류 발생:', error);
    });
}

function executeLikeDislike(postId, userId, isLike) {
  const data = { userId, postId };
  const api = isLike ? '/api/post/like' : '/api/post/dislike';
  axios.post(api, data)
    .then(function(response) {
      const action = isLike ? '좋아요' : '싫어요';
      console.log(action + ' 버튼 인식 성공');
      const targetElementId = isLike ? 'like-count' : 'dislike-count';
      document.getElementById(targetElementId).textContent = response.data;
    })
    .catch(function(error) {
      console.error('업데이트 중 오류 발생:', error);
    });
}



