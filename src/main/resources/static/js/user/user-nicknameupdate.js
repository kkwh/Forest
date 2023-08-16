/**
 * 
 */

document.getElementById("btnUpdate").addEventListener("click", function() {
        var nickname = document.getElementById("nickname").value;
        var id = document.getElementById("id").value;
        
        const url = `/user/updateinfo`;
       const data = { nickname: nickname,
                      id: id  };
        
         console.log(data);
        axios.post(url, data)
       
                .then(function(response) {
                        console.log(response.data);
                        window.location.href = '/user/myinfopage';
                })
                .catch(function(error) {
                        console.log(error);
               });
});

   