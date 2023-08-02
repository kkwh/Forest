/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
    
    const form = document.querySelector('#postModifyForm');
    
    const inputId = document.querySelector('input#id');

    const inputTitle = document.querySelector('input#title');

    const textareaContent = document.querySelector('textarea#content');
    
    const btnUpdate = document.querySelector('#btnUpdate');
    btnUpdate.addEventListener('click', (e) => {
        alert('업데이트');
        
        e.preventDefault();
        console.log('업데이트');
        
        const id = inputId.value; 
        const title = inputTitle.value; 
        const content = textareaContent.value; 
        
        if (title === '' || content === '') {
            alert('제목과 내용은 반드시 입력해야 합니다.');
            return; 
        }
        
        const result = confirm(`NO.${id} 포스트를 업데이트할까요?`);
        if (result) {
            form.action = '/post/update';
            form.method = 'post'; 
            form.submit(); 
        }
    });
    
    const btnDelete = document.querySelector('#btnDelete');
    btnDelete.addEventListener('click', (e) => {
        alert('삭제');

        e.preventDefault();
        
        console.log('삭제');
        
        const id = inputId.value;
        const result = confirm(`NO. ${id} 정말 삭제할까요?`);
        
        console.log(`삭제 확인 결과 = ${result}`);
        
        if (result) {
            form.action = '/post/delete'; // 폼 제출(요청) 주소
            form.method = 'post'; // 요청 방식
            form.submit(); // 폼 제출(요청 보내기)
        }
    })
});