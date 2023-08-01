/**
 * 댓글 영역 보이기/숨기기 토글
 * 댓글 검색, 등록, 수정, 삭제
 */
document.addEventListener('DOMContentLoaded', () => {
    // 순서 1-1!!!
    
        // 로그인한 사용자 이름 -> 댓글 등록, 수정, 삭제할 때 사용하기 위해서.
    const authName = document.querySelector('div#authName').innerHTML;
    console.log(authName);
    
    // 부트스트랩 Collapse 객체를 생성. 초기 상태는 화면에 보이지 않는 상태.
    const bsCollapse = new bootstrap.Collapse('div#replyToggleDiv', {toggle: false});
    
    // 순서 1-2!!!
    const btnToggleReply = document.querySelector('#btnToggleReply');
    btnToggleReply.addEventListener('click', (e) => {
       bsCollapse.toggle(); 
       //console.log(e.target);
       if (e.target.innerText === '보이기') {
           e.target.innerText = '숨기기';
           
           // 댓글 목록 불러오기:
           getRepliesWithPostId();
       } else {
           e.target.innerText = '보이기';
       }
        
    });
    
    // 순서 5-2!!!
    // 댓글 삭제 버튼들의 클릭을 처리하는 이벤트 리스너 콜백:
const deleteReply = (e) => {
    const result = confirm('정말 삭제할까요?');
    if (!result) {
        return;
    }

    const id = e.target.getAttribute('data-id');
    const userId = e.target.getAttribute('data-user-id');
   
    const idTag = `password_${id}`;
    const inputPassword = document.querySelector(`input#${idTag}`).value;
    const reqUrl = `/api/reply/${id}?userId=${userId}&password=${inputPassword}`;
   
    const realPassword = e.target.getAttribute('data-password');
        
     
        // 비밀번호가 입력되지않거나 틀린 경우 삭제를 하지 않음
        if (inputPassword === '') {
           console.log('오류');
            alert('비밀번호를 입력하세요.');
            return;
        }

        if (realPassword !== inputPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            getRepliesWithPostId();
            return;
        }
    

    axios
        .delete(reqUrl)
        .then((response) => {
            console.log(response);
            getRepliesWithPostId();
        })
        .catch((error) =>  console.log(error))
        ;
};
    
    
    // 순서 3!!!!!!!!!!!
    const makeReplyElements = (data) => {
        // 댓글 개수를 배열(data)의 원소 개수로 업데이트.
        const replyCount = data.list.length;
        document.querySelector('span#replyCount').innerText = `${replyCount}개가 달려 있어요`;
        
        // 댓글 목록을 삽입할 div 요소를 찾음.
        const replies = document.querySelector('div#replies');
        
        // div 안에 작성된 기존 내용은 삭제.
        replies.innerHtml = '';
        
        // div 안에 삽입할 HTML 코드 초기화.
        let htmlStr = '';
        for (let reply of data.list) {
        if(reply.userId == 0) {    
            htmlStr += `
            <div class="card my-2">
                <div>
                    <span class="d-none">${reply.id}</span>
                    <span class="fw-bold">${reply.replyNickname}</span>
                    <span class="fw-bold">(${reply.replyIp})</span>
                </div>
            
                <textarea id="replyText_${reply.id}">${reply.replyText}</textarea>
                <div class="my-2">
                    <label class="form-label" for="replyPassword">비밀번호</label>
                    <input class="form-control" id="password_${reply.id}"
                       type="text" name="replyPassword" placeholder="비밀번호를 입력하세요" required autofocus />
                </div>     
                <div>
                    <button class="btnDelete btn btn-outline-danger" 
                        data-password="${reply.replyPassword}" data-user-id="${reply.userId}" data-id="${reply.id}">삭제</button>
                </div>
            `;
        } else {
            if(reply.userId == data.userId) {
                htmlStr += `
                <div class="card my-2">
                    <div>
                        <span class="d-none">${reply.id}</span>
                        <span class="fw-bold">${reply.replyNickname}</span>
                        <span class="fw-bold">(${reply.replyIp})</span>
                    </div>
                    <textarea id="replyText_${reply.id}">${reply.replyText}</textarea>
                        <input class="form-control" id="password_${reply.id}"
                            type="hidden" name="replyPassword" value="0" required autofocus />
                    <div>
                        <button class="btnDelete btn btn-outline-danger" 
                            data-password="0" data-user-id="${reply.userId}" data-id="${reply.id}">삭제</button>
                    </div>
                `;
            }
             else {
                 htmlStr += `
                <div class="card my-2">
                    <div>
                        <span class="d-none">${reply.id}</span>
                        <span class="fw-bold">${reply.replyNickname}</span>
                        <span class="fw-bold">(${reply.replyIp})</span>
                    </div>
                
                    <textarea id="replyText_${reply.id}">${reply.replyText}</textarea>

                `;
             }
            
        }

        htmlStr += '</div>';
    }           

        
        // 작성된 HTML 문자열을 div 요소에 innerHTML로 설정.
        replies.innerHTML = htmlStr;
        
   // 순서 5-1!!!
   // 모든 댓글 삭제 버튼들에게 이벤트 리스너를 등록.
   const btnDeletes = document.querySelectorAll('button.btnDelete');
        for (let btn of btnDeletes) {
            btn.addEventListener('click', deleteReply);
        }        
        
    };
    
    // 순서 2!!!!    
    // 포스트 번호에 달려있는 댓글 목록을 (Ajax 요청으로) 가져오는 함수:
    const getRepliesWithPostId = async () => {
        const postId = document.querySelector('input#id').value; // 포스트 아이디(번호)
        const reqUrl = `/api/reply/all/${postId}`; // Ajax 요청 주소
        
        // Ajax 요청을 보내고 응답을 기다림.
        try {
            const response = await axios.get(reqUrl);
            console.log(response);
            makeReplyElements(response.data);
            
        } catch (error) {
            console.log(error);
        }
    };
    
    // 순서 4!!!!!!!    
    const btnReplyCreate = document.querySelector('button#btnReplyCreate');
    btnReplyCreate.addEventListener('click', () => {
       // 포스트 아이디 찾음.
       const postId = document.querySelector('input#id').value;
       // 댓글 내용 찾음.
       const replyText = document.querySelector('textarea#replyText').value;
       // const replyNickname = authName;
       const replyNickname = document.querySelector('input#replyNickname').value;
       const replyPassword = document.querySelector('input#replyPassword').value;
       const userId = document.querySelector('input#userId').value;
       
       // 댓글 내용이 비어있으면 경고창을 보여주고 종료.
       if(replyText === '') {
           alert('열매를 달아주세요');
           return;
       }
       
       // Ajax 요청에서 보낼 데이터.(key:value로 입력)
       // const data = {postId: postId, replyText: replyText, replyNickname: replyNickname};
       // 만약 key와 value 의 값이 같다면 아래처럼 하나를 생략해도 쌉가능
       const data = {postId, userId, replyText, replyNickname, replyPassword};
       
       // Ajax 요청을 보낼 URL
       const reqUrl = '/api/reply';
       
       
       axios
           .post(reqUrl, data)  // Ajax POST 방식 요청을 보냄.
           .then((response) => {
                console.log(response);
                
                // 댓글 목록 새로고침
                getRepliesWithPostId();
                // 댓글 입력한 내용을 지움.
                document.querySelector('input#replyNickname').value = '';
                document.querySelector('input#replyPassword').value = '';
                document.querySelector('textarea#replyText').value = '';
                
           })  // 성공 응답(response)일 때 실행할 콜백 등록
           .catch((error) => console.log(error)); // 실패(error)일 때 실행할 콜백 등록
       
       
    });
    
});