role = sessionStorage.getItem('role')
if (role !== 'ROLE_ADMIN'){
    console.log(1)
    location.href = '/api/v1/auth/login';
}