/**
 * 
 */

document.getElementById("btnfindid").addEventListener("click", function() {
    var email = document.getElementById("emailInput").value; // 이메일 입력값 가져오기
    
    const url = `/api/user/validate/findId?email=${email}`;
    
    if (email.trim() === "") {
        alert("이메일을 입력해주세요.");
        return;
    }
    
    axios.post(url) // 데이터 객체로 전송
        .then(function(response) {
            // 서버 응답 성공
            //console.log('자스 왔음.');
            console.log(response.data);
            if (response.data === '') {
                alert("해당 이메일이 존재하지 않습니다.");
            } else {
                alert("아이디: " + response.data);
            }
        })
        .catch(function(error) {
            console.log(error);
        });
});
