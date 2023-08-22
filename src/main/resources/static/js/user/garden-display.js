/**
 * garden-display.js
 */
document.addEventListener('DOMContentLoaded', () => {
        
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 만듭니다.
  const day = String(date.getDate()).padStart(2, '0'); // 일을 2자리로 만듭니다.
  const hours = String(date.getHours()).padStart(2, '0'); // 시를 2자리로 만듭니다.
  const minutes = String(date.getMinutes()).padStart(2, '0'); // 분을 2자리로 만듭니다.

  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;
  
  return formattedDate;
}
    
    const changeView = (e) => {
        const type = e.target.getAttribute('data-id');
        console.log(`type = ${type}`);
        
        if(type === 'board') {
            loadBoardList();
        } else if(type === 'post') {
            loadPostList();
        } else {
            loadReplyList();
        }
    };
    
    const loadBoardList = async () => {
        console.log('loadBoardList');
        
        const userId = document.querySelector('input#userId').value;
        const url = `/api/garden/gardenmain/getBoardList/${userId}`;
        
        const response = await axios.get(url);
        console.log(response.data);
        
        const divArea = document.querySelector('tbody#list-section');
        
        let htmlStr = '';
        
        for(let board of response.data) {
            const formattedDate = formatDate(new Date(board.createdTime.replace("T", " ")));
            
            htmlStr += `
                <tr>
                    <td>${board.boardCategory}</td>
                    <td>
                        <a href="/board/details/${board.id}" class="link-dark">${board.boardName}</a>
                    </td>
                    <td>${formattedDate}</td>
                </tr>
            `;
        }
        
        divArea.innerHTML = htmlStr;
        
        const categoryList = document.querySelector('thead#category-list');
        htmlStr =  `
            <tr>
                <th>카테고리</th>
                <th>랜드이름</th>
                <th>생성일자</th>
            </tr>
        `;
        
        categoryList.innerHTML = htmlStr;
    };
    
    const loadPostList = async () => {
        console.log('loadPostList');
        
        const userId = document.querySelector('input#userId').value;
        const url = `/api/garden/gardenmain/getPostList/${userId}`;
        
        const response = await axios.get(url);
        console.log(response.data);
        
        const divArea = document.querySelector('tbody#list-section');
        
        let htmlStr = '';
        for(let post of response.data) {
            const formattedDate = formatDate(new Date(post.createdTime.replace("T", " ")));
            
            htmlStr += `
                <tr>
                    <td>
                        <a href="/post/details?boardId=${post.board.id}&id=${post.id}" 
                        class="link-dark">${post.board.boardName}</a>
                    </td>
                    <td>${post.postTitle}</td>
                    <td>${formattedDate}</td>
                </tr>
            `;
        }
        
        divArea.innerHTML = htmlStr;
        
        const categoryList = document.querySelector('thead#category-list');
        htmlStr = `
            <tr>
                <th>랜드이름</th>
                <th>포스트 제목</th>
                <th>시간</th>
            </tr>
        `;
        
        categoryList.innerHTML = htmlStr;
    };
    
    const loadReplyList = async () => {
        console.log('loadReplyList');
        
        const userId = document.querySelector('input#userId').value;
        const url = `/api/garden/gardenmain/getReplyList/${userId}`;
        
        const response = await axios.get(url);
        console.log(response.data);
        
        const divArea = document.querySelector('tbody#list-section');
        
        let htmlStr = '';
        for(let reply of response.data) {
            const formattedDate = formatDate(new Date(reply.createdTime.replace("T", " ")));
            htmlStr += `
                <tr>
                    <td>${reply.replyText}</td>
                    <td>${formattedDate}</td>
                </tr>
            `;
        }
        
        divArea.innerHTML = htmlStr;
        
        const categoryList = document.querySelector('thead#category-list');
        htmlStr = `
            <tr>
                <th scope="col">댓글</th>
                <th scope="col">시간</th>
            </tr>
        `;
        
        categoryList.innerHTML = htmlStr;
    };
    
    const categoryBtns = document.querySelectorAll('a.categoryBtn');
    for(let btn of categoryBtns) {
        btn.addEventListener('click', changeView);
    }
    
});