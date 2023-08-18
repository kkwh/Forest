/**
 * 이벤트 업데이트 & 삭제
 */
document.addEventListener('DOMContentLoaded', () => {
    
    // <form> 요소를 찾음
    const eventModifyForm = document.querySelector('#eventModifyForm');
    // <button> 요소를 찾음
    const btnDelete = document.querySelector('#btnDelete');
    const btnUpdate = document.querySelector('#btnUpdate');
    
    btnUpdate.addEventListener('click', (e) => {
       
        const title = document.querySelector('#title').value;
        const content = document.querySelector('#content').value;
        
            if(title === '' || content === '') {
                alert('제목과 내용을 입력해주세요');
                return;
            }
        
            const check = confirm('수정하시려고 하는 내용이 맞습니까?')
            if(!check) {
                return;
            }
            
            eventModifyForm.action = '/event/update'; 
            eventModifyForm.method = 'post'; 
            eventModifyForm.submit();
    });
    
    btnDelete.addEventListener('click', (e) => {
            
            const check = confirm('삭제하시나요?');
        
            if(!check) {
               return;
            }
        
            eventModifyForm.action = '/event/delete'; 
            eventModifyForm.method = 'post'; 
            eventModifyForm.submit(); 
        
    
    });
     
});