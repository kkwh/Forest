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
       if (e.target.innerText === '열매 안보기') {
           e.target.innerText = '열매 보기'
           // 댓글 목록 불러오기:
           getRepliesWithPostId();
       } else if(e.target.innerText ==='열매 보기') {
           e.target.innerText = '열매 안보기';
          getRepliesWithPostId();
       } else {
           e.target.innerText = '열매 보기';
       }
        
    });
    
    
    
    // 순서 5-2!!!
    // 댓글 삭제 버튼들의 클릭을 처리하는 이벤트 리스너 콜백:
    const deleteReply = (e) => {
        
        console.log('ddddddddd');
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


    // !!!!!!  대댓글 삭제 버튼들의 클릭을 처리하는 이벤트 리스너 콜백:
    const deleteReply2 = (e) => {
        
        console.log('대댓글 삭제');
        const result = confirm('정말 삭제할까요?');
        if (!result) {
        return;
        }

        const id = e.target.getAttribute('data-id2');
        const userId = e.target.getAttribute('data-user-id2');
   
        const idTag2 = `password_${id}`;
        const inputPassword2 = document.querySelector(`input#${idTag2}`).value;
        const reqUrl = `/api/reReply/${id}?userId=${userId}&password=${inputPassword2}`;
   
        const realPassword2 = e.target.getAttribute('data-password2');
        
     
        // 비밀번호가 입력되지않거나 틀린 경우 삭제를 하지 않음
        if (inputPassword2 === '') {
           console.log('오류');
            alert('비밀번호를 입력하세요.');
            return;
        }

        if (realPassword2 !== inputPassword2) {
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


        const createReReply = (e) => {
            const replyId = e.target.getAttribute('data-reply-id');
            console.log(`id = ${replyId}`);
            const userId = e.target.getAttribute('data-reply-user-id')
            console.log(`id = ${userId}`);
            
            const nicknameTag = `replyNickname2_${replyId}`;
            const reReplyNickname = document.querySelector(`input#${nicknameTag}`).value;
            console.log(`nickname = ${reReplyNickname}`);
            
            const passwordTag = `replyPassword2_${replyId}`;
            const reReplyPassword = document.querySelector(`input#${passwordTag}`).value;
            console.log(`password = ${reReplyPassword}`);
            
            const textTag = `replyText2_${replyId}`;
            const reReplyText = document.querySelector(`textarea#${textTag}`).value;
            console.log(`text = ${reReplyText}`);
            
            //const ipTag = `replyIp2_${replyId}`;
            //const reReplyIp = document.querySelector(`span#${ipTag}`).value;
            //console.log(`text = ${reReplyIp}`);
            
            
            
                 if(reReplyText === '') {
                alert('열매를 달아주세요');
                return;
                }
                
                const data = {replyId, userId, reReplyText, reReplyNickname, reReplyPassword};
       
               // Ajax 요청을 보낼 URL
                const reqUrl = '/api/reReply';
               console.log('여기냐')
       
        axios
           .post(reqUrl, data)  // Ajax POST 방식 요청을 보냄.
           .then((response) => {
                console.log(response);
               console.log('찍히냐?');
                
                // 댓글 목록 새로고침   
                getRepliesWithPostId();
               console.log('어디냐 ');
                
           })  // 성공 응답(response)일 때 실행할 콜백 등록
           .catch((error) => console.log(error)); // 실패(error)일 때 실행할 콜백 등록
       
    };
    
    
    // 순서 3!!!!!!!!!!!
    const makeReplyElements = (data) => {
        // 댓글 개수를 배열(data)의 원소 개수로 업데이트.
        // 댓글 목록을 삽입할 div 요소를 찾음.
        const replies = document.querySelector('div#replies');
        console.log(' 오냐?');
        
        // div 안에 작성된 기존 내용은 삭제.
        replies.innerHtml = '';
        
        // div 안에 삽입할 HTML 코드 초기화.
        
        let htmlStr = '';
        for (let reply of data.list) {
        if(reply.userId == 0 & reply.replyPassword !== null) {    
            htmlStr += `
            <div class="card my-2">
                <div>
                    <span class="d-none" id=reID>${reply.id}</span>
                    <span class="fw-bold">${reply.replyNickname}</span>
                    <span class="fw-bold">(${reply.replyIp})</span>
                </div>
            
                <textarea id="replyText_${reply.id}" readonly>${reply.replyText}</textarea>
                <div class="my-2">
                    <input id="password_${reply.id}"
                       type="text" name="replyPassword" placeholder="비밀번호를 입력하세요"/>
                </div>     
                <div class= "reply-action">
                    <button class="btnDelete" data-password="${reply.replyPassword}" data-user-id="${reply.userId}" data-id="${reply.id}">썩은 열매 제거</button>
                </div>
                <div class="card my-2">
                    
                    <div id="rereplies_${reply.id}"></div> <!-- 대댓글이 들어갈 공간 -->
                </div>
            `;
        } else if(reply.userId == 0 & reply.replyPassword === null) {    
            htmlStr += `
            <div class="card my-2">
                <div>
                    <span class="d-none">${reply.id}</span>
                    <span class="fw-bold">${reply.replyNickname}</span>
                    <span class="fw-bold">(${reply.replyIp})</span>
                </div>
            
                <textarea id="replyText_${reply.id}" readonly>${reply.replyText}</textarea>
                
                <div class="card my-2">
                        <div id="rereplies_${reply.id}"></div> <!-- 대댓글이 들어갈 공간 -->
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
                    <textarea id="replyText_${reply.id}" readonly>${reply.replyText}</textarea>
                        <input class="form-control" id="password_${reply.id}"
                            type="hidden" name="replyPassword" value="0"/>
                    <div class= "reply-action">
                        <button class="btnDelete" data-password="${reply.replyPassword}" data-user-id="${reply.userId}" data-id="${reply.id}">썩은 열매 제거</button>
                    </div>
                        <div class="card my-2">
                        <div id="rereplies_${reply.id}"></div> <!-- 대댓글이 들어갈 공간 -->
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
                
                    <textarea id="replyText_${reply.id}" readonly>${reply.replyText}</textarea>
                    
                    <div class="card my-2">
                        <div id="rereplies_${reply.id}"></div> <!-- 대댓글이 들어갈 공간 -->
                    </div>
                `;
             }
        }
        htmlStr += '</div>';
        
        
    }            
        
        // 작성된 HTML 문자열을 div 요소에 innerHTML로 설정.
        replies.innerHTML = htmlStr;
        
        
        // 대댓글 입력
        for(let reply of data.list) {
            const rereply_id = `rereplies_${reply.id}`;
            const rereply_div = document.querySelector(`div#${rereply_id}`);
            

            
            let htmlStr2  = `
                <div id="rereply_create_${reply.id}">
                    <div class="cmt_write_box clear">    
                        

                        <div class="fl">
                            <div class="user_info_input nomem_nick">
                                <label class="blind" for="replyNickname2">닉네임</label>
                                <input  id="replyNickname2_${reply.id}" type="text" name="replyNickname2_${reply.id}" 
                                placeholder="닉네임을 입력하세요" />
                            </div>
                            <div class="user_info_input">
                                <label class="blind" for="user_pw">비밀번호</label>
                                <input class="reply-password-input" id="replyPassword2_${reply.id}"
                                   type="text" name="replyPassword2_${reply.id}" placeholder="비밀번호를 입력하세요" required autofocus />
                            </div>      
                        </div>          
                        <div class="cmt_txt_cont">
                            <div class="cmt_write">
                                <textarea id="replyText2_${reply.id}"  placeholder="열매를 달아주세요"></textarea>
                             </div>
                        </div>
                         <div class="cmt_cont_bottm clear">
                             <div class="dccon_guidebox">
                                  <button type="button" data-reply-id="${reply.id}" 
                                        class="btn_blue btn_svc small repley_add btnReReplyCreate">
                                        열매에 열매를 달자!!!
                                   </button>
                             </div>
                         </div>
                    </div>
                </div>
                
                <!--대댓글 리스트  -->
                <div id="rereply_list_${reply.id}">
                </div>
            `;
            rereply_div.innerHTML=htmlStr2;
        }
        
        for(let reply of data.list) {
            showReplies(reply.id);
        }
        
        
        const btnReReplyCreates = document.querySelectorAll('button.btnReReplyCreate');
        for(let reBtn of btnReReplyCreates) {
            reBtn.addEventListener('click', createReReply);
        }
        
        
        
        // 순서 5-1!!!
        // 모든 댓글 삭제 버튼들에게 이벤트 리스너를 등록.
        const btnDeletes = document.querySelectorAll('button.btnDelete');
        for (let btn of btnDeletes) {
            console.log('make make');
            btn.addEventListener('click', deleteReply);
        }    
      

    
    };
    
    
    // 순서 2!!!!    
    // 포스트 번호에 달려있는 댓글 목록을 (Ajax 요청으로) 가져오는 함수:
    const getRepliesWithPostId = async () => {
        const postId = document.querySelector('input#postId').value; // 포스트 아이디(번호)
        const reqUrl = `/api/reply/all/${postId}`; // Ajax 요청 주소
       console.log('순서2 ');
        
        // Ajax 요청을 보내고 응답을 기다림.
        try {
            const response = await axios.get(reqUrl);
            console.log(response);
            makeReplyElements(response.data);
            
        } catch (error) {
            console.log(error);
        }
    };
    
    
    // 대댓글  1 !!!
    // 댓글에 달려있는 대댓글 목록을 (Ajax 요청으로) 가져오는 함수:
        const getRepliesWithReplyId = async (replyId) => {
            const reqUrl = `/api/reReply/all/${replyId}`;
        
            try {
                const response = await axios.get(reqUrl);
                return response.data;
            } catch (error) {
                console.log(error);
                return [];
            }
        };
   
           // !!!!!!!1 대댓글을 보여주는 함수
        const showReplies = async (replyId) => {
            // 대댓글 가져오기
            const reReplies = await getRepliesWithReplyId(replyId);
            
            console.log(reReplies);
        
            // 대댓글을 보여줄 공간 선택
            //const reReplyContainer = document.querySelector(`div#rereplies_${replyId}`);
            //reReplyContainer.innerHTML = ''; // 기존 내용 삭제
            
            const divTag = `rereply_list_${replyId}`;
            const rereplyDiv = document.querySelector(`div#${divTag}`);
            
            
            // 대댓글 리스트를 보여줄 HTML 생성
            let htmlStr = '';
            for (let reReply of reReplies) {
                htmlStr += `
                    <div class="reply-container">
                        <div class="reply-details">
                            <span>${reReply.replyNickname2}</span>
                            <span>(${reReply.replyIp2})</span>
                            <textarea id="replyText2_${reReply.id}" readonly>${reReply.replyText2}</textarea>
                        </div>
                        <div class="reply-input">
                            <input id="password_${reReply.id}" type="text" name="replyPassword2" placeholder="비밀번호를 입력하세요" />
                        </div>
                        <div class="reply-action">
                            <button class="btnDelete2" data-password2="${reReply.replyPassword2}" data-user-id2="${reReply.userId}" data-id2="${reReply.id}">썩은 열매열매 제거</button>
                        </div>
                    </div>
                `;
            }
                rereplyDiv.innerHTML = htmlStr;
            
       
       // !!!!대댓글 삭제버튼
       const btnDeletes2 = rereplyDiv.querySelectorAll('button.btnDelete2');
       for(let btn of btnDeletes2) {
           console.log('lets 2 delete');
           btn.addEventListener('click', deleteReply2);
       }
            
    };     

    
    // 순서 4!!!!!!!    
    const btnReplyCreate = document.querySelector('button#btnReplyCreate');
    btnReplyCreate.addEventListener('click', () => {
       // 포스트 아이디 찾음.
       const postId = document.querySelector('input#postId').value;
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