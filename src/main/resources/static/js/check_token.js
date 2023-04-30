async function checkToken() {
    let token = sessionStorage.getItem('token');
    if (token) {
        let header = 'Bearer_' + token;
        try {
            let response = await fetch('/api/v1/check', {
                method: 'GET',
                headers:{
                    'Authorization': header
                }
            });
            if (!response.ok){
                location.href = 'api/v1/auth/login';
            }
        } catch (error) {
            console.error(error);
        }
    } else {
        location.href = 'api/v1/auth/login';
    }
}

checkToken();