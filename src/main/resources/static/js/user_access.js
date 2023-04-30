token = sessionStorage.getItem('token')
if (token == null){
    location.href = '/api/v1/auth/login';
}
