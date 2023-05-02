async function checkToken() {
    let token = sessionStorage.getItem('token');
    if (token) {
        let header = 'Bearer_' + token;
        try {
            let response = await fetch('/api/check', {
                method: 'GET',
                headers:{
                    'Authorization': header
                }
            });
            if (!response.ok){
                sessionStorage.setItem("message1", "message");
                location.href = 'auth/login';
            }
        } catch (error) {
            console.error(error);
        }
    } else {
        sessionStorage.setItem("message2", "message");
        location.href = 'auth/login';
    }
}

checkToken();